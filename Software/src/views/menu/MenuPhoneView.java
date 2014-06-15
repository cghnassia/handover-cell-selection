package views.menu;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import models.mobile.Mobile;
import models.utilities.LoadImage;
import config.MainConfig;

public class MenuPhoneView extends JPanel {
	
	private JButton button_call;
	private JButton button_data;
	private JButton button_power;
	
	private JSlider slider_speed;
	private JLabel label_speed;
	
	private JCheckBox checkbox_GSM;
	private JCheckBox checkbox_GPRS;
	private JCheckBox checkbox_EDGE;
	private JCheckBox checkbox_UMTS;

	private ImageIcon imagePowerOn;
	private ImageIcon imagePowerOff;
	
	private ImageIcon imageCallOn;
	private ImageIcon imageCallOff;
	
	private ImageIcon imageDataOn;
	private ImageIcon imageDataOff;
	
	
	public static int BUTTON_STATE_INACTIVE = 0;
	public static int BUTTON_STATE_ON = 1;
	public static int BUTTON_STATE_OFF = 2;
	
	
	public MenuPhoneView() {
		super(new BorderLayout(20, 10));
		this.setBorder(BorderFactory.createTitledBorder("Phone controls"));
		
		this.imagePowerOn = LoadImage.load(LoadImage.BUTTON_POWER_ON);
		this.imagePowerOff = LoadImage.load(LoadImage.BUTTON_POWER_OFF);
		
		this.imageCallOn = LoadImage.load(LoadImage.BUTTON_CALL_ON);
		this.imageCallOff = LoadImage.load(LoadImage.BUTTON_CALL_OFF);
		
		this.imageDataOn = LoadImage.load(LoadImage.BUTTON_DATA_ON);
		this.imageDataOff = LoadImage.load(LoadImage.BUTTON_DATA_OFF);
		
		this.button_power = new JButton(this.imagePowerOn);
		this.button_call = new JButton(this.imageCallOn);
		this.button_call.setEnabled(false);
		this.button_data = new JButton(this.imageDataOn);
		this.button_data.setEnabled(false);
		
		this.slider_speed = new JSlider(MainConfig.SPEED_MIN, MainConfig.SPEED_MAX, MainConfig.SPEED_MIN);
		this.label_speed = new JLabel(MainConfig.SPEED_MIN + " m/s");
		
		this.checkbox_GSM = new JCheckBox("GSM", true);
		this.checkbox_GSM.setMargin(new Insets(0, 30, 0, 0));
		this.checkbox_GPRS = new JCheckBox("GPRS", true);
		this.checkbox_EDGE = new JCheckBox("EDGE", true);
		this.checkbox_EDGE.setMargin(new Insets(0, 30, 0, 0));
		this.checkbox_UMTS = new JCheckBox("UMTS", true);
		
		JPanel topPanel = new JPanel(new BorderLayout());
		JPanel buttonsContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		buttonsContainer.add(this.button_power);
		buttonsContainer.add(this.button_call);
		buttonsContainer.add(this.button_data);
		topPanel.add(new JLabel("Phone functionnalities: ", SwingConstants.CENTER), BorderLayout.NORTH);
		topPanel.add(buttonsContainer, BorderLayout.CENTER);
		
		JPanel middlePanel = new JPanel(new BorderLayout());
		JPanel middleBottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 10));
		middleBottomPanel.add(this.slider_speed);
		middleBottomPanel.add(this.label_speed);
		middlePanel.add(new JLabel("Phone speed : ", JLabel.CENTER), BorderLayout.NORTH);
		middlePanel.add(middleBottomPanel, BorderLayout.CENTER);
		
		JPanel bottomPanel = new JPanel(new BorderLayout());
		JPanel checkboxContainerPanel = new JPanel(new GridLayout(2, 2));
		checkboxContainerPanel.add(this.checkbox_GSM);
		checkboxContainerPanel.add(this.checkbox_GPRS);
		checkboxContainerPanel.add(this.checkbox_EDGE);
		checkboxContainerPanel.add(this.checkbox_UMTS);
		bottomPanel.add(new JLabel("Phone features :", JLabel.CENTER), BorderLayout.NORTH);
		bottomPanel.add(checkboxContainerPanel, BorderLayout.CENTER);
		
		this.add(topPanel, BorderLayout.NORTH);
		this.add(middlePanel, BorderLayout.CENTER);
		this.add(bottomPanel, BorderLayout.SOUTH);
	}
	
	public void setButtonState(JButton button, int state) {
		
		if(state == MenuPhoneView.BUTTON_STATE_INACTIVE) {
			button.setEnabled(false);
		}
		else {
			button.setEnabled(true);
		}
		
		if(button == this.button_power) {
			if(state == BUTTON_STATE_ON || state == BUTTON_STATE_INACTIVE) {
				button.setIcon(this.imagePowerOn);
			}
			else {
				button.setIcon(this.imagePowerOff);
			}
		}
		else if(button == this.button_call) {
			if(state == BUTTON_STATE_ON || state == BUTTON_STATE_INACTIVE) {
				button.setIcon(this.imageCallOn);
			}
			else {
				button.setIcon(this.imageCallOff);
			}
		}
		else {
			if(state == BUTTON_STATE_ON || state == BUTTON_STATE_INACTIVE) {
				button.setIcon(this.imageDataOn);
			}
			else {
				button.setIcon(this.imageDataOff);
			}
		}
	}
	
	public JButton getButtonPower() {
		return this.button_power;
	}
	
	public JButton getButtonCall() {
		return this.button_call;
	}
	
	public JButton getButtonData() {
		return this.button_data;
	}
	
	public JSlider getSliderSpeed() {
		return this.slider_speed;
	}
	
	public JLabel getLabelSpeed() {
		return this.label_speed;
	}
	
	public JCheckBox getCheckBoxGSM() {
		return this.checkbox_GSM;
	}
	
	public JCheckBox getCheckBoxGPRS() {
		return this.checkbox_GPRS;
	}
	
	public JCheckBox getCheckBoxEDGE() {
		return this.checkbox_EDGE;
	}
	
	public JCheckBox getCheckBoxUMTS() {
		return this.checkbox_UMTS;
	}
	
}
