package events;

import java.awt.AWTEvent;
import java.awt.Event;

import models.mobile.Mobile;
import models.network.Cell;
import views.area.AreaView;
import views.menu.MenuView;

public class MobileEvent extends AWTEvent {
	
	public static final int MOBILE_CALL_OK = 0;
	public static final int MOBILE_DATA_OK = 1;
	public static final int MOBILE_CALL_NO = 3;
	public static final int MOBILE_DATA_NO = 4;
		
	public MobileEvent(Mobile mobileModel, int type) {
		super(mobileModel, type);
	}

	
	
}
