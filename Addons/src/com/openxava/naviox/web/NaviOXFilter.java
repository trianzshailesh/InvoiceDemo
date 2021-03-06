package com.openxava.naviox.web;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.openxava.controller.*;
import org.openxava.jpa.*;
import org.openxava.util.*;

import com.openxava.naviox.*;
import com.openxava.naviox.impl.*;
import com.openxava.naviox.model.*;
import com.openxava.naviox.util.*;

/**
 * 
 * @author Javier Paniza
 */
public class NaviOXFilter implements Filter {
	
	public void init(FilterConfig cfg) throws ServletException {
		Modules.init(cfg.getServletContext().getContextPath().substring(1)); 
	}



	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			XPersistence.reset();
			Initializer.init(); 
			HttpSession session = ((HttpServletRequest) request).getSession();
			Modules modules = (Modules) session.getAttribute("modules");
			if (modules == null) {
				modules = new Modules();
				session.setAttribute("modules", modules);
			}
			if (Is.empty(session.getAttribute("xava.user"))) {
				String autologinUser = NaviOXPreferences.getInstance().getAutologinUser();
				if (!Is.emptyString(autologinUser)) {
					if (SignInHelper.isAuthorized(autologinUser, NaviOXPreferences.getInstance().getAutologinPassword())) {
						SignInHelper.signIn(session, autologinUser);
					}					
				}
			}
			session.setAttribute("xava.user", session.getAttribute("naviox.user")); // We use naviox.user instead of working only
						// with xava.user in order to prevent some security hole using UrlParameters.setUser
			
			HttpServletRequest secureRequest = new HttpServletRequestWrapper((HttpServletRequest)request) {
				
				public String getRemoteUser() {				
					return (String) ((HttpServletRequest) getRequest()).getSession().getAttribute("naviox.user");
				}
				
			};
			
			Users.setCurrent(secureRequest);
		
			if (modules.isModuleAuthorized(secureRequest)) {
				chain.doFilter(secureRequest, response);
			}
			else {
				char base = secureRequest.getRequestURI().split("/")[2].charAt(0)=='p'?'p':'m'; 
				RequestDispatcher dispatcher = request.getRequestDispatcher("/" + base + "/SignIn?originalURI=" + secureRequest.getRequestURI()); 
				dispatcher.forward(request, response);
			}
		} 
		finally {
			XPersistence.commit();
		}
	}
		
	public void destroy() {
	}

}
