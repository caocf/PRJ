package com.zjport.supervise;

import com.common.base.BaseResult;
import com.zjport.information.service.InformationService;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RequestMapping("/supervise")
@Controller
public class SuperviseAction
{

	@Resource(name="superviseService")
	SuperviseService superviseService;

	@Resource(name = "inforService")
	InformationService inforService;

	/**
	 * 用户所在辖区船舶列表
	 *
	 * @param area 辖区编码
	 */
	@RequestMapping("/areaCbs")
	@ResponseBody
	public BaseResult queryAreaCbs(String area)
	{
		return this.superviseService.queryAreaCbs(null, area);
	}

	/**
	 * 用户所在辖区可视范围内的船舶列表
	 *
	 * @param minLon
	 * @param maxLon
	 * @param minLat
	 * @param maxLat
	 * @param area  辖区编码
	 */
	@RequestMapping("/cblist")
	@ResponseBody
	public BaseResult cbList(double minLon, double maxLon, double minLat, double maxLat, String area)
	{
		return this.superviseService.cbList(area, minLon, maxLon, minLat, maxLat);
	}

	/**
	 * 根据船名模糊查询船舶
	 *
	 * @param cbmc 船舶名称
	 */
	@RequestMapping("/queryCbs")
	@ResponseBody
	public BaseResult queryCbs(String cbmc, String area)
	{
		return this.superviseService.queryAreaCbs(cbmc, area);
	}

	/**
	 * 船舶实时信息
	 *
	 * @param cbmc
	 */
	@RequestMapping("/cbXx")
	@ResponseBody
	public BaseResult queryCbxx(String cbmc)
	{
		return this.superviseService.queryCbxx(cbmc);
	}

	/**
	 * 船舶详细信息
	 *
	 * @param cbmc
	 */
	@RequestMapping("/cbXq")
	@ResponseBody
	public BaseResult queryCbxq(String cbmc)
	{
		return this.superviseService.queryCbxq(cbmc);
	}

	/**
	 * 船舶历史轨迹查询
	 *
	 * @param cbmc 船舶名称
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 */
	@RequestMapping("/trajectory")
	@ResponseBody
	public BaseResult historyPath(String cbmc, String startTime, String endTime)
	{
		return this.superviseService.queryHistory(cbmc, startTime, endTime);
	}

	/**
	 * 报警信息
	 *
	 * @param area 区域
	 * @param bjlx 报警类型
	 * @param start 开始时间
	 * @param end 结束时间
	 * @param name 报警对象
	 * @param page 页码
	 */
	@RequestMapping("/warnlist")
	@ResponseBody
	public BaseResult queryWarnList(String area, Integer bjlx, String start, String end, String name, Integer page)
	{
		return this.superviseService.queryWarnList(start, end, area, name, page, bjlx);
	}

	/**
	 * 船舶概况
	 *
	 * @param area 区域
	 */
	@RequestMapping("/queryAreas")
	@ResponseBody
	public BaseResult queryAreas(String area)
	{
		// 全省
		if ("330000".equals(area))
		{
			return this.superviseService.queryAllArea();
		}

		return this.superviseService.querySingleArea(area);
	}

	/**
	 * 水位流量（树结构）
	 *
	 * @param area 区域
	 * @param type 数据类型 1 水位 2 流量
	 */
	@RequestMapping("/wftree")
	@ResponseBody
	public BaseResult wfTree(String area, String type)
	{
		return this.superviseService.loadWftree(area, type);
	}

	/**
	 * 水位流量（地图标点）
	 *
	 * @param area 区域
	 * @param type 数据类型 1 水位 2 流量
	 */
	@RequestMapping("/wflist")
	@ResponseBody
	public BaseResult wfList(String area, String type)
	{
		return this.superviseService.queryWfs(area, type);
	}

	/**
	 * 水位流量详情
	 *
	 * @param id 记录id
	 * @param type 数据类型 1 水位 2 流量
	 */
	@RequestMapping("/wfdetail")
	@ResponseBody
	public BaseResult wfDetail(String id, String type)
	{
		return this.superviseService.wfDetail(id, type);
	}

	/**
	 * 船舶当前位置
	 *
	 * @param cbmc 船名
	 */
	@RequestMapping("/cbwz")
	@ResponseBody
	public BaseResult queryWz(String cbmc)
	{
		return this.superviseService.queryWz(cbmc);
	}

	@RequestMapping("/cbmc")
	@ResponseBody
	public BaseResult queryLx(HttpServletRequest request, String cbmc)
	{
		request.getSession().setAttribute("cbmc", cbmc);
		return BaseResult.newResultOK();
	}

	@RequestMapping("/warnset")
	@ResponseBody
	public BaseResult querySets(Integer page)
	{
		return this.superviseService.querySets(page);
	}

	@RequestMapping("/switchwarn")
	@ResponseBody
	public BaseResult switchWarn(Integer id)
	{
		return this.superviseService.switchWarn(id);
	}

	/**
	 * 获取所有市级以上区域
	 */
	@RequestMapping("/areas")
	@ResponseBody
	public BaseResult queryAreas()
	{
		return this.superviseService.queryAreas();
	}

	/**
	 * 一键巡航 概况
	 */
	@RequestMapping("/yjxh")
	@ResponseBody
	public BaseResult yjxh(String area)
	{
		return this.superviseService.yjxh(area);
	}

	/**
	 * 一键巡航 船舶列表
	 *
	 * @param area
	 * @param type 1 危险品船 2 黑名单船 3 违章船舶 4 证书异常船舶 5 规费未缴清船舶 6 超载船舶 7 违停船舶
	 * @param page 分页
	 */
	@RequestMapping("/yccb")
	@ResponseBody
	public BaseResult queryYccbs(String area, Integer type, Integer page)
	{
		return this.superviseService.queryYccbs(area, type, page);
	}

	/**
	 * 船舶密度
	 *
	 * @param lon  经度
	 * @param lat  纬度
	 * @param distance 距离（单位：公里）
	 */
	@RequestMapping("/cbmd")
	@ResponseBody
	public BaseResult cbmd(double lon, double lat, Integer distance)
	{
		// 默认1公里范围
		double step = 0.005;
		double area = 1.0;

		// 设定范围
		if (distance != null)
		{
			step = step * distance;
			area = distance * distance;
		}

		return this.superviseService.cbmd(lon-step, lon+step, lat-step, lat+step, area);
	}

	/**
	 * 某时间段内经过矩形范围内的船舶列表
	 */
	@RequestMapping("/lscb")
	@ResponseBody
	public BaseResult lscb(double lon, double lat, Integer distance, String startTime, String endTime)
	{
		// 默认1公里范围
		double step = 0.005;

		// 设定范围
		if (distance != null)
		{
			step = step * distance;
		}

		return this.superviseService.lscb(lon-step, lon+step, lat-step, lat+step, startTime, endTime);
	}

	/**
	 * 船舶跟踪
	 *
	 * @param cbmc
	 */
	@RequestMapping("/cbgz")
	@ResponseBody
	public BaseResult cbgz(String cbmc)
	{
		return this.superviseService.cbgz(cbmc);
	}

	/**
	 * 船舶预警
	 *
	 * @param cbmc 船舶名称
	 * @param speed 航速
	 * @param qlmc 桥梁名称
	 * @param phone 手机号码
	 */
	@RequestMapping("/cbyj")
	@ResponseBody
	public BaseResult cbyj(String cbmc, String speed, String qlmc, String phone)
	{
		String message = "";
		if (StringUtils.isEmpty(qlmc))
		{
			message = "船舶【"+cbmc+"】正在以"+speed+"海里/每小时的速度行驶。";
		}
		else
		{
			message = "船舶【"+cbmc+"】正在以"+speed+"海里/每小时的速度行驶, 预计30分钟内抵达"+qlmc;
		}

		String rtn = "success";
		try
		{
			rtn = this.inforService.sendMessage(phone, message, 0);
		}
		catch (Exception e) {
			rtn = "fail";
		}

		if ("success".equals(rtn))
		{
			BaseResult.newResultOK();
		}

		return BaseResult.newResultFailed();
	}

	/**
	 * 流量点列表
	 *
	 */
	@RequestMapping("/sites")
	@ResponseBody
	public BaseResult querySites()
	{
		return this.superviseService.querySites();
	}

	/**
	 * 流量预测
	 *
	 * @param site
	 *
	 */
	@RequestMapping("/llyc")
	@ResponseBody
	public BaseResult ycFlow(String site)
	{
		return this.superviseService.ycFlow(site);
	}
}
