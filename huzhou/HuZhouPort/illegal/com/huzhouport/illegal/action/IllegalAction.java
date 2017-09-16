package com.huzhouport.illegal.action;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import com.huzhouport.attendace.model.Location;
import com.huzhouport.common.action.BaseAction;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.cruiselog.model.CruiseLogLink;
import com.huzhouport.illegal.model.AbImage;
import com.huzhouport.illegal.model.Abnormal;
import com.huzhouport.illegal.model.Excutor;
import com.huzhouport.illegal.model.IllegalEvidence;
import com.huzhouport.illegal.model.IllegalEvidenceLink;
import com.huzhouport.illegal.model.Illegal;
import com.huzhouport.illegal.model.IllegalSupplement;
import com.huzhouport.illegal.service.IllegalService;
import com.huzhouport.illegal.service.impl.AbnormalpushService;
import com.opensymphony.xwork2.ModelDriven;

public class IllegalAction extends BaseAction implements ModelDriven<Illegal>{
	private static final long serialVersionUID = 1L;
	private Illegal illegal=new Illegal();
	private IllegalEvidence illegalEvidence=new IllegalEvidence();
	private IllegalEvidenceLink illegalEvidenceLink=new IllegalEvidenceLink();
	private IllegalSupplement illegalSupplement=new IllegalSupplement();
	private Location location=new Location();
//	private IllegalReason illegalReason=new IllegalReason();
	private IllegalService illegalService;
	private int totalPage;// 总页数
	private int allTotal;// 显示总条数
	private List<?> list;
	private int result;
	
	private String potname;
	private String state;
	private Abnormal abnormalinfo=new Abnormal();
	private List<File> image=new ArrayList<File>();
	private List<String> identity;
	private static String[][] results={{"海事艇","小渔船","工程船","数据误报",""},{"已纠正","已立案处罚","已教育警告","加入黑名单","实际正常"}};
	private List<String> imglist=new ArrayList<String>();
	private AbnormalpushService pushservice;//=new AbnormalpushService();
	private AbImage abImage=new AbImage();
	private Excutor excutor=new Excutor();
	
	
	//public IllegalAction(){System.out.println(111111);}
	
	public Excutor getExcutor()
	{
		return excutor;
	}
	public void setExcutor(Excutor excutor)
	{
		this.excutor=excutor;
		//System.out.println("11");
	}
	public AbImage getAbImage()
	{
		return abImage;
	}
	public void setAbImage(AbImage abImage)
	{
		this.abImage=abImage;
		//System.out.println("11");
	}
	
	public AbnormalpushService getPushservice() {
		return pushservice;
	}
	public void setPushservice(AbnormalpushService pushservice) {
		this.pushservice = pushservice;
	}
	public List<String> getImglist()
	{
		return imglist;
	}
	public void setImglist(List<String> imglist)
	{
		this.imglist=imglist;
	}
	public List<String> getIdentity()
	{
		return identity;
	}
	public void setIdentity(List<String> identity)
	{
		this.identity=identity;
	}
	public List<File> getImage()
	{
		return image;
	}
	public void setImage(List<File> image)
	{
		this.image=image;
	}
	public Abnormal getAbnormalinfo()
	{
		return abnormalinfo;
	}
	public void setAbnormalinfo(Abnormal abnormalinfo)
	{
		this.abnormalinfo=abnormalinfo;
	}
	public String getState()
	{
		return state;
	}
	public void setState(String state)
	{
		this.state=state;
	}
	public String getPotname()
	{
		return potname;
	}
	public void setPotname(String potname) 
	{
		this.potname = potname;
	}
	
	public Illegal getIllegal() {
		return illegal;
	}
	public void setIllegal(Illegal illegal) {
		this.illegal = illegal;
	}
	public IllegalEvidence getIllegalEvidence() {
		return illegalEvidence;
	}
	public void setIllegalEvidence(IllegalEvidence illegalevidence) {
		this.illegalEvidence = illegalevidence;
	}
	public IllegalEvidenceLink getIllegalEvidenceLink() {
		return illegalEvidenceLink;
	}
	public void setIllegalEvidenceLink(IllegalEvidenceLink illegalEvidenceLink) {
		this.illegalEvidenceLink = illegalEvidenceLink;
	}
	public Illegal getModel() {
		return illegal;
	}
	public IllegalSupplement getIllegalSupplement() {
		return illegalSupplement;
	}
	public void setIllegalSupplement(IllegalSupplement illegalSupplement) {
		this.illegalSupplement = illegalSupplement;
	}
	public void setIllegalService(
			IllegalService illegalService) {
		this.illegalService = illegalService;
	}
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
	
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	
	/*public IllegalReason getIllegalReason() {
		return illegalReason;
	}
	public void setIllegalReason(IllegalReason illegalReason) {
		this.illegalReason = illegalReason;
	}
	*/
	//获取基本信息
	public String  AbNormalListByStatusAndPot() throws UnsupportedEncodingException 
	{
		//String s1=new String(potname.getBytes(),"utf-8");(totalPage-1)*10
		
		list=illegalService.getAbnormalList(potname, state,totalPage,allTotal);
		return SUCCESS;
	}
	//获取图片
	public String  AbImageByAid() 
	{
		//String s1=new String(potname.getBytes(),"utf-8");
		imglist=(List<String>) illegalService.AbImageByAid(abnormalinfo.getAid());
		return SUCCESS;
	}
	//提交
	public String  SubmitAbNormalProcess() throws IOException  
	{
		Abnormal indatabase=illegalService.getAbnormalById(abnormalinfo.getAid());//(Abnormal)illegalService.getAbnormalList(abnormalinfo, state).get(0);
		List<String> dirs=new ArrayList<String>();
		//存图片
		String realpath_base = ServletActionContext.getServletContext().getRealPath("/AbNormalImages/"+indatabase.getAid());
		File dir=new File(realpath_base);
		if(!dir.exists())
			dir.mkdirs();
		for(int i=0;i<image.size();i++)
		{
			File clientfile=image.get(i);
			
			String fname=realpath_base+"\\"+identity.get(i);
			
			File savefile=new File(fname);
			
			FileUtils.copyFile(clientfile, savefile);
			
			AbImage abim=new AbImage();
			abim.setAbid(abnormalinfo.getAid());
			String serverdir="AbNormalImages/"+indatabase.getAid()+"/"+identity.get(i);
			dirs.add(serverdir);
			abim.setDir(serverdir);
			illegalService.saveDir(abim);
		}
		//存入数据库
		String pass=abnormalinfo.getPass();
		String process=abnormalinfo.getProcess();
		indatabase.setPass(pass);
		indatabase.setProcess(process);
		
		indatabase.setStatus(abnormalinfo.getStatus());		
		indatabase.setRemark(abnormalinfo.getRemark());	
		illegalService.addProcessedAbInfo(indatabase);
		//推送监管平台
		Map<String,Object> map=new HashMap<String, Object>();
		JSONObject obj = new JSONObject();
		String result=pass==null?process:pass;
		int a;
		for(int x=0;x<2;x++)
		{
			for(int y=0;y<5;y++)
			{
				if(results[x][y].equals(result))
				{
					a=Integer.valueOf((x+1)+"0"+(y+1));
					obj.element("status", Integer.valueOf((x+1)+"0"+(y+1)));
					System.out.println( Integer.valueOf((x+1)+"0"+(y+1)));
					break;
				}
			}
			
		}	
		
		
		
		ArrayList<String> arry=new ArrayList<String>();
		for(int j=0;j<dirs.size();j++)
		{
			//"picPath["+j+"].path",
			arry.add( "http://120.55.100.184/HuZhouPort/"+dirs.get(j));
		}
		
		
		
		obj.element("pic", arry);
		obj.element("id", indatabase.getShipid());
		String s=indatabase.getRemark();
		String s2=URLEncoder.encode(s, "UTF-8");
		/*String s3=new String(s.getBytes(),"utf-8");
		String s4=new String(s.getBytes(),"GB2312");
		String s5=new String(s.getBytes(),"GBK");*/
		//obj.element("remark", s );
		obj.element("remark", s2);
		//obj.element("remark3", s3 );
		//obj.element("remark4", s4 );
		//obj.element("remark5", s5);
		post("http://202.107.242.115:10008/shipnetwork/info/feedback",obj);
		//post("http://test.xcornucopia.com:8080/shipnetwork/info/feedback",obj);
		
		/*HttpClient httpClient = new HttpClient(); 
		HttpPost httppost = new HttpPost(url);
		httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		HttpMethod m=new HttpMethod();
		httpClient.executeMethod(method)*/
		
		return SUCCESS;
	}
	public String post(String actionUrl,JSONObject obj)throws IOException
	{
		String sb2 = "";
		/*String BOUNDARY = java.util.UUID.randomUUID().toString();
		String PREFIX = "--", LINEND = "\r\n"; //"multipart/form-data"
		
		//String MULTIPART_FROM_DATA ="application/x-www-form-urlencoded";
		String CHARSET = "UTF-8";*/

		URL uri = new URL(actionUrl);
		HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
		conn.setConnectTimeout(15 * 1000); // 请求的最长时间
		conn.setReadTimeout(15 * 1000); // 缓存的最长时间
		conn.setDoInput(true);// 允许输入
		conn.setDoOutput(true);// 允许输出
		conn.setUseCaches(false); // 不允许使用缓存
		conn.setRequestMethod("POST");
		conn.setRequestProperty("connection", "keep-alive");
		conn.setRequestProperty("Charsert", "UTF-8");
		conn.setRequestProperty("Content-Type", "application/json");

		/*// 首先组拼文本类型的参数
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, Object> entry : params.entrySet()) 
		{
			sb.append(PREFIX);
			sb.append(BOUNDARY);
			sb.append(LINEND);
			sb.append("Content-Disposition: form-data; name=\""+ entry.getKey() + "\"" + LINEND);
			sb.append("Content-Type: application/json; charset=" + CHARSET + LINEND);
			sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
			sb.append(LINEND);
			sb.append(entry.getValue());
			sb.append(LINEND);
		}*/

		DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
		outStream.writeBytes(obj.toString());
		InputStream in = null;
		// 请求结束标志
		//byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
		//outStream.write(end_data);
		outStream.flush();

		// 得到响应码
		int res = conn.getResponseCode();
		if (res == 200) 
		{
			in = conn.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = "";
			for (line = br.readLine(); line != null; line = br.readLine()) 
			{
				sb2 = sb2 + line;
			}
		}
		outStream.close();
		conn.disconnect();
		return sb2;
	}
	//推送
	public String ReceiveAndPush()
	{
		result=-1;
		abnormalinfo.setStatus("0");
		result=illegalService.saveNewAbInfo(abnormalinfo);
		
		if(pushservice!=null)
			pushservice.pushAbnormal(abnormalinfo.getGatename());
		
		return SUCCESS;
	}
	public String post1(String actionUrl, Map<String, Object> params)
			throws IOException {
		String sb2 = "";
		String BOUNDARY = java.util.UUID.randomUUID().toString();
		String PREFIX = "--", LINEND = "\r\n";
		String MULTIPART_FROM_DATA = "multipart/form-data";
		String CHARSET = "UTF-8";

		URL uri = new URL(actionUrl);
		HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
		conn.setConnectTimeout(15 * 1000); // 请求的最长时间
		conn.setReadTimeout(15 * 1000); // 缓存的最长时间
		conn.setDoInput(true);// 允许输入
		conn.setDoOutput(true);// 允许输出
		conn.setUseCaches(false); // 不允许使用缓存
		conn.setRequestMethod("POST");
		conn.setRequestProperty("connection", "keep-alive");
		conn.setRequestProperty("Charsert", "UTF-8");
		conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
				+ ";boundary=" + BOUNDARY);

		// 首先组拼文本类型的参数
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			sb.append(PREFIX);
			sb.append(BOUNDARY);
			sb.append(LINEND);
			sb.append("Content-Disposition: form-data; name=\""
					+ entry.getKey() + "\"" + LINEND);
			sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
			sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
			sb.append(LINEND);
			sb.append(entry.getValue());
			sb.append(LINEND);
		}

		DataOutputStream outStream = new DataOutputStream(
				conn.getOutputStream());
		outStream.write(sb.toString().getBytes());
		InputStream in = null;
		// 请求结束标志
		byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
		outStream.write(end_data);
		outStream.flush();

		// 得到响应码
		int res = conn.getResponseCode();
		if (res == 200) {
			in = conn.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = "";
			for (line = br.readLine(); line != null; line = br.readLine()) {
				sb2 = sb2 + line;
			}
		}
		outStream.close();
		conn.disconnect();
		return sb2;
	}
	
	public String updateab() throws IOException 
	{		
		/*abnormalinfo.setStatus("0");
		result=illegalService.saveNewAbInfo(abnormalinfo);
		
		if(abpush!=null)
		abpush.pushAbnormal(abnormalinfo.getGatename());*/
		
		Abnormal ab=illegalService.getAbnormalById(abnormalinfo.getAid());
		ab.setShipname(abnormalinfo.getShipname());
		illegalService.upidateAB(ab);
		
		String s1=ab.getShipid();
		String j2=URLEncoder.encode(s1, "UTF-8");		
		String s2=excutor.getPotid();		
		String s3=abnormalinfo.getShipname();		
		String s4=potname;
	
		
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("id", j2);
		params.put("site", URLEncoder.encode(s2, "UTF-8"));
		params.put("name", URLEncoder.encode(s3, "UTF-8"));
		params.put("operator", URLEncoder.encode(s4, "UTF-8"));
		
		//String sb=post("http://202.107.242.115:10008/shipnetwork/ship/makeup",obj);
		//String sb=post("http://172.21.26.248:8080/shipnetwork/ship/makeup",obj);
		String sb=post1("http://202.107.242.115:10008/shipnetwork/ship/makeup",params);
		
		
		//System.out.println(sb);*/
		
		return SUCCESS;
	}
	
	public String ExcutorList()
	{
		list=illegalService.ExcutorList(potname,"1");
		
		return SUCCESS;
	}
	
	public String UpdateExcutorList()
	{
		//用以指代名字
		Excutor excutor=illegalService.getExcutorByName(this.excutor.getName());
		if(excutor!=null)
		{
			excutor.setPot(this.excutor.getPot());
			excutor.setPotid(this.excutor.getPotid());
			excutor.setState(this.excutor.getState());
			illegalService.UpdateExcutor(excutor);
			return SUCCESS;
		}
		
		Excutor excutor1=new Excutor();
		excutor1.setName(this.excutor.getName());
		excutor1.setPot(this.excutor.getPot());
		excutor1.setState(this.excutor.getState());
		excutor1.setPotid(this.excutor.getPotid());
		illegalService.saveExcutor(excutor1);
		return SUCCESS;
	}

	
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	// 分页排序查询
	public String showIllegalList() throws Exception {
		try {
			Map<String, Integer> map = new HashMap<String, Integer>();
			map = this.illegalService.countPageIllegalInfo(illegal, GlobalVar.PAGESIZE);
			totalPage = map.get("pages");
			allTotal = map.get("allTotal");
			if (totalPage != 0) {
				list = this.illegalService.searchIllegalInfo(illegal, Integer
						.valueOf(this.getCpage()), GlobalVar.PAGESIZE);

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}

		return SUCCESS;
	}
	// 分页排序查询-手机端
	public String showIllegalList_mobi() throws Exception {
		if(illegal.getIllegalObject()!=null)
			{
			illegal.setIllegalObject(new String(illegal.getIllegalObject().getBytes("ISO8859-1"),"UTF-8"));
			
			}
		try {
			Map<String, Integer> map = new HashMap<String, Integer>();
			map = this.illegalService.countPageIllegalInfo_mobi(illegal, GlobalVar.PAGESIZEAPP);
			totalPage = map.get("pages");
			allTotal = map.get("allTotal");
			if (totalPage != 0) {
				list = this.illegalService.searchIllegalInfo_mobi(illegal, Integer
						.valueOf(this.getCpage()), GlobalVar.PAGESIZEAPP);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}

		return SUCCESS;
	}
	// 分页排序查询-手机端-待审核
	public String showIllegalListByCheck_mobi() throws Exception {
		if(illegal.getIllegalObject()!=null)
			{
			illegal.setIllegalObject(new String(illegal.getIllegalObject().getBytes("ISO8859-1"),"UTF-8"));
			
			}
		try {
			Map<String, Integer> map = new HashMap<String, Integer>();
			map = this.illegalService.countPageIllegalInfoByCheck_mobi(illegal, GlobalVar.PAGESIZEAPP);
			totalPage = map.get("pages");
			allTotal = map.get("allTotal");
			if (totalPage != 0) {
				list = this.illegalService.searchIllegalInfoByCheck_mobi(illegal, Integer
						.valueOf(this.getCpage()), GlobalVar.PAGESIZEAPP);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}

		return SUCCESS;
	}
	//全部列表
	public String IllegalList() throws Exception {
		try {
			list=this.illegalService.IllegalList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	//显示违章信息
	public String showInfoByIllegalId()throws Exception {
		try {
			list=this.illegalService.showInfoByIllegalId(illegal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	//显示审核信息
	public String showCheckInfoByIllegalId()throws Exception {
		try {
			list=this.illegalService.showCheckInfoByIllegalId(illegal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	//显示违章附件信息
	public String showEvidenceByIllegalId()throws Exception {
		try {
			list=this.illegalService.showEvidenceByIllegalId(illegal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	//显示违章补充信息
	public String showSupplementByIllegalId()throws Exception {
		try {
			list=this.illegalService.showSupplementByIllegalId(illegal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	//增加违章取证信息
	private String cruiselogid; //巡航日志id
	
	public String getCruiselogid() {
		return cruiselogid;
	}
	public void setCruiselogid(String cruiselogid) {
		this.cruiselogid = cruiselogid;
	}
	public String addIllegalByIllegalId()throws Exception {
		try {
			String iID=this.illegalService.addIllegalByIllegalId(location,illegal,illegalEvidence);
			totalPage=Integer.valueOf(iID);
			result=1;
			if(cruiselogid!=null){
			if(!cruiselogid.equals("0")){
				CruiseLogLink cruiseloglink=new CruiseLogLink();
				cruiseloglink.setCruiseLogID(Integer.valueOf(cruiselogid));
				cruiseloglink.setIllegalID(totalPage);
				illegalService.addCruiseLogLink(cruiseloglink);
			}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	//增加违章取证信息--服务端
	public String addIllegalByServer() throws Exception {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			illegal.setIllegalTime(df.format(new Date()).toString());
			
			System.out.println("illegal.asda="+illegal.getReviewUser());
			String iID=this.illegalService.addIllegalByIllegalId(location,illegal,illegalEvidence);
			totalPage=Integer.valueOf(iID);
			result=1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	//补充违章信息-服务器
	public String addSupplementByIllegalId()throws Exception {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			illegalSupplement.setSupplementTime(df.format(new Date()).toString());
			this.illegalService.addSupplementByIllegalId(illegal,illegalEvidence,illegalSupplement);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	//补充违章信息-手机端
	public String addSupplementByMobi()throws Exception {
		try {
			this.illegalService.addSupplementByIllegalId(illegal,illegalEvidence,illegalSupplement);
			result=1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	//审核违章信息-服务器
	public String checkIllegalByServer()throws Exception {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			illegal.setReviewTime(df.format(new Date()).toString());
			this.illegalService.checkIllegalByIllegalId(illegal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	//审核违章信息-手机端
	public String checkIllegalByMobi()throws Exception {
		try {
			this.illegalService.checkIllegalByIllegalId(illegal);
			result=1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	//删除单个附件
	public String deleteEvidenceByEvidenceId()throws Exception {
		try {
			this.illegalService.deleteEvidenceByEvidenceId(illegalEvidenceLink);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	//位置信息
	public String showLocationByIllegalId()throws Exception {
		try {
			list=this.illegalService.showLocationByIllegalId(illegal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	//违章缘由列表
	public String showIllegalReasonList()throws Exception{
		try {
			list=this.illegalService.showIllegalReasonList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;	
	}
	// 搜索违章缘由列表 -手机端
	public String searchIllegalReasonList()throws Exception{
		try {
			list=this.illegalService.searchIllegalReasonList(cruiselogid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;	
	}
	
}
