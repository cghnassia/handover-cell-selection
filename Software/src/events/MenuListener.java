package events;

import java.util.EventListener;

public interface MenuListener extends EventListener {
	 
	void checkboxSelected(MenuControlEvent e);
	void buttonClicked(MenuControlEvent e);
	void listChanged(MenuControlEvent e);
	
	void sliderChanged(MenuDataEvent e);
	void ComboChanged(MenuDataEvent e);
}
