package org.openxava.formatters;

import java.text.*;

import javax.servlet.http.*;

import org.apache.commons.codec.binary.*;




/**
 * A simple implementation: Only it shows a icon to indicate that it's a image/photo. <p> 
 * 
 * @author Javier Paniza
 * @author Franklin Alier 
 */

public class ImageFormatter implements IFormatter {
				
	public String format(HttpServletRequest request, Object object) {		
		String encodedImage = Base64.encodeBase64String((byte[]) object); 
		return "<img src='data:image;base64," + encodedImage + "'/>"; 
	}
	
	public Object parse(HttpServletRequest request, String string) throws ParseException {
		return null;		
	}
	
}
