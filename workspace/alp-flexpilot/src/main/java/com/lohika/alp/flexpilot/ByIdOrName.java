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

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.NoSuchElementException;

public class ByIdOrName extends By {
	  private By idFinder;
	  private By nameFinder;
	  private String idOrName;

	  public ByIdOrName(String idOrName) {
	    this.idOrName = idOrName;
	    idFinder = By.id(idOrName);
	    nameFinder = By.name(idOrName);
	  }

	  @Override
	  public FlexElement findElement(SearchContext context) {
	    try {
	      // First, try to locate by id
	      return idFinder.findElement(context);
	    } catch (NoSuchElementException e) {
	      // Then by name
	      return nameFinder.findElement(context);
	    }
	  }

	  @Override
	  public List<FlexElement> findElements(SearchContext context) {
	    List<FlexElement> elements = new ArrayList<FlexElement>();

	    // First: Find by id ...
	    elements.addAll(idFinder.findElements(context));
	    // Second: Find by name ...
	    elements.addAll(nameFinder.findElements(context));

	    return elements;
	  }

	  @Override
	  public String toString() {
	    return "by id or name \"" + idOrName + '"';
	  }
	}
