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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.lohika.alp.flexpilot.FlexElement;

public class FlexPilotLocatingElementHandler implements InvocationHandler {
	private final FlexElementLocator locator;

	public FlexPilotLocatingElementHandler(FlexElementLocator locator) {
		this.locator = locator;
	}

	public Object invoke(Object object, Method method, Object[] objects)
			throws Throwable {
		FlexElement element = locator.findElement();

		if ("getWrappedElement".equals(method.getName())) {
			return element;
		}

		try {
			return method.invoke(element, objects);
		} catch (InvocationTargetException e) {
			// Unwrap the underlying exception
			Throwable t = e.getCause();
/*
			// TODO add utility to unwrap FlexPilotElement
			if (element instanceof FlexPilotElement) {
				if (t instanceof ElementNotVisibleException)
					throw new ElementNotVisibleException(
							(FlexPilotElement) element,
							(ElementNotVisibleException) t);
			}
*/
			throw t;
		}
	}

}
