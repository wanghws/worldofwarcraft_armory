package com.wow.web.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpClientUtil {
	
	
    public static String get(String url, String encoding) throws Exception {
    	DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse res = httpClient.execute(httpGet);
        return getContent(res, encoding);
    }

    public static String get(String url, String encoding, DefaultHttpClient client) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        HttpResponse res = client.execute(httpGet);
        return getContent(res, encoding);
    }


    public static String post(String url, StringEntity se, String host, String referer, String encoding) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(se);
        httpPost.setHeader("Host", host);
        httpPost.setHeader("Referer", referer);
        httpPost.setHeader("Accept", "*/*");
        httpPost.setHeader("Accept-Language", "zh-cn");
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.setHeader("UA-CPU", "x86");
        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1) ; .NET CLR 2.0.50727; InfoPath.2; CIBA)");
        httpPost.setHeader("Connection", "close");
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = httpClient.execute(httpPost);

        return getContent(response, encoding);
    }
    
    public static String httpPost(String url, String queryString,String encoding)  throws Exception{
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(new StringEntity(queryString));
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
		httpPost.getParams().setParameter("http.socket.timeout", new Integer(20000));
		httpPost.setHeader("Connection", "close");
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpResponse response = httpClient.execute(httpPost);
		return getContent(response, encoding);
	}

    public static String getContent(HttpResponse res, String encoding) throws Exception {
        HttpEntity ent = res.getEntity();
        String result = IOUtils.toString(ent.getContent(), encoding);
        ent.consumeContent();
        return result;
    }

    public static InputStream getStream(String url) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpResponse res = httpClient.execute(httpGet);
        return res.getEntity().getContent();
    }

    public static InputStream getStream(String url, DefaultHttpClient client) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1) ; .NET CLR 2.0.50727; InfoPath.2; CIBA)");
        httpGet.setHeader("Referer", "http://reg.126.com/regmail126/userRegist.do?action=fillinfo");
        httpGet.setHeader("Connection", "close");
        HttpResponse res = client.execute(httpGet);
        return res.getEntity().getContent();
    }

	public static String getUrlString(String url){
		try{
			HttpURLConnection conn = (HttpURLConnection)new URL(url).openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(50000);
			conn.setDoOutput(true);
			conn.connect();
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
			StringBuffer sb = new StringBuffer();
			String temp = rd.readLine();
			while(temp!=null){
				sb.append(temp);
				temp = rd.readLine();
			}
			rd.close();
			conn.disconnect();
			return sb.toString();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}
	public static void main(String[] rags){
		System.out.print(HttpClientUtil.getUrlString("http://www.sina.com.cn"));
	}
}
