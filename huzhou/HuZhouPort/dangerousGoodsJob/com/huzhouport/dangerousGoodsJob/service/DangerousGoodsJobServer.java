package com.huzhouport.dangerousGoodsJob.service;

import java.util.List;

import com.huzhouport.dangerousGoodsJob.model.DangerousWorkDeclare;
import com.huzhouport.dangerousGoodsJob.model.DangerousWorkDeclareBean;
import com.huzhouport.dangerousGoodsJob.model.WharfJobNum;
import com.huzhouport.dangerousgoodsportln.model.Port;

import com.huzhouport.log.model.PageModel;

public interface DangerousGoodsJobServer {

	public PageModel<DangerousWorkDeclareBean> findByScrollServer(
			int currentPage, int maxPage); // 危险品作业显示

	public PageModel<DangerousWorkDeclareBean> findByScrollServer1(
			int currentPage, int maxPage, String content); // 危险品申报显示

	public DangerousWorkDeclareBean findByID(String declareID);// 通过id 查找

	public void updateByID(String declareID, String userid,
			String reviewResult, String reviewOpinion);// 通过id 查找

	public void insert(DangerousWorkDeclare dangerousWorkDeclare); // 新建

	public List<DangerousWorkDeclareBean> findList(); // 危险品码头作业申报显示 手机端

	public List<DangerousWorkDeclareBean> findList1(String content); // 危险品码头作业申报查找手机端

	public List<DangerousWorkDeclareBean> findListByname(String shipName); // 危险品码头作业申报显示
																			// 手机端
																			// 船户版

	public List<DangerousWorkDeclareBean> findListByname1(String content,
			String shipName); // 危险品码头作业申报查找手机端 船户版

	public List<DangerousWorkDeclareBean> findListForWharf(String wharfName,
			String content); 

	public List<WharfJobNum> findWharfJobNum();

	public List<WharfJobNum> selectWharfJobNum(String content);

	public List<Port> findStartingPortName(String name);

	public void savePort(Port port);
}
