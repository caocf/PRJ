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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.huzhouport.pushmsg.PushMsg;

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

	// ʱ��:xx��xx��
	public String queryScheduleByTime(String sTime, int userId) {
		String url = HttpUtil.BASE_URL
				+ "EventListByUserIdAndTime.action?scheduleEvent.eventTime="
				+ sTime + "&user.userId=" + userId;

		return HttpUtil.queryStringForPost(url);

	}

	// ʱ��:xx��xx��
	public String queryScheduleByTimeInfo(String sTime, int userId) {

		String url = HttpUtil.BASE_URL
				+ "EventListByUserIdAndNowDate.action?scheduleEvent.eventTime="
				+ sTime + "&user.userId=" + userId;
		return HttpUtil.queryStringForPost(url);

	}
	
	// ʱ��:xx��xx��
		public String queryScheduleByTimeInfoR(String sTime, int userId) {

			String url = HttpUtil.BASE_URL
					+ "EventListByUserIdAndNowDateR.action?scheduleEvent.eventTime="
					+ sTime + "&user.userId=" + userId;
			return HttpUtil.queryStringForPost(url);

		}

	public String getPushMsgStatus(int userid, int modulerid, int messageid) 
	{
		String url = HttpUtil.BASE_URL + "getPushMsgDetail?pushMsg.userid="
				+ userid + "&pushMsg.modulerid=" + modulerid
				+ "&pushMsg.messageid=" + messageid;
		return HttpUtil.queryStringForPost(url);
	}

	// �û�id
	public String queryScheduleByUserId(int userId) {
		String url = HttpUtil.BASE_URL + "EventListByUserId?user.userId="
				+ userId;
		return HttpUtil.queryStringForPost(url);

	}

	// �����û�id
	public String EventClickListByUserId(int userId) {
		String url = HttpUtil.BASE_URL + "EventClickListByUserId?user.userId="
				+ userId;
		return HttpUtil.queryStringForPost(url);

	}

	// �¼�ID
	public String queryScheduleByEventId(String eventId) {
		String url = HttpUtil.BASE_URL
				+ "EventListByEventId.action?scheduleEvent.eventId=" + eventId;
		return HttpUtil.queryStringForPost(url);

	}

	// �����¼�ID��ظ�
	public String FindFeedbackByEventId(String eventId) {
		String url = HttpUtil.BASE_URL
				+ "FindFeedbackByEventId?scheduleEvent.eventId=" + eventId;
		return HttpUtil.queryStringForPost(url);

	}

	// ȫ���û�
	public String AllUser() {
		String url = HttpUtil.BASE_URL + "AllUser";
		// AllUserAndDepartment
		return HttpUtil.queryStringForPost(url);

	}

	// ȫ���û�
	public String AllUserAndDepartment() {
		String url = HttpUtil.BASE_URL + "AllUserAndDepartment";
		// AllUserAndDepartment
		return HttpUtil.queryStringForPost(url);

	}

	// ȫ���û�
	public String FindByName(String name) {
		String url = HttpUtil.BASE_URL + "FindByName_mobi?user.name=" + name;
		return HttpUtil.queryStringForPost(url);
	}

	// ȫ���û�����tel
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

	// ȫ���û�����tel
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

	// ȫ���û�����tel ͨ��id
	public String AllUserAndDepartmentById(String id) {

		String url = HttpUtil.BASE_URL
				+ "AllUserAndDepartmentById?user.userId=" + id;
		// AllUserAndDepartment
		return HttpUtil.queryStringForPost(url);

	}

	// �ճ�����
	public String EventKindList() {
		String url = HttpUtil.BASE_URL + "EventKindList";
		return HttpUtil.queryStringForPost(url);

	}

	// �ۺ���̬
	public String showPortDynmicNews(String page) {
		String url = HttpUtil.BASE_URL + "captureHtml?" + page;
		return HttpUtil.queryStringForPost(url);

	}

	// �ۺ���̬
	public String seePortDynmicNews(String url) {
		String url1 = HttpUtil.BASE_URL + "findDetailInfo?url=" + url;
		return HttpUtil.queryStringForPost(url1);

	}

	// �㽭�ۺ���̬
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

	// �㽭�ۺ���̬see
	public String seeDynmicNews(String url) {
		String url1 = HttpUtil.BASE_URL + "dynmicNewsfindDetailInfo?url=" + url;
		return HttpUtil.queryStringForPost(url1);

	}

	// �㽭�ۺ��ۺ�
	public String showComprehensive(String page) {
		String url = HttpUtil.BASE_URL + "comprehensiveCaptureHtml?" + page;
		return HttpUtil.queryStringForPost(url);

	}

	// �㽭�ۺ��ۺ�see
	public String seeComprehensive(String url) {
		String url1 = HttpUtil.BASE_URL + "comprehensiveFindDetailInfo?url="
				+ url;
		return HttpUtil.queryStringForPost(url1);

	}

	// ֪ͨ����
	public String showPortNotice(String page) {
		String url = HttpUtil.BASE_URL + "captureHtmlPortNotice?" + page;
		return HttpUtil.queryStringForPost(url);

	}

	// ֪ͨ����
	public String seePortNotice(String url) {
		String url1 = HttpUtil.BASE_URL + "findDetailInfoPortNotice?url=" + url;
		return HttpUtil.queryStringForPost(url1);
	}

	// �ճ�ɾ��
	public String deleteEvent(String eventId) {
		String url = HttpUtil.BASE_URL
				+ "deleteByEventId?scheduleEvent.eventId=" + eventId;
		return HttpUtil.queryStringForPost(url);

	}

	// ����
	public String FindAttachmentByEventId(String eventId) {
		String url = HttpUtil.BASE_URL
				+ "FindAttachmentByEventId?scheduleEvent.eventId=" + eventId;
		return HttpUtil.queryStringForPost(url);

	}

	// ɾ������
	public String DeleteAttachmentByEventId(String eventId, String attachmentId) {
		String url = HttpUtil.BASE_URL
				+ "deleteAttachmentIdByAttachmentId?scheduleEvent.eventId="
				+ eventId + "&scheduleAttachment.attachmentId=" + attachmentId;
		return HttpUtil.queryStringForPost(url);

	}

	// �����·��������
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

	// Υ��Ե�ɱ�
	public String showIllegalReasonList() {
		String url = HttpUtil.BASE_URL + "showIllegalReasonList";
		return HttpUtil.queryStringForPost(url);

	}

	// Υ��Ե���б�
	public String showIllegalList(String date) {
		String url = HttpUtil.BASE_URL + "showIllegalList_mobi?" + date;
		return HttpUtil.queryStringForPost(url);
	}

	// Υ��Ե���б�-δ���
	public String showIllegalListByCheck(String date) {
		String url = HttpUtil.BASE_URL + "showIllegalListByCheck_mobi?" + date;
		return HttpUtil.queryStringForPost(url);

	}

	// Υ����Ϣ
	public String showIllegalInfo(String illegalId) {
		String url = HttpUtil.BASE_URL
				+ "showInfoByIllegalId?illegal.illegalId=" + illegalId;
		return HttpUtil.queryStringForPost(url);

	}

	// Υ�������Ϣ
	public String showCheckIllegalInfo(String illegalId) {
		String url = HttpUtil.BASE_URL
				+ "showCheckInfoByIllegalId?illegal.illegalId=" + illegalId;
		return HttpUtil.queryStringForPost(url);

	}

	// Υ�´����������
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

	// �����������
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

	// ������ҵ�������
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

	// Υ�²�����Ϣ
	public String showIllegalUpdateInfo(String illegalId) {
		String url = HttpUtil.BASE_URL
				+ "showSupplementByIllegalId?illegal.illegalId=" + illegalId;
		return HttpUtil.queryStringForPost(url);
	}

	// Υ�¸���
	public String showEvidenceByIllegalId(String illegalId) {
		String url = HttpUtil.BASE_URL
				+ "showEvidenceByIllegalId?illegal.illegalId=" + illegalId;
		return HttpUtil.queryStringForPost(url);
	}

	// Υ��λ����Ϣ
	public String showLocationByIllegalId(String illegalId) {
		String url = HttpUtil.BASE_URL
				+ "showLocationByIllegalId?illegal.illegalId=" + illegalId;
		return HttpUtil.queryStringForPost(url);
	}

	// Υ��Ե���б�
	public String showInspectionList(String date) {
		String url = HttpUtil.BASE_URL + "showInspectionList_mobi?" + date;
		return HttpUtil.queryStringForPost(url);

	}

	// ��ɸ���
	public String showEvidenceByInspectionId(String date) {
		String url = HttpUtil.BASE_URL
				+ "showInspectionEvidenceByInspectionId?inspectionId=" + date;
		return HttpUtil.queryStringForPost(url);

	}

	// ���λ��
	public String showLocationByInspectionId(String date) {
		String url = HttpUtil.BASE_URL
				+ "showLocationByInspectionId?inspectionId=" + date;
		return HttpUtil.queryStringForPost(url);

	}

	// ��ɲ������
	public String showInspectionUpdateInfo(String date) {
		String url = HttpUtil.BASE_URL
				+ "showInspectionSupplementByInspectionId?inspectionId=" + date;
		return HttpUtil.queryStringForPost(url);

	}

	// ������
	public String showInspectionInfo(String date) {
		String url = HttpUtil.BASE_URL
				+ "showInspectionInfoByInspectionId?inspectionId=" + date;
		return HttpUtil.queryStringForPost(url);

	}

	// ���������
	public String showCheckInspectionInfo(String date) {
		String url = HttpUtil.BASE_URL
				+ "showInspectionCheckInfoByInspectionId?inspectionId=" + date;
		return HttpUtil.queryStringForPost(url);

	}

	// ���δ����б�
	public String showInspectionListByCheck(String date) {
		String url = HttpUtil.BASE_URL + "showInspectionListByCheck_mobi?"
				+ date;
		return HttpUtil.queryStringForPost(url);

	}

	// ֪ʶ��ȫ������
	public String GetKnowledge() {
		String url = HttpUtil.BASE_URL + "showKnowLedge?maxPage=10000";

		return HttpUtil.queryStringForPost(url);
	}

	// ֪ʶ��ȫ������
	public String GetKnowledgeadd() {
		String url = HttpUtil.BASE_URL + "showKnowLedge?maxPage=10000";

		return HttpUtil.queryStringForPost(url);
	}

	// ֪ʶ�����
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

	// ֪ʶ���Ƿ�������
	public String GetKnowledgeIslink(String id) {
		String url = HttpUtil.BASE_URL + "KnowLedgeIsLink?kindID=" + id;
		return HttpUtil.queryStringForPost(url);
	}

	// ��ʾ���
	public String Showleave(String userid) {
		String url = HttpUtil.BASE_URL + "Leave1?userid=" + userid;
		return HttpUtil.queryStringForPost(url);
	}

	// ��ʾ�Ӱ�
	public String Showovertime(String userid) {
		String url = HttpUtil.BASE_URL + "Overtime1?userid=" + userid;
		return HttpUtil.queryStringForPost(url);
	}

	// ��ʾȫ��
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

	// ��ʾ�Լ�������
	public String LeaveAndOvertimefinish(String userid) {
		String url = HttpUtil.BASE_URL + "LeaveAndOvertimefinish?userid="
				+ userid;
		return HttpUtil.queryStringForPost(url);
	}

	// ��ʾ�Լ�δ������
	public String LeaveAndOvertimeunfinish(String userid) {
		String url = HttpUtil.BASE_URL + "LeaveAndOvertimeunfinish?userid="
				+ userid;
		return HttpUtil.queryStringForPost(url);
	}

	// ��ʾ�Լ������
	public String LeaveAndOvertiemALLbymy(String userid) {
		String url = HttpUtil.BASE_URL + "LeaveAndOvertiemALLbymy?userid="
				+ userid;
		return HttpUtil.queryStringForPost(url);
	}

	// ��ʾ�Լ�δ������
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

	// ��ʾ�Լ������
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

	// �����Լ������
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

	// ����ȫ����
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

	// �����ʾ����
	public String Showneirong(String id) {
		String url = HttpUtil.BASE_URL
				+ "showLeaveAndOvertimeApproval?leaveOrOtID1=" + id;
		return HttpUtil.queryStringForPost(url);
	}

	// ��ʾ�������
	public String LeaveandovertimeKindType(String name) {
		String url = HttpUtil.BASE_URL + "LeaveAndOvertimeKindName?kindType="
				+ name;
		return HttpUtil.queryStringForPost(url);
	}

	// ��ͷѲ���б�
	public String showPatrolList(String date) {
		String url = HttpUtil.BASE_URL + "showPatrolList_mobi?" + date;
		return HttpUtil.queryStringForPost(url);

	}

	// ��ͷѲ����ϸ���
	public String showPatrolInfo(String date) {
		String url = HttpUtil.BASE_URL + "showPatrolInfoByPatrolId?patrolId="
				+ date;
		return HttpUtil.queryStringForPost(url);

	}

	// ��ͷѲ�鲹�����
	public String showPatrolUpdateInfo(String date) {
		String url = HttpUtil.BASE_URL
				+ "showPatrolSupplementByPatrolId?patrolId=" + date;
		return HttpUtil.queryStringForPost(url);

	}

	// ��ͷѲ�鸽��
	public String showEvidenceByPatrolId(String date) {
		String url = HttpUtil.BASE_URL
				+ "showPatrolEvidenceByPatrolId?patrolId=" + date;
		return HttpUtil.queryStringForPost(url);

	}

	// ��ʾΣ��Ʒ����
	public String ShowDangerousgoodsportln() {
		String url = HttpUtil.BASE_URL + "showDangerousGoodsPortln1";
		return HttpUtil.queryStringForPost(url);
	}

	// ǩ��ǩ���б�
	public String ShowAttendaceList(String date) {
		String url = HttpUtil.BASE_URL + "showSignInfo?" + date;
		return HttpUtil.queryStringForPost(url);
	}

	// ǩ������
	public String ShowAttendaceListInId(String signID) {
		String url = HttpUtil.BASE_URL + "seeSignInfoID?sign.signID=" + signID;
		return HttpUtil.queryStringForPost(url);
	}

	// // ǩ������
	// public String ShowAttendaceListBackId(String date) {
	// String url = HttpUtil.BASE_URL + "showSignInfo?" + date;
	// return HttpUtil.queryStringForPost(url);
	// }
	// ���е��ӱ���
	public String addElectricReport() {
		String url = HttpUtil.BASE_URL + "addElectricReportInfo";
		return HttpUtil.queryStringForPost(url);
	}

	// ���е��ӱ����б�
	public String ShowElectricReport(String param) throws Exception {
		String url = HttpUtil.BASE_URL + "showElectricReportInfoAD";
		return new HttpFileUpTool().sendPost1(url, param);
	}

	// ���е��ӱ���鿴
	public String showElectricReportID(String reportID) {
		String url = HttpUtil.BASE_URL
				+ "showElectricReportID?electricReport.reportID=" + reportID;
		return HttpUtil.queryStringForPost(url);
	}

	// ����ǩ֤
	public String addElectricVisa() {
		String url = HttpUtil.BASE_URL + "addElectricVisaInfo";
		return HttpUtil.queryStringForPost(url);
	}

	// ����ǩ֤�б�
	public String ShowElectricVisa(String param) throws Exception {
		String url = HttpUtil.BASE_URL + "showElectricVisaInfoAD";
		return new HttpFileUpTool().sendPost1(url, param);
	}

	// ����ǩ֤�鿴
	public String showElectricVisaID(String visaID) {
		String url = HttpUtil.BASE_URL
				+ "showElectricVisaID?electricVisa.visaID=" + visaID;
		return HttpUtil.queryStringForPost(url);
	}

	// ����ǩ֤�����б�
	public String ShowFixedTermTeport(String param) throws Exception {
		String url = HttpUtil.BASE_URL + "findFixedTermReportInfo";
		return new HttpFileUpTool().sendPost1(url, param);
	}

	// ����ǩ֤���α����б�
	public String ShowFixedTermTeportList(String param) throws Exception {
		String url = HttpUtil.BASE_URL + "findRegularVisaById";
		return new HttpFileUpTool().sendPost1(url, param);
	}

	// ����ǩ֤���α����б�
	public String ShowFixedTermTeportListSearch(String param) throws Exception {
		String url = HttpUtil.BASE_URL + "findRegularVisaInfo";
		return new HttpFileUpTool().sendPost1(url, param);
	}

	// ����ǩ֤���α���鿴
	public String showFixedTermReportSee(String regularvisaID) {
		String url = HttpUtil.BASE_URL
				+ "showRegularVisaID?regularVisa.regularvisaID="
				+ regularvisaID;
		return HttpUtil.queryStringForPost(url);
	}

	// ����Σ��Ʒ����
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

	// �鿴Σ��Ʒ����
	public String showDangerousGoodsPortlnApproval(String declareID) {
		String url = HttpUtil.BASE_URL
				+ "showDangerousGoodsPortlnApproval?declareID=" + declareID;
		return HttpUtil.queryStringForPost(url);
	}

	// ��ʾΣ��Ʒ���� //ֻ��ʾ�Լ�
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

	// ����Σ��Ʒ���� //ֻ��ʾ�Լ�
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

	// ��ʾΣ��Ʒ��ͷ��ҵ
	public String showDangerousGoodsJob() {
		String url = HttpUtil.BASE_URL + "showDangerousGoodsJob1";
		return HttpUtil.queryStringForPost(url);
	}

	// ����Σ��Ʒ��ͷ��ҵ
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

	// �鿴Σ��Ʒ��ͷ��ҵ
	public String showDangerousGoodsJobApproval(String declareID) {
		String url = HttpUtil.BASE_URL
				+ "showDangerousGoodsJobApproval?declareID=" + declareID;
		return HttpUtil.queryStringForPost(url);
	}

	// ��ʾΣ��Ʒ��ͷ��ҵ //ֻ��ʾ�Լ�
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

	// ����Σ��Ʒ��ͷ��ҵ //ֻ��ʾ�Լ�
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

	// �鿴��ҵ��ͷ����
	public String findWharfJobNum() {
		String url = HttpUtil.BASE_URL + "findWharfJobNum";
		return HttpUtil.queryStringForPost(url);
	}

	// �鿴��ҵ��ͷ����
	public String selectWharfJobNum(String content) {
		try {
			content = URLEncoder.encode(content, "UTF-8");
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}
		String url = HttpUtil.BASE_URL + "selectWharfJobNum?content=" + content;
		return HttpUtil.queryStringForPost(url);
	}

	// Ѳ����־ δ���
	public String showCruiseLogUnfinish() {
		String url = HttpUtil.BASE_URL + "showCruiseLogUnfinish";
		return HttpUtil.queryStringForPost(url);
	}

	// Ѳ����־ ���
	public String showCruiseLogFinish() {
		String url = HttpUtil.BASE_URL + "showCruiseLogFinish";
		return HttpUtil.queryStringForPost(url);
	}

	// //Ѳ����־ δ��� ����
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

	// Ѳ����־ ��� ����
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

	// Ѳ����־ ��� �鿴
	public String showCruiseLogAndIllegal(String id) {

		String url = HttpUtil.BASE_URL + "showCruiseLogAndIllegal?cruiseLogID="
				+ id;
		return HttpUtil.queryStringForPost(url);
	}

	// Ѳ����־ ��� �鿴
	public String showCruiseLogIllegalList(String illid) {

		String url = HttpUtil.BASE_URL + "showCruiseLogIllegalList?illid="
				+ illid;
		return HttpUtil.queryStringForPost(url);
	}

	// Ѳ����־ λ�� �鿴
	public String showMap(String illid) {

		String url = HttpUtil.BASE_URL + "showMap?cruiseLogID=" + illid;
		return HttpUtil.queryStringForPost(url);
	}

	// ͨѶ¼���
	public String newaddresslist(String departmentId) {

		String url = HttpUtil.BASE_URL + "newaddresslist?departmentId="
				+ departmentId;
		return HttpUtil.queryStringForPost(url);
	}
	// ͨѶ¼���
		public String newaddresslistmeeting(String departmentId) {

			String url = HttpUtil.BASE_URL + "newaddresslistmeeting?departmentId="
					+ departmentId;
			return HttpUtil.queryStringForPost(url);
		}

	// ͨѶ¼���
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

	// ֪ʶ����
	public String newknowledgelist(String departmentId) {

		String url = HttpUtil.BASE_URL + "newknowledgelist?kindID="
				+ departmentId;
		return HttpUtil.queryStringForPost(url);
	}

	// ֪ʶ����
	public String newknowledgelistall(String departmentId) {

		String url = HttpUtil.BASE_URL
				+ "newknowledgelistall?isinner=1&kindID=" + departmentId;
		return HttpUtil.queryStringForPost(url);
	}

	// ����֪ʶ����
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

	/**
	 * ��ȡ������ʱ��
	 * 
	 * @return
	 */
	public String GetServiceTime() {

		String url = HttpUtil.BASE_URL + "GetServiceTime";
		return HttpUtil.queryStringForPost(url);
	}

	// ��ʾ�Լ������� �豸
	public String equipmentApprovalbyMy(String userid) {
		String url = HttpUtil.BASE_URL + "equipmentApprovalbyMy?equipmentID="
				+ userid;
		return HttpUtil.queryStringForPost(url);
	}

	// ��ʾ�Լ������ �豸
	public String equipmentApply(String userid) {
		String url = HttpUtil.BASE_URL + "equipmentApply?equipmentID=" + userid;
		return HttpUtil.queryStringForPost(url);
	}

	// ��ʾȫ���� �豸
	public String equipmentAll() {
		String url = HttpUtil.BASE_URL + "equipmentAll";
		return HttpUtil.queryStringForPost(url);
	}

	// �鿴
	public String showEquipmentApproval(String id) {
		String url = HttpUtil.BASE_URL + "showEquipmentApproval?equipmentID="
				+ id;
		return HttpUtil.queryStringForPost(url);
	}

	// ����
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

	// ��ʾȫ����������Ʒ �豸
	public String equipmentkindAll() {
		String url = HttpUtil.BASE_URL + "equipmentkindAll";
		return HttpUtil.queryStringForPost(url);
	}

	// ��ѯ�ӷ������˵õ�����δ���͹�����Ϣ
	public String showUnpushedMsgList() {
		String url = HttpUtil.BASE_URL + "FindUnpushedMsg";
		return HttpUtil.queryStringForPost(url);
	}

	// ��ʾ������
	public String LeaveOrOtApproval(String userid) {
		String url = HttpUtil.BASE_URL + "LeaveOrOtApproval?userid=" + userid;
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

	// �ֻ���־
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

	// ��ͷ�б�
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
	// ������λ��
	public String ShipSearchNicecode(String id) {
		String name="";
		try {
			name = URLEncoder.encode(id, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String url="";
		try {
			//url = HttpUtil.BASE_URL+"queryShipnameByShipname?ais.name=" + name;
			url = "http://192.168.1.124:6080/HuZhouPort/"+"queryShipnameByShipname?ais.name=" + name;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				//"http://192.168.1.124:6080/HuZhouPort/"+ 
		return HttpUtil.queryStringForPost(url);
	}
   // ������λ��
    public String ShipSearchNicecodeAdd(String name,String num) {
    	try {
			name = URLEncoder.encode(name, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String url = HttpUtil.BASE_URL + "saveAisByShipname?ais.name=" + name+"&&ais.num="+num;
		//String url = "http://192.168.1.124:6080/HuZhouPort/"+ "saveAisByShipname?ais.name=" + name+"&&ais.num="+num;
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
