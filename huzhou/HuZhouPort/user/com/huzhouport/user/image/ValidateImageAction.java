package com.huzhouport.user.image;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import com.opensymphony.xwork2.ActionContext;

/**
 * *
 * 
 * <pre>
 * * 产生图片验证码 *
 * </pre>
 * 
 * * Login Page *
 * 
 * @author Michael Zheng 2008-11-18
 */
public class ValidateImageAction {
	private InputStream inputStream;
	private String randomString; // 图片上的字符串
	private int length; // 图片上字符的个数
	private int width; // 图片的宽度
	private int height; // 图片的高度

	public String getRandomString() {
		if (randomString == null) {
			randomString = "0123456789abcdefghijkmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ";
		}
		return randomString;
	}

	public void setRandomString(String randomString) {
		this.randomString = randomString;
	}

	public int getLength() {
		if (length == 0) {
			length = 3;
		}
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getWidth() {
		if (width == 0) {
			width = 134;
		}
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		if (height == 0) {
			height = 52;
		}
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	@SuppressWarnings("unchecked")
	public String execute() {
		RandomImage validateImage = new RandomImage(getRandomString(),
				getLength(), getWidth(), getHeight());
		System.out.println("111");
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ImageOutputStream ios = ImageIO.createImageOutputStream(bos);
			ImageIO.write(validateImage.getValidateImage(), "JPEG", ios);
			inputStream = new ByteArrayInputStream(bos.toByteArray());
			bos.close();
			System.out.println("222");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("333");
		}
		Map session = ActionContext.getContext().getSession(); // 获得session
		session.put("validateString", validateImage.getValidateString()
				.toLowerCase()); // 将验证码转小写放入session
		System.out.println("444");
		return "success";
	}
}
