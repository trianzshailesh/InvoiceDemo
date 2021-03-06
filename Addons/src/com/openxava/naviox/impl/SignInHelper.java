package com.openxava.naviox.impl;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;

import org.apache.commons.logging.*;
import org.openxava.util.*;

import com.openxava.naviox.*;
import com.openxava.naviox.model.*;

/**
 * 
 * @author Javier Paniza
 */

public class SignInHelper {
	
	private final static String PROPERTIES_FILE = "naviox-users.properties";
	private static Log log = LogFactory.getLog(SignInHelper.class);
	private static Properties users;
	
	public static void signIn(HttpSession session, String userName) {
		session.setAttribute("naviox.user", userName);
		Modules modules = (Modules) session.getAttribute("modules");
		modules.reset();		
	}
	
	public static boolean isAuthorized(String user, String password) {
		String storedPassword = getUsers().getProperty(user, null);
		return password.equals(storedPassword);
	}	 
	
	private static Properties getUsers() {		
		if (users == null) {
			PropertiesReader reader = new PropertiesReader(
					Users.class, PROPERTIES_FILE);
			try {
				users = reader.get();
			} catch (IOException ex) {
				log.error(XavaResources.getString("properties_file_error",
						PROPERTIES_FILE), ex);
				users = new Properties();
			}
		}
		return users;		
	}

}
