package com.wow.web.utils;


import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WOW {

	private static int      DISPLAY_NUMBER  = 99;
    private static String   XVFB            = "/usr/bin/Xvfb";
    private static String   XVFB_COMMAND    = XVFB + " :" + DISPLAY_NUMBER;
    //private static String   URL             = "http://www.battlenet.com.cn/wow/zh/character/%E9%A3%8E%E6%9A%B4%E4%B9%8B%E7%9C%BC/%E5%B8%8C%E8%BE%BE/simple";
    private static String   RESULT_FILENAME = "/data/www/www.aigaosu.com/wow/";
    //private static String   RESULT_FILENAME = "/Users/wanghw/Desktop/";

    public static void main ( String[] args ) throws IOException
    {
        //Process p = Runtime.getRuntime().exec(XVFB_COMMAND);
//        FirefoxBinary firefox = new FirefoxBinary();
//        firefox.setEnvironmentProperty("DISPLAY", ":" + DISPLAY_NUMBER);
//        WebDriver driver = new FirefoxDriver(firefox,null);
//        driver.get(URL);
//        File scrFile = ( (TakesScreenshot) driver ).getScreenshotAs(OutputType.FILE);
//        FileUtils.copyFile(scrFile, new File(RESULT_FILENAME));
//        driver.close();
//        //p.destroy();
//        System.exit(0);
    }
    
    public static long buildUserImage(String url)throws Exception{
    	//return System.currentTimeMillis();
    	long id = System.currentTimeMillis();
    	Process p = Runtime.getRuntime().exec(XVFB_COMMAND);
        FirefoxBinary firefox = new FirefoxBinary();
        firefox.setEnvironmentProperty("DISPLAY", ":" + DISPLAY_NUMBER);
        WebDriver driver = new FirefoxDriver(firefox,null);
        driver.get(url);
        File scrFile = ( (TakesScreenshot) driver ).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File(RESULT_FILENAME+id+".png"));
        driver.close();
        p.destroy();
        return id;
    }
}
