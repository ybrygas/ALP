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
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;

import com.google.common.collect.ImmutableMap;
import com.lohika.alp.flexpilot.By;
import com.lohika.alp.flexpilot.ElementNotVisibleException;
import com.lohika.alp.flexpilot.FindsByChain;
import com.lohika.alp.flexpilot.FindsById;
import com.lohika.alp.flexpilot.FindsByLinkText;
import com.lohika.alp.flexpilot.FindsByName;
import com.lohika.alp.flexpilot.FlexElement;
import com.lohika.alp.flexpilot.WrapsElement;
import com.lohika.alp.flexpilot.pagefactory.FlexPilotFactory;
import com.lohika.alp.selenium.log.DescribedElement;
import com.lohika.alp.selenium.log.LogDescriptionBean;

public class FlexPilotElement implements FlexElement, DescribedElement, WrapsElement {
	
	protected final Logger logger = Logger.getLogger(getClass());

	protected FlexPilotDriver driver;
	protected String id;
	protected LogDescriptionBean description;
	protected FlexPilotFactory factory;
	protected final FlexElement element;
	
	public FlexPilotElement(FlexPilotDriver driver) {
		this.driver = driver;
		element = this;
	}
	
	public FlexPilotElement(FlexPilotDriver driver, FlexPilotFactory factory) {
		this(driver);
		if (factory == null) {
			throw new IllegalArgumentException("Parameters can't be null");
		}
		this.factory = factory;
	}
	
	// TODO
	public FlexElement getWrappedElement() {
		//return this;
		return element;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setDescription(LogDescriptionBean description) {
		this.description = description;
	}

	public LogDescriptionBean getDescription() {
		return description;
	}

	public FlexElement findElement(By by) {
		return by.findElement(this);
	}

	public List<FlexElement> findElements(By by) {
		return by.findElements(this);
	}

	public void click() {
		logger.info(factory.click(this));
		execute(DriverCommand.CLICK, ImmutableMap.of("id", id));
	}

	public void sendKeys(CharSequence... keysToSend) {
		logger.info(factory.sendKeys(this, keysToSend));
		StringBuilder builder = new StringBuilder();
		for (CharSequence key : keysToSend) {
			builder.append(key);
		}
		execute(DriverCommand.TYPE, ImmutableMap.of("id", id, "text", builder.toString()));
	}
	
	public String getText() {
		return (String)execute(DriverCommand.GET_TEXT_VALUE, ImmutableMap.of("id", id));
	}

	protected Object execute(String command, Map<String, ?> parameters) throws ElementNotVisibleException {
		int timeout = 14;
		try {
			while (timeout-->0) {
				Object rId = driver.execute(
						DriverCommand.ASSERT_DISPLAY_OBJECT,
						ImmutableMap.of("id", id)); 
				if ((rId instanceof Boolean) && ((Boolean)rId))
					return driver.execute(command, parameters);
				Thread.sleep(500);
			}
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		throw new ElementNotVisibleException(this, (TakesScreenshot)driver);
	}
	
	protected void waitForObject(FlexPilotElement element) {
		int timeout = 14;
		try {
			while (timeout-->0) {
				Object rId = driver.execute(
						DriverCommand.ASSERT_DISPLAY_OBJECT,
						ImmutableMap.of("id", element.getId())); 
				if ((rId instanceof Boolean) && ((Boolean)rId))
					return;
				Thread.sleep(500);
			}
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		throw new ElementNotVisibleException(this, (TakesScreenshot)driver);
	}

	protected void waitForObjects(ArrayList<FlexPilotElement> elements) {
		int timeout = 14;
		Object[] r = new Object[elements.size()];
		try {
			while (timeout-->0) {
				int i=0;
				for (FlexPilotElement element: elements) {
					r[i++] = driver.execute(
							DriverCommand.ASSERT_DISPLAY_OBJECT,
							ImmutableMap.of("id", element.getId())); 
					if ((r[i] instanceof Boolean) && ((Boolean)r[i]))
						return;
				}
				Thread.sleep(500);
			}
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		throw new ElementNotVisibleException(this, (TakesScreenshot)driver);
	}
	
	public boolean isDisplayed() {
		Object r = driver.execute(
				DriverCommand.ASSERT_DISPLAY_OBJECT,
				ImmutableMap.of("id", id));
		if (r!=null && (r instanceof Boolean))
			return (Boolean)r;
		else return false;
	}

	public void dragAndDrop(FlexElement element) {
		dragAndDrop(element, new Point(8,8));
	}

	public void dragAndDrop(FlexElement element, Point point) {
		logger.info(factory.drugAndDrop(this, 
				((DescribedElement)((WrapsElement)element).getWrappedElement())));
			WrapsElement we = (WrapsElement)element;
			FlexElement wfe = we.getWrappedElement();
			FlexPilotElement toElement = (FlexPilotElement)wfe;
			
			// wait for object display, if not throw ElementNotVisibleException exception
			waitForObject(toElement);
			
			String optBy = "'opt"+toElement.getId().substring(1);
			
			execute(DriverCommand.DRAG_DROP_ELEM_TO_ELEM,
				ImmutableMap.of("id", id, "opt", optBy, "offsetx", point.x, "offsety", point.y));
	}

	public void dragAndDrop(Point point) {
		execute(DriverCommand.DRAG_DROP_TO_COORDS,
				ImmutableMap.of("id", id, "offsetx", point.getX(), "offsety", point.getY()));
	}

	public void select(int index) {
		logger.info(factory.select(this, Integer.valueOf(index).toString()));
		execute(DriverCommand.SELECT,
				ImmutableMap.of("id", id, "index", index));
	}

	public void select(String text) {
		logger.info(factory.select(this, text));
		execute(DriverCommand.SELECT,
			ImmutableMap.of("id", id, "data", text));
	}

	public void doubleClick() {
		logger.info(factory.doubleClick(this));
		execute(DriverCommand.DOUBLE_CLICK,
			ImmutableMap.of("id", id));
	}

}
