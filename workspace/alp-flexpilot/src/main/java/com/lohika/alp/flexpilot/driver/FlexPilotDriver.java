//Copyright 2011 Lohika .  This file is part of ALP.
//
//    ALP is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    ALP is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with ALP.  If not, see <http://www.gnu.org/licenses/>.
package com.lohika.alp.flexpilot.driver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import com.lohika.alp.flexpilot.By;
import com.lohika.alp.flexpilot.FindsById;
import com.lohika.alp.flexpilot.FindsByChain;
import com.lohika.alp.flexpilot.FindsByLinkText;
import com.lohika.alp.flexpilot.FindsByName;
import com.lohika.alp.flexpilot.FlexElement;
import com.lohika.alp.flexpilot.SearchContext;
import com.lohika.alp.flexpilot.pagefactory.FlexPilotFactory;
import com.lohika.alp.flexpilot.pagefactory.FlexPilotFactoryJAXB;

public class FlexPilotDriver implements FlexDriver, SearchContext,
FindsById, FindsByName, FindsByLinkText, FindsByChain, TakesScreenshot {
	
	private Logger log = Logger.getLogger(getClass());

	private WebDriver driver;
	private String flashObjectName;
	protected final FlexPilotFactory factory;
	
	public FlexPilotDriver(WebDriver driver, String flashObjectName) {
		this.driver = driver;
		this.flashObjectName = flashObjectName;
		this.factory = new FlexPilotFactoryJAXB();
	}
	
	protected Object doCommand(String command, Object... args) {
		String params = arrayToString(args,",");
		log.debug("COMMAND:"+command+" {"+params+"}");
		Object res = null;
		try {
			res = ((JavascriptExecutor) driver).executeScript("return document.getElementsByName('" +
				flashObjectName + "')[0]." +
				command +
				"(" +
				params +
				")");
		} catch (WebDriverException e) {
			return "Unable to locate element "+params;
		}
		return res;
	}

	protected Object execute(String command, Map<String, ?> parameters) {
		ArrayList<Object> args = new ArrayList<Object>();
		
		for(Map.Entry<String, ?> e : parameters.entrySet()) {
			StringBuffer item = new StringBuffer();
			
			if ("id".equals(e.getKey()) || "opt".equals(e.getKey()))
				item.append(e.getValue().toString());
			else {
				item.append("'");
				item.append(e.getKey().toString());
				item.append("':'");
				item.append(e.getValue());
				item.append("'");
			}
			args.add(item.toString());
		}
		
		return doCommand(command, args.toArray());
	}

	public static String arrayToString(Object[] a, String separator) {
	    StringBuffer result = new StringBuffer();
	    if (a.length > 0) {
		    result.append('{');
	        result.append(a[0]);
	        for (int i=1; i<a.length; i++) {
	            result.append(separator);
	            result.append(a[i]);
	        }
	        result.append('}');
	    }
	    return result.toString();
	}

	public FlexElement findElement(By by) {
		return by.findElement(this);
	}

	public List<FlexElement> findElements(By by) {
		return by.findElements(this);
	}
	
	private String getId(String by, String using) {
		return "'"+by+"':'"+using+"'";
	}

	private String getId(String xpath, String rootobj, String maxdepth) {
		String id =  "'xpath':'"+xpath+"'";
		if (!"".equals(rootobj))
			id += ",'rootobj':'"+rootobj+"'";
		if (!"".equals(maxdepth))
			id += ",'maxdepth':'"+maxdepth+"'";
		return id;
	}

	protected FlexElement findElement(String by, String using) {
		if (using == null) {
			throw new IllegalArgumentException("Cannot find elements when the selector is null.");
		}
		FlexPilotElement el = new FlexPilotElement(this, factory);
		el.setId(getId(by, using));
		return el;
	}
	
	/** TODO: implement searching elements **/
	protected List<FlexElement> findElements(String by, String using) {
		if (using == null) {
			throw new IllegalArgumentException("Cannot find elements when the selector is null.");
		}
		List<FlexElement> elements = new ArrayList<FlexElement>();
		return elements;
	}
	  
	protected FlexElement findElement(String xpath, String rootobj, String maxdepth) {
		if (xpath == null) {
			throw new IllegalArgumentException("Cannot find elements when the selector is null.");
		}
		FlexPilotElement el = new FlexPilotElement(this, factory);
		el.setId(getId(xpath, rootobj, maxdepth));
		return el;
	}

	/** TODO: implement searching elements **/
	protected List<FlexElement> findElements(String xpath, String rootobj, String maxdepth) {
		if (xpath == null) {
			throw new IllegalArgumentException("Cannot find elements when the selector is null.");
		}
		List<FlexElement> elements = new ArrayList<FlexElement>();
		return elements;
	}
	  
	public FlexElement findElementById(String using) {
		return findElement("id", using);
	}

	public List<FlexElement> findElementsById(String using) {
		return findElements("id", using);
	}
	  
	public FlexElement findElementByChain(String using) {
		return findElement("chain", using);
	}

	public List<FlexElement> findElementsByChain(String using) {
		return findElements("chain", using);
	}

	public FlexElement findElementByName(String using) {
		return findElement("name", using);
	}

	public List<FlexElement> findElementsByName(String using) {
		return findElements("name", using);
	}

	public FlexElement findElementByLinkText(String using) {
		return findElement("link", using);
	}

	public List<FlexElement> findElementsByLinkText(String using) {
		return findElements("link", using);
	}

	public FlexElement findElementByXPath(String xpathExpression, String rootobj, String maxdepth) {
		return findElement(xpathExpression, rootobj, maxdepth);
	}

	public List<FlexElement> findElementsByXPath(String xpathExpression, String rootobj, String maxdepth) {
		return findElements(xpathExpression, rootobj, maxdepth);
	}

	public String getVersion() {
		return (String)doCommand(DriverCommand.GET_VERSION);
	}

	public boolean isFlexLoaded() {
		log.debug("COMMAND: isFlexLoaded()");
		Object result = ((JavascriptExecutor) driver).executeScript("return document.getElementsByName('" +
				flashObjectName + "')[0]." + DriverCommand.CLICK);
		if (result==null)
			return false;
		else if (result instanceof String)
			return true;
		else return false;
	}

	public <X> X getScreenshotAs(OutputType<X> target)
			throws WebDriverException {
		return ((TakesScreenshot) driver).getScreenshotAs(target);
	}

}
