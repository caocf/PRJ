package com.huzhouport.portDynmicNews.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.huzhouport.common.action.BaseAction;
import com.huzhouport.portDynmicNews.dao.BaseQueryRecords;
import com.huzhouport.portDynmicNews.dao.IndustryInfoDao;
import com.huzhouport.portDynmicNews.model.IndustryInfo;
import com.huzhouport.pushmsg.model.PushMsg;
import com.huzhouport.pushmsg.service.PushMsgService;

@SuppressWarnings("all")
public class IndustryInfoAction extends BaseAction {
	private IndustryInfoDao industryInfoDao;
	private PushMsgService pushMsgService;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int page = -1;
	private int rows = -1;
	private int id = -1;
	private List<IndustryInfo> list;
	private IndustryInfo industryinfo;
	private int result;
	private String ids;
	private String msg;

	private String searchstring;

	int pages;
	int total;

	// 添加
	public String addIndustryInfo() throws Exception {
		if (industryinfo == null
				|| industryinfo.getTitle().equals("")
				|| industryinfo.getContent().equals("")
				|| (industryinfo.getOriobj() != IndustryInfo.oriobj_both
						&& industryinfo.getOriobj() != IndustryInfo.oriobj_inner && industryinfo
						.getOriobj() != IndustryInfo.oriobj_public)) {
			result = -1;
			msg = "输入信息有误";
		} else {
			IndustryInfo info = new IndustryInfo();

			info.setContent(industryinfo.getContent());
			info.setOriobj(industryinfo.getOriobj());
			info.setTitle(industryinfo.getTitle());
			info.setUpdatetime(new Date());

			industryinfo = this.industryInfoDao.addIndustryInfo(info);
			result = 0;

			// 添加推送消息
			PushMsg pushMsg = new PushMsg();
			pushMsg.setMessageid(info.getId());
			pushMsg.setModulerid(PushMsg.MODULERID_INDUSTRYINFO);
			pushMsg.setPushmsgtime(new Date());
			if (info.getOriobj() == IndustryInfo.oriobj_both)
				pushMsg.setUserid(PushMsg.USERID_BOTH);
			if (info.getOriobj() == IndustryInfo.oriobj_inner)
				pushMsg.setUserid(PushMsg.USERID_INNER);
			if (info.getOriobj() == IndustryInfo.oriobj_public)
				pushMsg.setUserid(PushMsg.USERID_PUBLIC);
			pushMsg.setMsgstatus(PushMsg.MSGSTATUS_NOTPUSH_NOTREAD);

			this.pushMsgService.add(pushMsg);

			msg = "添加成功";
		}
		return SUCCESS;
	}

	// 更新
	public String updateIndustryInfo() throws Exception {
		if (industryinfo == null
				|| industryinfo.getTitle().equals("")
				|| industryinfo.getContent().equals("")
				|| (industryinfo.getOriobj() != IndustryInfo.oriobj_both
						&& industryinfo.getOriobj() != IndustryInfo.oriobj_inner && industryinfo
						.getOriobj() != IndustryInfo.oriobj_public)) {
			result = -1;
			msg = "输入信息有误";
		} else {
			IndustryInfo info = this.industryInfoDao
					.findIndustryInfoDetail(industryinfo.getId());
			if (info == null) {
				result = -1;
				msg = "无法找到该信息";
			} else {
				info.setContent(industryinfo.getContent());
				info.setOriobj(industryinfo.getOriobj());
				info.setTitle(industryinfo.getTitle());
				info.setUpdatetime(new Date());

				this.industryInfoDao.updateIndustryInfo(info);

				List<Integer> messageids = new ArrayList<Integer>();
				messageids.add(info.getId());
				Map<String, PushMsg> pushMsgs = this.pushMsgService
						.getPushMsgs(PushMsg.MODULERID_INDUSTRYINFO, -1,
								messageids);
				if (pushMsgs != null && pushMsgs.size() > 0) {
					PushMsg msg = pushMsgs.get(0);

					msg.setMsgstatus(PushMsg.MSGSTATUS_NOTPUSH_NOTREAD);
					msg.setUserid(-info.getOriobj());
					msg.setPushmsgtime(new Date());
					this.pushMsgService.updatePushMsg(msg);
				}
				result = 0;
				msg = "更新成功";
			}
		}
		return SUCCESS;
	}

	// 删除
	public String deleteIndustryInfo() {
		this.industryInfoDao.deleteIndustryInfo(id);
		return SUCCESS;
	}

	// 查找所有，不区分内部外部
	public String findIndustryInfoList() {
		BaseQueryRecords records = null;
		if (searchstring == null) {
			records = this.industryInfoDao.findIndustryInfoList(page, rows);
		} else {
			records = this.industryInfoDao.searchIndustryInfoList(searchstring,
					page, rows);
		}

		this.list = (List<IndustryInfo>) records.getList();
		this.pages = records.getPages();
		this.total = records.getTotal();
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setContent("");
		}
		return SUCCESS;
	}

	// 查找内部及全部的
	public String findInnerIndustryInfoList() {
		BaseQueryRecords records = this.industryInfoDao
				.findInnerIndustryInfoList(page, rows);

		this.list = (List<IndustryInfo>) records.getList();
		this.pages = records.getPages();
		this.total = records.getTotal();
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setContent("");
		}
		return SUCCESS;
	}

	// 查找外部及全部的
	public String findPublicIndustryInfoList() {
		BaseQueryRecords records = this.industryInfoDao
				.findPublicIndustryInfoList(page, rows);

		this.list = (List<IndustryInfo>) records.getList();
		this.pages = records.getPages();
		this.total = records.getTotal();
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setContent("");
		}
		return SUCCESS;
	}

	// 获得详情
	public String findIndustryInfoDetail() {
		industryinfo = this.industryInfoDao.findIndustryInfoDetail(id);
		return SUCCESS;
	}

	public void setIndustryInfoDao(IndustryInfoDao industryInfoDao) {
		this.industryInfoDao = industryInfoDao;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public List<IndustryInfo> getList() {
		return list;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setList(List<IndustryInfo> list) {
		this.list = list;
	}

	public IndustryInfo getIndustryinfo() {
		return industryinfo;
	}

	public void setIndustryinfo(IndustryInfo industryinfo) {
		this.industryinfo = industryinfo;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public String getSearchstring() {
		return searchstring;
	}

	public void setSearchstring(String searchstring) {
		this.searchstring = searchstring;
	}

	public void setPushMsgService(PushMsgService pushMsgService) {
		this.pushMsgService = pushMsgService;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
}
