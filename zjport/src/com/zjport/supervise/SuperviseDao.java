package com.zjport.supervise;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.base.BaseRecords;
import com.common.base.BaseResult;
import com.common.base.dao.impl.BaseDaoDB;
import com.common.base.dao.impl.querycondition.HQL;
import com.common.base.dao.impl.querycondition.ObjectQuery;
import com.common.base.dao.impl.querycondition.SQL;
import com.common.utils.DateTimeUtil;
import com.zjport.supervise.model.*;
import com.zjport.util.GetDataFromXSJ;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository("superviseDao")
public class SuperviseDao extends BaseDaoDB
{

	/**
	 * 认证地址
	 */
	public static final String AUTHENTICATE = "http://10.100.70.2/etl/api/authentication/authenticate?login=apiuser_hzghcdp&password=hzghcdp&format=json";

	/**
	 * 接口地址
	 */
	public static final String PATH = "http://10.100.70.2/etl/api/generaldata/fetch?";

	/**
	 * 访问令牌
	 */
	private static String TOKEN;

	public BaseResult queryAreaCbs(String cbmc, String area, Date start)
	{
		String hql = "from ShipInfo where adddate > :start";

		Map<String, Object> params = new HashMap<>();
		params.put("start", start);

		if (!StringUtils.isEmpty(cbmc))
		{
			hql += " and shipname like :shipname";
			params.put("shipname", "%" + cbmc + "%");
		}

		// 非全省
		if (!StringUtils.isEmpty(area) && !"330000".equals(area))
		{
			hql += " and area = :area";
			params.put("area", area);
		}

		return BaseResult.newResultOK(super.find(new HQL(hql, params)));
	}

	public List<String> queryAllArea()
	{
		return (List<String>) super.find(new SQL("select st_area_code from t_area where st_p_code='330000' order by st_area_code desc")).getData();
	}

	public Facility queryStatus(Facility facility)
	{
		Map<String, Object> xqmap = this.queryCbxq(facility.getName()).getMap();

		// 证书检查
		List<CBCZXX> czxxs = (List<CBCZXX>) xqmap.get("czxx");
		if (CollectionUtils.isEmpty(czxxs))
		{
			facility.setOverdue(1);
		}
		else
		{
			boolean flag = false;
			for (CBCZXX cbczxx : czxxs)
			{
				if (StringUtils.isEmpty(cbczxx.getYxrq())) continue;

				// 证书过期
				if (formatDate(cbczxx.getYxrq()).compareTo(DateTimeUtil.formatCurrDate()) < 0)
				{
					flag = true;
					break;
				}
			}

			if (flag)
			{
				facility.setOverdue(1);
			}
			else
			{
				facility.setOverdue(0);
			}
		}

		// 违章检查
		List<CBWZXX> wzxxs = (List<CBWZXX>) xqmap.get("wzxx");
		if (CollectionUtils.isEmpty(wzxxs))
		{
			facility.setPeccancy(0);
		}
		else
		{
			boolean flag = false;
			for (CBWZXX cbwzxx : wzxxs)
			{
				// 未结案
				if (cbwzxx.getSfja() == null || cbwzxx.getSfja().intValue() == 0)
				{
					flag = true;
					break;
				}
			}

			if (flag)
			{
				facility.setPeccancy(1);
			}
			else
			{
				facility.setPeccancy(0);
			}
		}

		// 缴费检查
		List<CBJFXX> jfxxs = (List<CBJFXX>) xqmap.get("jfxx");
		if (CollectionUtils.isEmpty(jfxxs))
		{
			facility.setArrearage(1);
		}
		else
		{
			boolean flag = false;
			for (CBJFXX cbjfxx : jfxxs)
			{
				// 未作废，且未缴费的
				if ((cbjfxx.getZfbz() == null || cbjfxx.getZfbz() == 0) && (cbjfxx.getSjze() == null || cbjfxx.getYjze().doubleValue() != cbjfxx.getSjze().doubleValue()))
				{
					flag = true;
					break;
				}
			}

			if (flag)
			{
				facility.setArrearage(1);
			}
			else
			{
				facility.setArrearage(0);
			}
		}

		return facility;
	}

	private String formatDate(String date)
	{
		return DateTimeUtil.getDate(new Date(Long.parseLong(date)));
	}

	public List<TWater> queryWaters(String area)
	{
		if (StringUtils.isEmpty(area) || "330000".equals(area))
		{
			return super.find(new ObjectQuery(TWater.class)).getData();
		}

		String hql = "from TWater where area = :area";
		Map<String, Object> params = new HashMap<>();
		params.put("area", area);
		return (List<TWater>) super.find(new HQL(hql, params)).getData();
	}

	public List<TFlow> queryFlows(String area)
	{
		if (StringUtils.isEmpty(area) || "330000".equals(area))
		{
			return super.find(new ObjectQuery(TFlow.class)).getData();
		}

		String hql = "from TFlow where area = :area";
		Map<String, Object> params = new HashMap<>();
		params.put("area", area);
		return (List<TFlow>) super.find(new HQL(hql, params)).getData();
	}

	public BaseResult locationWfs(String area, String type)
	{
		if (StringUtils.isEmpty(area) || "330000".equals(area))
		{
			String hql = "";
			if (!"2".equals(type))
			{
				hql = "from TWater where jd is not null and wd is not null";
			}
			else
			{
				hql = "from TFlow where jd is not null and wd is not null";
			}

			return BaseResult.newResultOK(super.find(new HQL(hql)));
		}

		String hql = "";
		if (!"2".equals(type))
		{
			hql = "from TWater where jd is not null and wd is not null and area = :area";
		}
		else
		{
			hql = "from TFlow where jd is not null and wd is not null and area = :area";
		}

		Map<String, Object> params = new HashMap<>();
		params.put("area", area);

		return BaseResult.newResultOK(super.find(new HQL(hql, params)));
	}

	public BaseResult queryWz(String cbmc)
	{
		ShipInfo shipInfo = (ShipInfo) super.findUnique(new ObjectQuery(ShipInfo.class, "shipname", cbmc, "shipdate", true));
		if (shipInfo == null)
		{
			return BaseResult.newResultFailed("获取位置信息失败");
		}

		return BaseResult.newResultOK(shipInfo);
	}

	public BaseResult queryCbxq(String cbmc)
	{
		Map<String, Object> xqmap = new HashMap<String, Object>();

		// 船舶基本信息
		String cbjbxx = findData("CDP_JC_CBJBXX", cbmc);

		// 返回成功码
		if (JSON.parseObject(cbjbxx).getIntValue("returnCode") == 0)
		{
			String jsonstr = JSON.parseObject(cbjbxx).getJSONObject("record").toJSONString();
			if (StringUtils.isEmpty(jsonstr) || "{}".equals(jsonstr))
			{
				return BaseResult.newResultFailed("query cbjbxx failed");
			}

			CBJBXX jbxx = JSON.parseObject(jsonstr, CBJBXX.class);
			xqmap.put("jbxx", jbxx);
		}
		else
		{
			return BaseResult.newResultFailed("query cbjbxx failed");
		}

		// 船舶证书信息
		String cbczxx = findData("CDP_ZH_CBCZXX", cbmc);

		// 返回成功码
		if (JSON.parseObject(cbczxx).getIntValue("returnCode") == 0)
		{
			List<CBCZXX> czxxs = JSON.parseArray(JSON.parseObject(cbczxx).getJSONArray("recordset").toJSONString(), CBCZXX.class);
			if (czxxs != null && czxxs.size() > 0)
			{
				xqmap.put("czxx", czxxs);
			}
			else
			{
				xqmap.put("czxx", null);
			}
		}
		else
		{
			xqmap.put("czxx", null);
		}

		// 船舶缴费信息
		String cbjfxx = findData("CDP_JZ_CBJFXX", cbmc);

		// 返回成功码
		if (JSON.parseObject(cbjfxx).getIntValue("returnCode") == 0)
		{
			List<CBJFXX> jfxxs = JSON.parseArray(JSON.parseObject(cbjfxx).getJSONArray("recordset").toJSONString(), CBJFXX.class);
			if (jfxxs != null && jfxxs.size() > 0)
			{
				xqmap.put("jfxx", jfxxs);
			}
			else
			{
				xqmap.put("jfxx", null);
			}
		}
		else
		{
			xqmap.put("jfxx", null);
		}

		// 船舶检验信息
		String cbjyxx = findData("CDP_CJ_CBJYXX", cbmc);

		// 返回成功码
		if (JSON.parseObject(cbjyxx).getIntValue("returnCode") == 0)
		{
			List<CBJYXX> jyxxs = JSON.parseArray(JSON.parseObject(cbjyxx).getJSONArray("recordset").toJSONString(), CBJYXX.class);
			if (jyxxs != null && jyxxs.size() > 0)
			{
				xqmap.put("jyxx", jyxxs);
			}
			else
			{
				xqmap.put("jyxx", null);
			}
		}
		else
		{
			xqmap.put("jyxx", null);
		}

		// 船舶违章信息
		String cbwzxx = findData("CDP_CF_CBWZCFXX", cbmc);

		// 返回成功码
		if (JSON.parseObject(cbwzxx).getIntValue("returnCode") == 0)
		{
			List<CBWZXX> wzxxs = JSON.parseArray(JSON.parseObject(cbwzxx).getJSONArray("recordset").toJSONString(), CBWZXX.class);
			if (wzxxs != null && wzxxs.size() > 0)
			{
				xqmap.put("wzxx", wzxxs);
			}
			else
			{
				xqmap.put("wzxx", null);
			}
		}
		else
		{
			xqmap.put("wzxx", null);
		}

		return BaseResult.newResultOK(xqmap);
	}

	public CBJBXX queryCbjbxx(String cm)
	{
		// 船舶基本信息
		String cbjbxx = findData("CDP_JC_CBJBXX", cm);

		// 返回成功码
		if (JSON.parseObject(cbjbxx).getIntValue("returnCode") != 0)
		{
			return null;
		}

		String jsonstr = JSON.parseObject(cbjbxx).getJSONObject("record").toJSONString();
		if (StringUtils.isEmpty(jsonstr) || "{}".equals(jsonstr))
		{
			return null;
		}

		return JSON.parseObject(jsonstr, CBJBXX.class);
	}

	public List<ShipInfo> queryShipInfo()
	{
		return (List<ShipInfo>) super.find(new HQL("from ShipInfo where shiptype is null")).getData();
	}

	public void bcShiptype(String shipname, String shiptype)
	{
		String sql = "update shipinfo set shiptype=:shiptype where shipname=:shipname";
		Map<String, Object> param = new HashMap<>();
		param.put("shiptype", shiptype);
		param.put("shipname", shipname);
		super.update(new SQL(sql, param));
	}

	public XQCB queryXqcb(String areacode, Date start, Date end)
	{
		String countSql = "select count(1) from shipinfo where adddate>:start and adddate<:end and area=:area";
		String wxSql = "select count(1) from shipinfo where shiptype='2' and adddate>:start and adddate<:end and area=:area";
		String jzxSql = "select count(1) from shipinfo where shiptype='3' and adddate>:start and adddate<:end and area=:area";

		Map<String, Object> param = new HashMap<>();
		param.put("start", start);
		param.put("end", end);
		param.put("area",areacode);

		long count = super.count(new SQL(countSql, param));
		long wx = super.count(new SQL(wxSql, param));
		long jzx = super.count(new SQL(jzxSql, param));

		// 区域表翻译区域名
		String areaname = (String) super.findUnique(new SQL("select ST_AREA_NAME from t_area where ST_AREA_CODE='"+areacode+"'"));
		return new XQCB(areacode, areaname, count, wx, jzx);
	}

	public List<ShipInfo> queryWxcbs()
	{
		Calendar ca = Calendar.getInstance();
		ca.add(Calendar.MINUTE, -10);
		Date start = ca.getTime();

		String hql = "from ShipInfo where shiptype='2' and adddate>:start";

		Map<String, Object> param = new HashMap<>();
		param.put("start", start);

		return (List<ShipInfo>) super.find(new HQL(hql,param)).getData();
	}

	public boolean hasWarn(String cbmc, String area, int type)
	{
		String hql = "from TWarnLog where type=:type and zwcm=:zwcm order by createtime desc";
		Map<String, Object> param = new HashMap<>();
		param.put("type", type);
		param.put("zwcm", cbmc);

		List<TWarnLog> warnLogs = (List<TWarnLog>) super.find(new HQL(hql, param)).getData();
		if (warnLogs == null || warnLogs.size() == 0)
		{
			return false;
		}

		if (area.equals(warnLogs.get(0).getArea()))
		{
			return true;
		}

		return false;
	}

	public List<ShipInfo> queryAllcbs(Date start, boolean flag)
	{
		String hql = "from ShipInfo where adddate>:start";
		if (flag)
		{
			hql += " and shiptype <> '4'";
		}

		Map<String, Object> param = new HashMap<>();
		param.put("start", start);

		return (List<ShipInfo>) super.find(new HQL(hql,param)).getData();
	}

	public BaseResult queryWarnList(String start, String end, String area, String name, Integer page, Integer bjlx)
	{
		if (page == null)
		{
			page =1;
		}

		if (bjlx == null)
		{
			bjlx =1;
		}

		Map<String, Object> param = new HashMap<>();

		String hql = "from TWarnLog where type=:type";
		param.put("type", bjlx);

		if (!StringUtils.isEmpty(start))
		{
			hql +=" and createtime > :start";
			param.put("start", DateTimeUtil.getDateByStringFmt(start + ":00"));
		}

		if (!StringUtils.isEmpty(end))
		{
			hql +=" and createtime < :end";
			param.put("end", DateTimeUtil.getDateByStringFmt(end + ":59"));
		}

		if (!StringUtils.isEmpty(area) && !"330000".equals(area))
		{
			hql +=" and area = :area";
			param.put("area", area);
		}

		if (!StringUtils.isEmpty(name))
		{
			hql +=" and zwcm like :zwcm";
			param.put("zwcm", "%" + name + "%");
		}

		hql += " order by createtime desc";

		HQL hql1 = new HQL(hql, param);
		hql1.setPaging(page, 10);
		return BaseResult.newResultOK(super.find(hql1));
	}

	public BaseResult querySets(int page)
	{
		return BaseResult.newResultOK(super.find(new ObjectQuery(TWarnSet.class, "id", false, page, 10)));
	}

	public BaseResult switchWarn(Integer id)
	{
		TWarnSet warnSet = (TWarnSet) super.findUnique(new ObjectQuery(TWarnSet.class, "id", id));
		if (warnSet != null)
		{
			warnSet.setStatus(warnSet.getStatus() == 1 ? 0 : 1);
			warnSet.setUpdatetime(Timestamp.valueOf(DateTimeUtil.formatCurrDate()));
			super.saveOrUpdate(warnSet);

			return BaseResult.newResultOK();
		}

		return BaseResult.newResultFailed();
	}

	public BaseResult queryAreas()
	{
		String hql = "from TArea where stPCode='0' or stPCode='330000' order by stAreaCode";
		return BaseResult.newResultOK(super.find(new HQL(hql)));
	}

	public boolean checkOff(int warnType)
	{
		TWarnSet warnSet = (TWarnSet) super.findUnique(new ObjectQuery(TWarnSet.class, "id", warnType));
		if (warnSet != null && warnSet.getStatus() == 1)
		{
			return false;
		}

		return true;
	}

	/**
	 * 查询船舶相关数据
	 *
	 * @param serviceCode
	 * @param cm
	 * @return
	 */
	private String findData(String serviceCode, String cm)
	{
		if (StringUtils.isEmpty(TOKEN))
		{
			authentication();
		}

		try
		{
			cm = URLEncoder.encode(cm, "utf-8");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		String realPath = PATH + "serviceCode=" + serviceCode + "&name=" + cm + "&format=json&token=" + TOKEN;
		String result = GetDataFromXSJ.queryData(realPath);
		if (checkCode(result))
		{
			authentication();

			realPath = PATH + "serviceCode=" + serviceCode + "&name=" + cm + "&format=json&token=" + TOKEN;
			result = GetDataFromXSJ.queryData(realPath);
		}

		return result;
	}

	/**
	 *  获取令牌
	 */
	private void authentication()
	{
		String rtn = GetDataFromXSJ.queryData(AUTHENTICATE);
		JSONObject jsonObject = JSON.parseObject(rtn);
		int code = jsonObject.getIntValue("returnCode");
		if (code == 0)
		{
			jsonObject = jsonObject.getJSONObject("record");
			String token = jsonObject.getString("token");
			if (!StringUtils.isEmpty(token))
			{
				TOKEN = token;
			}
		}
	}

	/**
	 * 判断是否令牌过期
	 */
	private boolean checkCode(String str)
	{
		JSONObject jsonObject = JSON.parseObject(str);
		return jsonObject.getIntValue("returnCode") == 10;
	}

	public BaseResult cbList(String area, double minLon, double maxLon, double minLat, double maxLat, Date start)
	{
		String hql = "from ShipInfo where adddate >= :start and longitude between :minLon and :maxLon and latitude between :minLat and :maxLat";

		Map<String, Object> params = new HashMap<>();
		params.put("start", start);
		params.put("minLon", minLon);
		params.put("maxLon", maxLon);
		params.put("minLat", minLat);
		params.put("maxLat", maxLat);

		if (!StringUtils.isEmpty(area) && !"330000".equals(area))
		{
			hql += " and area = :area";
			params.put("area", area);
		}

		return BaseResult.newResultOK(super.find(new HQL(hql, params)));
	}

	public BaseResult queryLscb(double minLon, double maxLon, double minLat, double maxLat, String start, String end)
	{
		String hql = "from ShipDetail where shipdate between :start and :end and longitude between :minLon and :maxLon and latitude between :minLat and :maxLat group by shipname";

		Map<String, Object> params = new HashMap<>();
		params.put("minLon", minLon);
		params.put("maxLon", maxLon);
		params.put("minLat", minLat);
		params.put("maxLat", maxLat);

		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			params.put("start", sdf.parse(start));
			params.put("end", sdf.parse(end));
		}
		catch (Exception e)
		{
			return BaseResult.newResultFailed("参数格式错误");
		}

		BaseRecords records = super.find(new HQL(hql, params));
		if (records.getTotal() == 0)
		{
			return BaseResult.newResultFailed("无数据");
		}

		return BaseResult.newResultOK(records);
	}

	public long countWf(String area, String type)
	{
		if (!"2".equals(type))
		{
			return super.count(new ObjectQuery(TWater.class, "area", area));
		}

		return super.count(new ObjectQuery(TFlow.class, "area", area));
	}

	public BaseResult queryYccbs(String area, Integer type, Integer page)
	{
		String hql = "";
		if (type == 1)
		{
			hql = "from ShipInfo where shiptype = '2' and  adddate > :start";
		}
		else if (type == 3)
		{
			hql = "from ShipInfo where adddate > :start and peccancy = 1";
		}
		else if (type == 4)
		{
			hql = "from ShipInfo where adddate > :start and overdue = 1";
		}
		else if (type == 5)
		{
			hql = "from ShipInfo where adddate > :start and arrearage = 1";
		}

		Calendar ca = Calendar.getInstance();
		ca.add(Calendar.MINUTE, -30);
		Date start = ca.getTime();

		Map<String, Object> params = new HashMap<>();
		params.put("start", start);

		if (!StringUtils.isEmpty(area) && !"330000".equals(area))
		{
			hql +=" and area = :area";
			params.put("area", area);
		}

		HQL hql1= new HQL(hql, params);
		hql1.setPaging(page, 10);
		return BaseResult.newResultOK(super.find(hql1));
	}

	public BaseRecords queryQls(double minLon, double maxLon, double minLat, double maxLat)
	{
		String hql = "from CHdQl where jd between :minLon and :maxLon and wd between :minLat and :maxLat";

		Map<String, Object> params = new HashMap<>();
		params.put("minLon", minLon);
		params.put("maxLon", maxLon);
		params.put("minLat", minLat);
		params.put("maxLat", maxLat);

		return super.find(new HQL(hql, params));
	}

	public BaseResult queryHistory(String cbmc, String start, String end)
	{
		String hql = "from ShipDetail where shipdate between :start and :end and shipname = :shipname";
		Map<String, Object> params = new HashMap<>();
		params.put("shipname", cbmc);
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			params.put("start", sdf.parse(start));
			params.put("end", sdf.parse(end));
		}
		catch (Exception e)
		{
			return BaseResult.newResultFailed("参数格式错误");
		}

		BaseRecords records = super.find(new HQL(hql, params));
		if (records.getTotal() == 0)
		{
			return BaseResult.newResultFailed("无数据");
		}

		// 时间排序
		Collections.sort((List<ShipDetail>) records.getData());
		return BaseResult.newResultOK(records);
	}
}
