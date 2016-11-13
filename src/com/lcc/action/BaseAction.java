package com.lcc.action;

import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import com.opensymphony.xwork2.ActionSupport;

public abstract class BaseAction extends ActionSupport implements ServletRequestAware,ServletResponseAware,SessionAware{
	
	private static final long serialVersionUID = 1L;
	public static final String LIST = "list";
	public HttpServletRequest request;
	public HttpServletResponse response;
	public Map<String, Object> session;
	public ServletContext application;
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request = request;
		this.application = request.getSession().getServletContext();
	}
	
	@Override
	public void setSession(Map<String, Object> session) {
		// TODO Auto-generated method stub
		this.session = session;
	}
	
	@Override
	public void setServletResponse(HttpServletResponse response) {
		// TODO Auto-generated method stub
		this.response = response;
	}
	
	public String execute() throws Exception {
		return list();
	};
	
	public abstract String list() throws Exception;
	
}
