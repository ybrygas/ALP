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

public abstract class By {
	  /**
	   * @param id The value of the "id" attribute to search for
	   * @return a By which locates elements by the value of the "id" attribute.
	   */
	  public static By id(final String id) {
	    if (id == null)
	      throw new IllegalArgumentException(
	          "Cannot find elements with a null id attribute.");

	    return new ById(id);
	  }

	  /**
	   * @param name The value of the "name" attribute to search for
	   * @return a By which locates elements by the value of the "name" attribute.
	   */
	  public static By name(final String name) {
	    if (name == null)
	      throw new IllegalArgumentException(
	          "Cannot find elements when name text is null.");

	    return new ByName(name);
	  }
	  
	  /**
	   * @param linkText The exact text to match against
	   * @return a By which locates A elements by the exact text it displays
	   */
	  public static By linkText(final String linkText) {
	    if (linkText == null)
	      throw new IllegalArgumentException(
	          "Cannot find elements when link text is null.");

	    return new ByLinkText(linkText);
	  }

	  /**
	   * @param xpathExpression The xpath to use
	   * @return a By which locates elements via XPath
	   */
	  public static By chain(final String xpathExpression) {
	    if (xpathExpression == null)
	      throw new IllegalArgumentException(
	          "Cannot find elements when the XPath expression is null.");

	    return new ByChain(xpathExpression);
	  }	 
	  
	  /**
	   * Find a single element. Override this method if necessary.
	   * 
	   * @param context A context to use to find the element
	   * @return The WebElement that matches the selector
	   */
	  public FlexElement findElement(SearchContext context) {
	    List<FlexElement> allElements = findElements(context);
	    if (allElements == null || allElements.isEmpty())
	      throw new NoSuchElementException("Cannot locate an element using "
	          + toString());
	    return allElements.get(0);
	  }

	  /**
	   * Find many elements.
	   * 
	   * @param context A context to use to find the element
	   * @return A list of WebElements matching the selector
	   */
	  public abstract List<FlexElement> findElements(SearchContext context);
	  
	  public static class ById extends By {
	    private final String id;

	    public ById(String id) {
	      this.id = id;
	    }

	    @Override
	    public List<FlexElement> findElements(SearchContext context) {
	      if (context instanceof FindsById)
	        return ((FindsById) context).findElementsById(id);
	      return ((FindsByChain) context).findElementsByChain("*[@id = '" + id
	          + "']");
	    }

	    @Override
	    public FlexElement findElement(SearchContext context) {
	      if (context instanceof FindsById)
	        return ((FindsById) context).findElementById(id);
	      return ((FindsByChain) context).findElementByChain("*[@id = '" + id
	          + "']");
	    }

	    @Override
	    public String toString() {
	      return "By.id: " + id;
	    }
	  }
	  
	  public static class ByName extends By {
	    private final String name;

	    public ByName(String name) {
	      this.name = name;
	    }

	    @Override
	    public List<FlexElement> findElements(SearchContext context) {
	      if (context instanceof FindsByName)
	        return ((FindsByName) context).findElementsByName(name);
	      return ((FindsByChain) context).findElementsByChain(".//*[@name = '"
	          + name + "']");
	    }

	    @Override
	    public FlexElement findElement(SearchContext context) {
	      if (context instanceof FindsByName)
	        return ((FindsByName) context).findElementByName(name);
	      return ((FindsByChain) context).findElementByChain("name:"
	          + name + "");
	    }

	    @Override
	    public String toString() {
	      return "By.name: " + name;
	    }
	  }
	  
	  public static class ByLinkText extends By {
	    private final String linkText;

	    public ByLinkText(String linkText) {
	      this.linkText = linkText;
	    }

	    @Override
	    public List<FlexElement> findElements(SearchContext context) {
	      return ((FindsByLinkText) context).findElementsByLinkText(linkText);
	    }

	    @Override
	    public FlexElement findElement(SearchContext context) {
	      return ((FindsByLinkText) context).findElementByLinkText(linkText);
	    }

	    @Override
	    public String toString() {
	      return "By.linkText: " + linkText;
	    }
	  }

	  public static class ByChain extends By {
	    private final String chainExpression;

	    public ByChain(String in_Expression) {
	      this.chainExpression = in_Expression;
	    }

	    @Override
	    public List<FlexElement> findElements(SearchContext context) {
	      return ((FindsByChain) context).findElementsByChain(chainExpression);
	    }

	    @Override
	    public FlexElement findElement(SearchContext context) {
	      return ((FindsByChain) context).findElementByChain(chainExpression);
	    }

	    @Override
	    public String toString() {
	      return "By.chain: " + chainExpression;
	    }
	  }  
	  
}
