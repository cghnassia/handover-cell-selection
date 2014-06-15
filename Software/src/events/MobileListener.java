package events;

import java.util.EventListener;

public interface MobileListener extends EventListener {
	 
	void callChanged(MobileEvent e);
	void dataChanged(MobileEvent e);
}
