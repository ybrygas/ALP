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

import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.lohika.alp.selenium.log.DescribedElement;

public interface FlexPilotFactory {

	public Object element(DescribedElement self);
	public Object click(DescribedElement self);
	public Object sendKeys(DescribedElement self, CharSequence... keysToSend);
	public Object screenshot(TakesScreenshot takesScreenshot, String description);

	/**
	 * Creates screenshot log element or null if WebDriver instance doesn't
	 * support TakesScreenshot interface
	 * 
	 * @param driver
	 * @param description
	 * @return
	 */
	public Object screenshot(WebDriver driver, String description);
	public Object drugAndDrop(DescribedElement self, DescribedElement toElement);
	public Object doubleClick(DescribedElement self);
	public Object select(DescribedElement self, String text);
}
