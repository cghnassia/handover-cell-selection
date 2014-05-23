package views.menu;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.EventListener;

import javax.sound.sampled.DataLine;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

import sun.security.krb5.Config;
import models.menu.MenuModel;
import models.utilities.LoadImage;
import config.MainConfig;
import controllers.ApplicationController;
import events.MenuControlEvent;
import events.MenuDataEvent;
import events.MenuListener;

public class MenuView extends JPanel {
	
	private MenuModel menuModel;
	private MenuPhoneView menuPhoneView;
	
	public MenuView(MenuModel menuModel) {
		
		super(new BorderLayout());
		this.menuModel = menuModel;
		
		this.setMenuPhoneView(new MenuPhoneView());
		this.add(getMenuPhoneView(), BorderLayout.NORTH);
		this.setPreferredSize(new Dimension(menuModel.getMenuWidth(), menuModel.getMenuHeight()));
		
		this.setListeners();
	}
	
	public void addMenuListener(MenuListener menuListener) {
		this.listenerList.add(MenuListener.class, menuListener);
	}
	
	public void removeMenuListener(MenuListener menuListener) {
		this.listenerList.remove(MenuListener.class, menuListener);
	}
	
	private void setListeners() {
		this.getMenuPhoneView().getButtonCall().addActionListener(new ButtonPhoneListener());
		this.getMenuPhoneView().getButtonCall().addActionListener(new ButtonPhoneListener());
		this.getMenuPhoneView().getSliderSpeed().addChangeListener(new SliderSpeedListener());
		this.getMenuPhoneView().getCheckBoxGSM().addItemListener(new CheckBoxNetworkListener());
		this.getMenuPhoneView().getCheckBoxGPRS().addItemListener(new CheckBoxNetworkListener());
		this.getMenuPhoneView().getCheckBoxEDGE().addItemListener(new CheckBoxNetworkListener());
		this.getMenuPhoneView().getCheckBoxUMTS().addItemListener(new CheckBoxNetworkListener());
	}
	
	public void updateSpeed(int value) {
		this.getMenuPhoneView().getSliderSpeed().setValue(value);
		this.getMenuPhoneView().getLabelSpeed().setText(value + " m/s");
	}
	
	protected void fireControlCheckBoxEvent(MenuControlEvent controlEvent) {
		
		Object[] listeners = this.listenerList.getListenerList();
	     
	     int numListeners = listeners.length;
	     for (int i = 0; i< numListeners; i += 2) 
	     {
	          if (listeners[i] == MenuListener.class) 
	          {
	               // pass the event to the listeners event dispatch method
	                ((MenuListener)listeners[i+1]).checkboxSelected(controlEvent);
	          }            
	     }
	}
	
	protected void fireControlButtonEvent(MenuControlEvent controlEvent) {
		Object[] listeners = this.listenerList.getListenerList();
	     
	     int numListeners = listeners.length;
	     for (int i = 0; i< numListeners; i += 2) 
	     {
	          if (listeners[i] == MenuListener.class) 
	          {
	               // pass the event to the listeners event dispatch method
	                ((MenuListener)listeners[i+1]).buttonClicked(controlEvent);
	          }            
	     }
	}
	
	protected void fireDataSliderEvent(MenuDataEvent dataEvent) {
		Object[] listeners = this.listenerList.getListenerList();
	     
	     int numListeners = listeners.length;
	     for (int i = 0; i< numListeners; i += 2) 
	     {
	          if (listeners[i] == MenuListener.class) 
	          {
	               // pass the event to the listeners event dispatch method
	                ((MenuListener)listeners[i+1]).sliderChanged(dataEvent);
	          }            
	     }
	}
		
	
	public MenuPhoneView getMenuPhoneView() {
		return this.menuPhoneView;
	}

	public void setMenuPhoneView(MenuPhoneView menuPhoneView) {
		this.menuPhoneView = menuPhoneView;
	}


	class ButtonPhoneListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
	class SliderSpeedListener implements ChangeListener {
		
		@Override
		public void stateChanged(ChangeEvent e) {
			
			JSlider source = (JSlider) e.getSource();
			int value = source.getValue();
			
			MenuDataEvent dataEvent = new MenuDataEvent(MenuView.this, MenuDataEvent.TYPE_SLIDER_SPEED, value);
			MenuView.this.fireDataSliderEvent(dataEvent);
		}
	}
	
	class CheckBoxNetworkListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
			
			JCheckBox source = (JCheckBox) e.getSource();
			int type;
			
			if(source == MenuView.this.getMenuPhoneView().getCheckBoxGSM()) {
				type = MenuControlEvent.TYPE_CHECKBOX_GSM;
			}
			else if(source == MenuView.this.getMenuPhoneView().getCheckBoxGPRS()) {
				type = MenuControlEvent.TYPE_CHECKBOX_GPRS;
			}
			else if(source == MenuView.this.getMenuPhoneView().getCheckBoxEDGE()) {
				type = MenuControlEvent.TYPE_CHECKBOX_EDGE;
			}
			else {	//MenuView.this.menuPhoneView.getCheckBoxUMTS()
				type = MenuControlEvent.TYPE_CHECKBOX_UMTS;
			}
			
			MenuControlEvent controlEvent = new MenuControlEvent(MenuView.this, type);
			MenuView.this.fireControlCheckBoxEvent(controlEvent);
			
			// TODO Auto-generated method stub
			/*if (ApplicationController.this.getView().getMenuView().getCheckBoxGSM().isSelected()) {
				ApplicationController.this.getView().getMenuView().getCheckBoxGPRS().setEnabled(true);
				ApplicationController.this.getView().getMenuView().getCheckBoxEDGE().setEnabled(true);
			}
			else {
				ApplicationController.this.getView().getMenuView().getCheckBoxGPRS().setEnabled(false);
				ApplicationController.this.getView().getMenuView().getCheckBoxEDGE().setEnabled(false);
			}
			
			ApplicationController.this.getModel().getNetwork().setGSM(ApplicationController.this.getView().getMenuView().getCheckBoxGSM().isSelected());
			ApplicationController.this.getModel().getNetwork().setUMTS(ApplicationController.this.getView().getMenuView().getCheckBoxUMTS().isSelected());
			ApplicationController.this.getModel().getNetwork().setGPRS(ApplicationController.this.getView().getMenuView().getCheckBoxGPRS().isSelected() && ApplicationController.this.getView().getMenuView().getCheckBoxGPRS().isEnabled());
			ApplicationController.this.getModel().getNetwork().setEDGE(ApplicationController.this.getView().getMenuView().getCheckBoxEDGE().isSelected() && ApplicationController.this.getView().getMenuView().getCheckBoxEDGE().isEnabled());
			*/
			//ApplicationController.this.getView().getAreaView().getCellsLayerView().repaint();
		}
		
	}

	
	
	
}
