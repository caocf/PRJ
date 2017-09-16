package com.huzhouport.leaveandovertime;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.hxkg.network.Constants;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;


public class Query {
	public String query(String sCBname, int method) {

		try {
			sCBname = URLEncoder.encode(sCBname, "UTF-8");
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}
		String url = HttpUtil.BASE_URL + "GetAndPostDate.action?cbname="
				+ sCBname + "&method=" + method;
		return HttpUtil.queryStringForPost(url);

	}

	public String queryScheduleByTime(String sTime, String userId) {
		String url = Constants.BaseURL
				+ "AgendaByUserAndTime?time="
				+ sTime + "&id=" + userId;

		return HttpUtil.queryStringForPost(url);

	}

	public String queryScheduleByTimeInfo(String sTime, int userId) {

		String url = HttpUtil.BASE_URL
				+ "EventListByUserIdAndNowDate.action?scheduleEvent.eventTime="
				+ sTime + "&user.userId=" + userId;
		return HttpUtil.queryStringForPost(url);

	}

	public String getPushMsgStatus(int userid, int modulerid, int messageid) {
		String url = HttpUtil.BASE_URL + "getPushMsgDetail?pushMsg.userid="
				+ userid + "&pushMsg.modulerid=" + modulerid
				+ "&pushMsg.messageid=" + messageid;
		return HttpUtil.queryStringForPost(url);
	}

	public String queryScheduleByUserId(String userId) {
		String url = Constants.BaseURL + "AgendaByUser?id="
				+ userId;
		return HttpUtil.queryStringForPost(url);

	}

	public String EventClickListByUserId(int userId) {
		String url = HttpUtil.BASE_URL + "EventClickListByUserId?user.userId="
				+ userId;
		return HttpUtil.queryStringForPost(url);

	}

	public String queryScheduleByEventId(String eventId) {
		String url = Constants.BaseURL
				+ "AgendaByID?aid=" + eventId;
		return HttpUtil.queryStringForPost(url);

	}

	public String FindFeedbackByEventId(String eventId) {
		String url = HttpUtil.BASE_URL
				+ "FindFeedbackByEventId?scheduleEvent.eventId=" + eventId;
		return HttpUtil.queryStringForPost(url);

	}

	// 鍏ㄩ敓鏂ゆ嫹閿熺煫浼欐嫹
	public String AllUser() {
		String url = HttpUtil.BASE_URL + "AllUser";
		// AllUserAndDepartment
		return HttpUtil.queryStringForPost(url);

	}

	// 鍏ㄩ敓鏂ゆ嫹閿熺煫浼欐嫹
	public String AllUserAndDepartment() {
		String url = HttpUtil.BASE_URL + "AllUserAndDepartment";
		// AllUserAndDepartment
		return HttpUtil.queryStringForPost(url);

	}

	// 鍏ㄩ敓鏂ゆ嫹閿熺煫浼欐嫹
	public String FindByName(String name) {
		String url = HttpUtil.BASE_URL + "FindByName_mobi?user.name=" + name;
		return HttpUtil.queryStringForPost(url);
	}

	// 鍏ㄩ敓鏂ゆ嫹閿熺煫浼欐嫹閿熸枻鎷烽敓鏂ゆ嫹tel
	public String AllUserAndDepartmentTel(String addressname) {
		try {
			addressname = URLEncoder.encode(addressname, "UTF-8");
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}

		String url = HttpUtil.BASE_URL + "AllUserAndDepartmentTel?user.name="
				+ addressname;
		// AllUserAndDepartment
		return HttpUtil.queryStringForPost(url);

	}

	// 鍏ㄩ敓鏂ゆ嫹閿熺煫浼欐嫹閿熸枻鎷烽敓鏂ゆ嫹tel
	public String AllUserAndDepartmentUsername(String addressname) {
		try {
			addressname = URLEncoder.encode(addressname, "UTF-8");
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}

		String url = HttpUtil.BASE_URL
				+ "AllUserAndDepartmentUsername?user.name=" + addressname;
		// AllUserAndDepartment
		return HttpUtil.queryStringForPost(url);

	}

	// 鍏ㄩ敓鏂ゆ嫹閿熺煫浼欐嫹閿熸枻鎷烽敓鏂ゆ嫹tel 閫氶敓鏂ゆ嫹id
	public String AllUserAndDepartmentById(String id) {

		String url = HttpUtil.BASE_URL
				+ "AllUserAndDepartmentById?user.userId=" + id;
		// AllUserAndDepartment
		return HttpUtil.queryStringForPost(url);

	}

	// 閿熺Ц绛规嫹閿熸枻鎷烽敓鏂ゆ嫹
	public String EventKindList() {
		String url = HttpUtil.BASE_URL + "EventKindList";
		return HttpUtil.queryStringForPost(url);

	}

	// 閿熸鐚存嫹閿熸枻鎷锋��
	public String showPortDynmicNews(String page) {
		String url = HttpUtil.BASE_URL + "captureHtml?" + page;
		return HttpUtil.queryStringForPost(url);

	}

	// 閿熸鐚存嫹閿熸枻鎷锋��
	public String seePortDynmicNews(String url) {
		String url1 = HttpUtil.BASE_URL + "findDetailInfo?url=" + url;
		return HttpUtil.queryStringForPost(url1);

	}

	// 閿熷姹熼敓妗旂尨鎷烽敓鏂ゆ嫹鎬�
	public String showDynmicNews(String page) 
	{
		String url = HttpUtil.BASE_URL + "dynmicNewscaptureHtml?" + page;
		return HttpUtil.queryStringForPost(url);

	}

	public String showIndustryInfo(String page) {
		String url = HttpUtil.BASE_URL + "findInnerIndustryInfoList?" + page
				+ "&rows=10";
		System.out.println("usr===" + url);
		return HttpUtil.queryStringForPost(url);
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

	public String seeIndustryInfo(String id) {
		String url = HttpUtil.BASE_URL + "findIndustryInfoDetail?id=" + id;
		System.out.println("usr===" + url);
		return HttpUtil.queryStringForPost(url);
	}

	public String seeDynmicNews(String url) {
		String url1 = HttpUtil.BASE_URL + "dynmicNewsfindDetailInfo?url=" + url;
		return HttpUtil.queryStringForPost(url1);

	}

	//知识库列表
	public String showComprehensive(String page) 
	{
		//String url = HttpUtil.BASE_URL + "comprehensiveCaptureHtml?" + page;
		String url = Constants.BaseURL + "CodexListLocal?" + page;
		return HttpUtil.queryStringForPost(url);

	}

	public String seeComprehensive(int id) {
		String url1 = Constants.BaseURL + "CodexListLocalByID?id="
				+ id;
		return HttpUtil.queryStringForPost(url1);

	}

	public String showPortNotice(String page) {
		String url = HttpUtil.BASE_URL + "captureHtmlPortNotice?" + page;
		return HttpUtil.queryStringForPost(url);

	}

	// 閫氱煡閿熸枻鎷烽敓鏂ゆ嫹
	public String seePortNotice(String url) {
		String url1 = HttpUtil.BASE_URL + "findDetailInfoPortNotice?url=" + url;
		return HttpUtil.queryStringForPost(url1);
	}

	// 閿熺Ц绛规嫹鍒犻敓鏂ゆ嫹
	public String deleteEvent(String eventId) {
		String url = HttpUtil.BASE_URL
				+ "deleteByEventId?scheduleEvent.eventId=" + eventId;
		return HttpUtil.queryStringForPost(url);

	}

	// 閿熸枻鎷烽敓鏂ゆ嫹
	public String FindAttachmentByEventId(String eventId) {
		String url = HttpUtil.BASE_URL
				+ "FindAttachmentByEventId?scheduleEvent.eventId=" + eventId;
		return HttpUtil.queryStringForPost(url);

	}

	// 鍒犻敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
	public String DeleteAttachmentByEventId(String eventId, String attachmentId) {
		String url = HttpUtil.BASE_URL
				+ "deleteAttachmentIdByAttachmentId?scheduleEvent.eventId="
				+ eventId + "&scheduleAttachment.attachmentId=" + attachmentId;
		return HttpUtil.queryStringForPost(url);

	}

	// 閿熸枻鎷烽敓鏂ゆ嫹閿熼摪鍑ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
	public int download(String urlstr, String path, String name) {
		InputStream inputStream = null;
		try {
			FileUtils fileUtils = new FileUtils();
			if (fileUtils.isFileExist(path + name)) {
				return 1;
			} else {

				inputStream = getInputStreamFromUrl(urlstr);
				File reaultFile = fileUtils
						.writeSDFrom(path, name, inputStream);
				if (reaultFile == null) {
					return -1;
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
			return -1;
		} finally {
			try {
				inputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	public InputStream getInputStreamFromUrl(String urlstr)
			throws MalformedURLException, IOException {
		URL url = new URL(urlstr);
		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
		InputStream inputStream = urlConn.getInputStream();
		return inputStream;
	}

	public String AddAgreeReason(String urlstr) {
		String url = HttpUtil.BASE_URL + "AddAgreeReason?" + urlstr;
		return HttpUtil.queryStringForPost(url);

	}

	// 杩濋敓鏂ゆ嫹缂橀敓缂存唻鎷�
	public String showIllegalReasonList() {
		String url = HttpUtil.BASE_URL + "showIllegalReasonList";
		return HttpUtil.queryStringForPost(url);

	}

	// 杩濋敓鏂ゆ嫹缂橀敓鏂ゆ嫹閿熷彨鎲嬫嫹
	public String showIllegalList(String date) {
		String url = HttpUtil.BASE_URL + "showIllegalList_mobi?" + date;
		return HttpUtil.queryStringForPost(url);
	}

	// 杩濋敓鏂ゆ嫹缂橀敓鏂ゆ嫹閿熷彨鎲嬫嫹-鏈敓鏂ゆ嫹閿燂拷
	public String showIllegalListByCheck(String date) {
		String url = HttpUtil.BASE_URL + "showIllegalListByCheck_mobi?" + date;
		return HttpUtil.queryStringForPost(url);

	}

	// 杩濋敓鏂ゆ嫹閿熸枻鎷锋伅
	public String showIllegalInfo(String illegalId) {
		String url = HttpUtil.BASE_URL
				+ "showInfoByIllegalId?illegal.illegalId=" + illegalId;
		return HttpUtil.queryStringForPost(url);

	}

	public String showCheckIllegalInfo(String illegalId) {
		String url = HttpUtil.BASE_URL
				+ "showCheckInfoByIllegalId?illegal.illegalId=" + illegalId;
		return HttpUtil.queryStringForPost(url);

	}

	public Boolean CheckWZResult(String xmlstring) throws Exception {
		JSONTokener jsonParser = new JSONTokener(xmlstring);
		JSONObject data = (JSONObject) jsonParser.nextValue();
		String listString = data.getString("list");

		if (listString.equals("null"))
			return false;
		else {
			return true;
		}
	}

	public Boolean CheckShipResult(String xmlstring) throws Exception {
		JSONTokener jsonParser = new JSONTokener(xmlstring);
		JSONObject data = (JSONObject) jsonParser.nextValue();
		String listString = data.getString("list");
		if (listString.equals("null"))
			return false;
		else {
			JSONArray jsonArray = data.getJSONArray("list");
			if (jsonArray.length() > 0) {

				JSONObject JO = (JSONObject) jsonArray.opt(0);
				if (JO.length() == 0) {
					return false;
				} else {
					return true;
				}
			} else {
				return false;
			}
		}
	}

	public Boolean CheckCompanResult(String xmlstring) throws Exception {
		JSONTokener jsonParser = new JSONTokener(xmlstring);
		JSONObject data = (JSONObject) jsonParser.nextValue();
		String listString = data.getString("list");
		if (listString.equals("null"))
			return false;
		else {
			JSONArray jsonArray = data.getJSONArray("list");
			if (jsonArray.length() > 0) {
				JSONObject JO = (JSONObject) jsonArray.opt(0);
				if (JO.length() == 0) {
					return false;
				} else {
					return true;
				}
			} else {
				return false;
			}
		}
	}

	public String showIllegalUpdateInfo(String illegalId) {
		String url = HttpUtil.BASE_URL
				+ "showSupplementByIllegalId?illegal.illegalId=" + illegalId;
		return HttpUtil.queryStringForPost(url);
	}

	public String showEvidenceByIllegalId(String illegalId) {
		String url = HttpUtil.BASE_URL
				+ "showEvidenceByIllegalId?illegal.illegalId=" + illegalId;
		return HttpUtil.queryStringForPost(url);
	}

	public String showLocationByIllegalId(String illegalId) {
		String url = HttpUtil.BASE_URL
				+ "showLocationByIllegalId?illegal.illegalId=" + illegalId;
		return HttpUtil.queryStringForPost(url);
	}

	public String showInspectionList(String date) {
		String url = HttpUtil.BASE_URL + "showInspectionList_mobi?" + date;
		return HttpUtil.queryStringForPost(url);

	}

	public String showEvidenceByInspectionId(String date) {
		String url = HttpUtil.BASE_URL
				+ "showInspectionEvidenceByInspectionId?inspectionId=" + date;
		return HttpUtil.queryStringForPost(url);

	}

	public String showLocationByInspectionId(String date) {
		String url = HttpUtil.BASE_URL
				+ "showLocationByInspectionId?inspectionId=" + date;
		return HttpUtil.queryStringForPost(url);

	}

	public String showInspectionUpdateInfo(String date) {
		String url = HttpUtil.BASE_URL
				+ "showInspectionSupplementByInspectionId?inspectionId=" + date;
		return HttpUtil.queryStringForPost(url);

	}

	public String showInspectionInfo(String date) {
		String url = HttpUtil.BASE_URL
				+ "showInspectionInfoByInspectionId?inspectionId=" + date;
		return HttpUtil.queryStringForPost(url);

	}

	public String showCheckInspectionInfo(String date) {
		String url = HttpUtil.BASE_URL
				+ "showInspectionCheckInfoByInspectionId?inspectionId=" + date;
		return HttpUtil.queryStringForPost(url);

	}

	public String showInspectionListByCheck(String date) {
		String url = HttpUtil.BASE_URL + "showInspectionListByCheck_mobi?"
				+ date;
		return HttpUtil.queryStringForPost(url);

	}

	public String GetKnowledge() {
		String url = HttpUtil.BASE_URL + "showKnowLedge?maxPage=10000";

		return HttpUtil.queryStringForPost(url);
	}

	public String GetKnowledgeadd() {
		String url = HttpUtil.BASE_URL + "showKnowLedge?maxPage=10000";

		return HttpUtil.queryStringForPost(url);
	}

	public String showKnowLedgedian(String kindid) {
		String url = HttpUtil.BASE_URL
				+ "showKnowLedgedian?maxPage=1000&kindID=" + kindid;

		return HttpUtil.queryStringForPost(url);
	}

	public String KnowLedgeView(String knowledgeID) {
		String url = HttpUtil.BASE_URL
				+ "KnowLedgeView?maxPage=1000&knowledgeID=" + knowledgeID;

		return HttpUtil.queryStringForPost(url);
	}

	public String GetKnowledgeIslink(String id) {
		String url = HttpUtil.BASE_URL + "KnowLedgeIsLink?kindID=" + id;
		return HttpUtil.queryStringForPost(url);
	}

	public String Showleave(String userid) {
		String url = HttpUtil.BASE_URL + "Leave1?userid=" + userid;
		return HttpUtil.queryStringForPost(url);
	}

	public String Showovertime(String userid) {
		String url = HttpUtil.BASE_URL + "Overtime1?userid=" + userid;
		return HttpUtil.queryStringForPost(url);
	}

	public String FindAttedanceByPermission_APP(String userId,
			String SeachContent) {
		String result = null;
		HttpFileUpTool hft = new HttpFileUpTool();
		String url = HttpUtil.BASE_URL + "FindAttedanceByPermission_APP";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("leaveOrOt.leaveOrOtUser", userId);
		params.put("leaveOrOt.leaveOrOtReason", SeachContent);
		try {
			result = hft.post(url, params);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String LeaveAndOvertimefinish(String userid,String page) 
	{
		
		String url = Constants.BaseURL + "LeaveAndOvertimefinish?userid="+ userid+"&page="+page;
		
		return HttpUtil.queryStringForPost(url);
	}
	public String LeaveAndOvertimeApply(String userid,String page) 
	{
		
		String url = Constants.BaseURL + "LeaveAndOvertimeApply?userid="+ userid+"&page="+page;
		
		return HttpUtil.queryStringForPost(url);
	}
	public String LeaveAndOvertime(String userid,String ra,String rb)  
	{
		
		String url = Constants.BaseURL + "LeaveAndOvertime?userid="+ userid+"&p1="+ra+"&p2="+rb;
		
		return HttpUtil.queryStringForPost(url);
	}

	public String LeaveAndOvertimeunfinish(String userid,String ra,String rb) {
		String url = Constants.BaseURL + "LeaveAndOvertimeunfinish?userid="
				+ userid+"&p1="+ra+"&p2="+rb;
		return HttpUtil.queryStringForPost(url);
	}

	public String LeaveAndOvertiemALLbymy(String userid) {
		String url = HttpUtil.BASE_URL + "LeaveAndOvertiemALLbymy?userid="
				+ userid;
		return HttpUtil.queryStringForPost(url);
	}

	public String selectLeaveAndOvertimeunfinish(String userid, String content) {
		try {
			content = URLEncoder.encode(content, "UTF-8");
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}
		String url = HttpUtil.BASE_URL
				+ "selectLeaveAndOvertimeunfinish?userid=" + userid
				+ "&content=" + content;
		return HttpUtil.queryStringForPost(url);
	}

	public String selectLeaveAndOvertimefinish(String userid, String content) {
		try {
			content = URLEncoder.encode(content, "UTF-8");
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}
		String url = HttpUtil.BASE_URL + "selectLeaveAndOvertimefinish?userid="
				+ userid + "&content=" + content;
		;
		return HttpUtil.queryStringForPost(url);
	}

	public String selectLeaveAndOvertiemALLbymy(String userid, String content) {
		try {
			content = URLEncoder.encode(content, "UTF-8");
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}
		String url = HttpUtil.BASE_URL
				+ "selectLeaveAndOvertiemALLbymy?userid=" + userid
				+ "&content=" + content;
		return HttpUtil.queryStringForPost(url);
	}

	public String selectShowleaveALL(String content) {
		try {
			content = URLEncoder.encode(content, "UTF-8");
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}
		String url = HttpUtil.BASE_URL + "selectLeaveAndOvertiemALL?content="
				+ content;
		return HttpUtil.queryStringForPost(url);
	}

	public String Showneirong(String id) {
		String url = Constants.BaseURL + "showLeaveAndOvertimeApproval?leaveOrOtID1=" + id;
		return HttpUtil.queryStringForPost(url);
	}

	public String LeaveandovertimeKindType(String name) {
		String url = Constants.BaseURL + "LeaveAndOvertimeKindName?kindType="
				+ name;
		return HttpUtil.queryStringForPost(url);
	}

	public String showPatrolList(String date) {
		String url = HttpUtil.BASE_URL + "showPatrolList_mobi?" + date;
		return HttpUtil.queryStringForPost(url);

	}

	public String showPatrolInfo(String date) {
		String url = HttpUtil.BASE_URL + "showPatrolInfoByPatrolId?patrolId="
				+ date;
		return HttpUtil.queryStringForPost(url);

	}

	// 閿熸枻鎷峰ご宸￠敓浠嬭ˉ閿熸枻鎷烽敓鏂ゆ嫹閿燂拷
	public String showPatrolUpdateInfo(String date) {
		String url = HttpUtil.BASE_URL
				+ "showPatrolSupplementByPatrolId?patrolId=" + date;
		return HttpUtil.queryStringForPost(url);

	}

	// 閿熸枻鎷峰ご宸￠敓浠嬮檮閿熸枻鎷�
	public String showEvidenceByPatrolId(String date) {
		String url = HttpUtil.BASE_URL
				+ "showPatrolEvidenceByPatrolId?patrolId=" + date;
		return HttpUtil.queryStringForPost(url);

	}

	// 閿熸枻鎷风ず鍗遍敓鏂ゆ嫹鍝侀敓鏂ゆ嫹閿熸枻鎷�
	public String ShowDangerousgoodsportln() {
		String url = HttpUtil.BASE_URL + "showDangerousGoodsPortln1";
		return HttpUtil.queryStringForPost(url);
	}

	// 绛鹃敓鏂ゆ嫹绛鹃敓鏂ゆ嫹閿熷彨鎲嬫嫹
	public String ShowAttendaceList(String date) {
		String url = HttpUtil.BASE_URL + "showSignInfo?" + date;
		return HttpUtil.queryStringForPost(url);
	}

	// 绛鹃敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
	public String ShowAttendaceListInId(String signID) {
		String url = HttpUtil.BASE_URL + "seeSignInfoID?sign.signID=" + signID;
		return HttpUtil.queryStringForPost(url);
	}

	// // 绛鹃敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
	// public String ShowAttendaceListBackId(String date) {
	// String url = HttpUtil.BASE_URL + "showSignInfo?" + date;
	// return HttpUtil.queryStringForPost(url);
	// }
	// 閿熸枻鎷烽敓鍙鎷烽敓鎺ユ唻鎷烽敓鏂ゆ嫹
	public String addElectricReport() {
		String url = HttpUtil.BASE_URL + "addElectricReportInfo";
		return HttpUtil.queryStringForPost(url);
	}

	// 閿熸枻鎷烽敓鍙鎷烽敓鎺ユ唻鎷烽敓鏂ゆ嫹閿熷彨鎲嬫嫹
	public String ShowElectricReport(String param) throws Exception {
		String url = HttpUtil.BASE_URL + "showElectricReportInfoAD";
		return new HttpFileUpTool().sendPost1(url, param);
	}

	// 閿熸枻鎷烽敓鍙鎷烽敓鎺ユ唻鎷烽敓鏂ゆ嫹姒拷
	public String showElectricReportID(String reportID) {
		String url = HttpUtil.BASE_URL
				+ "showElectricReportID?electricReport.reportID=" + reportID;
		return HttpUtil.queryStringForPost(url);
	}

	// 閿熸枻鎷烽敓鏂ゆ嫹绛捐瘉
	public String addElectricVisa() {
		String url = HttpUtil.BASE_URL + "addElectricVisaInfo";
		return HttpUtil.queryStringForPost(url);
	}

	// 閿熸枻鎷烽敓鏂ゆ嫹绛捐瘉閿熷彨鎲嬫嫹
	public String ShowElectricVisa(String param) throws Exception {
		String url = HttpUtil.BASE_URL + "showElectricVisaInfoAD";
		return new HttpFileUpTool().sendPost1(url, param);
	}

	// 閿熸枻鎷烽敓鏂ゆ嫹绛捐瘉閿熶粙鐪�
	public String showElectricVisaID(String visaID) {
		String url = HttpUtil.BASE_URL
				+ "showElectricVisaID?electricVisa.visaID=" + visaID;
		return HttpUtil.queryStringForPost(url);
	}

	// 閿熸枻鎷烽敓鏂ゆ嫹绛捐瘉閿熸枻鎷烽敓鏂ゆ嫹閿熷彨鎲嬫嫹
	public String ShowFixedTermTeport(String param) throws Exception {
		String url = HttpUtil.BASE_URL + "findFixedTermReportInfo";
		return new HttpFileUpTool().sendPost1(url, param);
	}

	// 閿熸枻鎷烽敓鏂ゆ嫹绛捐瘉閿熸枻鎷烽敓杞挎唻鎷烽敓鏂ゆ嫹閿熷彨鎲嬫嫹
	public String ShowFixedTermTeportList(String param) throws Exception {
		String url = HttpUtil.BASE_URL + "findRegularVisaById";
		return new HttpFileUpTool().sendPost1(url, param);
	}

	// 閿熸枻鎷烽敓鏂ゆ嫹绛捐瘉閿熸枻鎷烽敓杞挎唻鎷烽敓鏂ゆ嫹閿熷彨鎲嬫嫹
	public String ShowFixedTermTeportListSearch(String param) throws Exception {
		String url = HttpUtil.BASE_URL + "findRegularVisaInfo";
		return new HttpFileUpTool().sendPost1(url, param);
	}

	// 閿熸枻鎷烽敓鏂ゆ嫹绛捐瘉閿熸枻鎷烽敓杞挎唻鎷烽敓鏂ゆ嫹姒拷
	public String showFixedTermReportSee(String regularvisaID) {
		String url = HttpUtil.BASE_URL
				+ "showRegularVisaID?regularVisa.regularvisaID="
				+ regularvisaID;
		return HttpUtil.queryStringForPost(url);
	}

	// 閿熸枻鎷烽敓鏂ゆ嫹鍗遍敓鏂ゆ嫹鍝侀敓鏂ゆ嫹閿熸枻鎷�
	public String SelectDangerousGoodsPortln(String content) {
		try {
			content = URLEncoder.encode(content, "UTF-8");
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}
		String url = HttpUtil.BASE_URL + "selectDangerousGoodsPortln1?content="
				+ content;
		return HttpUtil.queryStringForPost(url);
	}

	// 閿熶粙鐪嬪嵄閿熸枻鎷峰搧閿熸枻鎷烽敓鏂ゆ嫹
	public String showDangerousGoodsPortlnApproval(String declareID) {
		String url = HttpUtil.BASE_URL
				+ "showDangerousGoodsPortlnApproval?declareID=" + declareID;
		return HttpUtil.queryStringForPost(url);
	}

	// 閿熸枻鎷风ず鍗遍敓鏂ゆ嫹鍝侀敓鏂ゆ嫹閿熸枻鎷� //鍙敓鏂ゆ嫹绀洪敓鐨嗙》鎷�
	public String ShowDangerousgoodsportln1(String shipName) {
		try {
			shipName = URLEncoder.encode(shipName, "UTF-8");
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}
		String url = HttpUtil.BASE_URL + "showDangerousGoodsPortln2?shipName="
				+ shipName;
		return HttpUtil.queryStringForPost(url);
	}

	// 閿熸枻鎷烽敓鏂ゆ嫹鍗遍敓鏂ゆ嫹鍝侀敓鏂ゆ嫹閿熸枻鎷� //鍙敓鏂ゆ嫹绀洪敓鐨嗙》鎷�
	public String SelectDangerousGoodsPortln1(String content, String shipName) {
		try {
			content = URLEncoder.encode(content, "UTF-8");
			shipName = URLEncoder.encode(shipName, "UTF-8");
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}
		String url = HttpUtil.BASE_URL + "selectDangerousGoodsPortln2?content="
				+ content + "&shipName=" + shipName;
		return HttpUtil.queryStringForPost(url);
	}

	// 閿熸枻鎷风ず鍗遍敓鏂ゆ嫹鍝侀敓鏂ゆ嫹澶撮敓鏂ゆ嫹涓�
	public String showDangerousGoodsJob() {
		String url = HttpUtil.BASE_URL + "showDangerousGoodsJob1";
		return HttpUtil.queryStringForPost(url);
	}

	// 閿熸枻鎷烽敓鏂ゆ嫹鍗遍敓鏂ゆ嫹鍝侀敓鏂ゆ嫹澶撮敓鏂ゆ嫹涓�
	public String selectDangerousGoodsJob1(String content) {
		try {
			content = URLEncoder.encode(content, "UTF-8");
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}
		String url = HttpUtil.BASE_URL + "selectDangerousGoodsJob1?content="
				+ content;
		return HttpUtil.queryStringForPost(url);
	}

	// 閿熶粙鐪嬪嵄閿熸枻鎷峰搧閿熸枻鎷峰ご閿熸枻鎷蜂笟
	public String showDangerousGoodsJobApproval(String declareID) {
		String url = HttpUtil.BASE_URL
				+ "showDangerousGoodsJobApproval?declareID=" + declareID;
		return HttpUtil.queryStringForPost(url);
	}

	// 閿熸枻鎷风ず鍗遍敓鏂ゆ嫹鍝侀敓鏂ゆ嫹澶撮敓鏂ゆ嫹涓� //鍙敓鏂ゆ嫹绀洪敓鐨嗙》鎷�
	public String showDangerousGoodsJob2(String shipName) {
		try {
			shipName = URLEncoder.encode(shipName, "UTF-8");
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}
		String url = HttpUtil.BASE_URL + "showDangerousGoodsJob2?shipName="
				+ shipName;
		return HttpUtil.queryStringForPost(url);
	}

	// 閿熸枻鎷烽敓鏂ゆ嫹鍗遍敓鏂ゆ嫹鍝侀敓鏂ゆ嫹澶撮敓鏂ゆ嫹涓� //鍙敓鏂ゆ嫹绀洪敓鐨嗙》鎷�
	public String selectDangerousGoodsJob2(String content, String shipName) {
		try {
			content = URLEncoder.encode(content, "UTF-8");
			shipName = URLEncoder.encode(shipName, "UTF-8");
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}
		String url = HttpUtil.BASE_URL + "selectDangerousGoodsJob2?content="
				+ content + "&shipName=" + shipName;
		return HttpUtil.queryStringForPost(url);
	}

	// 閿熶粙鐪嬮敓鏂ゆ嫹涓氶敓鏂ゆ嫹澶撮敓鏂ゆ嫹閿熸枻鎷�
	public String findWharfJobNum() {
		String url = HttpUtil.BASE_URL + "findWharfJobNum";
		return HttpUtil.queryStringForPost(url);
	}

	// 閿熶粙鐪嬮敓鏂ゆ嫹涓氶敓鏂ゆ嫹澶撮敓鏂ゆ嫹閿熸枻鎷� 
	public String selectWharfJobNum(String content) {
		try {
			content = URLEncoder.encode(content, "UTF-8");
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}
		String url = HttpUtil.BASE_URL + "selectWharfJobNum?content=" + content;
		return HttpUtil.queryStringForPost(url);
	}

	// 宸￠敓鏂ゆ嫹閿熸枻鎷峰織 鏈敓鏂ゆ嫹閿燂拷
	public String showCruiseLogUnfinish() {
		String url = HttpUtil.BASE_URL + "showCruiseLogUnfinish";
		return HttpUtil.queryStringForPost(url);
	}

	// 宸￠敓鏂ゆ嫹閿熸枻鎷峰織 閿熸枻鎷烽敓锟�
	public String showCruiseLogFinish() {
		String url = HttpUtil.BASE_URL + "showCruiseLogFinish";
		return HttpUtil.queryStringForPost(url);
	}

	// //宸￠敓鏂ゆ嫹閿熸枻鎷峰織 鏈敓鏂ゆ嫹閿燂拷 閿熸枻鎷烽敓鏂ゆ嫹
	public String selectCruiseLogUnfinish(String content) {
		try {
			content = URLEncoder.encode(content, "UTF-8");
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}
		String url = HttpUtil.BASE_URL + "selectCruiseLogUnfinish?content="
				+ content;
		return HttpUtil.queryStringForPost(url);
	}

	// 宸￠敓鏂ゆ嫹閿熸枻鎷峰織 閿熸枻鎷烽敓锟� 閿熸枻鎷烽敓鏂ゆ嫹
	public String selectCruiseLogFinish(String content) {
		try {
			content = URLEncoder.encode(content, "UTF-8");
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}
		String url = HttpUtil.BASE_URL + "selectCruiseLogFinish?content="
				+ content;
		return HttpUtil.queryStringForPost(url);
	}

	// 宸￠敓鏂ゆ嫹閿熸枻鎷峰織 閿熸枻鎷烽敓锟� 閿熶粙鐪�
	public String showCruiseLogAndIllegal(String id) {

		String url = HttpUtil.BASE_URL + "showCruiseLogAndIllegal?cruiseLogID="
				+ id;
		return HttpUtil.queryStringForPost(url);
	}

	// 宸￠敓鏂ゆ嫹閿熸枻鎷峰織 閿熸枻鎷烽敓锟� 閿熶粙鐪�
	public String showCruiseLogIllegalList(String illid) {

		String url = HttpUtil.BASE_URL + "showCruiseLogIllegalList?illid="
				+ illid;
		return HttpUtil.queryStringForPost(url);
	}

	// 宸￠敓鏂ゆ嫹閿熸枻鎷峰織 浣嶉敓鏂ゆ嫹 閿熶粙鐪�
	public String showMap(String illid) {

		String url = HttpUtil.BASE_URL + "showMap?cruiseLogID=" + illid;
		return HttpUtil.queryStringForPost(url);
	}

	public String newaddresslist(String departmentId) {

		String url = HttpUtil.BASE_URL + "newaddresslist?departmentId="
				+ departmentId;
		return HttpUtil.queryStringForPost(url);
	}

	public String newaddresslistInfo(String departmentId, String eventId) {

		String url = HttpUtil.BASE_URL + "newaddresslistInfo?departmentId="
				+ departmentId + "&userid=" + eventId;
		return HttpUtil.queryStringForPost(url);
	}

	public String selectnewaddresslist(String content) {
		try {
			content = URLEncoder.encode(content, "UTF-8");
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}
		String url = HttpUtil.BASE_URL + "selectnewaddresslist?content="
				+ content;
		return HttpUtil.queryStringForPost(url);
	}

	public String newknowledgelist(String departmentId) {

		String url = HttpUtil.BASE_URL + "newknowledgelist?kindID="
				+ departmentId;
		return HttpUtil.queryStringForPost(url);
	}

	public String newknowledgelistall(String departmentId) {

		String url = HttpUtil.BASE_URL
				+ "newknowledgelistall?isinner=1&kindID=" + departmentId;
		return HttpUtil.queryStringForPost(url);
	}

	public String selectknowledgelist(String content) {
		try {
			content = URLEncoder.encode(content, "UTF-8");
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}

		String url = HttpUtil.BASE_URL + "selectknowledgelistinner?content="
				+ content;
		return HttpUtil.queryStringForPost(url);
	}

	public String GetServiceTime() {

		String url = HttpUtil.BASE_URL + "GetServiceTime";
		return HttpUtil.queryStringForPost(url);
	}

	public String equipmentApprovalbyMy(String userid) {
		String url = HttpUtil.BASE_URL + "equipmentApprovalbyMy?equipmentID="
				+ userid;
		return HttpUtil.queryStringForPost(url);
	}

	public String equipmentApply(String userid) {
		String url = HttpUtil.BASE_URL + "equipmentApply?equipmentID=" + userid;
		return HttpUtil.queryStringForPost(url);
	}

	public String equipmentAll() {
		String url = HttpUtil.BASE_URL + "equipmentAll";
		return HttpUtil.queryStringForPost(url);
	}

	public String showEquipmentApproval(String id) {
		String url = HttpUtil.BASE_URL + "showEquipmentApproval?equipmentID="
				+ id;
		return HttpUtil.queryStringForPost(url);
	}

	public String equipmentApprovalbyMycontent(String userid, String content) {
		try {
			content = URLEncoder.encode(content, "UTF-8");
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}
		String url = HttpUtil.BASE_URL
				+ "equipmentApprovalbyMycontent?equipmentID=" + userid
				+ "&content=" + content;
		return HttpUtil.queryStringForPost(url);
	}

	public String equipmentkindAll() {
		String url = HttpUtil.BASE_URL + "equipmentkindAll";
		return HttpUtil.queryStringForPost(url);
	}

	public String showUnpushedMsgList() {
		String url = HttpUtil.BASE_URL + "FindUnpushedMsg";
		return HttpUtil.queryStringForPost(url);
	}

	public String LeaveOrOtApproval(String userid) {
		String url = Constants.BaseURL + "Approvers?id=" + userid;
		return HttpUtil.queryStringForPost(url);
	}

	public String showWharfWork(String wharfworkid) {
		String url = HttpUtil.BASE_URL + "showWharfWork?wharfworkid="
				+ wharfworkid;
		return HttpUtil.queryStringForPost(url);
	}

	public String showWharfWork(String wharfworkid, int cpage) {
		String url = HttpUtil.BASE_URL + "showWharfWork?wharfworkid="
				+ wharfworkid + "&page=" + cpage;
		return HttpUtil.queryStringForPost(url);
	}

	public String showWharfWork1() {
		String url = HttpUtil.BASE_URL + "showWharfWork1";

		return HttpUtil.queryStringForPost(url);
	}

	public String showWharfWork1(int cpage) {
		String url = HttpUtil.BASE_URL + "showWharfWork1?page=" + cpage;
		return HttpUtil.queryStringForPost(url);
	}

	public String selectWharfWork(String wharfworkid, String content, int cpage) {
		try {
			content = URLEncoder.encode(content, "UTF-8");
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}

		String url = HttpUtil.BASE_URL + "selectWharfWork?wharfworkid="
				+ wharfworkid + "&content=" + content + "&page=" + cpage;
		return HttpUtil.queryStringForPost(url);
	}

	public String selectWharfWork1(String content, int cpage) {
		try {
			content = URLEncoder.encode(content, "UTF-8");
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}

		String url = HttpUtil.BASE_URL + "selectWharfWork1?&content=" + content
				+ "&page=" + cpage;
		return HttpUtil.queryStringForPost(url);
	}

	public String viewWharfWork(String id) {
		String url = HttpUtil.BASE_URL + "viewWharfWork?id=" + id;
		return HttpUtil.queryStringForPost(url);
	}

	public String viewWharf(String wharfworkid) {
		String url = HttpUtil.BASE_URL + "viewWharf?wharfworkid=" + wharfworkid;
		return HttpUtil.queryStringForPost(url);
	}

	public String GoodsKindAll() {
		String url = HttpUtil.BASE_URL + "GoodsKindAll";
		return HttpUtil.queryStringForPost(url);
	}

	public String GoodsAll(String goodsKindID) {
		String url = HttpUtil.BASE_URL + "GoodsAll?goodsKindID=" + goodsKindID;
		return HttpUtil.queryStringForPost(url);
	}

	public String getAndAnServiceTime() {
		String timesString = null;
		String url = HttpUtil.BASE_URL + "GetServiceTime";
		String jsonstring = HttpUtil.queryStringForPost(url);
		JSONTokener jsonParser = new JSONTokener(jsonstring);
		try {
			JSONObject data = (JSONObject) jsonParser.nextValue();
			timesString = data.getString("serverTime");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (timesString == null) {
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Date curDate = new Date();
			timesString = formatter.format(curDate);
		}
		return timesString;
	}

	public String ElectricReportByReportId(String reportID) {
		String url = HttpUtil.BASE_URL
				+ "ElectricReportByReportId?electricReportNew.reportID="
				+ reportID;
		return HttpUtil.queryStringForPost(url);
	}

	public String updateCruiseLogByID(String cruiselogid) {
		String url = HttpUtil.BASE_URL + "updateCruiseLogByID?cruiseLogID="
				+ cruiselogid;
		return HttpUtil.queryStringForPost(url);
	}

	public String SaveLog(String name, String content, int type, int isApp,String bz) {
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

	public String GetWharfListFromDate(String mc) {
		String url1 = HttpUtil.BASE_URL + "GetWharfListFromDate?mc=" + mc;
		return HttpUtil.queryStringForPost(url1);
	}

	public String querySelectPort() {
		String url = HttpUtil.BASE_URL + "findPortAreas";
		return HttpUtil.queryStringForPost(url);
	}

	public String querySelectPiece(int id) {
		String url = HttpUtil.BASE_URL + "findPieceAreas?portareaid=" + id;
		return HttpUtil.queryStringForPost(url);
	}

	public String querySelectWharf(int id) {
		String url = HttpUtil.BASE_URL + "findWharfItems?pieceareaid=" + id;
		return HttpUtil.queryStringForPost(url);
	}

	public String querySelectWharfByContent(String content, int portid,
			int pieceid) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("wharfname", content);

		if (portid > 0)
			params.put("portareaid", portid);

		if (pieceid > 0)
			params.put("pieceareaid", pieceid);

		try {
			return new HttpFileUpTool().post(HttpUtil.BASE_URL
					+ "searchWharfItems", params);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";
	}
	
		public String ShipSearchNicecode(String id) {
			String url = HttpUtil.BASE_URL
					+ "queryShipnameByShipname?ais.name=" + id;
			return HttpUtil.queryStringForPost(url);
		}

	    public String ShipSearchNicecodeAdd(String name,String num) {
	    	try {
				name = URLEncoder.encode(name, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			String url = HttpUtil.BASE_URL
							+ "saveAisByShipname?ais.name=" + name+"&&ais.num="+num;
			return HttpUtil.queryStringForPost(url);
		}
	    public String ShipSearchNicecodeUpdate(String name,String num) {
	    	try {
				name = URLEncoder.encode(name, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			String url = HttpUtil.BASE_URL
							+ "updateAis?ais.name=" + name+"&&ais.num="+num;
			return HttpUtil.queryStringForPost(url);
		}
}
