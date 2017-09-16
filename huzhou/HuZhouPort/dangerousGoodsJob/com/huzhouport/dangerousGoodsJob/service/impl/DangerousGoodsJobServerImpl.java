package com.huzhouport.dangerousGoodsJob.service.impl;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import com.huzhouport.dangerousGoodsJob.dao.Dao;
import com.huzhouport.dangerousGoodsJob.model.DangerousWorkDeclare;
import com.huzhouport.dangerousGoodsJob.model.DangerousWorkDeclareBean;
import com.huzhouport.dangerousGoodsJob.model.WharfJobNum;
import com.huzhouport.dangerousGoodsJob.service.DangerousGoodsJobServer;
import com.huzhouport.dangerousgoodsportln.model.Port;

import com.huzhouport.log.model.PageModel;

public class DangerousGoodsJobServerImpl implements DangerousGoodsJobServer {
	private Dao dao;

	public void setDao(Dao dao) {
		this.dao = dao;
	}

	public PageModel<DangerousWorkDeclareBean> findByScrollServer(
			int currentPage, int maxPage) { //
		String hql = "from DangerousWorkDeclare d  order by d.declareTime desc";
		return this.dao.findByPageScroll(hql, currentPage, maxPage);
	}

	public PageModel<DangerousWorkDeclareBean> findByScrollServer1(
			int currentPage, int maxPage, String content) { //

		// String hql
		// ="from Port p1,DangerousArrivalDeclare d,Port p2 where p1.portID=d.startingPort and p2.portID=d.arrivalPort and(d.shipName like '%"+content+"%' or p1.portName like '%"+content+"%' or p2.portName like '%"+content+"%' or d.declareTime like '%"+content+"%' or d.cargoTypes like '%"+content+"%' ) order by d.reviewResult ";
		String hql = "from DangerousWorkDeclare d where (d.shipName like '%"
				+ content + "%' or d.wharfID like '%" + content
				+ "%' or d.cargoTypes like '%" + content
				+ "%') order by d.declareTime desc";
		return this.dao.findByPageScroll(hql, currentPage, maxPage);
	}

	public DangerousWorkDeclareBean findByID(String declareID) {
		// String hql
		// ="from Port p1,DangerousArrivalDeclare d,Port p2 where p1.portID=d.startingPort and p2.portID=d.arrivalPort and d.declareID="+declareID;
		String hql = "from DangerousWorkDeclare d  , Port p1 , Port p2 where p1.portID=d.startingPort and p2.portID=d.arrivalPort  and d.declareID="
				+ declareID;
		return this.dao.findByID(hql);
	}

	public void updateByID(String declareID, String userid,
			String reviewResult, String reviewOpinion) {
		Date now = new Date();
		DateFormat d8 = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,
				DateFormat.MEDIUM); // 显示日期，时间（精确到分）
		String declareTime = d8.format(now);
		// String hql
		// ="update DangerousArrivalDeclare d set d.reviewUser="+userid+" ,d.reviewResult="+reviewResult+"  where  d.declareID="+declareID;
		String hql = "update DangerousWorkDeclare d set d.reviewUser=" + userid
				+ " ,d.reviewResult=" + reviewResult + ",d.reviewOpinion='"
				+ reviewOpinion + "',d.reviewTime='" + declareTime
				+ "'  where  d.declareID=" + declareID;
		dao.update(hql);
	}

	public void insert(DangerousWorkDeclare dangerousWorkDeclare) {
		dao.insert(dangerousWorkDeclare);
	}

	public List<DangerousWorkDeclareBean> findList() { // 危险品码头作业申报显示 手机端
		String hql = "from DangerousWorkDeclare d order by d.declareTime desc ";
		return this.dao.findList(hql);
	}

	public List<DangerousWorkDeclareBean> findList1(String content) { // 危险品码头作业申报查找手机端
		try {
			content = new String(content.getBytes("ISO8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String hql = "from DangerousWorkDeclare d where  (d.shipName like '%"
				+ content + "%' or d.wharfID like '%" + content
				+ "%' ) order by d.declareTime desc";
		return this.dao.findList(hql);
	}

	public List<DangerousWorkDeclareBean> findListByname(String shipName) { // 危险品码头作业申报显示
		// 手机端
		// 船户版
		try {

			shipName = new String(shipName.getBytes("ISO8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String hql = "from DangerousWorkDeclare d where   d.shipName='"
				+ shipName + "' order by d.declareTime desc";
		return this.dao.findList(hql);
	}

	public List<DangerousWorkDeclareBean> findListByname1(String content,
			String shipName) { // 危险品码头作业申报查找手机端 船户版
		try {
			content = new String(content.getBytes("ISO8859-1"), "UTF-8");
			shipName = new String(shipName.getBytes("ISO8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String hql = "from DangerousWorkDeclare d  where  (d.shipName like '%"
				+ content + "%' or d.wharfID like '%" + content
				+ "%') and d.shipName='" + shipName
				+ "'  order by d.declareTime desc";
		return this.dao.findList(hql);
	}

	public List<WharfJobNum> findWharfJobNum() {
		String hql = "from WharfJobNum ";

		return this.dao.findWharfJobNum(hql);
	}

	public List<WharfJobNum> selectWharfJobNum(String content) {
		try {
			content = new String(content.getBytes("ISO8859-1"), "UTF-8");

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String hql = "from WharfJobNum w where w.wharfName like'%" + content
				+ "%'";

		return this.dao.findWharfJobNum(hql);
	}

	public List<Port> findStartingPortName(String name) {
		String hql = "from Port p where p.portName='" + name + "'";

		System.out.println("hql=" + hql);
		return this.dao.findStartingPortName(hql);
	}

	public void savePort(Port port) {
		dao.savePort(port);
	}

	public List<DangerousWorkDeclareBean> findListForWharf(String wharfName,
			String wharfContent) {
		try {
			wharfName = new String(wharfName.getBytes("ISO8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String hql = "from DangerousWorkDeclare d where d.wharfID='"
				+ wharfName + "'";

		if (wharfContent != null && !wharfContent.equals("")) {
			try {
				wharfContent = new String(wharfContent.getBytes("ISO8859-1"),
						"UTF-8");
				
				hql+=" and (d.shipName like '%"
						+ wharfContent + "%' or d.wharfID like '%" + wharfContent
						+ "%')";
				
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		hql+=" order by d.declareTime desc";
		return this.dao.findList(hql);
	}
}
