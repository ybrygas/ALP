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

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.NoSuchElementException;

import com.lohika.alp.flexpilot.By;
import com.lohika.alp.flexpilot.FlexElement;
import com.lohika.alp.flexpilot.SearchContext;

public class ByChained extends By {

  private By[] bys;

  public ByChained(By... bys) {
    this.bys = bys;
  }

  @Override
  public FlexElement findElement(SearchContext context) {
    List<FlexElement> elements = findElements(context);
    if (elements.isEmpty())
      throw new NoSuchElementException("Cannot locate an element using " + toString());
    return elements.get(0);
  }

  @Override
  public List<FlexElement> findElements(SearchContext context) {
    if (bys.length == 0) {
      return new ArrayList<FlexElement>();
    }

    List<FlexElement> elems = null;
    for (By by : bys) {
      List<FlexElement> newElems = new ArrayList<FlexElement>();

      if (elems == null) {
        newElems.addAll(by.findElements(context));
      } else {
        for (FlexElement elem : elems) {
          newElems.addAll(elem.findElements(by));
        }
      }
      elems = newElems;
    }

    return elems;
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder("By.chained(");
    stringBuilder.append("{");

    boolean first = true;
    for (By by : bys) {
      stringBuilder.append((first ? "" : ",")).append(by);
      first = false;
    }
    stringBuilder.append("})");
    return stringBuilder.toString();
  }
}
