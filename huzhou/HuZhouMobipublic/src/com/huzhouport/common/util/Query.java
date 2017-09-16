package com.huzhouport.common.util;

/*
 * @author �򵤵�
 * ����˵Ĳ�ѯ
 * 
 * */
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.huzhouport.pushmsg.PushMsg;

public class Query
{
	public String query(String sCBname, int method)
	{

		try
		{
			sCBname = URLEncoder.encode(sCBname, "UTF-8");
		} catch (UnsupportedEncodingException e)
		{

			e.printStackTrace();
		}
		String url = HttpUtil.BASE_URL + "GetAndPostDate.action?cbname="
				+ sCBname + "&method=" + method;
		return HttpUtil.queryStringForPost(url);

	}

	public String newknowledgelistall(String departmentId)
	{

		String url = HttpUtil.BASE_URL
				+ "newknowledgelistall?isinner=0&kindID=" + departmentId;
		return HttpUtil.queryStringForPost(url);
	}

	// �ۺ���̬
	public String showPortDynmicNews(String page)
	{
		String url = HttpUtil.BASE_URL + "captureHtml?" + page;
		return HttpUtil.queryStringForPost(url);

	}

	// �㽭�ۺ���̬
	public String showDynmicNews(String page)
	{
		String url = HttpUtil.BASE_URL + "dynmicNewscaptureHtml?" + page;
		return HttpUtil.queryStringForPost(url);

	}

	// �㽭�ۺ���̬see
	public String seeDynmicNews(String url)
	{
		String url1 = HttpUtil.BASE_URL + "dynmicNewsfindDetailInfo?url=" + url;
		return HttpUtil.queryStringForPost(url1);

	}

	public String showIndustryInfo(String page)
	{
		String url = HttpUtil.BASE_URL + "findPublicIndustryInfoList?" + page
				+ "&rows=10";
		return HttpUtil.queryStringForPost(url);
	}

	public String seeIndustryInfo(String id)
	{
		String url = HttpUtil.BASE_URL + "findIndustryInfoDetail?id=" + id;
		return HttpUtil.queryStringForPost(url);
	}

	// �㽭�ۺ��ۺ�
	public String showComprehensive(String page)
	{
		String url = HttpUtil.BASE_URL + "comprehensiveCaptureHtml?" + page;
		return HttpUtil.queryStringForPost(url);

	}

	// �㽭�ۺ��ۺ�see
	public String seeComprehensive(String url)
	{
		String url1 = HttpUtil.BASE_URL + "comprehensiveFindDetailInfo?url="
				+ url;
		return HttpUtil.queryStringForPost(url1);

	}
	
	public String showIndustryInfoExclude(String userids,
			String messageids) {
		String url = HttpUtil.BASE_URL + "getPushMsgsExclude?modulerid="
				+ PushMsg.MODULERID_INDUSTRYINFO;
		
		if (userids != null && !userids.equals(""))
			url += "&userids=" + userids;
		if (messageids != null && !messageids.equals(""))
			url += "&messageids=" + messageids;
		
		System.out.println("usr===" + url);
		return HttpUtil.queryStringForPost(url);
	}

	// �ۺ���̬
	public String seePortDynmicNews(String url)
	{
		String url1 = HttpUtil.BASE_URL + "findDetailInfo?url=" + url;
		return HttpUtil.queryStringForPost(url1);

	}

	// ֪ͨ����
	public String showPortNotice(String page)
	{
		String url = HttpUtil.BASE_URL + "captureHtmlPortNotice?" + page;
		return HttpUtil.queryStringForPost(url);

	}

	// ֪ͨ�鿴
	public String seePortNotice(String url)
	{
		String url1 = HttpUtil.BASE_URL + "findDetailInfoPortNotice?url=" + url;
		return HttpUtil.queryStringForPost(url1);
	}

	// ���й���
	public String showPortnavigation(String param, String page)
			throws Exception
	{

		String url = HttpUtil.BASE_URL + "captureHtmlPortNavigation";
		return new HttpFileUpTool().sendPost1(url, "?" + page + param);

	}

	// ���в鿴
	public String seePortnavigation(String url)
	{
		String url1 = HttpUtil.BASE_URL + "findDetailInfoPortNavigation?url="
				+ url;
		return HttpUtil.queryStringForPost(url1);
	}

	// �����·��������
	public int download(String urlstr, String path, String name)
	{
		InputStream inputStream = null;
		try
		{
			FileUtils fileUtils = new FileUtils();
			if (fileUtils.isFileExist(path + name))
			{
				return 1;
			} else
			{

				inputStream = getInputStreamFromUrl(urlstr);
				File reaultFile = fileUtils
						.writeSDFrom(path, name, inputStream);
				if (reaultFile == null)
				{
					return -1;
				}
			}
		} catch (Exception e)
		{

			e.printStackTrace();
			return -1;
		} finally
		{
			try
			{
				inputStream.close();
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return 0;
	}

	public InputStream getInputStreamFromUrl(String urlstr)
			throws MalformedURLException, IOException
	{
		URL url = new URL(urlstr);
		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
		InputStream inputStream = urlConn.getInputStream();
		return inputStream;
	}

	public String AddAgreeReason(String urlstr)
	{
		String url = HttpUtil.BASE_URL + "AddAgreeReason?" + urlstr;
		return HttpUtil.queryStringForPost(url);

	}

	// �����������
	public Boolean CheckResult(String xmlstring, int method) throws Exception
	{
		JSONTokener jsonParser = new JSONTokener(xmlstring);
		JSONObject data = (JSONObject) jsonParser.nextValue();
		// �������ľ���JSON����Ĳ�����
		JSONArray jsonArray = data.getJSONArray("list");
		JSONObject jsonObject2 = (JSONObject) jsonArray.opt(0);
		if (jsonObject2 == null)
			return false;
		else
		{
			if (jsonObject2.length() == 0)
				return false;
			else
				return true;
		}
	}

	// ֪ʶ��ȫ������
	public String GetKnowledge()
	{
		String url = HttpUtil.BASE_URL + "showKnowLedge?maxPage=10000";

		return HttpUtil.queryStringForPost(url);
	}

	// ֪ʶ�����
	public String showKnowLedgedian(String kindid)
	{
		String url = HttpUtil.BASE_URL
				+ "showKnowLedgedian?maxPage=1000&kindID=" + kindid;

		return HttpUtil.queryStringForPost(url);
	}

	public String KnowLedgeView(String knowledgeID)
	{
		String url = HttpUtil.BASE_URL
				+ "KnowLedgeView?maxPage=1000&knowledgeID=" + knowledgeID;

		return HttpUtil.queryStringForPost(url);
	}

	// ֪ʶ���Ƿ�������
	public String GetKnowledgeIslink(String id)
	{
		String url = HttpUtil.BASE_URL + "KnowLedgeIsLink?kindID=" + id;
		return HttpUtil.queryStringForPost(url);
	}

	// ��������Υ���б�
	public String ShowOther(String param) throws Exception
	{
		String url = HttpUtil.BASE_URL + "CheckShipResultByList";
		return new HttpFileUpTool().sendPost1(url, param);
	}

	// ��ͷѲ�鸽��
	public String showEvidenceByPatrolId(String date)
	{
		String url = HttpUtil.BASE_URL
				+ "showPatrolEvidenceByPatrolId?patrolId=" + date;
		return HttpUtil.queryStringForPost(url);

	}

	// ��ʾΣ��Ʒ����
	public String ShowDangerousgoodsportln()
	{
		String url = HttpUtil.BASE_URL + "showDangerousGoodsPortln1";
		return HttpUtil.queryStringForPost(url);
	}

	// ǩ��ǩ���б�
	public String ShowAttendaceList(String date)
	{
		String url = HttpUtil.BASE_URL + "showSignInfo?" + date;
		return HttpUtil.queryStringForPost(url);
	}

	// ǩ������
	public String ShowAttendaceListInId(String signID)
	{
		String url = HttpUtil.BASE_URL + "seeSignInfoID?sign.signID=" + signID;
		return HttpUtil.queryStringForPost(url);
	}

	// ���е��ӱ���
	public String addElectricReport()
	{
		String url = HttpUtil.BASE_URL + "addElectricReportInfo";
		return HttpUtil.queryStringForPost(url);
	}

	// ���е��ӱ����б�
	public String ShowElectricReport(String param) throws Exception
	{
		String url = HttpUtil.BASE_URL + "showElectricReportInfoPublic";
		return new HttpFileUpTool().sendPost1(url, param);
	}

	// ���е��ӱ���鿴
	public String showElectricReportID(String reportID)
	{
		String url = HttpUtil.BASE_URL
				+ "showElectricReportID?electricReport.reportID=" + reportID;
		return HttpUtil.queryStringForPost(url);
	}

	// ����ǩ֤
	public String addElectricVisa()
	{
		String url = HttpUtil.BASE_URL + "addElectricVisaInfo";
		return HttpUtil.queryStringForPost(url);
	}

	// ����ǩ֤�б�
	public String ShowElectricVisa(String param) throws Exception
	{
		String url = HttpUtil.BASE_URL + "showElectricVisaInfoAD";
		return new HttpFileUpTool().sendPost1(url, param);
	}

	// ����ǩ֤�鿴
	public String showElectricVisaID(String visaID)
	{
		String url = HttpUtil.BASE_URL
				+ "showElectricVisaID?electricVisa.visaID=" + visaID;
		return HttpUtil.queryStringForPost(url);
	}

	// ����ǩ֤�����б�
	public String ShowFixedTermTeport(String param) throws Exception
	{
		String url = HttpUtil.BASE_URL + "findFixedTermReportInfoById";
		return new HttpFileUpTool().sendPost1(url, param);
	}

	// ����ǩ֤���α����б�
	public String ShowFixedTermTeportList(String param) throws Exception
	{
		String url = HttpUtil.BASE_URL + "findRegularVisaById";
		return new HttpFileUpTool().sendPost1(url, param);
	}

	// ����ǩ֤���α���鿴
	public String showFixedTermReportSee(String regularvisaID)
	{
		String url = HttpUtil.BASE_URL
				+ "showRegularVisaID?regularVisa.regularvisaID="
				+ regularvisaID;
		return HttpUtil.queryStringForPost(url);
	}

	// ����Σ��Ʒ����
	public String SelectDangerousGoodsPortln(String content)
	{
		try
		{
			content = URLEncoder.encode(content, "UTF-8");
		} catch (UnsupportedEncodingException e)
		{

			e.printStackTrace();
		}
		String url = HttpUtil.BASE_URL + "selectDangerousGoodsPortln1?content="
				+ content;
		return HttpUtil.queryStringForPost(url);
	}

	// �鿴Σ��Ʒ����
	public String showDangerousGoodsPortlnApproval(String declareID)
	{
		String url = HttpUtil.BASE_URL
				+ "showDangerousGoodsPortlnApproval?declareID=" + declareID;
		return HttpUtil.queryStringForPost(url);
	}

	// ��ʾΣ��Ʒ���� //ֻ��ʾ�Լ�
	public String ShowDangerousgoodsportln1(String userid)
	{

		String url = HttpUtil.BASE_URL + "showDangerousGoodsPortln2?userid="
				+ userid;
		return HttpUtil.queryStringForPost(url);
	}

	// ����Σ��Ʒ���� //ֻ��ʾ�Լ�
	public String SelectDangerousGoodsPortln1(String content, String userid)
	{

		String url = HttpUtil.BASE_URL + "selectDangerousGoodsPortln2?content="
				+ content + "&userid=" + userid;
		return HttpUtil.queryStringForPost(url);
	}

	// ��ʾΣ��Ʒ��ͷ��ҵ
	public String showDangerousGoodsJob()
	{
		String url = HttpUtil.BASE_URL + "showDangerousGoodsJob1";
		return HttpUtil.queryStringForPost(url);
	}

	// ����Σ��Ʒ��ͷ��ҵ
	public String selectDangerousGoodsJob1(String content)
	{
		try
		{
			content = URLEncoder.encode(content, "UTF-8");
		} catch (UnsupportedEncodingException e)
		{

			e.printStackTrace();
		}
		String url = HttpUtil.BASE_URL + "selectDangerousGoodsJob1?content="
				+ content;
		return HttpUtil.queryStringForPost(url);
	}

	// �鿴Σ��Ʒ��ͷ��ҵ
	public String showDangerousGoodsJobApproval(String declareID)
	{
		String url = HttpUtil.BASE_URL
				+ "showDangerousGoodsJobApproval?declareID=" + declareID;
		return HttpUtil.queryStringForPost(url);
	}

	/**
	 * 
	 * @param wharf
	 * @return
	 */
	public String showDangerousByWharf(String wharf)
	{
		return showDangerousByWharf(wharf, "");
	}

	/**
	 * 
	 * @param wharf
	 * @param content
	 * @return
	 */
	public String showDangerousByWharf(String wharf, String content)
	{
		try
		{
			wharf = URLEncoder.encode(wharf, "UTF-8");
			content = URLEncoder.encode(content, "UTF-8");
		} catch (UnsupportedEncodingException e)
		{

			e.printStackTrace();
		}
		String url = HttpUtil.BASE_URL + "showDangerousGoodsJobByWharf?wharf="
				+ wharf + "&content=" + content;
		return HttpUtil.queryStringForPost(url);
	}

	// ��ʾΣ��Ʒ��ͷ��ҵ //ֻ��ʾ�Լ�
	public String showDangerousGoodsJob2(String shipName)
	{
		try
		{
			shipName = URLEncoder.encode(shipName, "UTF-8");
		} catch (UnsupportedEncodingException e)
		{

			e.printStackTrace();
		}
		String url = HttpUtil.BASE_URL + "showDangerousGoodsJob2?shipName="
				+ shipName;
		return HttpUtil.queryStringForPost(url);
	}

	// ����Σ��Ʒ��ͷ��ҵ //ֻ��ʾ�Լ�
	public String selectDangerousGoodsJob2(String content, String shipName)
	{
		try
		{
			content = URLEncoder.encode(content, "UTF-8");
			shipName = URLEncoder.encode(shipName, "UTF-8");
		} catch (UnsupportedEncodingException e)
		{

			e.printStackTrace();
		}
		String url = HttpUtil.BASE_URL + "selectDangerousGoodsJob2?content="
				+ content + "&shipName=" + shipName;
		return HttpUtil.queryStringForPost(url);
	}

	// �鿴��ҵ��ͷ����
	public String findWharfJobNum()
	{
		String url = HttpUtil.BASE_URL + "findWharfJobNum";
		return HttpUtil.queryStringForPost(url);
	}

	// �鿴��ҵ��ͷ����
	public String selectWharfJobNum(String content)
	{
		try
		{
			content = URLEncoder.encode(content, "UTF-8");
		} catch (UnsupportedEncodingException e)
		{

			e.printStackTrace();
		}
		String url = HttpUtil.BASE_URL + "selectWharfJobNum?content=" + content;
		return HttpUtil.queryStringForPost(url);
	}

	public String selectnewaddresslist(String content)
	{
		try
		{
			content = URLEncoder.encode(content, "UTF-8");
		} catch (UnsupportedEncodingException e)
		{

			e.printStackTrace();
		}
		String url = HttpUtil.BASE_URL + "selectnewaddresslist?content="
				+ content;
		return HttpUtil.queryStringForPost(url);
	}

	// ֪ʶ����
	public String newknowledgelist(String departmentId)
	{

		String url = HttpUtil.BASE_URL + "newknowledgelistpublic?kindID="
				+ departmentId;
		return HttpUtil.queryStringForPost(url);
	}

	// ����֪ʶ����
	public String selectknowledgelist(String content)
	{
		try
		{
			content = URLEncoder.encode(content, "UTF-8");
		} catch (UnsupportedEncodingException e)
		{

			e.printStackTrace();
		}

		String url = HttpUtil.BASE_URL + "selectknowledgelist?content="
				+ content;
		return HttpUtil.queryStringForPost(url);
	}

	public String GetServiceTime()
	{

		String url = HttpUtil.BASE_URL + "GetServiceTime";
		return HttpUtil.queryStringForPost(url);
	}

	public String showWharfWork(String wharfworkid)
	{
		String url = HttpUtil.BASE_URL + "showWharfWork?wharfworkid="
				+ wharfworkid;
		return HttpUtil.queryStringForPost(url);
	}

	public String showWharfWork(String wharfworkid, int cpage)
	{
		String url = HttpUtil.BASE_URL + "showWharfWork?wharfworkid="
				+ wharfworkid + "&page=" + cpage;
		return HttpUtil.queryStringForPost(url);
	}

	public String selectWharfWork(String wharfworkid, String content, int cpage)
	{
		try
		{
			content = URLEncoder.encode(content, "UTF-8");
		} catch (UnsupportedEncodingException e)
		{

			e.printStackTrace();
		}

		String url = HttpUtil.BASE_URL + "selectWharfWork?wharfworkid="
				+ wharfworkid + "&content=" + content + "&page=" + cpage;
		return HttpUtil.queryStringForPost(url);
	}

	public String viewWharfWork(String id)
	{
		String url = HttpUtil.BASE_URL + "viewWharfWork?id=" + id;
		return HttpUtil.queryStringForPost(url);
	}

	public String viewWharf(String wharfworkid)
	{
		String url = HttpUtil.BASE_URL + "viewWharf?wharfworkid=" + wharfworkid;
		return HttpUtil.queryStringForPost(url);
	}

	public String GoodsKindAll()
	{
		String url = HttpUtil.BASE_URL + "GoodsKindAll";
		return HttpUtil.queryStringForPost(url);
	}

	public String GoodsAll(String goodsKindID)
	{
		String url = HttpUtil.BASE_URL + "GoodsAll?goodsKindID=" + goodsKindID;
		return HttpUtil.queryStringForPost(url);
	}

	// ���е��ӱ���
	public String ElectricReportListByInfo(String shipName, int page)
	{

		HttpFileUpTool hft = new HttpFileUpTool();
		Map<String, Object> p = new HashMap<String, Object>();
		String result = null;
		try
		{
			p.put("cpage", page);
			p.put("electricReportNew.shipName", shipName);
			result = hft
					.post(HttpUtil.BASE_URL + "ElectricReportListByInfo", p);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	public String ElectricReportByReportId(String reportID)
	{
		String url = HttpUtil.BASE_URL
				+ "ElectricReportByReportId?electricReportNew.reportID="
				+ reportID;
		return HttpUtil.queryStringForPost(url);
	}

	public String ElectricReportListByInfo(String shipName, int page,
			String content)
	{
		HttpFileUpTool hft = new HttpFileUpTool();
		Map<String, Object> p = new HashMap<String, Object>();
		String result = null;
		try
		{
			p.put("cpage", page);
			p.put("electricReportNew.shipName", shipName);
			p.put("electricReportNew.shipInfo", content);
			result = hft
					.post(HttpUtil.BASE_URL + "ElectricReportListByInfo", p);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	public String ShowBoatUserInfo(String content)
	{

		HttpFileUpTool hft = new HttpFileUpTool();
		Map<String, Object> p = new HashMap<String, Object>();
		String result = null;
		try
		{
			p.put("boatman.boatmanShip", content);
			result = hft.post(HttpUtil.BASE_URL + "ShowBoatUserInfo", p);
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return result;
	}
	public String ShowBoatcardList()
	{
		String url = HttpUtil.BASE_URL + "ShowBoatcardList";
		return HttpUtil.queryStringForPost(url);
	}

	public String showPortList()
	{
		String url = HttpUtil.BASE_URL + "showPortList";
		return HttpUtil.queryStringForPost(url);
	}

	public String showStartPort(String shipName)
	{

		HttpFileUpTool hft = new HttpFileUpTool();
		Map<String, Object> p = new HashMap<String, Object>();
		String result = null;
		try
		{
			p.put("electricReportNew.shipName", shipName);
			result = hft.post(HttpUtil.BASE_URL + "showStartPort", p);
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return result;
	}

	// ��ѯ�ӷ������˵õ�����δ���͹�����Ϣ
	public String showUnpushedMsgList()
	{
		String url = HttpUtil.BASE_URL + "FindUnpushedMsg";
		return HttpUtil.queryStringForPost(url);
	}

	// �ֻ���־
	public String SaveLog(String name, String content, int type, int isApp,String bz)
	{
		try {
			name = URLEncoder.encode(name, "UTF-8");
			content = URLEncoder.encode(content, "UTF-8");
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}
		String url = HttpUtil.BASE_URL + "logSave?logbean.LogUser=" + name
				+ "&logbean.LogContent=" + content + "&logbean.PartOfStyle="
				+ type + "&logbean.isApp=" + isApp+"&logbean.bz="+(bz==null?"":bz);
		return HttpUtil.queryStringForPost(url);
	}

	public String showShipName(String userid)
	{

		String url = HttpUtil.BASE_URL + "showShipName?userid=" + userid;
		return HttpUtil.queryStringForPost(url);
	}

	// ���ߵ�����¼
	public String GetOldReport(String reportID)
	{
		String url1 = HttpUtil.BASE_URL
				+ "GetOldReport?electricReportNew.reportID=" + reportID;
		return HttpUtil.queryStringForPost(url1);
	}

	// ��ͷ�б�
	public String GetWharfListFromDate(String mc)
	{
		String url1 = HttpUtil.BASE_URL + "GetWharfListFromDate?mc=" + mc;
		return HttpUtil.queryStringForPost(url1);
	}

	public String GetwharfWorkSurplus(String id)
	{
		String url = HttpUtil.BASE_URL + "wharfWorkSurplus?id=" + id;
		return HttpUtil.queryStringForPost(url);
	}

	public String querySelectPort()
	{
		String url = HttpUtil.BASE_URL + "findPortAreas";
		return HttpUtil.queryStringForPost(url);
	}

	public String querySelectPiece(int id)
	{
		String url = HttpUtil.BASE_URL + "findPieceAreas?portareaid=" + id;
		return HttpUtil.queryStringForPost(url);
	}

	public String querySelectWharf(int id)
	{
		String url = HttpUtil.BASE_URL + "findWharfItems?pieceareaid=" + id;
		return HttpUtil.queryStringForPost(url);
	}

	public String querySelectWharfByContent(String content, int portid,
			int pieceid)
	{

		Map<String, Object> params=new HashMap<String, Object>();
		params.put("wharfname", content);

		if (portid > 0)
			params.put("portareaid", portid);

		if (pieceid > 0)
			params.put("pieceareaid", pieceid);

		try
		{
			return new HttpFileUpTool().post(HttpUtil.BASE_URL + "searchWharfItems", params);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";

	}
	
	public String queryBoatmanKindList(String name) {

		Map<String, Object> params = new HashMap<String, Object>();
		if (name.length()!=0)
			params.put("boatmanKind.boatmanKindName", name);
		try {
			return new HttpFileUpTool().post(HttpUtil.BASE_URL
					+ "ShowBoatmanKindList", params);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
		public static Boolean isNumberAndMax(String str){
				if(str.trim().length()==0){
					return false;
				}
				Pattern pattern = Pattern.compile("[0-9]*"); 
				   Matcher isNum = pattern.matcher(str);
				   if( !isNum.matches() ){
				       return false; 
				   } else if(Integer.parseInt(str)>650000){
					   return false; 
				   }
				   return true; 
				
			}
		}
