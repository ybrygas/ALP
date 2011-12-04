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

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import org.openqa.selenium.support.CacheLookup;
import com.lohika.alp.flexpilot.pagefactory.ByChained;

public class Annotations {
	  private Field field;

	  public Annotations(Field field) {
	    this.field = field;
	  }

	  public boolean isLookupCached() {
	    return (field.getAnnotation(CacheLookup.class) != null);
	  }

	  public By buildBy() {
	    assertValidAnnotations();

	    By ans = null;

	    FindBys findBys = field.getAnnotation(FindBys.class);
	    if (ans == null && findBys != null) {
	      ans = buildByFromFindBys(findBys);
	    }

	    FindBy findBy = field.getAnnotation(FindBy.class);
	    if (ans == null && findBy != null) {
	      ans = buildByFromFindBy(findBy);
	    }

	    if (ans == null) {
	      ans = buildByFromDefault();
	    }

	    if (ans == null) {
	      throw new IllegalArgumentException("Cannot determine how to locate element " + field);
	    }

	    return ans;
	  }

	  protected By buildByFromDefault() {
	    return new ByIdOrName(field.getName());
	  }

	  protected By buildByFromFindBys(FindBys findBys) {
	    assertValidFindBys(findBys);

	    FindBy[] findByArray = findBys.value();
	    By[] byArray = new By[findByArray.length];
	    for (int i = 0; i < findByArray.length; i++) {
	      byArray[i] = buildByFromFindBy(findByArray[i]);
	    }

	    return new ByChained(byArray);
	  }

	  protected By buildByFromFindBy(FindBy findBy) {
	    assertValidFindBy(findBy);

	    By ans = buildByFromShortFindBy(findBy);
	    if (ans == null) {
	      ans = buildByFromLongFindBy(findBy);
	    }

	    return ans;
	  }

	  protected By buildByFromLongFindBy(FindBy findBy) {
	    How how = findBy.how();
	    String using = findBy.using();   

	    switch (how) {

	      case ID:
	        return By.id(using);

	      case ID_OR_NAME:
	        return new ByIdOrName(using);

	      case LINK_TEXT:
	        return By.linkText(using);

	      case NAME:
	        return By.name(using);

	      case CHAIN:
		        return By.chain(using);	        

	      default:
	        // Note that this shouldn't happen (eg, the above matches all
	        // possible values for the How enum)
	        throw new IllegalArgumentException("Cannot determine how to locate element " + field);
	    }
	  }

	  protected By buildByFromShortFindBy(FindBy findBy) {

	    if (!"".equals(findBy.id()))
	    	return By.id(findBy.id());

	    if (!"".equals(findBy.linkText()))
	    	return By.linkText(findBy.linkText());

	    if (!"".equals(findBy.name()))
	    	return By.name(findBy.name());

	    if (!"".equals(findBy.chain()))
	    	return By.chain(findBy.chain());

	    
	    // Fall through
	    return null;
	  }

	  private void assertValidAnnotations() {
	    FindBys findBys = field.getAnnotation(FindBys.class);
	    FindBy findBy = field.getAnnotation(FindBy.class);
	    if (findBys != null && findBy != null) {
	      throw new IllegalArgumentException("If you use a '@FindBys' annotation, "
	          + "you must not also use a '@FindBy' annotation");
	    }
	  }

	  private void assertValidFindBys(FindBys findBys) {
	    for (FindBy findBy : findBys.value()) {
	      assertValidFindBy(findBy);
	    }
	  }

	  private void assertValidFindBy(FindBy findBy) {
	    if (findBy.how() != null) {
	      if (findBy.using() == null) {
	        throw new IllegalArgumentException(
	            "If you set the 'how' property, you must also set 'using'");
	      }
	    }

	    Set<String> finders = new HashSet<String>();
	    if (!"".equals(findBy.using())) finders.add("how: " + findBy.using());
	    if (!"".equals(findBy.id())) finders.add("id: " + findBy.id());
	    if (!"".equals(findBy.linkText())) finders.add("link text: " + findBy.linkText());
	    if (!"".equals(findBy.name())) finders.add("name: " + findBy.name());
	    if (!"".equals(findBy.chain())) finders.add("chain: " + findBy.chain()); 

	    // A zero count is okay: it means to look by name or id.
	    if (finders.size() > 1) {
	      throw new IllegalArgumentException(
	          String.format("You must specify at most one location strategy. Number found: %d (%s)",
	              finders.size(), finders.toString()));
	    }
	  }
}
