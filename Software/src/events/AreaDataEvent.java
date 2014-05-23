package events;

import java.awt.AWTEvent;

import views.area.AreaView;
import views.menu.MenuView;

public class AreaDataEvent extends AWTEvent {
	
	private int value;
	
	public static final int TYPE_SLIDER_SCALE = 0;
		
	public AreaDataEvent(AreaView areaView, int type, int value) {
		super(areaView, type);
		this.setValue(value);
	}

	public int getValue() {
		return this.value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
}
