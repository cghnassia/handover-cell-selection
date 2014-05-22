package events;

import java.awt.AWTEvent;

import views.menu.MenuView;

public class MenuDataEvent extends AWTEvent {
	
	private int value;
	
	public static final int TYPE_SLIDER_SPEED = 0;
		
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
