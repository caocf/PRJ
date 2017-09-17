package com.common.utils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;


/*import com.sun.image.codec.jpeg.JPEGCodec;  
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;*/
import com.common.action.BaseResult;
import com.common.action.Constants;

/**
 * 图片上传Util
 * 
 * @author Administrator
 *
 */

public class FileUpload {
	static Logger log = Logger.getLogger(FileUpload.class);
	public FileUpload(){
		
	}

	//上传文件
		public static BaseResult imgUpload(String fileName,File file,String uploadFileName,String uploadPath){
			//上传目录String uploadPath;文件路径String filePath;上传到服务器的文件名称 String fileName
			/*String  uploadPath = Constants.IMG_STORE_PATH;	*/
			System.out.print("进入无压缩图片上传");
	        
		    boolean flag = upLoadFile(uploadPath,file,fileName,uploadFileName);
		    if(flag==false){
		    	return new BaseResult(83,"文件上传失败");
		    }
		    return new BaseResult(1,"成功");
		}
		
		//文件上传，生成缩略图
		public static BaseResult imgUploadAndCompress(String fileName,File file,String uploadFileName,String uploadPath){
			//上传目录String uploadPath;文件路径String filePath;上传到服务器的文件名称 String fileName
			/*String  uploadPath = Constants.IMG_STORE_PATH;	*/
			System.out.print("进入压缩图片上传");
		    boolean flag = upLoadFile(uploadPath,file,fileName,uploadFileName);
		    if(flag==false){
		    	return new BaseResult(83,"文件上传失败");
		    }
		    flag = upLoadCompressedImg(uploadPath,file,fileName+Constants.HEAD_SMALL_ENDNAME,uploadFileName);
		    if(flag==false){
		    	return new BaseResult(83,"图像压缩失败");
		    }
		    return new BaseResult(1,"成功");
		}

	
	private static void createDir(String uploadPath){
		File file = new File(uploadPath);
		//如果该文件目录不存在，则创建一个新的目录
		if(!file.exists()){
			file.mkdir();
		}
	}
	
	private static File createFile(String uploadPath){
		File file = new File(uploadPath);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
		return file;
	}
	/**
	 * 得到一个文件输入流
	 */
	private static FileInputStream getStream(String filePath){
		FileInputStream fileInputStream  = null;
		try {
			fileInputStream = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("文件没有找到！");
		}
		return fileInputStream;
	}
	/**
	 * 得到一个文件输出流
	 */
	private static FileOutputStream getOutStream(File file){
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileOutputStream;
	}
	
	/**
	 * 文件大小
	 */
	public static Long getFileSize(File file){
		//判断该文件是否是一个有效的文件
		if(file!=null){
			return file.length();
		}
		return 0l;
	}
	/**
	 * 文件上传,不经过图片处理
	 * 包含的属性有：上传目录String uploadPath;文件路径String filePath;上传到服务器的文件名称 String fileName;
	 */
	public static boolean upLoadFile(String uploadPath,File file,String fileName,String uploadFileName){
		boolean isSuccess = true;
		createDir(uploadPath);
		String extension = "";
		if(file!=null){
			if(uploadFileName.lastIndexOf(".")!=-1){
				extension = uploadFileName.substring(uploadFileName.lastIndexOf(".")+1);
			}
		}
		try{
		uploadPath = uploadPath + fileName +"."+extension;//上传保存的路径+文件名
		/*File fileNew = createFile(uploadPath);//生成新的文件
*/
		 InputStream inStream =new FileInputStream(file);
	        //得到图片的二进制数据，以二进制封装得到数据，具有通用性  
	        byte[] data = readInputStream(inStream);  
	      
	        File imageFile = createFile(uploadPath);
	        //创建输出流  
	        FileOutputStream outStream = new FileOutputStream(imageFile);  
	        //写入数据  
	        outStream.write(data);  
	        //关闭输出流  
	        outStream.close();  
		}catch(IOException e){
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isSuccess;
	}

	/**
	 * 无限制文件上传
	 * @param uploadPath 文件上传路径
	 * @param filePath   上传文件
	 * @param fileName   上传到服务器的文件名称 
	 * @param uploadFileName  上传文件名的名称
	 */
	public static boolean upLoad(String uploadPath,File filePath,String fileName,String uploadFileName){
		boolean isSuccess = true;
		createDir(uploadPath);
		String extension = "";
		if(filePath!=null&&!filePath.equals("")){
			if(uploadFileName.lastIndexOf(".")!=-1){
				extension = uploadFileName.substring(uploadFileName.lastIndexOf(".")+1);
			}
		}
		uploadPath = uploadPath + fileName +"."+extension;//上传保存的路径+文件名
		System.out.println("路径名"+uploadPath);
		File file = createFile(uploadPath);//生成新的文件
			//图片
			BufferedImage src = null;
		    FileOutputStream out = null;
            try {
				src = javax.imageio.ImageIO.read(filePath);
		/*		 if(src.getTransparency() == Transparency.TRANSLUCENT){//透明png图片
					 src = get24BitImage(src);
	              }	*/
			Image image = null;
			BufferedImage oimage = null;
			image=src.getScaledInstance(src.getWidth(),src.getHeight(),Image.SCALE_DEFAULT);
			oimage = new BufferedImage(src.getWidth(),src.getHeight(),Image.SCALE_DEFAULT); 
			System.out.println("透明度"+oimage.getTransparency());
			
		        Graphics2D g2d = oimage.createGraphics();  
		/*        if(src.getTransparency() == Transparency.TRANSLUCENT){//透明png图片
		        	g2d.drawImage(image, 0, 0, Color.white,null);
		        }*/
		        g2d.drawImage(image, 0, 0,null);  
		          
		        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,1.0f)); //透明度设置开始    
//		                 
		        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER)); //透明度设置 结束  
		        g2d.dispose();
		        
		        ByteArrayOutputStream bos = new ByteArrayOutputStream();   
				ImageIO.write(oimage,extension,bos);			
				out = new FileOutputStream(file);
				out.write(bos.toByteArray());  //写文件  
				out.close();
		     	} catch (IOException e){
				e.printStackTrace();
				isSuccess = false;
			   }
		return isSuccess;
	}
	
	
	/**
	 * 限制文件大小的上传---如果超过一定的大小则压缩或者限制上传
	 * 包含的属性有：上传目录String uploadPath;文件路径String filePath;上传到服务器的文件名称 String fileName;
	 *              文件限制大小:int commitSize,大小以k表示
	 *              boolean flag:true为压缩大小;false为限制上传
	 */
	public static boolean upLoadCompressedImg(String uploadPath,File f,String fileName,String uploadFileName){ 
		//首先要得到上传的文件后缀名，如果不是图片格式的，则将flag属性修改为false，直接限制其上传，如果是图片格式，则对图片进行压缩
		String extension = "jpg";
			//取得服务器的文件，放入一个新的File对象当中
			
		 String filePath_new = uploadPath +fileName+"."+extension;
		 File file = new File(filePath_new);
		  FileOutputStream out = null; 
	          try { 
	              /*   Image img = ImageIO.read(f); */
	                  BufferedImage tagimage = ImageIO.read(f); 
	                  BufferedImage tag = new BufferedImage(Constants.SMALL_HEAD_WIDTH,Constants.SMALL_HEAD_HEIGHT, BufferedImage.TYPE_INT_RGB);
	     /*			 if(tagimage.getTransparency() == Transparency.TRANSLUCENT){//透明png图片
	     				 System.out.println("是透明图片");
	     				tagimage = get24BitImage(tagimage);
	                  }*/
	                  Graphics2D g2d = tag.createGraphics();  
	                 
	                  if(tagimage.getTransparency() == Transparency.TRANSLUCENT){//透明png图片
	  		        	g2d.drawImage(tagimage.getScaledInstance(Constants.SMALL_HEAD_WIDTH,Constants.SMALL_HEAD_HEIGHT, Image.SCALE_SMOOTH), 0, 0, Color.white,null);
	  		           }else{
	  		        	 g2d.drawImage(tagimage.getScaledInstance(Constants.SMALL_HEAD_WIDTH,Constants.SMALL_HEAD_HEIGHT, Image.SCALE_SMOOTH), 0, 0, null);  
	  		           }
	                  g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,1.0f)); //透明度设置开始    
//	                  g2d.drawImage(logo_src,x,y,logo_width,logo_height, null);              
	                  g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER)); //透明度设置 结束  
	                  // tag.getGraphics().drawImage(); 
	                 out = new FileOutputStream(file); 
	                   // JPEGImageEncoder可适用于其他图片类型的转换 
	               /*    JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out); 
	                   JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(tag); 
	                   param.setQuality(1f, true); 
	                   encoder.encode(tag); */
	                ByteArrayOutputStream bos = new ByteArrayOutputStream();   
	   				ImageIO.write(tag,extension,bos);	
	   				out.write(bos.toByteArray());  //写文件  
	   				out.close();
	  } catch (IOException e) { 
	  e.printStackTrace(); 
	  return false;
	 
	  } 
	  return true; 
	  } 
	
	/**
	 * 加水印的图片上传
	 */
	
	
	/**
	 * 图片的上传
	 */
	
	/**
	 * 加水印图片
	 * 包含的属性有：String pressImg,水印图片;String targetImg,目标图片
	 */
	/* public  void pressImage(String pressImg, String targetImg,    
	            int x, int y) {    
	        try {    
	            File file = new File(targetImg);  
	            Image src = ImageIO.read(file);    
	            int wideth = src.getWidth(null);    
	            int height = src.getHeight(null);    
	            BufferedImage image = new BufferedImage(wideth, height,    
	                    BufferedImage.TYPE_INT_RGB);    
	            Graphics g = image.createGraphics();    
	            g.drawImage(src, 0, 0, wideth, height, null);    
	   
	            // 水印文件    
	            File _filebiao = new File(pressImg);    
	            Image src_biao = ImageIO.read(_filebiao);    
	            int wideth_biao = src_biao.getWidth(null);    
	            int height_biao = src_biao.getHeight(null);    
	            g.drawImage(src_biao, wideth - wideth_biao - x, height    
	                    - height_biao - y, wideth_biao, height_biao, null);    
	            // /    
	            g.dispose();    
	            FileOutputStream out = new FileOutputStream(targetImg);    
	            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);    
	            encoder.encode(image);    
	            out.close();    
	        } catch (Exception e) {    
	            e.printStackTrace();    
	        }    
	    }    */
	  
	/**
	 *删除文件  对应属性 String filePath
	 */
	public  static boolean deleteWebFile(String filePath){
	        File file = new File(filePath);    
	        if(file.isFile() && file.exists()){    
	            file.delete();    
	            return true;    
	        }else{    
	            return false;    
	        } 
		}
/*	public static void main(String [] args){
		FileUpload f = new FileUpload();
		//System.out.println(f.getFileSize("C:\\WINDOWS\\Web\\Wallpaper\\chibi.jpg"));
		//System.out.println(f.getFileSize("C:\\WINS\\Web\\Wallpaper\\chijpg"));
		//f.upLoad("e:/sstext/", "C:\\WINDOWS\\Web\\Wallpaper\\1920CHINA_2011.jpg", "aaaa");
		f.upLoad("E:/sstext/","C:\\WINDOWS\\Web\\Wallpaper\\chibi.jpg","texst",50,true);
	}*/
	
	
	//验证是否有效图片， 文件限制大小:int commitSize,大小以k表示 
		public static BaseResult  checkImageType(File file,String uploadFileName,int commitSize){
			
	    try {
	    //0.验证图片大小
	    	long fileSize = getFileSize(file);
	    	System.out.println("文件大小"+fileSize);
	    	if(fileSize>commitSize*1000l){ 
	    	 return new BaseResult(86,"图片应小于2M");//图片大小超过限制	
	    	}
	    	
		//1.验证后缀名
			String extension = "";
			if(!file.exists()){
			    return new BaseResult(81,"图片不存在");//图片不存在
			}
			if(uploadFileName.lastIndexOf(".")!=-1){
					extension = uploadFileName.substring(uploadFileName.lastIndexOf(".")+1);
				}
			
			Pattern pattern = Pattern.compile("bmp|gif|png|jpeg|jpg");
			Matcher matcher = pattern.matcher(extension);
			if(!matcher.find()){
				  return new BaseResult(82,"不是指定的图片格式"); //82:不是指定的图片格式
			}else{
				//2.验证图片长宽 
				Image img = null;  
				img = ImageIO.read(file);  
			     if (img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0) {  
			    	 return new BaseResult(82,"不是指定的图片格式"); //82:不是指定的图片格式
				  }  
				        return new BaseResult(1,"成功"); 
			   }
			} catch (Exception e) {  
				        e.printStackTrace();
				        return new BaseResult(85,"图片读取异常");
				    } 
		}
		
	public static String findFiles(String filePath) {     
	       
	        File file = new File(filePath);       // 创建一个File对象  
	        if (file.exists()) {  // 判断目录是否存在  
	           return file.getAbsolutePath();
	        }  
	          return null;
	    }     
		
		
	/*public static void writeJPEG(File $source, File $dest, int $quality) throws IOException
	{
	    String __formatName = "jpeg";
	    BufferedImage __image = ImageIO.read($source);
	    //如果图像是透明的，就丢弃Alpha通道
	    if(__image.getTransparency() == Transparency.TRANSLUCENT)
	        __image = get24BitImage(__image);
	        //__image = get24BitImage(__image, Color.BLACK);
	    ImageWriter __writer = ImageIO.getImageWritersByFormatName(__formatName).next();
	    ImageWriteParam __writeParam = __writer.getDefaultWriteParam();
	    FileOutputStream __out = new FileOutputStream($dest);
	    __writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
	    __writeParam.setCompressionQuality((float)$quality/100f);
	    __writer.setOutput(ImageIO.createImageOutputStream(__out));
	    __writer.write(null, new IIOImage(__image, null, null), __writeParam);
	    __out.flush();
	    __out.close();
	    __writer.dispose();
	}
	*/
	/**
	 * 使用删除alpha值的方式去掉图像的alpha通道
	 * @param $image
	 * @return
	 */
	protected static BufferedImage get24BitImage(BufferedImage image)
	{
	    int w = image.getWidth();
	    int h = image.getHeight();
	    int[] imgARGB = getRGBs(image.getRGB(0, 0, w, h, null, 0, w));
	    BufferedImage newImg = new BufferedImage(w,h, BufferedImage.TYPE_INT_RGB);
	    newImg.setRGB(0, 0,w,h, imgARGB, 0,w);
	    return newImg;
	}

	/**
	 * 使用绘制的方式去掉图像的alpha值
	 * @param $image
	 * @param $bgColor
	 * @return
	 */
	protected static BufferedImage get24BitImage(BufferedImage $image, Color bgColor)
	{
	    int w = $image.getWidth();
	    int h = $image.getHeight();
	    BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	    Graphics2D graphic = image.createGraphics();
	    graphic.setColor(bgColor);
	    graphic.fillRect(0,0,w,h);
	    graphic.drawRenderedImage(image, null);
	    graphic.dispose();
	    return image; 
	}

	/**
	 * 将32位色彩转换成24位色彩（丢弃Alpha通道）
	 * @param $argb
	 * @return
	 */
	public static int[] getRGBs(int[] argb)
	{
	    int[] rgbs = new int[argb.length];
	    for(int i=0;i<argb.length;i++)
	    {
	        rgbs[i] = argb[i] & 0xFFFFFF;
	    }
	    return rgbs;
	}

	
    public static byte[] readInputStream(InputStream inStream) { 
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        //创建一个Buffer字符串  
        byte[] buffer = new byte[8092];  
        //每次读取的字符串长度，如果为-1，代表全部读取完毕  
        int len = 0;  
        try{
        //使用一个输入流从buffer里把数据读取出来  
        while( (len=inStream.read(buffer)) != -1 ){  
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度  
            outStream.write(buffer, 0, len);  
        }  
        //关闭输入流  
        inStream.close();  
        }catch(IOException e){
        	e.printStackTrace();
        }
        //把outStream里的数据写入内存  
        return outStream.toByteArray();  
    }  
	
}


