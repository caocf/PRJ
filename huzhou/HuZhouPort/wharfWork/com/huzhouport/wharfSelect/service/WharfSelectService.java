package com.huzhouport.wharfSelect.service;

import java.util.ArrayList;
import java.util.List;

import com.huzhouport.wharfSelect.dao.PieceAreaDao;
import com.huzhouport.wharfSelect.dao.PortAreaDao;
import com.huzhouport.wharfSelect.dao.WharfItemDao;
import com.huzhouport.wharfSelect.model.PieceArea;
import com.huzhouport.wharfSelect.model.PortArea;
import com.huzhouport.wharfSelect.model.WharfItem;
import com.huzhouport.wharfSelect.service.webservice.WharfWebservice;
import com.huzhouport.wharfSelect.service.webservice.model.WharfWebServiceNode;

public class WharfSelectService {
	private PieceAreaDao pieceAreaDao;
	private PortAreaDao portAreaDao;
	private WharfItemDao wharfItemDao;

	public void setPieceAreaDao(PieceAreaDao pieceAreaDao) {
		this.pieceAreaDao = pieceAreaDao;
	}

	public void setPortAreaDao(PortAreaDao portAreaDao) {
		this.portAreaDao = portAreaDao;
	}

	public void setWharfItemDao(WharfItemDao wharfItemDao) {
		this.wharfItemDao = wharfItemDao;
	}

	/**
	 * 获得港区列表
	 * 
	 * @return
	 */
	public List<PortArea> findPortAreas() {
		List<PortArea> list = new ArrayList<PortArea>();
		try {
			list = this.portAreaDao.queryAllData(new PortArea());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 获得某港区的片区
	 * 
	 * @param portareaid
	 * @return
	 */
	public List<PieceArea> findPieceAreas(int portareaid) {
		List<PieceArea> list = new ArrayList<PieceArea>();
		try {
			list = this.pieceAreaDao.queryDataByConditions(new PieceArea(),
					"portareaid", portareaid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 获得某片区的码头列表
	 */
	public List<WharfItem> findWharfItems(int pieceareaid) {
		List<WharfItem> list = new ArrayList<WharfItem>();
		try {
			list = this.wharfItemDao.queryDataByConditions(new WharfItem(),
					"pieceareaid", pieceareaid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 搜索
	 * 
	 * @return
	 */
	public List<WharfItem> searchWharfItems(int portareaid, int pieceareaid,
			String wharfname) {
		return this.wharfItemDao.searchWharfItems(portareaid, pieceareaid,
				wharfname);
	}

	public void addAllPortsFromWebService() {
		List<WharfWebServiceNode> nodes = WharfWebservice.getAllPort();
		for (int i = 0; i < nodes.size(); i++) {
			// webservice中取得的数据
			WharfWebServiceNode node = nodes.get(i);

			// 寻找数据库中是否已经有这个码头数据，如果有，则进行更新，如果没有，则添加
			boolean existwharfItem = false;

			WharfItem wharfItem = this.wharfItemDao.findByBH(node.getBh());

			if (wharfItem != null)
				existwharfItem = true;
			else
				wharfItem = new WharfItem();

			String piecename = node.getQy();
			// 数据库中寻找该码头所属片区
			PieceArea pieceArea = this.pieceAreaDao.findPieceByName(piecename);
			if (pieceArea != null) {
				wharfItem.setBh(node.getBh());
				wharfItem.setFzdh(node.getFzdh());
				wharfItem.setFzr(node.getFzr());
				wharfItem.setJd(node.getJd());
				wharfItem.setMc(node.getMc());
				wharfItem.setWd(node.getWd());
				wharfItem.setPieceareaid(pieceArea.getId());

				if (existwharfItem)
					this.wharfItemDao.update(wharfItem);
				else
					this.wharfItemDao.save(wharfItem);
			} else {
				System.out.println("无法找到 " + piecename);
			}
		}
		
		System.out.println("查新完毕");
	}
}
