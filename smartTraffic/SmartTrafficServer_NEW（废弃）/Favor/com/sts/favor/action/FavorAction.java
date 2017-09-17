package com.sts.favor.action;

import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.sts.favor.model.LineFavor;
import com.sts.favor.model.StationFavor;
import com.sts.favor.model.TransferFavor;
import com.sts.favor.service.FavorService;
import com.sts.user.action.UserAction;

public class FavorAction 
{
	FavorService favorService;
	
	List<StationFavor> stationFavors;
	List<LineFavor> lineFavors;
	List<TransferFavor> transferFavors;
	
	int lineID;
	int direct;
	int stationID;
	String stationName;
	String stationLantitude;
	String stationLongtitude;
	
	String startLantitude;
	String startLongtitude;
	String endLantitude;
	String endLongtitude;
	String startName;
	String endName;
	int status;						//-1用户未登录（session中为空） 0:已收藏 1 操作成功
		
	public void setDirect(int direct) {
		this.direct = direct;
	}
	public List<StationFavor> getStationFavors() {
		return stationFavors;
	}

	public void setStationFavors(List<StationFavor> stationFavors) {
		this.stationFavors = stationFavors;
	}

	public List<LineFavor> getLineFavors() {
		return lineFavors;
	}

	public void setLineFavors(List<LineFavor> lineFavors) {
		this.lineFavors = lineFavors;
	}

	public List<TransferFavor> getTransferFavors() {
		return transferFavors;
	}

	public void setTransferFavors(List<TransferFavor> transferFavors) {
		this.transferFavors = transferFavors;
	}

	public String getStationLantitude() {
		return stationLantitude;
	}

	public void setStationLantitude(String stationLantitude) {
		this.stationLantitude = stationLantitude;
	}

	public String getStationLongtitude() {
		return stationLongtitude;
	}

	public void setStationLongtitude(String stationLongtitude) {
		this.stationLongtitude = stationLongtitude;
	}

	public int getLineID() {
		return lineID;
	}

	public void setLineID(int lineID) {
		this.lineID = lineID;
	}

	public int getStationID() {
		return stationID;
	}

	public void setStationID(int stationID) {
		this.stationID = stationID;
	}

	

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getStartLantitude() {
		return startLantitude;
	}

	public void setStartLantitude(String startLantitude) {
		this.startLantitude = startLantitude;
	}

	public String getStartLongtitude() {
		return startLongtitude;
	}

	public void setStartLongtitude(String startLongtitude) {
		this.startLongtitude = startLongtitude;
	}

	public String getEndLantitude() {
		return endLantitude;
	}

	public void setEndLantitude(String endLantitude) {
		this.endLantitude = endLantitude;
	}

	public String getEndLongtitude() {
		return endLongtitude;
	}

	public void setEndLongtitude(String endLongtitude) {
		this.endLongtitude = endLongtitude;
	}

	public void setEndName(String endName) {
		this.endName = endName;
	}
	public void setStartName(String startName) {
		this.startName = startName;
	}
	
	public void setFavorService(FavorService favorService) {
		this.favorService = favorService;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * 获取session中的userid
	 * @return
	 */
	public int GetUserID()
	{
		ActionContext actionContext=ActionContext.getContext();
		Map<String,Object> session=actionContext.getSession();
		
		boolean existed= session.containsKey(UserAction.USER_SESSION_ID);
		
		if(!existed)
			return -1;
		
		int id=Integer.parseInt(session.get(UserAction.USER_SESSION_ID).toString());
		return id;
	}
	
	/**
	 * 保存
	 * @param stationFavor
	 */
	public String SaveStationFavor()
	{
		int userid=GetUserID();
		
		if(userid==-1)
			status=-1;
		else
		{
			status=this.favorService.SaveStationFavor(userid, stationID,stationName,stationLantitude,stationLongtitude);
		}
		
		return "success";
	}

	
	public String SaveLineFavor()
	{
		int userid=GetUserID();
		
		if(userid==-1)
			status=-1;
		else
		{
			status=this.favorService.SaveLineFavor(userid,lineID,direct);
		}
		
		return "success";
	}
	
	public String SaveTransferFavor()
	{
		int userid=GetUserID();
		
		if(userid==-1)
			status=-1;
		else
		{
			status=this.favorService.SaveTransferFavor(userid, startLantitude, startLongtitude,endLantitude,endLongtitude, startName, endName);
		}
		
		return "success";
	}
	
	/**
	 * 查找当前用户所有
	 * @param userID
	 * @return
	 */
	public String QueryAllStationFavor()
	{
		int userid=GetUserID();
		
		if(userid==-1)
			status=-1;
		else
		{
			stationFavors=this.favorService.QueryAllStationFavor(userid);
			status=1;
		}
		
		return "success";
	}
	
	public String QueryAllLineFavor()
	{
		int userid=GetUserID();
		
		if(userid==-1)
			status=-1;
		else
		{
			lineFavors=this.favorService.QueryAllLineFavor(userid);
			status=1;
		}
		
		return "success";
	}
	
	public String QueryAllTransferFavor()
	{
		int userid=GetUserID();
		
		if(userid==-1)
			status=-1;
		else
		{
			transferFavors=this.favorService.QueryAllTransferFavor(userid);
			status=1;
		}
		
		return "success";
	}
	
	String stationIDs;
	String lineIDs;
	String transferIDs;
	boolean success;
	
	public String getStationIDs() {
		return stationIDs;
	}

	public void setStationIDs(String stationIDs) {
		this.stationIDs = stationIDs;
	}

	public String getLineIDs() {
		return lineIDs;
	}

	public void setLineIDs(String lineIDs) {
		this.lineIDs = lineIDs;
	}

	public String getTransferIDs() {
		return transferIDs;
	}

	public void setTransferIDs(String transferIDs) {
		this.transferIDs = transferIDs;
	}
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String DeleteForStation()
	{
		int userid=GetUserID();
		
		if(userid==-1)
			status=-1;
		else
		{
			success=this.favorService.deleteStation(stationIDs, userid);
		}
		return "success";
	}
	
	public String DeleteForLine()
	{
		int userid=GetUserID();
		
		if(userid==-1)
			status=-1;
		else
		{
			success=this.favorService.deleteLine(lineIDs, userid);
		}
		return "success";
	}
	
	public String DeleteForTransfer()
	{
		int userid=GetUserID();
		
		if(userid==-1)
			status=-1;
		else
		{
			success=this.favorService.deleteTransfer(transferIDs, userid);
		}
		return "success";
	}
}
