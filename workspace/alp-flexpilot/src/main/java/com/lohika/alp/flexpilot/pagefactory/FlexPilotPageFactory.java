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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.openqa.selenium.support.PageFactory;

import com.lohika.alp.flexpilot.driver.FlexDriver;
import com.lohika.alp.flexpilot.driver.FlexPilotDriver;


public class FlexPilotPageFactory {
	
	public static <T> T initElements(FlexPilotDriver driver, Class<T> pageClassToProxy) {
		T page = instantiatePage(driver, pageClassToProxy);
		initElements(driver, page);
		return page;
	}

	public static void initElements(FlexPilotDriver driver, Object page) {
		final FlexDriver driverRef = driver;

		FlexElementLocatorFactory factory;

		factory = new FlexPilotElementLocatorFactory(driverRef);

		//factory = new LogDescriptionElementLocatorFactory(factory);

		initElements(factory, page);
	}
	
	public static void initElements(FlexElementLocatorFactory factory, Object page) {
		final FlexElementLocatorFactory factoryRef = factory;
		
		// Temporary fix for http://code.google.com/p/selenium/issues/detail?id=1754
		PageFactory.initElements(new FlexPilotDefaultFieldDecorator(factoryRef), page);
	}

	private static <T> T instantiatePage(FlexPilotDriver driver,
			Class<T> pageClassToProxy) {
		try {
			try {
				Constructor<T> constructor = pageClassToProxy
						.getConstructor(FlexPilotDriver.class);
				return constructor.newInstance(driver);
			} catch (NoSuchMethodException e) {
				return pageClassToProxy.newInstance();
			}
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
}
