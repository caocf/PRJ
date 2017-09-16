package com.huzhouport.wharfSelect.action;

import java.util.List;

import com.huzhouport.common.action.BaseAction;
import com.huzhouport.wharfSelect.model.PieceArea;
import com.huzhouport.wharfSelect.model.PortArea;
import com.huzhouport.wharfSelect.model.WharfItem;
import com.huzhouport.wharfSelect.service.WharfSelectService;

public class WharfSelectAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5747596115307410961L;

	private WharfSelectService wharfSelectService;
	private List<PieceArea> pieceAreas;
	private List<PortArea> portAreas;
	private List<WharfItem> wharfItems;
	private int portareaid = -1;
	private int pieceareaid = -1;
	private String wharfname = null;

	/**
	 * 获得港区列表
	 * 
	 * @return
	 */
	public String findPortAreas() {
		portAreas = this.wharfSelectService.findPortAreas();
		return SUCCESS;
	}

	/**
	 * 获得某港区的片区
	 * 
	 * @param portareaid
	 * @return
	 */
	public String findPieceAreas() {
		pieceAreas = this.wharfSelectService.findPieceAreas(portareaid);
		return SUCCESS;
	}

	/**
	 * 获得某片区的码头列表
	 */
	public String findWharfItems() {
		wharfItems = this.wharfSelectService.findWharfItems(pieceareaid);
		return SUCCESS;
	}

	/**
	 * 搜索
	 * 
	 * @return
	 */
	public String searchWharfItems() {
		wharfItems = this.wharfSelectService.searchWharfItems(portareaid,
				pieceareaid, wharfname);
		return SUCCESS;
	}

	public void setWharfSelectService(WharfSelectService wharfSelectService) {
		this.wharfSelectService = wharfSelectService;
	}

	public List<PieceArea> getPieceAreas() {
		return pieceAreas;
	}

	public void setPieceAreas(List<PieceArea> pieceAreas) {
		this.pieceAreas = pieceAreas;
	}

	public List<PortArea> getPortAreas() {
		return portAreas;
	}

	public void setPortAreas(List<PortArea> portAreas) {
		this.portAreas = portAreas;
	}

	public List<WharfItem> getWharfItems() {
		return wharfItems;
	}

	public void setWharfItems(List<WharfItem> wharfItems) {
		this.wharfItems = wharfItems;
	}

	public int getPortareaid() {
		return portareaid;
	}

	public void setPortareaid(int portareaid) {
		this.portareaid = portareaid;
	}

	public String getWharfname() {
		return wharfname;
	}

	public void setWharfname(String wharfname) {
		this.wharfname = wharfname;
	}

	public int getPieceareaid() {
		return pieceareaid;
	}

	public void setPieceareaid(int pieceareaid) {
		this.pieceareaid = pieceareaid;
	}
}
