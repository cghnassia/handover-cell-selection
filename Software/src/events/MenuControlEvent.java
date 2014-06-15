package events;

import java.awt.AWTEvent;
import java.awt.Event;

import views.menu.MenuView;

public class MenuControlEvent extends AWTEvent {
	
	public static final int TYPE_BUTTON_POWER = 0;
	public static final int TYPE_BUTTON_CALL = 1;
	public static final int TYPE_BUTTON_DATA = 2;
	
	public static final int TYPE_CHECKBOX_GSM = 3;
	public static final int TYPE_CHECKBOX_GPRS = 4;
	public static final int TYPE_CHECKBOX_EDGE = 5;
	public static final int TYPE_CHECKBOX_UMTS = 6;
	
	public static final int TYPE_CHECKBOX_CELL_GSM = 7;
	public static final int TYPE_CHECKBOX_CELL_UMTS = 8;
	
	public static final int TYPE_GSM_NEIGHBORS = 9;
	public static final int TYPE_UMTS_NEIGHBORS = 10;
		
	public MenuControlEvent(MenuView menuView, int type) {
		super(menuView, type);
	}
	
}
