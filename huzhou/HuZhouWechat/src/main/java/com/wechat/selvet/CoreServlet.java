/**
 * @Title CodeServlet.java
 * @Package com.chuangyuan.servlet
 * @Description
 * @Author 庄佳彬
 * @Date 2016年4月12日
 * @Version v1.0
 */
package com.wechat.selvet;

import com.visionagent.utils.PropertiesLoader;
import com.visionagent.utils.Value;
import com.visionagent.utils.xml.XMLObject;
import com.visionagent.utils.xml.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Properties;


/**
 * 
 * <li>ClassName: CoreServlet</li>
 * <li>Description:微信验证用</li>
 * <li>Change:</li>
 * <li>Date: 2016年5月9日 上午10:24:53</li>
 * @author 庄佳彬
 */
public class CoreServlet extends HttpServlet {
	
	private static final Logger log = LoggerFactory.getLogger(CoreServlet.class);
	
	private static Properties ask = null;
	private static Properties key = null;
	
	static {
		loadProperties();
	}

	private static void loadProperties() {
		ask = PropertiesLoader.getPropertiesFromClassPath("ask.properties");
		key = PropertiesLoader.getPropertiesFromClassPath("key.properties");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 190678509557765894L;

	/**
	 * (non-Javadoc)
	 * <p>
	 * Title: doGet
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @see HttpServlet#doGet(HttpServletRequest,
	 *      HttpServletResponse)
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		PrintWriter out = response.getWriter();
		if (checkSignature(signature, timestamp, nonce)) {
			out.print(echostr);
		}
		out.close();
		out = null;
	}

	private boolean checkSignature(String signature, String timestamp,
			String nonce) {
		String[] arr = new String[] { key.getProperty("token"), timestamp, nonce };
		// 将 token, timestamp, nonce 三个参数进行字典排序
		Arrays.sort(arr);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		MessageDigest md = null;
		String tmpStr = null;

		try {
			md = MessageDigest.getInstance("SHA-1");
			// 将三个参数字符串拼接成一个字符串进行 shal 加密
			byte[] digest = md.digest(content.toString().getBytes());
			tmpStr = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			log.error("",e);
		}
		content = null;
		// 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
		return tmpStr != null && tmpStr.equals(signature.toUpperCase());
	}

	/**
	 * 将字节数组转换为十六进制字符串
	 * 
	 * @param digest
	 * @return
	 */
	private String byteToStr(byte[] digest) {
		StringBuilder strDigest = new StringBuilder();
		for (byte aDigest : digest) {
			strDigest.append(byteToHexStr(aDigest));
		}
		return strDigest.toString();
	}

	/**
	 * 将字节转换为十六进制字符串
	 * 
	 * @param b
	 * @return
	 */
	private String byteToHexStr(byte b) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(b >>> 4) & 0X0F];
		tempArr[1] = Digit[b & 0X0F];

		return new String(tempArr);
	}

	/**
	 * (non-Javadoc)
	 * <p>
	 * Title: doPost
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @see HttpServlet#doPost(HttpServletRequest,
	 *      HttpServletResponse)
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (ask == null) {
			loadProperties();
		}
		StringBuffer sb = new StringBuffer();
		String line;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			XMLObject object = XMLUtils.xml2Map(sb.toString());
			log.info("接收消息{}", object.toString());
			XMLObject event =  (XMLObject)object.recursionValue("Event");
			XMLObject eventKey =  (XMLObject)object.recursionValue("EventKey");
			XMLObject toUserName =  (XMLObject)object.recursionValue("ToUserName");
			XMLObject fromUserName =  (XMLObject)object.recursionValue("FromUserName");
			XMLObject content =  (XMLObject)object.recursionValue("Content");
			XMLObject msgType = (XMLObject)object.recursionValue("MsgType");

			String returnStr = null;
			if (event != null &&  "click".equalsIgnoreCase(Value.ofEmpty(event.getValue()))) {
				if(eventKey.getValue() != null){
					returnStr = ask.getProperty(Value.ofEmpty(eventKey.getValue()));
				}
			}else if(event != null &&  "subscribe".equalsIgnoreCase(Value.ofEmpty(event.getValue()))){
				returnStr = ask.getProperty("welcome");
			}else if(msgType != null && "text".equalsIgnoreCase(Value.ofEmpty(msgType.getValue()))){
				Object o = ask.get(Value.ofEmpty(content.getValue()));
				if(o != null){
					returnStr = o.toString();
				}else{
					returnStr = ask.getProperty("error");
				}
			}
			WXMessage message = new WXMessage(Value.ofEmpty(fromUserName.getValue()), Value.ofEmpty(toUserName.getValue()), System.currentTimeMillis() / 1000, "text", returnStr);
			log.info("发送消息{}", message.toString());
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(message.toString());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			response.getWriter().print("success");
		}
	}


}
