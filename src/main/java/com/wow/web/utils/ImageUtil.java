package com.wow.web.utils;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import magick.CompositeOperator;
import magick.ImageInfo;
import magick.MagickException;
import magick.MagickImage;



public class ImageUtil {
	public static final Integer IMAGE_MAX_WIDTH = 400;
	public static final Integer IMAGE_MIN_WIDTH = 180;
	
	static{
		System.setProperty("jmagick.systemclassloader", "no");
		//System.out.println(System.getProperty("java.library.path"));
	}
	
	public static void main(String[] args)throws Exception {
		ImageUtil.cutImg("/Users/wanghw/Desktop/test.png", "/Users/wanghw/Desktop/out.png", 100, 100, 100, 100);
	}
	
	/**切取图片 
     * @param       imgPath     原图路径 
     *              toPath      生成文件位置 
     *              w           左上位置横坐标 
     *              h           左上位置竖坐标 
     *              x           右下位置横坐标 
     *              y           右下位置竖坐标 
     * @return  
     * @throw   MagickException 
     * @author sulliy@sina.com 2010-8-11 
     */  
    public static void cutImg(String imgPath, String toPath, int w, int h,  
            int x, int y) throws MagickException {  
        ImageInfo infoS = null;  
        MagickImage image = null;  
        MagickImage cropped = null;  
        Rectangle rect = null;  
        try {  
            infoS = new ImageInfo(imgPath);  
            image = new MagickImage(infoS);  
            rect = new Rectangle(x, y, w, h);  
            cropped = image.cropImage(rect);  
            cropped.setFileName(toPath);  
            cropped.writeImage(infoS);  
        }catch(Exception e){
        	e.printStackTrace();
        }finally {
            if (cropped != null) {  
                cropped.destroyImages();  
            }  
        }  
    }  
    
    /**创建图片水印 
     * @param       filePath    源文件路径 
     *              toImg       生成文件位置 
     *              logoPath    logo路径 
     *              pos         logo在源图片中的相对位置，以像素点为单位 
     * @return  
     * @throw   MagickException 
     * @author sulliy@sina.com 2010-8-11 
     */  
    public static void createWaterPrintByImg(String filePath, String toImg,  
            String logoPath, Point pos) throws MagickException {
          
        ImageInfo info = new ImageInfo();  
        MagickImage fImage = null;  
        MagickImage sImage = null;  
        MagickImage fLogo = null;  
        MagickImage sLogo = null;  
        Dimension imageDim = null;  
        Dimension logoDim = null;  
        try {  
            fImage = new MagickImage(new ImageInfo(filePath));  
            imageDim = fImage.getDimension();  
            int width = imageDim.width;  
            int height = imageDim.height;  
            sImage = fImage.scaleImage(width, height);  
              
            fLogo = new MagickImage(new ImageInfo(logoPath));  
            //fLogo.transparentImage(PixelPacket.queryColorDatabase("white"), 65535); //设置背景色  
            logoDim = fLogo.getDimension();  
            int lw = logoDim.width;  
            int lh = logoDim.height;  
              
            //默认或者参数出错时，LOGO放在右下角  
            if (width <= (int)pos.getX() || height <= (int)pos.getY()) {  
                pos.setLocation(width - lw, height - lh);  
            }  
              
            sLogo = fLogo.scaleImage(lw, lh);  
            sImage.compositeImage(CompositeOperator.AtopCompositeOp, sLogo,  
                    (int)pos.getX(), (int)pos.getY());  
            sImage.setFileName(toImg);  
            sImage.writeImage(info);  
        }catch(Exception e){
        	e.printStackTrace();
        } finally {  
            if (sImage != null) {  
                sImage.destroyImages();  
            }  
        }  
    }  
}
