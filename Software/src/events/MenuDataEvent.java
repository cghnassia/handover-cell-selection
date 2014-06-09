package events;

import java.awt.AWTEvent;

import views.menu.MenuView;

public class MenuDataEvent extends AWTEvent {
	
	private int value;
	
	public static final int TYPE_SLIDER_SPEED = 0;
	public static final int TYPE_SLIDER_GSM_POWER = 1 ;
	public static final int TYPE_SLIDER_GSM_ACCESS_MIN = 2 ;
	public static final int TYPE_SLIDER_GSM_RESELECT_OFFSET = 3 ;
	public static final int TYPE_SLIDER_GSM_RESELECT_HYSTERESIS = 4 ;
	
	public static final int TYPE_COMBO_AREA = 5 ;
	public static final int TYPE_COMBO_GSM_QSI = 6 ;
	public static final int TYPE_COMBO_GSM_QSC = 7 ;
		
	public MenuDataEvent(MenuView menuView, int type, int value) {
		super(menuView, type);
		this.setValue(value);
	}

	public int getValue() {
		return this.value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
}
