package com.huzhouport.electricreport.action;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huzhouport.common.action.BaseAction;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.electricreport.service.ElectricReportService;
import com.huzhouport.electricreport.model.Boatman;
import com.huzhouport.electricreport.model.ElectricArrival;
import com.huzhouport.electricreport.model.ElectricReportNew;
import com.huzhouport.integratedquery.action.Webservice;
import com.opensymphony.xwork2.ModelDriven;

public class ElectricReportAction extends BaseAction implements
		ModelDriven<ElectricReportNew> {
	private static final long serialVersionUID = 1L;
	private static final String PROMPT_SHIPINFO_NO="<font color=red>无法获取船舶信息。</font><br/>";
	private static final String PROMPT_SUCCESS_TISHI="本航次航行电子报告已提交浙江港航部门；<br/>根据船舶电子记录，本航次可<font color=#3e9bd4>免停靠航行</font>。<br/>本航次若涉及浙江省外港口，请用其他方式向当地港口报告；若需变更目的港，请选择底下<font color=#3e9bd4>“调整航线”</font>功能。";
	private static final String PROMPT_ERROR_TISHI="本航次航行电子报告已提交浙江港航部门；<br/>根据船舶电子记录，本航次有一项以上<font color=#3e9bd4>告警提醒</font>。<br/><font color=red>请及时到相关机构处理上述业务</font>；若需变更目的港，请选择底下<font color=#3e9bd4>“调整航线”</font>功能。";
	
	private ElectricReportNew electricReportNew = new ElectricReportNew();
	private Boatman boatman = new Boatman();
	private String ReturnValue;
	private ElectricReportService electricReportService;
	private int totalPage;// 总页数
	private int allTotal;// 显示总条数
	private List<?> list;

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getAllTotal() {
		return allTotal;
	}

	public void setAllTotal(int allTotal) {
		this.allTotal = allTotal;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public ElectricReportNew getElectricReportNew() {
		return electricReportNew;
	}

	public void setElectricReportNew(ElectricReportNew electricReportNew) {
		this.electricReportNew = electricReportNew;
	}

	public void setElectricReportService(
			ElectricReportService electricReportService) {
		this.electricReportService = electricReportService;
	}

	public ElectricReportNew getModel() {
		return electricReportNew;
	}
	public Boatman getBoatman() {
		return boatman;
	}

	public void setBoatman(Boatman boatman) {
		this.boatman = boatman;
	}

	public String getReturnValue() {
		return ReturnValue;
	}

	// 显示原showElectricReportNewApp，showElectricReportNew,searchElectricReportNewApp,
	// searchElectricReportNewApp1、searchElectricReportNew
	public String ElectricReportListByInfo() {
		try {
			Map<String, Integer> map = new HashMap<String, Integer>();
			map = this.electricReportService.CountListByInfo(electricReportNew,
					GlobalVar.PAGESIZE);
			totalPage = map.get("pages");
			allTotal = map.get("allTotal");
			if (totalPage != 0) {
				list = this.electricReportService.SearchListByInfo(
						electricReportNew, Integer.valueOf(this.getCpage()),
						GlobalVar.PAGESIZE);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return SUCCESS;

	}

	// 显示PC端船户
	public String ElectricReportListByShip() {
		try {
			String shipName = electricReportNew.getShipName();
			if (shipName == null && session.get("shipName") != null) {
				shipName = (String) session.get("shipName");
				electricReportNew.setShipName(shipName);
			}else{
				return ERROR;
			}
			Map<String, Integer> map = new HashMap<String, Integer>();
			map = this.electricReportService.CountListByInfo(electricReportNew,
					GlobalVar.PAGESIZE);
			totalPage = map.get("pages");
			allTotal = map.get("allTotal");
			if (totalPage != 0) {
				list = this.electricReportService.SearchListByInfo(
						electricReportNew, Integer.valueOf(this.getCpage()),
						GlobalVar.PAGESIZE);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return SUCCESS;

	}

	// 某条电子报港 showElectricReportNewByIDApp、showElectricReportNewByID 修改参数
	// userid--》reportID
	public String ElectricReportByReportId() {
		try {
			list = this.electricReportService
					.ElectricReportByReportId(electricReportNew.getReportID());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return SUCCESS;

	}

	// 增加某条电子报港全部信息，saveElectricReportNewApp
	public String SaveElectricReport() {
		try {
			//pc上报
			String shipName = electricReportNew.getShipName();
			if (shipName == null && session.get("shipName") != null) {
				electricReportNew.setShipName((String) session.get("shipName"));
				electricReportNew.setShipUser(Integer.parseInt(session.get("pUserId").toString()));
				electricReportNew.setShipUserName((String) session.get("pUserName"));
			}
			Date now = new Date();
			DateFormat d8 = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,
					DateFormat.MEDIUM); // 显示日期，时间（精确到分）
			String declareTime = d8.format(now);
			electricReportNew.setReportTime(declareTime);
			
			if(!electricReportNew.getStartingPort().contains("湖州") && !electricReportNew.getArrivalPort().contains("湖州"))
			{
				electricReportNew.setToBeDockedAtThePier("");
			}
			
			ReturnValueByShipName();
			this.electricReportService.SaveElectricReport(electricReportNew);
		} catch (Exception e) {
			e.printStackTrace();
			ReturnValue="提交失败";
		}

		return SUCCESS;

	}
	
	List<Map<String, String>> result;
	
	public List<Map<String, String>> getResult() {
		return result;
	}
	
	public String CheckElectricReport()
	{
		String shipName = electricReportNew.getShipName();
		
		try {
			result=Webservice.AnalysisAttributeOfXML(Webservice.huzGetCheckResult(shipName));
		} catch (Exception e) {
			
			System.out.println(e);
			
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	
	//提示信息
	public void ReturnValueByShipName(){
		String prompt=PROMPT_SHIPINFO_NO;
		
		boolean success=true;
		
		try {
			prompt=Webservice.CheckShipResultForPrompt(electricReportNew.getShipName());
			
			if(prompt.equals(PROMPT_SHIPINFO_NO)||prompt.trim().length()==0)
				electricReportNew.setAbnormal(1);
			else
				success=false;
			prompt+="<br/>";
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(PROMPT_SHIPINFO_NO);
		}
		
		if(success)
			prompt="<html>"+prompt+PROMPT_SUCCESS_TISHI+"</html>";
		else
			prompt="<html>"+prompt+PROMPT_ERROR_TISHI+"</html>";
		electricReportNew.setPrompt(prompt);
		ReturnValue=prompt;
	}

	// 修改某条电子报港全部信息，updateElectricReportNewApp
	public String UpdateElectricReport() {
		try {
			//pc上报
			if (electricReportNew.getShipUserName() == null && session.get("pUserName") != null) {
				electricReportNew.setShipUser(Integer.parseInt(session.get("pUserId").toString()));
				electricReportNew.setShipUserName((String) session.get("pUserName"));
			}
			ReturnValueByShipName();
			Boolean operation = this.electricReportService
					.UpdateElectricReport(electricReportNew);
			if(operation==false){
				ReturnValue=PROMPT_SHIPINFO_NO;
				return ERROR;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}

		return SUCCESS;

	}

	// 航线调整修改目的港 参数
	public String updateReportArrivalPort() {
		try {
			ElectricArrival electricArrival = new ElectricArrival();
			electricArrival.setReportID(electricReportNew.getReportID());
			electricArrival.setArrivalPort(electricReportNew.getStartingPort());
			electricArrival.setArrivalTime(electricReportNew.getReportTime());
			this.electricReportService
					.updateReportArrivalPort(electricReportNew,electricArrival);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}

	// 设置船员信息 newBoatman
	public String SetBoatUserInfo() {
		try {
			this.electricReportService.SetBoatUserInfo(boatman);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}

	// 显示船员信息 shownewBoatman
	public String ShowBoatUserInfo() {
		try {
			String shipName = boatman.getBoatmanShip();
		if (shipName == null && session.get("shipName") != null) {
			boatman.setBoatmanShip((String)session.get("shipName"));
		}
			list = this.electricReportService.ShowBoatUserInfo(boatman);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}

	// 港口列表
	public String showPortList() {
		try {
			list = this.electricReportService.showPortList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	// 显示始发港
	public String showStartPort() {
		try {
			String arrivalPort = this.electricReportService
					.showStartPort(electricReportNew.getShipName());
			electricReportNew.setArrivalPort(arrivalPort);// 为null时无记录
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	// 历史航线
	public String GetOldReport() {

		try {
			list = this.electricReportService.GetOldReport(electricReportNew);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;

	}
}
