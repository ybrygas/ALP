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
package com.lohika.alp.flexpilot;

import java.util.List;

import org.openqa.selenium.NoSuchElementException;

public interface SearchContext {
	  /**
	   * Find all elements within the current context using the given mechanism.
	   * 
	   * @param by The locating mechanism to use
	   * @return A list of all {@link FlexElement}s, or an empty list if nothing matches
	   * @see org.openqa.selenium.By
	   */
	  List<FlexElement> findElements(By by);


	  /**
	   * Find the first {@link FlexElement} using the given method.
	   * 
	   * @param by The locating mechanism
	   * @return The first matching element on the current context
	   * @throws NoSuchElementException If no matching elements are found
	   */
	  FlexElement findElement(By by);
}
