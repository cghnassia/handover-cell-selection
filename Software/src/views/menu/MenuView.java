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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import sun.security.krb5.Config;
import models.menu.MenuModel;
import models.network.Network;
import models.utilities.LoadImage;
import config.MainConfig;
import controllers.ApplicationController;
import events.MenuControlEvent;
import events.MenuDataEvent;
import events.MenuListener;

public class MenuView extends JPanel {
	
	private MenuModel menuModel;
	private MenuPhoneView menuPhoneView;
	private MenuAntennaView menuAntennaView;
	
	public MenuView(MenuModel menuModel) {
		
		super(new BorderLayout());
		this.menuModel = menuModel;
		
		this.setMenuPhoneView(new MenuPhoneView());
		this.setMenuAntennaView(new MenuAntennaView());
		this.add(getMenuPhoneView(), BorderLayout.NORTH);
		this.add(getMenuAntennaView(),BorderLayout.SOUTH);
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
		this.getMenuPhoneView().getButtonData().addActionListener(new ButtonPhoneListener());
		this.getMenuPhoneView().getSliderSpeed().addChangeListener(new SliderListener());
		this.getMenuPhoneView().getCheckBoxGSM().addItemListener(new CheckBoxListener());
		this.getMenuPhoneView().getCheckBoxGPRS().addItemListener(new CheckBoxListener());
		this.getMenuPhoneView().getCheckBoxEDGE().addItemListener(new CheckBoxListener());
		this.getMenuPhoneView().getCheckBoxUMTS().addItemListener(new CheckBoxListener());
		
		this.getMenuAntennaView().getComboArea().addItemListener(new ComboIntegerListener());
		this.getMenuAntennaView().getMenuCellGSMView().getCheckBoxGSM().addItemListener(new CheckBoxListener());
		this.getMenuAntennaView().getMenuCellGSMView().getSliderPower().addChangeListener(new SliderListener());
		this.getMenuAntennaView().getMenuCellGSMView().getSliderRxAccessMin().addChangeListener(new SliderListener());
		this.getMenuAntennaView().getMenuCellGSMView().getSliderReselectOffset().addChangeListener(new SliderListener());
		this.getMenuAntennaView().getMenuCellGSMView().getSliderReselectHysteresis().addChangeListener(new SliderListener());
		this.getMenuAntennaView().getMenuCellGSMView().getComboQSI().addItemListener(new ComboIntegerListener());
		this.getMenuAntennaView().getMenuCellGSMView().getComboQSC().addItemListener(new ComboIntegerListener());
		this.getMenuAntennaView().getMenuCellGSMView().getListNeighbors().addListSelectionListener(new ListListener());
	}
	
	public void updateSpeed(int value) {
		this.getMenuPhoneView().getSliderSpeed().setValue(value);
		this.getMenuPhoneView().getLabelSpeed().setText(value + " m/s");
	}
	
	public void updateCheckBoxes() {
		Network network = Network.Instance();
		
		this.getMenuPhoneView().getCheckBoxGPRS().setEnabled(network.isGSM());
		this.getMenuPhoneView().getCheckBoxEDGE().setEnabled(network.isGSM());
		
		this.getMenuPhoneView().getCheckBoxGSM().setSelected(network.isGSM());
		this.getMenuPhoneView().getCheckBoxGPRS().setSelected(network.isGPRS());
		this.getMenuPhoneView().getCheckBoxEDGE().setSelected(network.isEDGE());
		this.getMenuPhoneView().getCheckBoxUMTS().setSelected(network.isUMTS());
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
	
	protected void fireDataComboEvent(MenuDataEvent dataEvent) {
		Object[] listeners = this.listenerList.getListenerList();
	     
	     int numListeners = listeners.length;
	     for (int i = 0; i< numListeners; i += 2) 
	     {
	          if (listeners[i] == MenuListener.class) 
	          {
	               // pass the event to the listeners event dispatch method
	                ((MenuListener)listeners[i+1]).ComboChanged(dataEvent);
	          }            
	     }
	}
	
	protected void fireControlListEvent(MenuControlEvent controlEvent) {
		Object[] listeners = this.listenerList.getListenerList();
	     
	     int numListeners = listeners.length;
	     for (int i = 0; i< numListeners; i += 2) 
	     {
	          if (listeners[i] == MenuListener.class) 
	          {
	               // pass the event to the listeners event dispatch method
	                ((MenuListener)listeners[i+1]).listChanged(controlEvent);
	          }            
	     }
	}
		
	
	public MenuPhoneView getMenuPhoneView() {
		return this.menuPhoneView;
	}

	public void setMenuPhoneView(MenuPhoneView menuPhoneView) {
		this.menuPhoneView = menuPhoneView;
	}

	public MenuAntennaView getMenuAntennaView() {
		return this.menuAntennaView;
	}
	
	public void setMenuAntennaView(MenuAntennaView menuAntennaView) {
		this.menuAntennaView = menuAntennaView;
	}

	class ButtonPhoneListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			int type;
			if (e.getSource() == MenuView.this.getMenuPhoneView().getButtonCall()) {
				type = MenuControlEvent.TYPE_BUTTON_CALL;
			}
			else { // (e.getSource() == MenuView.this.getMenuPhoneView().getButtonData()) {
				type = MenuControlEvent.TYPE_BUTTON_DATA;
			}
			
			MenuControlEvent controlEvent = new MenuControlEvent(MenuView.this, type);
			MenuView.this.fireControlButtonEvent(controlEvent);
		}
		
	}
	
	class SliderListener implements ChangeListener {
		
		@Override
		public void stateChanged(ChangeEvent e) {
			
			JSlider source = (JSlider) e.getSource();
			int value = source.getValue();
			
			if(source == MenuView.this.getMenuPhoneView().getSliderSpeed()) {
				MenuDataEvent dataEvent = new MenuDataEvent(MenuView.this, MenuDataEvent.TYPE_SLIDER_SPEED, value);
				MenuView.this.fireDataSliderEvent(dataEvent);
			}
			else if (source == MenuView.this.getMenuAntennaView().getMenuCellGSMView().getSliderPower()) {
				MenuDataEvent dataEvent = new MenuDataEvent(MenuView.this, MenuDataEvent.TYPE_SLIDER_GSM_POWER, value);
				MenuView.this.fireDataSliderEvent(dataEvent);
			}
			else if (source == MenuView.this.getMenuAntennaView().getMenuCellGSMView().getSliderRxAccessMin()) {
				MenuDataEvent dataEvent = new MenuDataEvent(MenuView.this, MenuDataEvent.TYPE_SLIDER_GSM_ACCESS_MIN, value);
				MenuView.this.fireDataSliderEvent(dataEvent);
			}
			else if (source == MenuView.this.getMenuAntennaView().getMenuCellGSMView().getSliderReselectOffset()) {
				MenuDataEvent dataEvent = new MenuDataEvent(MenuView.this, MenuDataEvent.TYPE_SLIDER_GSM_RESELECT_OFFSET, value);
				MenuView.this.fireDataSliderEvent(dataEvent);
			}
			else if (source == MenuView.this.getMenuAntennaView().getMenuCellGSMView().getSliderReselectHysteresis()) {
				MenuDataEvent dataEvent = new MenuDataEvent(MenuView.this, MenuDataEvent.TYPE_SLIDER_GSM_RESELECT_HYSTERESIS, value);
				MenuView.this.fireDataSliderEvent(dataEvent);
			}
		}
	}
	
	class CheckBoxListener implements ItemListener {

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
			else if (source == MenuView.this.menuPhoneView.getCheckBoxUMTS()) {
				type = MenuControlEvent.TYPE_CHECKBOX_UMTS;
			}
			else { //MenuView.this.menuAntennaView.getMenuCellGSMView().getCheckboxGSM()
				type = MenuControlEvent.TYPE_CHECKBOX_CELL_GSM;
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
	
	class ComboIntegerListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			JComboBox<ComboOption> source = (JComboBox<ComboOption>) e.getSource();
			int value = ((ComboOption) e.getItem()).getId();
			
			int type;
			if(source == MenuView.this.getMenuAntennaView().getComboArea()) {
				type = MenuDataEvent.TYPE_COMBO_AREA;
			}
			else if(source == MenuView.this.getMenuAntennaView().getMenuCellGSMView().getComboQSC()) {
				type = MenuDataEvent.TYPE_COMBO_GSM_QSC;
			}
			else { //if (source == MenuView.this.getMenuAntennaView().getMenuCellGSMView().getComboQSI()) {
				type = MenuDataEvent.TYPE_COMBO_GSM_QSI;
			}
			
			MenuDataEvent dataEvent = new MenuDataEvent(MenuView.this, type, value);
			MenuView.this.fireDataComboEvent(dataEvent);
			
		}	
	}
	
	class ListListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			// TODO Auto-generated method stub
			JList<ComboOption> source = (JList<ComboOption>) e.getSource();
			//int[] values = source.getSelectedIndices();*/
			if(source == MenuView.this.getMenuAntennaView().getMenuCellGSMView().getListNeighbors()) {
				MenuControlEvent controlEvent = new MenuControlEvent(MenuView.this, MenuControlEvent.TYPE_GSM_NEIGHBORS);
				MenuView.this.fireControlListEvent(controlEvent);
			}
			
		}
		
	}

	
	
	
}
