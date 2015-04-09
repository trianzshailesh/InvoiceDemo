package com.openxava.naviox.impl;

import org.openxava.annotations.parse.*;

import com.openxava.naviox.model.*;


/**
 * 
 * @author Javier Paniza
 */
public class Initializer {
	
	private static boolean initiated = false; 
	
	public static void init() {
		if (initiated) return;
		AnnotatedClassParser.getManagedClassNames().add(SignIn.class.getName());	
		initiated = true;
	}

}
