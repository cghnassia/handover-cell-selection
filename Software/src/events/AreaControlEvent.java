package events;

import java.awt.AWTEvent;
import java.awt.Event;

import views.area.AreaView;
import views.menu.MenuView;

public class AreaControlEvent extends AWTEvent {
	
	public static final int KEYUP_PRESSED = 1;
	public static final int KEYUP_RELEASED = 2;
	
	public static final int KEYDOWN_PRESSED = 3;
	public static final int KEYDOWN_RELEASED = 4;
	
	public static final int KEYLEFT_PRESSED = 7;
	public static final int KEYLEFT_RELEASED = 8;
	
	public static final int KEYRIGHT_PRESSED = 5;
	public static final int KEYRIGHT_RELEASED = 6;
	
	public static final int FOCUS_REQUESTED = 7;
		
	public AreaControlEvent(AreaView areaView, int type) {
		super(areaView, type);
	}

	
	
}
