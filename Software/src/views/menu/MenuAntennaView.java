package views.menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class MenuAntennaView extends JPanel {
	
	//Controller for panel control
	private JCheckBox parameter_default; //Box to choose if the parameter are by default
	
	private JComboBox LAC_choice; //Menu to choose the lac
	private JLabel label_LAC;
	
	private JCheckBox box_GSM; //Box to choose if we use GSM
	
	private JCheckBox box_UMTS; //Box to choose if we use UMTS
	
	//Controller for panel GSM
	private JSlider slider_power; //Power of the antenna choose with a slider
	private JLabel label_power;
	private JLabel label_power_bis;
	
	private JSlider slider_hysteresis; //Hysteresis of the antenna choose with a slider
	private JLabel label_hysteresis;
	private JLabel label_hysteresis_bis;
	
	private JComboBox QSI_choice; //Menu to choose the QSI
	private JLabel label_QSI;
	
	private JComboBox QSC_choice; //Menu to choose the QSC
	private JLabel label_QSC;
	
	private JList List_neighbors; //List of the neighbors what we can choose
	private JLabel label_neighbors;
	
	//Controller for panel UMTS
	private JSlider slider_power_UMTS; //Power of the antenna choose with a slider
	private JLabel label_power_UMTS;
	private JLabel label_power_UMTS_bis;
	
	private JSlider slider_hysteresis_UMTS; //Hysteresis of the antenna choose with a slider
	private JLabel label_hysteresis_UMTS;
	private JLabel label_hysteresis_UMTS_bis;
		
	private JList List_neighbors_UMTS; //List of the neighbors what we can choose
	private JLabel label_neighbors_UMTS;
	
	//Creation of the panel
	private JPanel Panel_control = new JPanel();
	private JPanel Panel_control_bis = new JPanel();
	private JPanel Panel_control_GSM = new JPanel();
	private JPanel Panel_control_UMTS = new JPanel();
	private JPanel Panel_power = new JPanel();
	private JPanel Panel_power_UMTS = new JPanel();
	private JPanel Panel_hysteresis = new JPanel();
	private JPanel Panel_hysteresis_UMTS = new JPanel();
	private JPanel Panel_LAC = new JPanel();
	private JPanel Panel_QSI = new JPanel();
	private JPanel Panel_QSC = new JPanel();
	private JPanel Panel_neighbors = new JPanel();
	private JPanel Panel_neighbors_UMTS = new JPanel();
	private JPanel Panel_checkbox = new JPanel();	
	
	public MenuAntennaView() {
			
		super(new BorderLayout(20, 10));
		this.setBorder(BorderFactory.createTitledBorder("Antenna controls"));
		
		String tab[]={"1","2","3"};
		String tab1[]={"lol","mdr","ptdr"};
		String QSI[]={"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"};
		String QSC[]={"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"};
		int a=1,b=100;
		
		Panel_control_bis.setLayout(new GridLayout(2,1));
		
		//Management of the panel_control
		this.parameter_default = new JCheckBox("Default parameter",true);
		
		Panel_control.add(parameter_default);
		
		this.LAC_choice = new JComboBox<Object>(tab1);
		this.label_LAC = new JLabel(" LAC choice : ");
		
		Panel_control.add(label_LAC);
		Panel_control.add(LAC_choice);
		
		this.box_GSM = new JCheckBox("GSM",true);

		Panel_control_GSM.add(box_GSM);
		
		this.box_UMTS = new JCheckBox("UMTS",true);

		Panel_control_UMTS.add(box_UMTS);
		
		//Panel_control.add(Panel_LAC);
		
		//Management of the panel_control_GSM
		this.slider_power = new JSlider(a, b, a);
		this.label_power = new JLabel("Power : ");
		this.label_power_bis = new JLabel( 1 + " db ");
		
		Panel_power.add(label_power);
		Panel_power.add(slider_power);
		Panel_power.add(label_power_bis);
		
		this.List_neighbors = new JList<Object>(tab);
		this.label_neighbors = new JLabel(" Neighbours choice : ");
		
		Panel_neighbors.add(label_neighbors);
		Panel_neighbors.add(List_neighbors);		
		
		this.QSI_choice = new JComboBox<Object>(QSI);
		this.label_QSI = new JLabel(" QSI choice : ");
		QSI_choice.setSelectedIndex(14);
		
		Panel_QSI.add(label_QSI);
		Panel_QSI.add(QSI_choice);
		
		this.QSC_choice = new JComboBox<Object>(QSC);
		this.label_QSC = new JLabel(" QSC choice : ");
		QSC_choice.setSelectedIndex(14);
		
		Panel_QSC.add(label_QSC);
		Panel_QSC.add(QSC_choice);
		
		this.slider_hysteresis = (new JSlider(0, 10, 1));
		this.label_hysteresis = new JLabel("hysteresis : ");
		this.label_hysteresis_bis = new JLabel( 1 + " db ");
		
		Panel_hysteresis.add(label_hysteresis);
		Panel_hysteresis.add(slider_hysteresis);
		Panel_hysteresis.add(label_hysteresis_bis);
		
		Panel_control_GSM.add(Panel_power);
		Panel_control_GSM.add(Panel_hysteresis);
		Panel_control_GSM.add(Panel_QSI);
		Panel_control_GSM.add(Panel_QSC);
		Panel_control_GSM.add(Panel_neighbors);
		
		//Management of the panel_control_UMTS
		this.slider_power_UMTS = new JSlider(a, b, a);
		this.label_power_UMTS = new JLabel("Power : ");
		this.label_power_UMTS_bis = new JLabel(1 + " db ");
		
		Panel_power_UMTS.add(label_power_UMTS);
		Panel_power_UMTS.add(slider_power_UMTS);
		Panel_power_UMTS.add(label_power_UMTS_bis);
		
		this.List_neighbors_UMTS = new JList<Object>(tab);
		this.label_neighbors_UMTS = new JLabel(" Neighbours choice : ");
		
		Panel_neighbors_UMTS.add(label_neighbors_UMTS);
		Panel_neighbors_UMTS.add(List_neighbors_UMTS);
		
		this.slider_hysteresis_UMTS = (new JSlider(0, 10, 1));
		this.label_hysteresis_UMTS = new JLabel("hysteresis : ");
		this.label_hysteresis_UMTS_bis = new JLabel(1 + " db ");
		
		Panel_hysteresis_UMTS.add(label_hysteresis_UMTS);
		Panel_hysteresis_UMTS.add(slider_hysteresis_UMTS);
		Panel_hysteresis_UMTS.add(label_hysteresis_UMTS_bis);
		
		Panel_control_UMTS.add(Panel_power_UMTS);
		Panel_control_UMTS.add(Panel_hysteresis_UMTS);
		Panel_control_UMTS.add(Panel_neighbors_UMTS);
		
		//Add to the MenuAntennaView panel
		Panel_control_bis.add(Panel_control_GSM);
		Panel_control_bis.add(Panel_control_UMTS);
		
		this.add(Panel_control,BorderLayout.NORTH);
		//this.add(Panel_control_GSM);
		//this.add(Panel_control_UMTS,BorderLayout.SOUTH);
		this.add(Panel_control_bis);
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