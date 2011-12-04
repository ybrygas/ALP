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
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

import com.lohika.alp.flexpilot.FlexElement;
import com.lohika.alp.flexpilot.WrapsElement;

public class FlexPilotDefaultFieldDecorator implements FieldDecorator {
	
	protected FlexElementLocatorFactory factory;
	  
	public FlexPilotDefaultFieldDecorator(FlexElementLocatorFactory factory) {
		this.factory = factory;
	}

	public Object decorate(ClassLoader loader, Field field) {
		if (!FlexElement.class.isAssignableFrom(field.getType())) {
			return null;
		}

		FlexElementLocator locator = factory.createLocator(field);
		if (locator == null) {
			return null;
		}

		return proxyForLocator(loader, locator);
	}
	
	protected FlexElement proxyForLocator(ClassLoader loader,
			FlexElementLocator locator) {
		InvocationHandler handler = new FlexPilotLocatingElementHandler(
				locator);

		FlexElement proxy;

		// Temporary fix for
		// http://code.google.com/p/selenium/issues/detail?id=1754
		proxy = (FlexElement) Proxy.newProxyInstance(loader, new Class[] {
				FlexElement.class, WrapsElement.class, Locatable.class },
				handler);
		return proxy;
	}
}
