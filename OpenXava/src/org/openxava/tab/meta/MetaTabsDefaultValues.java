package org.openxava.tab.meta;


import java.util.*;
import org.openxava.tab.meta.xmlparse.*;
import org.openxava.util.*;

public class MetaTabsDefaultValues {
	
	private static Map<String, Collection<MetaTab>> tabsForModels;
	private static Map<String, Collection<MetaTab>> tabsExceptForModels;
	private static Collection<MetaTab> defaultTabs; 	
	
	
	public static void _putMetaTabForModel(String model, MetaTab tab) throws XavaException {
		if (tabsForModels == null) {
			throw new XavaException("only_from_parse", "MetaTabs._putMetaTabForModel");
		}		
		
		if (!tabsForModels.containsKey(model)) tabsForModels.put(model, new ArrayList<MetaTab>());
		tabsForModels.get(model).add(tab);
	}
	
	public static void _putMetaTabExceptForModel(String model, MetaTab tab) throws XavaException {
		if (tabsExceptForModels == null) {
			throw new XavaException("only_from_parse", "MetaTabs._putMetaTabExceptForModel");
		}		
		
		if (!tabsExceptForModels.containsKey(model)) tabsExceptForModels.put(model, new ArrayList<MetaTab>());
		tabsExceptForModels.get(model).add(tab);
	}
	
	public static void _addDefaultMetaTab(MetaTab tab) throws XavaException {
		if (defaultTabs == null) {
			throw new XavaException("only_from_parse", "MetaTabs._addDefaultMetaTab");
		}		
		defaultTabs.add(tab);
	}
	
	private static void setup() throws XavaException {
		tabsForModels = new HashMap<String, Collection<MetaTab>>();
		tabsExceptForModels = new HashMap<String, Collection<MetaTab>>();
		defaultTabs = new ArrayList<MetaTab>();
		TabsDefaultValuesParser.setupTabs();		
	}
	
	static Collection<MetaTab> getMetaTabsForModel(String model) throws ElementNotFoundException, XavaException {
		if (tabsForModels == null) {
			setup();
		}
		Collection<MetaTab> tabs = new ArrayList<MetaTab>(defaultTabs);
		for (Map.Entry<String, Collection<MetaTab>> e: tabsExceptForModels.entrySet()) {
			if (!e.getKey().equals(model)) tabs.addAll(e.getValue());
		}
		
		if (tabsForModels.containsKey(model)) {
			tabs.addAll(tabsForModels.get(model));
		}
		
		return tabs;
	}
		
}


