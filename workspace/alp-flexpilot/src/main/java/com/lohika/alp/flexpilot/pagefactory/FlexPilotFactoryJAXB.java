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

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.lohika.alp.log.elements.schema.Action;
import com.lohika.alp.log.elements.schema.ObjectFactory;
import com.lohika.alp.log.elements.schema.Screenshot;
import com.lohika.alp.log.elements.schema.Webelement;
import com.lohika.alp.log4j.LogFileAttachment;
import com.lohika.alp.selenium.log.DescribedElement;

public class FlexPilotFactoryJAXB implements FlexPilotFactory {

	protected ObjectFactory factory = new ObjectFactory();

	protected Action getAction(DescribedElement self, String name) {
		Action action = factory.createAction();

		action.setName(name);
		action.setWebelement((Webelement) element(self));

		return action;
	}
	
	public Object element(DescribedElement element) {
		Webelement webelement = factory.createWebelement();

		if (element != null && element.getDescription() != null) {
			webelement.setName(element.getDescription().getName());
			webelement.setType(element.getDescription().getType());
		}

		return webelement;
	}

	public Object click(DescribedElement self) {
		return getAction(self, "click");
	}

	public Object sendKeys(DescribedElement self, CharSequence... keysToSend) {
		Action action = getAction(self, "send keys");

		StringBuilder builder = new StringBuilder();
		for (CharSequence key : keysToSend) {
			builder.append(key);
		}

		action.getArg().add(builder.toString());

		return action;
	}
	
	public Object screenshot(TakesScreenshot takesScreenshot, String description) {

		Screenshot screenshot = factory.createScreenshot();
		screenshot.setDescription(description);

		File tempFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

		File attachmentFile = null;
		try {
			attachmentFile = LogFileAttachment.getAttachmentFile("", "png");
			FileUtils.copyFile(tempFile, attachmentFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (attachmentFile != null)
			screenshot.setUrl(attachmentFile.getName());

		return screenshot;
	}

	public Object screenshot(WebDriver driver, String description) {

		if (driver instanceof TakesScreenshot) {
			return screenshot((TakesScreenshot) driver, description);
		} else {
			return null;
		}
	}

	public Object drugAndDrop(DescribedElement self, DescribedElement toElement) {
		Action action = getAction(self, "drag and drop");
		action.getArg().add(toElement.getDescription().getName());
		return action;
	}

	public Object doubleClick(DescribedElement self) {
		return getAction(self, "double click");
	}

	public Object select(DescribedElement self, String text) {
		Action action = getAction(self, "select");
		action.getArg().add(text);
		return action;
	}

}
