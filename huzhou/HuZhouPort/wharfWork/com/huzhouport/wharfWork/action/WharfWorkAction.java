package com.huzhouport.wharfWork.action;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.struts2.ServletActionContext;
import com.huzhouport.attendace.model.Location;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.dangerousgoodsportln.model.Port;
import com.huzhouport.log.model.PageModel;
import com.huzhouport.log.service.LogsaveServer;
import com.huzhouport.publicuser.model.WharfBinding;
import com.huzhouport.wharfWork.model.Wharf;
import com.huzhouport.wharfWork.model.WharfWork;
import com.huzhouport.wharfWork.model.Wharfbean;
import com.huzhouport.wharfWork.service.WharfWorkServer;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class WharfWorkAction extends ActionSupport {
	private Map session = ActionContext.getContext().getSession(); // 获得session
	private int current; // 第几页
	private String page; // 第几页
	private String content;// 查找内容
	private PageModel pagemodel; // 分页类
	private WharfWorkServer wharfWorkServer; // server
	private Wharfbean wharfbean; // 封装类
	private String wharfworkid; // 码头编号
	private List<Wharfbean> wharfbeanlist;
	private String id; // 码头作业报告编码
	private Wharf wharf;
	private Location location;
	private String locationid;
	private List<Port> listport;
	private String portid;
	private WharfWork wharfwork;
	private LogsaveServer logsaveserver;

	public void setLogsaveserver(LogsaveServer logsaveserver) {
		this.logsaveserver = logsaveserver;
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public PageModel getPagemodel() {
		return pagemodel;
	}

	public void setPagemodel(PageModel pagemodel) {
		this.pagemodel = pagemodel;
	}

	public WharfWorkServer getWharfWorkServer() {
		return wharfWorkServer;
	}

	public void setWharfWorkServer(WharfWorkServer wharfWorkServer) {
		this.wharfWorkServer = wharfWorkServer;
	}

	public Wharfbean getWharfbean() {
		return wharfbean;
	}

	public void setWharfbean(Wharfbean wharfbean) {
		this.wharfbean = wharfbean;
	}

	public String getWharfworkid() {
		return wharfworkid;
	}

	public void setWharfworkid(String wharfworkid) {
		this.wharfworkid = wharfworkid;
	}

	public List<Wharfbean> getWharfbeanlist() {
		return wharfbeanlist;
	}

	public void setWharfbeanlist(List<Wharfbean> wharfbeanlist) {
		this.wharfbeanlist = wharfbeanlist;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Wharf getWharf() {
		return wharf;
	}

	public void setWharf(Wharf wharf) {
		this.wharf = wharf;
	}

	public List<Port> getListport() {
		return listport;
	}

	public void setListport(List<Port> listport) {
		this.listport = listport;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getLocationid() {
		return locationid;
	}

	public void setLocationid(String locationid) {
		this.locationid = locationid;
	}

	public String getPortid() {
		return portid;
	}

	public void setPortid(String portid) {
		this.portid = portid;
	}

	public WharfWork getWharfwork() {
		return wharfwork;
	}

	public void setWharfwork(WharfWork wharfwork) {
		this.wharfwork = wharfwork;
	}

	//
	public String showWharfWork() {
		try {
			int currentPage = 1; // 当前页
			int maxPage = 10; // 一页有几条
			if (null != page) {
				currentPage = Integer.parseInt(page);
			}
			current = currentPage;
			pagemodel = wharfWorkServer.findByScrollServer((currentPage - 1)
					* maxPage, maxPage, wharfworkid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	public String showWharfWork1() { // pc端
		try {
			int currentPage = 1; // 当前页
			int maxPage = 10; // 一页有几条
			if (null != page) {
				currentPage = Integer.parseInt(page);
			}
			current = currentPage;
			pagemodel = wharfWorkServer.findByScrollServer2((currentPage - 1)
					* maxPage, maxPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";

	}

	public String selectWharfWork() {
		try {
			int currentPage = 1; // 当前页
			int maxPage = 10; // 一页有几条
			if (null != page) {
				currentPage = Integer.parseInt(page);
			}
			current = currentPage;
			pagemodel = wharfWorkServer.findByScrollServer1((currentPage - 1)
					* maxPage, maxPage, wharfworkid, content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	public String selectWharfWork1() {
		try {
			int currentPage = 1; // 当前页
			int maxPage = 10; // 一页有几条
			if (null != page) {
				currentPage = Integer.parseInt(page);
			}
			current = currentPage;
			pagemodel = wharfWorkServer.findByScrollServer3((currentPage - 1)
					* maxPage, maxPage, content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	public String viewWharfWork() {
		try {
			if (session.size() != 0) {
				logsaveserver.logsave((String) session.get("name"), "查看码头作业报告",
						GlobalVar.LOGSEE, GlobalVar.LOGPC,"");
			}
			wharfbean = wharfWorkServer.findBywharfworkid(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	public String viewWharf() {
		try {
			wharf = wharfWorkServer.viewWharf(wharfworkid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	public String addWharfWork() {
		try {
			// wharf=wharfWorkServer.viewWharf(wharfworkid);
			WharfWork wharfwork = new WharfWork();

			Location l = new Location();
			l.setLongitude(wharfbean.getLongitude());
			l.setLatitude(wharfbean.getLatitude());
			l.setLocationName(wharfbean.getLocationName());
			locationid = wharfWorkServer.insertLocation(l); // 地址id

			int startingPort = 0;
			int arrivalPort = 0;
			listport = wharfWorkServer.findStartingPortName(wharfbean
					.getStartingPortName()); // 通过起运港name来找id list
			if (listport.size() == 0) {
				Port port = new Port();
				port.setPortName(wharfbean.getStartingPortName());
				portid = wharfWorkServer.savePort(port);
				startingPort = Integer.parseInt(portid);

			} else {
				Port p1 = listport.get(0);
				startingPort = p1.getPortID();
			}

			listport = wharfWorkServer.findStartingPortName(wharfbean
					.getArrivalPortName());
			if (listport.size() == 0) {
				Port port1 = new Port();
				port1.setPortName(wharfbean.getArrivalPortName());
				portid = wharfWorkServer.savePort(port1);

				arrivalPort = Integer.parseInt(portid);

			} else {
				Port p1 = listport.get(0);
				arrivalPort = p1.getPortID();
			}

			// 附件
			List<File> uploads = wharfbean.getEf();
			List<String> uploadsName = wharfbean.getEfFileName();
			if (uploads != null) {
				int i = 0;
				for (; i < uploads.size(); i++) {
					java.io.InputStream is;
					try {
						is = new java.io.FileInputStream(uploads.get(i));
						int beginIndex = uploadsName.get(i).lastIndexOf('.');
						String stype = uploadsName.get(i).substring(
								beginIndex + 1);
						String sname = (new Date()).getTime() + "." + stype;
						String root = ServletActionContext.getServletContext()
								.getRealPath(GlobalVar.FilePATH)
								+ "/" + sname;
						java.io.OutputStream os = new java.io.FileOutputStream(
								root);
						byte buffer[] = new byte[8192];
						int count = 0;
						while ((count = is.read(buffer)) > 0) {
							os.write(buffer, 0, count);
						}
						os.close();
						is.close();
						wharfwork.setWorkPhoto(sname);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			wharfwork.setWharfWorkID(wharfbean.getWharfID());
			wharfwork.setShipName(wharfbean.getShipName());
			wharfwork.setStartingPort(startingPort);
			wharfwork.setArrivalPort(arrivalPort);
			wharfwork.setPortMart(wharfbean.getPortMart());
			wharfwork.setCargoTypes(wharfbean.getCargoTypes());
			wharfwork.setCarrying(wharfbean.getCarrying());
			wharfwork.setUniti(wharfbean.getUniti());
			wharfwork.setWorkTime(wharfbean.getWorkTime());
			wharfwork.setLocationID(Integer.parseInt(locationid));

			Date now = new Date();
			DateFormat d8 = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,
					DateFormat.MEDIUM); // 显示日期，时间（精确到分）
			String declareTime = d8.format(now);
			wharfwork.setDeclareTime(declareTime);
			wharfwork.setName(wharfbean.getName());

			wharfWorkServer.saveWharfwork(wharfwork);

			int wharfworksurplus = wharfbean.getWharfWorkSurplus()
					- wharfbean.getCarrying();

			wharfWorkServer.updateWharf(wharfworksurplus, wharfbean
					.getWharfID());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	// 码头列表 pc端

	public String wharfBinDingWharfName() {
		try {
			// logsaveserver.logsave((String) session.get("name"), "查看码头作业报告",
			// GlobalVar.LOGSEE,GlobalVar.LOGPC);
			int currentPage = 1; // 当前页
			int maxPage = 10; // 一页有几条
			if (null != page) {
				currentPage = Integer.parseInt(page);
			}
			current = currentPage;
			pagemodel = wharfWorkServer.findByScrollServer4((currentPage - 1)
					* maxPage, maxPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	public String selectwharfBinDingWharfName() {
		try {
			int currentPage = 1; // 当前页
			int maxPage = 10; // 一页有几条
			if (null != page) {
				currentPage = Integer.parseInt(page);
			}
			current = currentPage;
			pagemodel = wharfWorkServer.findByScrollServer5((currentPage - 1)
					* maxPage, maxPage, content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	public String updateWharfName() {
		try {
			wharfWorkServer.updateWharfBinding(content, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	private WharfBinding wharfBinding	;
	
	public WharfBinding getWharfBinding() {
		return wharfBinding;
	}

	public void setWharfBinding(WharfBinding wharfBinding) {
		this.wharfBinding = wharfBinding;
	}

	public String  wharfWorkSurplus() {
		try {
			wharfBinding=wharfWorkServer.wharfWorkSurplus(id).get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
}
