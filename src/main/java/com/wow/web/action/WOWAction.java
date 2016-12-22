package com.wow.web.action;

import java.awt.Point;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.wow.web.utils.HttpClientUtil;
import com.wow.web.utils.ImageUtil;
import com.wow.web.utils.WOW;


@Controller("wowAction")
@Scope("prototype")
@Namespace("/wow")
@Results({
	@Result(name="welcome", location="/WEB-INF/welcome.jsp"),
	@Result(name="add", location="/WEB-INF/channel/add.jsp"),
	@Result(name="modify", location="/WEB-INF/channel/modify.jsp")
	})
public class WOWAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(this.getClass());

	public HttpServletRequest getRequest(){
		return ServletActionContext.getRequest();
	}
	
	@Action(value="welcome")
	public String welcome(){
		return "welcome";
	}
	
	@Action(value="query")
	public String query(){
		PrintWriter pw = null;
		HttpServletResponse response = ServletActionContext.getResponse();
		try{
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			pw = ServletActionContext.getResponse().getWriter();
			String server = getRequest().getParameter("server");
			if(null==server)server = "";
			server = URLEncoder.encode(server,"UTF-8");
			
			String name = getRequest().getParameter("name");
			if(null==name)name = "";
			name = URLEncoder.encode(name,"UTF-8");
			
			String url = "http://www.battlenet.com.cn/wow/zh/character/"+server+"/"+name+"/simple";
			log.info(url);
			String content = HttpClientUtil.get(url, "UTF-8");
			
			String result = "{\"result\":\"success\"}";
			if(null==content || content.indexOf("server-error") >=0 ){
				result = "{\"result\":\"error\"}";
			}
			pw.print(result);
		}catch(Exception e){
			pw.print("{\"result\":\"error\"}");
			log.error(e.getMessage(), e);
		}
		return null;
	}
	@Action(value="build")
	public String build(){
		PrintWriter pw = null;
		HttpServletResponse response = ServletActionContext.getResponse();
		try{
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			pw = ServletActionContext.getResponse().getWriter();
			String server = getRequest().getParameter("server");
			if(null==server)server = "";
			server = URLEncoder.encode(server,"UTF-8");
			
			String name = getRequest().getParameter("name");
			if(null==name)name = "";
			name = URLEncoder.encode(name,"UTF-8");
			
			String url = "http://www.battlenet.com.cn/wow/zh/character/"+server+"/"+name+"/simple";
			log.info(url);
			long id = WOW.buildUserImage(url);
			//long id = System.currentTimeMillis();
			String result = "{\"result\":\"success\",\"uuid\":\""+id+"\"}";
			pw.print(result);
		}catch(Exception e){
			pw.print("{\"result\":\"success\",\"uuid\":\"\"}");
			log.error(e.getMessage(), e);
		}
		return null;
	}
	@Action(value="show")
	public String show(){
		PrintWriter pw = null;
		HttpServletResponse response = ServletActionContext.getResponse();
		try{
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			pw = ServletActionContext.getResponse().getWriter();
			String id = getRequest().getParameter("uuid");
			if(null==id){
				pw.print("{\"result\":\"success\",\"url\":\"\"}");
				return null;
			}
			
			int w = 60;
			String name = getRequest().getParameter("name");
			if(null==name)name = "";
			w = w*name.length();
			
			String org = "/data/www/www.aigaosu.com/wow/"+id+".png";
			String body = "/data/www/www.aigaosu.com/wow/"+id+"_body.png";
			String title = "/data/www/www.aigaosu.com/wow/"+id+"_title.png";
			String show = "/data/www/www.aigaosu.com/wow/"+id+"_show.png";
			String url = "http://www.aigaosu.com/wow/"+id+"_show.png";
			ImageUtil.cutImg(org, body, 510, 450, 360, 428);
			ImageUtil.cutImg(org, title, w+140, 140, 15, 270);
			log.info(url);
			Point pos = new Point();
			pos.x = 0;
			pos.y = 450-140;
			ImageUtil.createWaterPrintByImg(body, show, title, pos);
			String result = "{\"result\":\"success\",\"url\":\""+url+"\"}";
			pw.print(result);
		}catch(Exception e){
			pw.print("{\"result\":\"error\",\"url\":\"\"}");
			log.error(e.getMessage(), e);
			return null;
		}
		return null;
	}
}
