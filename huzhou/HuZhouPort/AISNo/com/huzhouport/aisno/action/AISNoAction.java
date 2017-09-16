package com.huzhouport.aisno.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import com.huzhouport.aisno.model.AIS;
import com.huzhouport.aisno.model.AISNo;
import com.huzhouport.aisno.service.AISNoService;
import com.nci.data.DataDriver;

public class AISNoAction 
{
	AISNoService aisNoService;	
	public void setAisNoService(AISNoService aisNoService) 
	{
		this.aisNoService = aisNoService;
	}
	
	String aisNo;
	AIS ais;
	int fnum;
	AIS editResult;
	AISNo result=new AISNo();
	int resultCode;
	int page,num;
	List<AIS> aiss;
	int total;
	
	public void setPage(int page) {
		this.page = page;
	}
	public void setNum(int num) {
		this.num = num;
	}	
	public List<AIS> getAiss() {
		return aiss;
	}
	public int getTotal() {
		return total;
	}	
	public void setAisNo(String aisNo) {
		this.aisNo = aisNo;
	}
	public void setAis(AIS ais) {
		this.ais = ais;
	}
	public AIS getAis() {
		return ais;
	}	
	
	public int getFnum() {
		return fnum;
	}
	public void setFnum(int fnum) {
		this.fnum = fnum;
	}	
	public AIS getEditResult() {
		return editResult;
	}
	public void setEditResult(AIS editResult) {
		this.editResult = editResult;
	}
	public AISNo getResult() {
		return result;
	}
	public int getResultCode() {
		return resultCode;
	}	
	
	
	/**
	 * APP根据ais查询船名
	 */
	public String queryShipnameByAisNo()
	{
		if(aisNo==null || aisNo.equals(""))
		{
			//参数为空
			resultCode=-1;
			return "success";
		}
		
		result=this.aisNoService.queryShipNameByAisNo(aisNo);
		
		if(result==null)
			resultCode=-2;				//无数据
		else 
			resultCode=0;				//返回成功
		
		return "success";
	}	
	
	//APP按AIS修改船名
	public String updateShipnameByAisNo()
	{
		/*try {
			//result.setShipname(new String(result.getShipname().getBytes("ISO8859-1"),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		this.aisNoService.updateShipnameByAisNo(result);
		
		return "success";
		
	}
	
	/**
	 * APP根据船名查ais
	 * @return
	 */
	public String queryShipnameByShipname()
	{
		
		if(ais==null || ais.getName().equals(""))
		{			
			resultCode=-1;//参数为空 算作都唔数据
			return "success";
		}			
		
		String n=ais.getName();
		editResult=this.aisNoService.queryEditShipByName(ais.getName());//编辑表	
		if(editResult!=null)
		{
			if(editResult.getAppflag()==2)
			{
				editResult=null;
			}
		}
		result=this.aisNoService.queryShipNameByShipName(ais.getName());//感知表		
			
		
		if(result==null && editResult==null)
			resultCode=-2;				//都无数据			
		if(result!=null && editResult==null)
			resultCode=0;				//有数据，无修改数据		
		if(result==null && editResult!=null)
			resultCode=-3;				//有修改数据，无数据		
		if(result!=null && editResult!=null)
			resultCode=-4;				//有数据，也有修改数据
		
		return "success";
	}
	//APP按船名修改AIS到后台审核
	public String saveAisByShipname()
	{		
		if(ais==null || ais.getName()==null || ais.getNum()==null || ais.getName().equals("") || ais.getNum().equals(""))
		{
			//参数为空
			resultCode=-1;
			return "success";
		}		
		String n=ais.getName();
		ais.setAppflag(0);
		ais.setChecker("");
		this.aisNoService.saveAis(ais);
		
		return "success";
	}
	
	//网页查询APP提交的AIS修改列表
	public String queryEditAisList()
	{
		this.aiss=this.aisNoService.queryEditAis(page, num);
		this.total=this.aisNoService.countAis();
		
		return "success";
	}
	
	//网页查询ais修改详情
	public String showAisInfo()
	{
		if(aisNo==null || aisNo.equals(""))
		{
			//参数为空
			resultCode=-1;
			return "success";
		}
		
		editResult=this.aisNoService.showAisInfo(aisNo);
		
		if(editResult==null)
			resultCode=-2;				//无数据
		else 
			resultCode=0;				//返回成功
		
		return "success";
	}
	
	
	//审核通过后到数据中心
	public String appAisInfo()
	{
		if(aisNo==null || aisNo.equals(""))
		{
			//参数为空
			resultCode=-1;
			return "success";
		}
		
		AIS temp=this.aisNoService.showAisInfo(aisNo);
	
		if(temp==null){
			resultCode=-2;				//无数据
		}
		else 
		{
			temp.setAppflag(fnum);
			String c=ais.getChecker();
			temp.setChecker(ais.getChecker());
			
			this.aisNoService.updateAisInfo(temp);//URLEncoder.encode(temp.getName(),"UTF-8")
			if(fnum==1)
			try
			{
				String result=sendPost("http://202.107.242.115:10009/interaction/modifyAisCm","aisnum="+temp.getNum()+"&cm="+URLEncoder.encode(temp.getName(),"UTF-8")+
					"&operator="	+URLEncoder.encode(temp.getMan(),"UTF-8")+"&flag="+temp.getModifyflag());
				System.out.println(result);
			}catch(Exception e)
			{
				
			}
			
			resultCode=0;				//返回成功
		}
			
	
		return "success";
	}
	public  String sendPost(String url, String param)
    {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            //conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);

            e.printStackTrace();

        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
	
	
	
	
	/**
	 * 保存接口
	 * @return
	 */
	public String saveAis()
	{
		if(ais==null || ais.getName()==null || ais.getNum()==null || ais.getName().equals("") || ais.getNum().equals(""))
		{
			//参数为空
			resultCode=-1;
			return "success";
		}
		
		editResult=this.aisNoService.hasEditData(ais.getNum());
		
		if(editResult!=null)
		{
			resultCode=-2;				//已有数据
			return "success";
		}
		
		try {
			ais.setName(new String(ais.getName().getBytes("ISO8859-1"),"UTF-8"));	
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		this.aisNoService.saveAis(ais);
		
		return "success";
	}
	
	/**
	 * 更新接口
	 * @return
	 */
	public String updateAis()
	{
		if(ais==null || ais.getName()==null || ais.getNum()==null || ais.getName().equals("") || ais.getNum().equals(""))
		{
			//参数为空
			resultCode=-1;
			return "success";
		}
		
		try {
			//ais.setName(new String(ais.getName().getBytes("ISO8859-1"),"UTF-8"));
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		this.aisNoService.updateAis(ais);
		
		return "success";
	}
	//感知接口2
	public void UpDateGZ2()
	{
		DataDriver driver = null;
		try{
			driver = DataDriver.getInstance();
			driver.setHost("10.100.70.101");
			driver.setPort(8090);
			driver.setUser("csp");
			driver.setPwd("password");
			driver.connect();
			
			String str =ais.getName();
			String num=ais.getNum();
			boolean result = driver.setAissbh(num, str);//保存船舶的9位码信息
			System.out.println(result);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(driver!=null){
				driver.close();
			}
		}
	}
	public String aistest()
	{
		try
		{
			String result=sendPost("http://202.107.242.115:10009/interaction/modifyAisCm","aisnum="+ais.getNum()+"&cm="+URLEncoder.encode(ais.getName(),"UTF-8")+
				"&operator="	+URLEncoder.encode(ais.getMan(),"UTF-8")+"&flag="+num);
			System.out.println(result);
		}catch(Exception e)
		{
			
		}
		return "success";
	}
	
}
