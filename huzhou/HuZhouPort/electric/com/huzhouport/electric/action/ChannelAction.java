package com.huzhouport.electric.action;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huzhouport.common.action.BaseAction;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.electric.model.Channel;
import com.huzhouport.electric.service.ChannelService;
import com.opensymphony.xwork2.ModelDriven;

public class ChannelAction extends BaseAction implements ModelDriven<Channel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Channel channel = new Channel();

	private File excelFile; // 上传的文件
	private String excelFileFileName; // 保存原始文件名
	private InputStream downfileExcl;// 导出文件
	private ChannelService channelService;
	private List<?> list;
	private int totalPage;// 总页数
	private int allTotal;
	// 分页查询
	public String showChannelInfo() throws Exception {
		try {
			Map<String, Integer> map = new HashMap<String, Integer>();
			map = this.channelService.countPageChannelInfo(
					channel, GlobalVar.PAGESIZE);
			totalPage = map.get("pages");
			allTotal = map.get("allTotal");
			if (totalPage != 0) {

				list = this.channelService.searchChannelInfo(
						channel, Integer.valueOf(this.getCpage()),
						GlobalVar.PAGESIZE);

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return SUCCESS;
	}
	
	
	public String downloadFileModel() throws Exception{
		try{
		downfileExcl=this.channelService.getChannelModelInputStream();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "excl";
	}
	public String inputChannelInfo() throws Exception{
		try{
			if(excelFile==null||excelFile.equals("")){
				throw new Exception("请添加导入文件");
			}else{
			this.channelService.inputChannel(channel, excelFile, excelFileFileName);
			}
			}catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		return SUCCESS;
	}
	public Channel getModel() {
		return channel;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public File getExcelFile() {
		return excelFile;
	}

	public void setExcelFile(File excelFile) {
		this.excelFile = excelFile;
	}

	public String getExcelFileFileName() {
		return excelFileFileName;
	}

	public void setExcelFileFileName(String excelFileFileName) {
		this.excelFileFileName = excelFileFileName;
	}

	public InputStream getDownfileExcl() {
		return downfileExcl;
	}

	public void setDownfileExcl(InputStream downfileExcl) {
		this.downfileExcl = downfileExcl;
	}

	public void setChannelService(ChannelService channelService) {
		this.channelService = channelService;
	}


	public List<?> getList() {
		return list;
	}


	public void setList(List<?> list) {
		this.list = list;
	}


	public int getTotalPage() {
		return totalPage;
	}


	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}


	public int getAllTotal() {
		return allTotal;
	}


	public void setAllTotal(int allTotal) {
		this.allTotal = allTotal;
	}

}
