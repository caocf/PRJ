package com.hztuen.lvyou.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.hxkg.ghpublic.R;


public class ImageUtil {

	//放大缩小图片 
	public static Bitmap zoomBitmap(Bitmap bitmap,int w,int h){ 
		int width = bitmap.getWidth(); 
		int height = bitmap.getHeight(); 
		Matrix matrix = new Matrix(); 
		float scaleWidht = ((float)w / width); 
		float scaleHeight = ((float)h / height); 
		matrix.postScale(scaleWidht, scaleHeight); 
		Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true); 
		return newbmp; 
	} 
	//将Drawable转化为Bitmap 
	public static Bitmap drawableToBitmap(Drawable drawable){ 
		int width = drawable.getIntrinsicWidth(); 
		int height = drawable.getIntrinsicHeight(); 
		Bitmap bitmap = Bitmap.createBitmap(width, height, 
				drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 
						: Bitmap.Config.RGB_565); 
		Canvas canvas = new Canvas(bitmap); 
		drawable.setBounds(0,0,width,height); 
		drawable.draw(canvas); 
		return bitmap;

	}

	//获得圆角图片的方法 
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap,float roundPx){

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap 
				.getHeight(), Config.ARGB_8888); 
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242; 
		final Paint paint = new Paint(); 
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()); 
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true); 
		canvas.drawARGB(0, 0, 0, 0); 
		paint.setColor(color); 
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN)); 
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output; 
	} 
	//获得带倒影的图片方法 
	public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap){ 
		final int reflectionGap = 4; 
		int width = bitmap.getWidth(); 
		int height = bitmap.getHeight();

		Matrix matrix = new Matrix(); 
		matrix.preScale(1, -1);

		Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 
				0, height/2, width, height/2, matrix, false);

		Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + height/2), Config.ARGB_8888);

		Canvas canvas = new Canvas(bitmapWithReflection); 
		canvas.drawBitmap(bitmap, 0, 0, null); 
		Paint deafalutPaint = new Paint(); 
		canvas.drawRect(0, height,width,height + reflectionGap, 
				deafalutPaint);

		canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

		Paint paint = new Paint(); 
		LinearGradient shader = new LinearGradient(0, 
				bitmap.getHeight(), 0, bitmapWithReflection.getHeight() 
				+ reflectionGap, 0x70ffffff, 0x00ffffff, TileMode.CLAMP); 
		paint.setShader(shader); 
		// Set the Transfer mode to be porter duff and destination in 
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN)); 
		// Draw a rectangle using the paint with our linear gradient 
		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight() 
				+ reflectionGap, paint);

		return bitmapWithReflection; 
	}
	
	
	
	
	/** 
     * 怀旧效果(相对之前做了优化快一倍) 
     * @param bmp 
     * @return 
     */  
    public static Bitmap oldRemeber(Bitmap bmp)  
    {  
        // 速度测试  
        long start = System.currentTimeMillis();  
        int width = bmp.getWidth();  
        int height = bmp.getHeight();  
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);  
        int pixColor = 0;  
        int pixR = 0;  
        int pixG = 0;  
        int pixB = 0;  
        int newR = 0;  
        int newG = 0;  
        int newB = 0;  
        int[] pixels = new int[width * height];  
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);  
        for (int i = 0; i < height; i++)  
        {  
            for (int k = 0; k < width; k++)  
            {  
                pixColor = pixels[width * i + k];  
                pixR = Color.red(pixColor);  
                pixG = Color.green(pixColor);  
                pixB = Color.blue(pixColor);  
                newR = (int) (0.393 * pixR + 0.769 * pixG + 0.189 * pixB);  
                newG = (int) (0.349 * pixR + 0.686 * pixG + 0.168 * pixB);  
                newB = (int) (0.272 * pixR + 0.534 * pixG + 0.131 * pixB);  
                int newColor = Color.argb(255, newR > 255 ? 255 : newR, newG > 255 ? 255 : newG, newB > 255 ? 255 : newB);  
                pixels[width * i + k] = newColor;  
            }  
        }  
          
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);  
        long end = System.currentTimeMillis();  
        Log.d("may", "used time="+(end - start));  
        return bitmap;  
    }  
    
    /** 
     * 模糊效果 
     * @param bmp 
     * @return 
     */  
    public static Bitmap blurImage(Bitmap bmp)  
    {  
        int width = bmp.getWidth();  
        int height = bmp.getHeight();  
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);  
          
        int pixColor = 0;  
          
        int newR = 0;  
        int newG = 0;  
        int newB = 0;  
          
        int newColor = 0;  
          
        int[][] colors = new int[9][3];  
        for (int i = 1, length = width - 1; i < length; i++)  
        {  
            for (int k = 1, len = height - 1; k < len; k++)  
            {  
                for (int m = 0; m < 9; m++)  
                {  
                    int s = 0;  
                    int p = 0;  
                    switch(m)  
                    {  
                    case 0:  
                        s = i - 1;  
                        p = k - 1;  
                        break;  
                    case 1:  
                        s = i;  
                        p = k - 1;  
                        break;  
                    case 2:  
                        s = i + 1;  
                        p = k - 1;  
                        break;  
                    case 3:  
                        s = i + 1;  
                        p = k;  
                        break;  
                    case 4:  
                        s = i + 1;  
                        p = k + 1;  
                        break;  
                    case 5:  
                        s = i;  
                        p = k + 1;  
                        break;  
                    case 6:  
                        s = i - 1;  
                        p = k + 1;  
                        break;  
                    case 7:  
                        s = i - 1;  
                        p = k;  
                        break;  
                    case 8:  
                        s = i;  
                        p = k;  
                    }  
                    pixColor = bmp.getPixel(s, p);  
                    colors[m][0] = Color.red(pixColor);  
                    colors[m][1] = Color.green(pixColor);  
                    colors[m][2] = Color.blue(pixColor);  
                }  
                  
                for (int m = 0; m < 9; m++)  
                {  
                    newR += colors[m][0];  
                    newG += colors[m][1];  
                    newB += colors[m][2];  
                }  
                  
                newR = (int) (newR / 9F);  
                newG = (int) (newG / 9F);  
                newB = (int) (newB / 9F);  
                  
                newR = Math.min(255, Math.max(0, newR));  
                newG = Math.min(255, Math.max(0, newG));  
                newB = Math.min(255, Math.max(0, newB));  
                  
                newColor = Color.argb(255, newR, newG, newB);  
                bitmap.setPixel(i, k, newColor);  
                  
                newR = 0;  
                newG = 0;  
                newB = 0;  
            }  
        }  
          
        return bitmap;  
    }  
      
    /** 
     * 柔化效果(高斯模糊)(优化后比上面快三倍) 
     * @param bmp 
     * @return 
     */  
    public static Bitmap blurImageAmeliorate(Bitmap bmp)  
    {  
        long start = System.currentTimeMillis();  
        // 高斯矩阵  
        int[] gauss = new int[] { 1, 2, 1, 2, 4, 2, 1, 2, 1 };  
          
        int width = bmp.getWidth();  
        int height = bmp.getHeight();  
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);  
          
        int pixR = 0;  
        int pixG = 0;  
        int pixB = 0;  
          
        int pixColor = 0;  
          
        int newR = 0;  
        int newG = 0;  
        int newB = 0;  
          
        int delta = 16; // 值越小图片会越亮，越大则越暗  
          
        int idx = 0;  
        int[] pixels = new int[width * height];  
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);  
        for (int i = 1, length = height - 1; i < length; i++)  
        {  
            for (int k = 1, len = width - 1; k < len; k++)  
            {  
                idx = 0;  
                for (int m = -1; m <= 1; m++)  
                {  
                    for (int n = -1; n <= 1; n++)  
                    {  
                        pixColor = pixels[(i + m) * width + k + n];  
                        pixR = Color.red(pixColor);  
                        pixG = Color.green(pixColor);  
                        pixB = Color.blue(pixColor);  
                          
                        newR = newR + (int) (pixR * gauss[idx]);  
                        newG = newG + (int) (pixG * gauss[idx]);  
                        newB = newB + (int) (pixB * gauss[idx]);  
                        idx++;  
                    }  
                }  
                  
                newR /= delta;  
                newG /= delta;  
                newB /= delta;  
                  
                newR = Math.min(255, Math.max(0, newR));  
                newG = Math.min(255, Math.max(0, newG));  
                newB = Math.min(255, Math.max(0, newB));  
                  
                pixels[i * width + k] = Color.argb(255, newR, newG, newB);  
                  
                newR = 0;  
                newG = 0;  
                newB = 0;  
            }  
        }  
          
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);  
        long end = System.currentTimeMillis();  
        Log.d("may", "used time="+(end - start));  
        return bitmap;  
    }  
	
    
    
    /** 
     * 图片锐化（拉普拉斯变换） 
     * @param bmp 
     * @return 
     */  
    public static Bitmap sharpenImageAmeliorate(Bitmap bmp)  
    {  
        long start = System.currentTimeMillis();  
        // 拉普拉斯矩阵  
        int[] laplacian = new int[] { -1, -1, -1, -1, 9, -1, -1, -1, -1 };  
          
        int width = bmp.getWidth();  
        int height = bmp.getHeight();  
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);  
          
        int pixR = 0;  
        int pixG = 0;  
        int pixB = 0;  
          
        int pixColor = 0;  
          
        int newR = 0;  
        int newG = 0;  
        int newB = 0;  
          
        int idx = 0;  
        float alpha = 0.3F;  
        int[] pixels = new int[width * height];  
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);  
        for (int i = 1, length = height - 1; i < length; i++)  
        {  
            for (int k = 1, len = width - 1; k < len; k++)  
            {  
                idx = 0;  
                for (int m = -1; m <= 1; m++)  
                {  
                    for (int n = -1; n <= 1; n++)  
                    {  
                        pixColor = pixels[(i + n) * width + k + m];  
                        pixR = Color.red(pixColor);  
                        pixG = Color.green(pixColor);  
                        pixB = Color.blue(pixColor);  
                          
                        newR = newR + (int) (pixR * laplacian[idx] * alpha);  
                        newG = newG + (int) (pixG * laplacian[idx] * alpha);  
                        newB = newB + (int) (pixB * laplacian[idx] * alpha);  
                        idx++;  
                    }  
                }  
                  
                newR = Math.min(255, Math.max(0, newR));  
                newG = Math.min(255, Math.max(0, newG));  
                newB = Math.min(255, Math.max(0, newB));  
                  
                pixels[i * width + k] = Color.argb(255, newR, newG, newB);  
                newR = 0;  
                newG = 0;  
                newB = 0;  
            }  
        }  
          
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);  
        long end = System.currentTimeMillis();  
        Log.d("may", "used time="+(end - start));  
        return bitmap;  
    }  

    
    //临时创建
Context mContext;
    
    /** 
     * 图片效果叠加 
     * @param bmp 限制了尺寸大小的Bitmap 
     * @return 
     */  
    private Bitmap overlay(Bitmap bmp)  
    {  
        long start = System.currentTimeMillis();  
        int width = bmp.getWidth();  
        int height = bmp.getHeight();  
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);  
          
        // 对边框图片进行缩放  
        Bitmap overlay = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_local);  
        int w = overlay.getWidth();  
        int h = overlay.getHeight();  
        float scaleX = width * 1F / w;  
        float scaleY = height * 1F / h;  
        Matrix matrix = new Matrix();  
        matrix.postScale(scaleX, scaleY);  
          
        Bitmap overlayCopy = Bitmap.createBitmap(overlay, 0, 0, w, h, matrix, true);  
          
        int pixColor = 0;  
        int layColor = 0;  
          
        int pixR = 0;  
        int pixG = 0;  
        int pixB = 0;  
        int pixA = 0;  
          
        int newR = 0;  
        int newG = 0;  
        int newB = 0;  
        int newA = 0;  
          
        int layR = 0;  
        int layG = 0;  
        int layB = 0;  
        int layA = 0;  
          
        final float alpha = 0.5F;  
          
        int[] srcPixels = new int[width * height];  
        int[] layPixels = new int[width * height];  
        bmp.getPixels(srcPixels, 0, width, 0, 0, width, height);  
        overlayCopy.getPixels(layPixels, 0, width, 0, 0, width, height);  
          
        int pos = 0;  
        for (int i = 0; i < height; i++)  
        {  
            for (int k = 0; k < width; k++)  
            {  
                pos = i * width + k;  
                pixColor = srcPixels[pos];  
                layColor = layPixels[pos];  
                  
                pixR = Color.red(pixColor);  
                pixG = Color.green(pixColor);  
                pixB = Color.blue(pixColor);  
                pixA = Color.alpha(pixColor);  
                  
                layR = Color.red(layColor);  
                layG = Color.green(layColor);  
                layB = Color.blue(layColor);  
                layA = Color.alpha(layColor);  
                  
                newR = (int) (pixR * alpha + layR * (1 - alpha));  
                newG = (int) (pixG * alpha + layG * (1 - alpha));  
                newB = (int) (pixB * alpha + layB * (1 - alpha));  
                layA = (int) (pixA * alpha + layA * (1 - alpha));  
                  
                newR = Math.min(255, Math.max(0, newR));  
                newG = Math.min(255, Math.max(0, newG));  
                newB = Math.min(255, Math.max(0, newB));  
                newA = Math.min(255, Math.max(0, layA));  
                  
                srcPixels[pos] = Color.argb(newA, newR, newG, newB);  
            }  
        }  
          
        bitmap.setPixels(srcPixels, 0, width, 0, 0, width, height);  
        long end = System.currentTimeMillis();  
        Log.d("may", "overlayAmeliorate used time="+(end - start));  
        return bitmap;  
    }  
	
    
    /** 
     * 光晕效果 
     * @param bmp 
     * @param x 光晕中心点在bmp中的x坐标 
     * @param y 光晕中心点在bmp中的y坐标 
     * @param r 光晕的半径 
     * @return 
     */  
    public Bitmap halo(Bitmap bmp, int x, int y, float r)  
    {  
        long start = System.currentTimeMillis();  
        // 高斯矩阵  
        int[] gauss = new int[] { 1, 2, 1, 2, 4, 2, 1, 2, 1 };  
          
        int width = bmp.getWidth();  
        int height = bmp.getHeight();  
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);  
          
        int pixR = 0;  
        int pixG = 0;  
        int pixB = 0;  
          
        int pixColor = 0;  
          
        int newR = 0;  
        int newG = 0;  
        int newB = 0;  
          
        int delta = 18; // 值越小图片会越亮，越大则越暗  
          
        int idx = 0;  
        int[] pixels = new int[width * height];  
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);  
        for (int i = 1, length = height - 1; i < length; i++)  
        {  
            for (int k = 1, len = width - 1; k < len; k++)  
            {  
                idx = 0;  
                int distance = (int) (Math.pow(k - x, 2) + Math.pow(i - y, 2));  
                // 不是中心区域的点做模糊处理  
                if (distance > r * r)  
                {  
                    for (int m = -1; m <= 1; m++)  
                    {  
                        for (int n = -1; n <= 1; n++)  
                        {  
                            pixColor = pixels[(i + m) * width + k + n];  
                            pixR = Color.red(pixColor);  
                            pixG = Color.green(pixColor);  
                            pixB = Color.blue(pixColor);  
                              
                            newR = newR + (int) (pixR * gauss[idx]);  
                            newG = newG + (int) (pixG * gauss[idx]);  
                            newB = newB + (int) (pixB * gauss[idx]);  
                            idx++;  
                        }  
                    }  
                      
                    newR /= delta;  
                    newG /= delta;  
                    newB /= delta;  
                      
                    newR = Math.min(255, Math.max(0, newR));  
                    newG = Math.min(255, Math.max(0, newG));  
                    newB = Math.min(255, Math.max(0, newB));  
                      
                    pixels[i * width + k] = Color.argb(255, newR, newG, newB);  
                      
                    newR = 0;  
                    newG = 0;  
                    newB = 0;  
                }  
            }  
        }  
          
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);  
        long end = System.currentTimeMillis();  
        Log.d("may", "used time="+(end - start));  
        return bitmap;  
    }  
	
    
    public static final float DISPLAY_WIDTH = 200;
    public static final float DISPLAY_HEIGHT = 200;
    /**
     * 从path中获取图片信息
     * @param path
     * @return
     */
    private Bitmap decodeBitmap(String path){
      BitmapFactory.Options op = new BitmapFactory.Options();
      //inJustDecodeBounds 
      //If set to true, the decoder will return null (no bitmap), but the out…
      op.inJustDecodeBounds = true;
      Bitmap bmp = BitmapFactory.decodeFile(path, op); //获取尺寸信息
      //获取比例大小
      int wRatio = (int)Math.ceil(op.outWidth/DISPLAY_WIDTH);
      int hRatio = (int)Math.ceil(op.outHeight/DISPLAY_HEIGHT);
      //如果超出指定大小，则缩小相应的比例
      if(wRatio > 1 && hRatio > 1){
        if(wRatio > hRatio){
          op.inSampleSize = wRatio;
        }else{
          op.inSampleSize = hRatio;
        }
      }
      op.inJustDecodeBounds = false;
      bmp = BitmapFactory.decodeFile(path, op);
      return bmp;
    }
    
    
    
    /** 
     * 截取图片的中间的200X200的区域 
     * @param bm 
     * @return 
     */  
    private Bitmap cropCenter(Bitmap bm)  
    {  
        int dstWidth = 200;  
           int dstHeight = 200;  
           int startWidth = (bm.getWidth() - dstWidth)/2;  
           int startHeight = ((bm.getHeight() - dstHeight) / 2);  
           Rect src = new Rect(startWidth, startHeight, startWidth + dstWidth, startHeight + dstHeight);  
           return dividePart(bm, src);  
    }  
      
    /** 
     * 剪切图片 
     * @param bmp 被剪切的图片 
     * @param src 剪切的位置 
     * @return 剪切后的图片 
     */  
    private Bitmap dividePart(Bitmap bmp, Rect src)  
    {  
        int width = src.width();  
        int height = src.height();  
        Rect des = new Rect(0, 0, width, height);  
        Bitmap croppedImage = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);  
        Canvas canvas = new Canvas(croppedImage);  
        canvas.drawBitmap(bmp, src, des, null);  
        return croppedImage;  
    }  
    
    
    /**
     * 压缩图片
     * 
     * @param bitmap
     *            源图片
     * @param width
     *            想要的宽度
     * @param height
     *            想要的高度
     * @param isAdjust
     *            是否自动调整尺寸, true图片就不会拉伸，false严格按照你的尺寸压缩
     * @return Bitmap
     */
    public static Bitmap reduce(Bitmap bitmap, int width, int height,
            boolean isAdjust) {
        // 如果想要的宽度和高度都比源图片小，就不压缩了，直接返回原图
        if (bitmap.getWidth() < width && bitmap.getHeight() < height) {
            return bitmap;
        }
        if (width == 0 && height == 0) {
            width = bitmap.getWidth();
            height = bitmap.getHeight();
        }

        // 根据想要的尺寸精确计算压缩比例, 方法详解：public BigDecimal divide(BigDecimal divisor,
        // int scale, int
        // roundingMode);
        // scale表示要保留的小数位, roundingMode表示如何处理多余的小数位，BigDecimal.ROUND_DOWN表示自动舍弃
        float sx = new BigDecimal(width).divide(
                new BigDecimal(bitmap.getWidth()), 4, BigDecimal.ROUND_DOWN)
                .floatValue();
        float sy = new BigDecimal(height).divide(
                new BigDecimal(bitmap.getHeight()), 4, BigDecimal.ROUND_DOWN)
                .floatValue();
        if (isAdjust) {// 如果想自动调整比例，不至于图片会拉伸
            sx = (sx < sy ? sx : sy);
            sy = sx;// 哪个比例小一点，就用哪个比例
        }
        Matrix matrix = new Matrix();
        matrix.postScale(sx, sy);// 调用api中的方法进行压缩，就大功告成了
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
    }
    

    /**
     * 压缩图片
     * 
     * @param filePath
     *            源图片地址
     * @param width
     *            想要的宽度
     * @param height
     *            想要的高度
     * @param isAdjust
     *            是否自动调整尺寸, true图片就不会拉伸，false严格按照你的尺寸压缩
     * @return Bitmap
     */
    public static Bitmap getSmallImageBitmap(Context cxt, String filePath,
            int width, int height, boolean isAdjust) {

        Bitmap bitmap = reduce(BitmapFactory.decodeFile(filePath), width,
                height, isAdjust);

//        File file = new File(getRandomFileName(cxt.getCacheDir().getPath()));
//
//        BufferedOutputStream outputStream = null;
//        try {
//            outputStream = new BufferedOutputStream(new FileOutputStream(file));
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, outputStream);
//            outputStream.flush();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (outputStream != null) {
//                    outputStream.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        return bitmap;
    }
}