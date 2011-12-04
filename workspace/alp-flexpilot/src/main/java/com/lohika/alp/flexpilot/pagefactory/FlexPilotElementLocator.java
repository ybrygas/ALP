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
package com.lohika.alp.flexpilot.pagefactory;

import java.lang.reflect.Field;
import java.util.List;

import com.lohika.alp.flexpilot.Annotations;
import com.lohika.alp.flexpilot.By;
import com.lohika.alp.flexpilot.FlexElement;
import com.lohika.alp.flexpilot.driver.FlexDriver;
import com.lohika.alp.selenium.log.DescribedElement;
import com.lohika.alp.selenium.log.LogDescriptionAnnotations;
import com.lohika.alp.selenium.log.LogDescriptionBean;

public class FlexPilotElementLocator implements FlexElementLocator {
	
	  private final FlexDriver driver;
	  private final boolean shouldCache;
	  private final By by;
	  private FlexElement cachedElement;
	  private List<FlexElement> cachedElementList;
	  private final LogDescriptionBean descr;

	  /**
	   * Creates a new element locator.
	   * 
	   * @param driver The driver to use when finding the element
	   * @param field The field on the Page Object that will hold the located value
	   */
	  public FlexPilotElementLocator(FlexDriver driver, Field field) {
	    this.driver = driver;
	    Annotations annotations = new Annotations(field);
	    shouldCache = annotations.isLookupCached();
	    by = annotations.buildBy();
		LogDescriptionAnnotations descAnnotations = new LogDescriptionAnnotations(
				field);

		descr = descAnnotations.buildLogDescriptionBean();
	  }

	  /**
	   * Find the element.
	   */
	  public FlexElement findElement() {
	    if (cachedElement != null && shouldCache) {
	      return cachedElement;
	    }

    	FlexElement element = driver.findElement(by);

		if (element instanceof DescribedElement) {

			((DescribedElement) element).setDescription(descr);
		}
		
	    if (shouldCache) {
	      cachedElement = element;
	    }

	    return element;
	  }

	  /**
	   * Find the element list.
	   */
	  public List<FlexElement> findElements() {
	    if (cachedElementList != null && shouldCache) {
	      return cachedElementList;
	    }

	    List<FlexElement> elements = driver.findElements(by);
	    if (shouldCache) {
	      cachedElementList = elements;
	    }

	    return elements;
	  }
}
