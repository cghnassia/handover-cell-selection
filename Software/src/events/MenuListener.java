package events;

import java.util.EventListener;

public interface MenuListener extends EventListener {
	 
	void checkboxSelected(MenuControlEvent e);
	void buttonClicked(MenuControlEvent e);
	void sliderChanged(MenuDataEvent e);
}
