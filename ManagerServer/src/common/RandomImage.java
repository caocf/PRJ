package common;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class RandomImage {
	private String validateString; // 生成的验证字符串
	//private BufferedImage validateImage; // 生成的验证图片
	private String randomString; // 图片上的字符串
	private int length; // 图片上字符的个数
	private int width; // 图片的宽度
	private int height; // 图片的高度

	public RandomImage(String randomString, int length, int width, int height) {
		this.randomString = randomString;
		this.length = length;
		this.width = width;
		this.height = height;
	}

	// 获取生成的验证字符串
	public String getValidateString() {
		if (validateString == null) {
			getValidateImage();
		}
		return validateString;
	}

	// 获取生成的验证图片
	public BufferedImage getValidateImage() {
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);// 在内存中创建图象
		Graphics2D raphics = (Graphics2D) image.getGraphics();// 获取图形上下文
		Color c = new Color(203,231,240);
		raphics.setColor(c);// 设定背景色
		raphics.fillRect(0, 0, width, height);
		raphics.setFont(new Font("Times New Roman", Font.ITALIC, 28));// 设定字体
																		// style:HANGING_BASELINE
		raphics.setColor(getRandColor(160, 200));// 给定范围获得随机颜色
		Random random = new Random(); // 生成随机类
		// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
		for (int i = 0; i < 255; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			raphics.drawLine(x, y, x, y);
		}
		// 取随机产生的认证码(length位数字)
		String rand = "";
		StringBuffer vString = new StringBuffer();
		for (int i = 0; i < length; i++) {
			rand = String.valueOf(randomString.charAt(random
					.nextInt(randomString.length())));
			vString.append(rand);
			raphics.setColor(Color.BLACK);// 设置为黑色字体
			raphics.drawString(rand, 15 * i + 25, 32);
		}
		validateString = vString.toString(); // 将认证码存入 validateString
		raphics.dispose(); // 图象生效
	
		return image;
	}

	private Color getRandColor(int fc, int bc) { // 给定范围获得随机颜色
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
}
