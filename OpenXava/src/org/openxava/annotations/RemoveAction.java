package org.openxava.annotations;

import java.lang.annotation.*;

/**
 * Allows to define a custom action to remove the element from the collection. 
 * 
 * Applies to @OneToMany/@ManyToMany collections. <p>
 * 
 * This is the action executed on click in 'Remove detail 'link.<br>
 * Example:
 * <pre>
 * &nbsp;@OneToMany (mappedBy="delivery", cascade=CascadeType.REMOVE)
 * &nbsp;@RemoveAction("DeliveryDetails.remove")
 * &nbsp;private Collection<DeliveryDetail> details;	
 * </pre>
 * 
 * @author Javier Paniza
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD })
public @interface RemoveAction {
	
	/**
	 * List of comma separated view names where this annotation applies. <p>
	 * 
	 * Exclusive with notForViews.<br>
	 * If both forViews and notForViews are omitted then this annotation
	 * apply to all views.<br>
	 * You can use the string "DEFAULT" for referencing to the default
	 * view (the view with no name).
	 */	
	String forViews() default "";
	
	/**
	 * List of comma separated view names where this annotation does not apply. <p>
	 * 
	 * Exclusive with forViews.<br>
	 * If both forViews and notForViews are omitted then this annotation
	 * apply to all views.<br>
	 * You can use the string "DEFAULT" for referencing to the default
	 * view (the view with no name).
	 */ 	
	String notForViews() default "";
	
	/**
	 * You have to write the action identifier that is the controller
	 * name and the action name. This action must be registered in controllers.xml
	 */	
	String value();
	
}
