package com.zjport.supervise;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.base.BaseRecords;
import com.common.base.BaseResult;
import com.common.base.dao.impl.querycondition.ObjectQuery;
import com.common.base.service.BaseService;
import com.common.utils.DateTimeUtil;
import com.zjport.model.CHdQl;
import com.zjport.model.TArea;
import com.zjport.supervise.model.*;
import com.zjport.util.GetDataFromXSJ;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.*;


@Service("superviseService")
public class SuperviseService extends BaseService
{

	@Resource(name="superviseDao")
	SuperviseDao superviseDao;

	public BaseResult queryAreaCbs(String cbmc, String area)
	{
		Calendar ca = Calendar.getInstance();
		ca.add(Calendar.MINUTE, -10);
		Date start = ca.getTime();
		return this.superviseDao.queryAreaCbs(cbmc, area, start);
	}

	public BaseResult queryAllArea()
	{
		List<XQCB> xqcbs = new ArrayList<>();

		Calendar ca = Calendar.getInstance();
		Date end = ca.getTime();
		ca.add(Calendar.MINUTE, -10);
		Date start = ca.getTime();

		List<String> areas = this.superviseDao.queryAllArea();
		for (String area : areas)
		{
			xqcbs.add(this.superviseDao.queryXqcb(area, start, end));
		}

		long scount =0;
		long swx=0;
		long sjzx=0;
		for (XQCB xqcb : xqcbs)
		{
			scount += xqcb.getCount();
			swx += xqcb.getWx();
			sjzx += xqcb.getJzx();
		}

		xqcbs.add(new XQCB("330000", "浙江省", scount, swx, sjzx));
		return BaseResult.newResultOK(xqcbs);
	}

	public BaseResult querySingleArea(String area)
	{
		Calendar ca = Calendar.getInstance();
		Date end = ca.getTime();
		ca.add(Calendar.MINUTE, -10);
		Date start = ca.getTime();
		return BaseResult.newResultOK(this.superviseDao.queryXqcb(area, start, end));
	}

	public BaseResult queryCbxx(String cbmc)
	{
		// 船舶基本信息
		CBJBXX cbjbxx = this.superviseDao.queryCbjbxx(cbmc);

		// 船舶实时信息
		ShipInfo shipInfo = (ShipInfo) this.superviseDao.findUnique(new ObjectQuery(ShipInfo.class, "shipname", cbmc));

		Map<String, Object> map = new HashMap<>();
		map.put("jbxx", cbjbxx);
		map.put("ssxx", shipInfo);
		return BaseResult.newResultOK(map);
	}

	public BaseResult queryCbxq(String cbmc)
	{
		return this.superviseDao.queryCbxq(cbmc);
	}

	public BaseResult loadWftree(String area, String type)
	{
		List<WfTree> wfTrees = new ArrayList<>();

		if (!StringUtils.isEmpty(area) && !"330000".equals(area))
		{
			TArea tArea = (TArea) this.superviseDao.findUnique(new ObjectQuery(TArea.class, "stAreaCode", area));
			wfTrees.add(new WfTree(area, tArea.getStAreaName(), "0", 1, this.superviseDao.countWf(area, type)));
		}
		else
		{
			List<TArea> areas = (List<TArea>) this.superviseDao.queryAreas().getRecords().getData();
			for (TArea tArea : areas)
			{
				if (!"330000".equals(tArea.getStAreaCode()))
				{
					wfTrees.add(new WfTree(tArea.getStAreaCode(), tArea.getStAreaName(), "0", 1, this.superviseDao.countWf(tArea.getStAreaCode(), type)));
				}
			}
		}

		// 水位
		if (!"2".equals(type))
		{
			List<TWater> tWaters = this.superviseDao.queryWaters(area);
			if (tWaters != null && tWaters.size() > 0)
			{
				for (TWater tWater : tWaters)
				{
					wfTrees.add(new WfTree(tWater.getId().toString(), tWater.getName(), tWater.getArea(), 2, 0));
				}
			}
		}
		else
		{
			List<TFlow> tFlows = this.superviseDao.queryFlows(area);
			if (tFlows != null && tFlows.size() > 0)
			{
				for (TFlow tFlow : tFlows)
				{
					wfTrees.add(new WfTree(tFlow.getId().toString(), tFlow.getName(), tFlow.getArea(), 3, 0));
				}
			}
		}

		return BaseResult.newResultOK(wfTrees);
	}

	public BaseResult queryWfs(String area, String type)
	{
		return this.superviseDao.locationWfs(area, type);
	}

	public BaseResult wfDetail(String id, String type)
	{
		if (StringUtils.isEmpty(id))
		{
			return BaseResult.newResultFailed();
		}

		if (!"2".equals(type))
		{
			return BaseResult.newResultOK(this.superviseDao.findUnique(new ObjectQuery(TWater.class, "id", id)));
		}

		return queryFdetail(id);
	}

	private BaseResult queryFdetail(String id)
	{
		TFlow tFlow = (TFlow) this.superviseDao.findUnique(new ObjectQuery(TFlow.class, "id", id));
		if (tFlow == null)
		{
			return BaseResult.newResultFailed();
		}

		// 获取实时流量数据
		String start = DateTimeUtil.formatCurrDate("yyyy-MM-dd 00:00:00");
		String end = DateTimeUtil.formatCurrDate();
		tFlow.setGxsj(end);

		String url = "";
		try
		{
			url = "http://172.21.25.53/realMonitor/querySiteLl?siteId=" + id + "&start=" + URLEncoder.encode(start, "UTF8") + "&end=" + URLEncoder.encode(end, "UTF8");
		}
		catch (Exception e)
		{
			return BaseResult.newResultFailed();
		}

		String jsondata = GetDataFromXSJ.queryData(url);
		JSONObject jsonobj = JSONObject.parseObject(jsondata);
		JSONObject mapjson = jsonobj.getJSONObject("map");
		String cbup = mapjson.getString("up");
		String cbdown = mapjson.getString("down");
		String cbtotal = mapjson.getString("total");
		tFlow.setUp(Integer.parseInt(cbup));
		tFlow.setDown(Integer.parseInt(cbdown));
		tFlow.setZll(Integer.parseInt(cbtotal));
		return BaseResult.newResultOK(tFlow);
	}

	public List<ShipInfo> queryShipInfo()
	{
		return this.superviseDao.queryShipInfo();
	}

	public void bcShiptype(String shipname, String shiptype)
	{
		this.superviseDao.bcShiptype(shipname, shiptype);
	}

	public void wxcbWarn()
	{
		boolean flag = this.superviseDao.checkOff(1);
		if (flag) return;

		List<ShipInfo> shipInfos = this.superviseDao.queryWxcbs();
		for (ShipInfo shipInfo : shipInfos)
		{
			// 该船舶最后一次危险品报警不在该区域
			if (!this.superviseDao.hasWarn(shipInfo.getShipname(), shipInfo.getArea(), 1))
			{
				TWarnLog warnLog = new TWarnLog(shipInfo.getArea(), shipInfo.getShipname(), 1, Timestamp.valueOf(DateTimeUtil.formatCurrDate()), 0, 0, 0);
				this.superviseDao.save(warnLog);
			}
		}
	}

	public BaseResult queryWz(String cbmc)
	{
		return this.superviseDao.queryWz(cbmc);
	}

	public void statusWarn()
	{
		boolean flag = this.superviseDao.checkOff(2);
		if (flag) return;

		Calendar ca = Calendar.getInstance();
		ca.add(Calendar.MINUTE, -10);
		Date start = ca.getTime();
		List<ShipInfo> shipInfos = this.superviseDao.queryAllcbs(start, true);
		for (ShipInfo shipInfo : shipInfos)
		{
			if (this.superviseDao.queryCbjbxx(shipInfo.getShipname()) == null)
			{
				continue;
			}

			Facility facility = new Facility(shipInfo.getShipname());
			facility = this.superviseDao.queryStatus(facility);

			// 状态异常
			if (facility.getArrearage() == 1 || facility.getPeccancy() == 1 || facility.getOverdue() == 1)
			{
				// 最近一次的状态异常报警不在该区域
				if (!this.superviseDao.hasWarn(shipInfo.getShipname(), shipInfo.getArea(), 2))
				{
					TWarnLog warnLog = new TWarnLog(shipInfo.getArea(), shipInfo.getShipname(), 2, Timestamp.valueOf(DateTimeUtil.formatCurrDate()), facility.getPeccancy(), facility.getOverdue(), facility.getArrearage());
					this.superviseDao.save(warnLog);
				}
			}
		}
	}

	public void bcShipInfo()
	{
		Calendar ca = Calendar.getInstance();
		ca.add(Calendar.HOUR, -2);
		Date start = ca.getTime();
		List<ShipInfo> shipInfos = this.superviseDao.queryAllcbs(start, false);
		for (ShipInfo shipInfo : shipInfos)
		{
			CBJBXX cbjbxx = this.superviseDao.queryCbjbxx(shipInfo.getShipname());
			if (cbjbxx == null)
			{
				shipInfo.setShiptype("4");
			}
			else
			{
				String cblx = cbjbxx.getCblx();
				shipInfo.setCblx(cblx);

				if (!StringUtils.isEmpty(cblx))
				{
					// 危险品船
					if (cblx.contains("油") || cblx.contains("危") || cblx.contains("化学"))
					{
						shipInfo.setShiptype("2");
					}

					// 集装箱船
					else if (cblx == "集装箱船")
					{
						shipInfo.setShiptype("3");
					}

					// 普通船舶
					else
					{
						shipInfo.setShiptype("1");
					}
				}

				// 船舶类型属性为空的，默认为普通船舶
				else
				{
					shipInfo.setShiptype("1");
				}

				Facility facility = new Facility(shipInfo.getShipname());
				facility = this.superviseDao.queryStatus(facility);

				shipInfo.setPeccancy(facility.getPeccancy());
				shipInfo.setOverdue(facility.getOverdue());
				shipInfo.setArrearage(facility.getArrearage());
			}

			this.superviseDao.saveOrUpdate(shipInfo);
		}
	}

	public BaseResult queryWarnList(String start, String end, String area, String name, Integer page, Integer bjlx)
	{
		return this.superviseDao.queryWarnList(start, end, area, name, page, bjlx);
	}

	public BaseResult querySets(Integer page)
	{
		if (page == null)
		{
			page = 1;
		}
		return this.superviseDao.querySets(page);
	}

	public BaseResult switchWarn(Integer id)
	{
		if (id == null)
		{
			return BaseResult.newResultFailed();
		}

		return this.superviseDao.switchWarn(id);
	}

	public BaseResult queryAreas()
	{
		BaseResult result = this.superviseDao.queryAreas();

		// 区域中心点
		double[][] temp = {{120.180904,30.096037},{119.491261,29.972158},{121.554708,29.700067},{120.721453,27.843362},{120.865827,30.666801},{119.867678,30.794084},{120.648093,29.823804},{119.985853,29.165704},{118.789557,28.925483},{122.276958,30.335135},{121.267475,28.821588},{119.596522,28.225183}};
		List<double[]> centers = Arrays.asList(temp);
		result.setObj(centers);
		return result;
	}

	public BaseResult cbList(String area, double minLon, double maxLon, double minLat, double maxLat)
	{
		Calendar ca = Calendar.getInstance();
		ca.add(Calendar.MINUTE, -10);
		Date start = ca.getTime();

		return this.superviseDao.cbList(area, minLon, maxLon, minLat, maxLat, start);
	}

	public BaseResult yjxh(String area)
	{
		Calendar ca = Calendar.getInstance();
		ca.add(Calendar.MINUTE, -30);
		Date start = ca.getTime();

		int wx = 0; //危险品船舶数
		int zs = 0; //证书过期
		int jf = 0; //规费未缴清
		int wz = 0; //违章

		List<ShipInfo> shipInfos = (List<ShipInfo>) this.superviseDao.queryAreaCbs(null, area, start).getRecords().getData();
		for (ShipInfo shipInfo : shipInfos)
		{
			if ("2".equals(shipInfo.getShiptype()))
			{
				wx++;
			}

			if (shipInfo.getPeccancy() == 1)
			{
				wz++;
			}

			if (shipInfo.getOverdue() == 1)
			{
				zs++;
			}

			if (shipInfo.getArrearage() == 1)
			{
				jf++;
			}
		}

		Map<String, Object> map = new HashMap<>();
		map.put("wxcnt", wx);
		map.put("hmdcnt", null);
		map.put("wzcnt", wz);
		map.put("zscnt", zs);
		map.put("jfcnt", jf);
		map.put("czcnt", null);
		map.put("wtcnt", null);

		return BaseResult.newResultOK(map);
	}

	public BaseResult queryYccbs(String area, Integer type, Integer page)
	{
		if (type == null)
		{
			type = 1;
		}

		// 黑名单船舶
		if (type == 2 || type == 6 || type == 7)
		{
			return BaseResult.newResultFailed("暂无数据");
		}

		return this.superviseDao.queryYccbs(area, type, page);
	}

	public BaseResult lscb(double minLon, double maxLon, double minLat, double maxLat, String startTime, String endTime)
	{
		return this.superviseDao.queryLscb(minLon, maxLon, minLat, maxLat, startTime, endTime);
	}

	public BaseResult cbmd(double minLon, double maxLon, double minLat, double maxLat, double area)
	{
		Calendar ca = Calendar.getInstance();
		ca.add(Calendar.MINUTE, -15);
		Date start = ca.getTime();

		// 矩形范围内的船舶
		BaseResult result = this.superviseDao.cbList("", minLon, maxLon, minLat, maxLat, start);
		return BaseResult.newResultOK(result.getRecords().getTotal() / area);
	}

	public BaseResult cbgz(String cbmc)
	{
		// 船舶实时信息
		ShipInfo shipInfo = (ShipInfo) this.superviseDao.findUnique(new ObjectQuery(ShipInfo.class, "shipname", cbmc));

		Map<String, Object> map = new HashMap<>();
		map.put("ssxx", shipInfo);

		// 船舶半小时行驶距离（公里）
		double distance = shipInfo.getSpeed() * 0.5;

		// 每0.5km相差约0.005
		double step = 0.005 * (distance/0.5);

		double minLon = shipInfo.getLongitude() - step;
		double maxLon = shipInfo.getLongitude() + step;
		double minLat = shipInfo.getLatitude() - step;
		double maxLat = shipInfo.getLatitude() + step;

		BaseRecords records = this.superviseDao.queryQls(minLon, maxLon, minLat, maxLat);
		if (records.getTotal() > 0)
		{
			CHdQl ql = (CHdQl) records.getData().get(0);
			map.put("ql", ql.getMc());
		}
		else
		{
			map.put("ql", "");
		}

		return BaseResult.newResultOK(map);
	}

	public BaseResult queryHistory(String cbmc, String start, String end)
	{
		return this.superviseDao.queryHistory(cbmc, start, end);
	}

	public BaseResult querySites()
	{
		return BaseResult.newResultOK(this.superviseDao.find(new ObjectQuery(TFlow.class, "area", "330500", "id", false)));
	}

	public BaseResult ycFlow(String site)
	{
		String url = "http://172.21.25.53/forecast/ycData?site=" + site;
		String rtn = GetDataFromXSJ.queryData(url);
		List<YcData> data = JSON.parseArray(JSONObject.parseObject(rtn).getJSONArray("list2").toJSONString(), YcData.class);
		return BaseResult.newResultOK(data);
	}
}
