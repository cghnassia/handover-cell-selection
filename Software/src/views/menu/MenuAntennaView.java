package views.menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class MenuAntennaView extends JPanel {
	
	//Controller for panel control
	
	private JCheckBox parameter_default; //Boxe to choose
	private JLabel label_parameter;
	
	private JComboBox LAC_choice; //Menu to choose the lac
	private JLabel label_LAC;
	
	//Controller for panel GSM
	private JSlider slider_power; //Power of the antenna choose with a slider
	private JLabel label_power;
	
	private JSlider slider_hysteresis; //Hysteresis of the antenna choose with a slider
	private JLabel label_hysteresis;
	
	private JComboBox QSI_choice; //Menu to choose the QSI
	private JLabel label_QSI;
	
	private JComboBox QSC_choice; //Menu to choose the QSC
	private JLabel label_QSC;
	
	private JList List_neighbors; //List of the neighbors what we can choose
	private JLabel label_neighbors;
	
	//Controller for panel UMTS
	private JSlider slider_power_UMTS; //Power of the antenna choose with a slider
	private JLabel label_power_UMTS;	
	
	private JSlider slider_hysteresis_UMTS; //Hysteresis of the antenna choose with a slider
	private JLabel label_hysteresis_UMTS;
		
	private JList List_neighbors_UMTS; //List of the neighbors what we can choose
	private JLabel label_neighbors_UMTS;
	
	//Creation of the panel
	private JPanel Panel_control = new JPanel();
	private JPanel Panel_control_GSM = new JPanel();
	private JPanel Panel_control_UMTS = new JPanel();
	private JPanel Panel_power = new JPanel();
	private JPanel Panel_hysteresis = new JPanel();
	private JPanel Panel_LAC = new JPanel();
	private JPanel Panel_QSI = new JPanel();
	private JPanel Panel_QSC = new JPanel();
	private JPanel Panel_neighbors = new JPanel();
	
	public MenuAntennaView() {
			
		super(new BorderLayout(50, 10));
		this.setBorder(BorderFactory.createTitledBorder("Antenna controls"));
		
		String tab[]={"1","2","3"};
		String tab1[]={"lol","mdr","ptdr"};
		String tab2[]={"toto","titi","tata"};
		String tab3[]={"zut","flute","pouet"};
		int a=1,b=100;
		
		this.slider_power = new JSlider(a, b, a);
		this.label_power = new JLabel("Power : " + 1 + " db ");
		
		Panel_power.add(label_power);
		Panel_power.add(slider_power);
		
		this.LAC_choice = new JComboBox<Object>(tab1);
		this.label_LAC = new JLabel(" LAC choice : ");
		
		Panel_LAC.add(label_LAC);
		Panel_LAC.add(LAC_choice);
		
		this.QSI_choice = new JComboBox<Object>(tab2);
		this.label_QSI = new JLabel(" QSI choice : ");
		
		Panel_QSI.add(label_QSI);
		Panel_QSI.add(QSI_choice);
		
		this.QSC_choice = new JComboBox<Object>(tab3);
		this.label_QSC = new JLabel(" QSC choice : ");
		
		Panel_QSC.add(label_QSC);
		Panel_QSC.add(QSC_choice);
		
		this.List_neighbors = new JList<Object>(tab);
		this.label_neighbors = new JLabel(" Neighbours choice : ");
		
		Panel_neighbors.add(label_neighbors);
		Panel_neighbors.add(List_neighbors);
		
		this.slider_hysteresis = (new JSlider(0, 10, 1));
		this.label_hysteresis = new JLabel("hysteresis : " + 1 + " db ");
		
		Panel_hysteresis.add(label_hysteresis);
		Panel_hysteresis.add(slider_hysteresis);
		
		Panel_control_GSM.add(Panel_power);
		Panel_control_GSM.add(Panel_hysteresis);
		Panel_control_GSM.add(Panel_LAC);
		Panel_control_GSM.add(Panel_QSI);
		Panel_control_GSM.add(Panel_QSC);
		Panel_control_GSM.add(Panel_neighbors);
		
		this.add(Panel_control,BorderLayout.NORTH);
		this.add(Panel_control_GSM,BorderLayout.CENTER);
		this.add(Panel_control_UMTS,BorderLayout.SOUTH);
		
	}

	public JSlider getSlider_power() {
		return slider_power;
	}

	public void setSlider_power(JSlider slider_power) {
		this.slider_power = slider_power;
	}

	public JComboBox getLAC_choice() {
		return LAC_choice;
	}

	public void setLAC_choice(JComboBox lAC_choice) {
		LAC_choice = lAC_choice;
	}

	public JList getList_neighbours() {
		return List_neighbors;
	}

	public void setList_neighbours(JList list_neighbours) {
		List_neighbors = list_neighbours;
	}

}