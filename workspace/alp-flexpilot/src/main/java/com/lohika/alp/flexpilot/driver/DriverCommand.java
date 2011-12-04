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
package com.lohika.alp.flexpilot.driver;

public interface DriverCommand {

	String CLICK = "fp_click";
	String TYPE = "fp_type";
	String GET_TEXT_VALUE = "fp_getTextValue";
	String SELECT = "fp_select";
	String DOUBLE_CLICK = "fp_doubleClick";
	String MOUSE_OUT = "fp_mouseOut";
	String MOUSE_OVER = "fp_mouseOver";
	String GET_PROPERTY_VALUE = "fp_getPropertyValue"; 
	String DATE = "fp_date";
	String DRAG_DROP_TO_COORDS = "fp_dragDropToCoords";
	String DRAG_DROP_ELEM_TO_ELEM = "fp_dragDropElemToElem";
	String GET_OBJECT_COORDS = "fp_getObjectCoords";

	String GET_VERSION = "fp_getVersion";
	
	String ASSERT_DISPLAY_OBJECT = "fp_assertDisplayObject";

}
