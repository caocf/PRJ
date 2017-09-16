/**
 * @Title WXMessage.java
 * @Package com.chuangyuan.servlet
 * @Description
 * @Author 庄佳彬
 * @Date 2016年4月20日
 * @Version v1.0
 */
package com.wechat.selvet;

/**
 * <li>ClassName: WXMessage</li>
 * <li>Description:</li>
 * <li>Change:</li>
 * <li>Date: 2016年4月20日 下午12:08:42</li>
 * @author 庄佳彬
 */
public class WXMessage {

	private String ToUserName;
	private String FromUserName;
	private long CreateTime;
	private String MsgType;
	private String Content;
	private String PicUrl;
	private String MediaId;
	
	
	
	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 */
	public WXMessage() {
	}
	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param toUserName
	 * @param fromUserName
	 * @param createTime
	 * @param msgType
	 * @param content
	 */
	public WXMessage(String toUserName, String fromUserName, long createTime,
			String msgType, String content) {
		ToUserName = toUserName;
		FromUserName = fromUserName;
		CreateTime = createTime;
		MsgType = msgType;
		Content = content;
	}
	/**
	 * @return the toUserName
	 */
	public String getToUserName() {
		return ToUserName;
	}
	/**
	 * @param toUserName the toUserName to set
	 */
	public WXMessage setToUserName(String toUserName) {
		ToUserName = toUserName;
		return this;
	}
	/**
	 * @return the fromUserName
	 */
	public String getFromUserName() {
		return FromUserName;
	}
	/**
	 * @param fromUserName the fromUserName to set
	 */
	public WXMessage setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
		return this;
	}
	/**
	 * @return the createTime
	 */
	public long getCreateTime() {
		return CreateTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public WXMessage setCreateTime(long createTime) {
		CreateTime = createTime;
		return this;
	}
	/**
	 * @return the msgType
	 */
	public String getMsgType() {
		return MsgType;
	}
	/**
	 * @param msgType the msgType to set
	 */
	public WXMessage setMsgType(String msgType) {
		MsgType = msgType;
		return this;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return Content;
	}
	/**
	 * @param content the content to set
	 */
	public WXMessage setContent(String content) {
		Content = content;
		return this;
	}
	/**
	 * @return the picUrl
	 */
	public String getPicUrl() {
		return PicUrl;
	}
	/**
	 * @param picUrl the picUrl to set
	 */
	public WXMessage setPicUrl(String picUrl) {
		PicUrl = picUrl;
		return this;
	}
	/**
	 * @return the mediaId
	 */
	public String getMediaId() {
		return MediaId;
	}
	/**
	 * @param mediaId the mediaId to set
	 */
	public WXMessage setMediaId(String mediaId) {
		MediaId = mediaId;
		return this;
	}
	/**
	 * @see Object#toString()
	 * @return 
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append("<xml><ToUserName>").append(ToUserName)
				.append("</ToUserName><FromUserName>").append(FromUserName)
				.append("</FromUserName><CreateTime>").append(CreateTime)
				.append("</CreateTime><MsgType>").append(MsgType)
				.append("</MsgType>");
		if (Content != null) {
			sb.append("<Content>").append(Content).append("</Content>");
		}
		if (MediaId != null) {
			sb.append("<MediaId>").append(MediaId).append("</MediaId>");
		}
		if (PicUrl != null) {
			sb.append("<PicUrl>").append(PicUrl).append("</PicUrl>");
		}
		sb.append("</xml>");

		return sb.toString();
	}
	
	
}
