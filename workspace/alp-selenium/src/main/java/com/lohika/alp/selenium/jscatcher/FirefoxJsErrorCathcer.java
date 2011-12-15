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
package com.lohika.alp.selenium.jscatcher;

import java.util.ArrayList;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

/*
 * Firefox implementation of javascript error catcher. 
 */

public class FirefoxJsErrorCathcer implements JSErrorCatcher {

	private WebDriver driver;
	
	public FirefoxJsErrorCathcer(WebDriver driver) {
		this.driver = driver;
	}
	
	/*
	 * return list of javascript errors
	 * @see com.lohika.alp.selenium.jscatcher.JSErrorCatcher#getJsErrors()
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<String> getJsErrors() throws JsErrorCatcherException {
		if (JsErrorCatcherConfiguration.getInstance().getAllowDomains()==null)
			throw new JsErrorCatcherException("Unable to get JS errors. Need to provide allowed domains in the config file");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String script = "netscape.security.PrivilegeManager.enablePrivilege('UniversalXPConnect UniversalBrowserRead UniversalBrowserWrite UniversalPreferencesRead UniversalPreferencesWrite UniversalFileRead');"
		+"var consoleService = Components.classes[\"@mozilla.org/consoleservice;1\"].getService(Components.interfaces.nsIConsoleService);"
		+ "var errors = {};"
		+ "var count = {};"
		+ "consoleService.getMessageArray(errors, count);"
		+ "var r = [];"
		+ "for (var i=0; i<errors.value.length; i++) {"
		+ "msg = errors.value[i];"
		+ "if (msg instanceof Components.interfaces.nsIScriptError) {"
		+ "msg.QueryInterface(Components.interfaces.nsIScriptError);"
		+ "if (msg.category=='HUDConsole'"
		+ " || msg.category=='content javascript'"
		+ " || msg.category=='CSS Parser'"
		+ " || msg.category=='CSS Loader'"
		+ " || msg.category=='DOM Events'"
		+ " || msg.category=='DOM:HTML'"
		+ " || msg.category=='DOM Window'"
		+ " || msg.category=='SVG'"
		+ " || msg.category=='ImageMap'"
		+ " || msg.category=='HTML'"
		+ " || msg.category=='Canvas'"
		+ " || msg.category=='DOM3 Load'"
		+ " || msg.category=='DOM'"
		+ " || msg.category=='malformed-xml'"
		+ " || msg.category=='DOM Worker javascript')"
		+ "r.push(msg.message);"
		+ "}};"
		+ "consoleService.reset();"
		+ "return r;";
		
		return((ArrayList<String>)js.executeScript(script));
	}

}
