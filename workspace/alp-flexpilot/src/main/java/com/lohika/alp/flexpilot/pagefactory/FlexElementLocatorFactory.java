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

/**
 * A factory for producing {@link FlexElementLocator}s. It is expected that a new FlexElementLocator will be
 * returned per call.
 */
public interface FlexElementLocatorFactory {
	  /**
	   * When a field on a class needs to be decorated with an {@link FlexElementLocator} this method will
	   * be called.
	   * 
	   * @param field
	   * @return
	   */
	  FlexElementLocator createLocator(Field field);
}
