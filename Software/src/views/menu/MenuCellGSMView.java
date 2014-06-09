package views.menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import models.network.Antenna;
import models.network.AntennaManager;
import models.network.Cell;
import models.network.CellGSM;
import models.network.CellManager;
import models.utilities.SpringUtilities;

public class MenuCellGSMView extends JPanel {
	
	private JCheckBox checkbox_GSM;
	
	private JSlider slider_power;
	private JLabel label_power;
	
	private JSlider slider_rxAccessMin;
	private JLabel label_rxAccessMin;
	
	private JSlider slider_reselectOffset;
	private JLabel label_reselectOffset;
	
	private JSlider slider_reselectHysteresis;
	private JLabel label_reselectHysteresis;
	
	private JComboBox<ComboOption> combo_QSI;
	private JComboBox<ComboOption> combo_QSC;
	
	private JLabel label_neighbors;
	private JList<ComboOption> list_neighbors;
	private DefaultListModel<ComboOption> list_neighbors_model;
	
	
	public MenuCellGSMView() {
		
		this.checkbox_GSM = new JCheckBox("GSM", true);
		
		this.slider_power = new JSlider(CellGSM.MIN_STRENGTH, CellGSM.MAX_STRENGTH, CellGSM.DEFAULT_STRENGTH);
		this.label_power = new JLabel(CellGSM.DEFAULT_STRENGTH + " dBm");
		
		this.slider_rxAccessMin = new JSlider(CellGSM.MIN_RX_ACCESS_MIN, CellGSM.MAX_RX_ACCESS_MIN, CellGSM.DEFAULT_RX_ACCESS_MIN);
		this.label_rxAccessMin= new JLabel(CellGSM.DEFAULT_RX_ACCESS_MIN + " dBm");
		
		this.slider_reselectOffset = new JSlider(CellGSM.MIN_RESELECT_OFFSET, CellGSM.MAX_RESELECT_OFFSET ,CellGSM.DEFAULT_RESELECT_OFFSET);
		this.label_reselectOffset = new JLabel(CellGSM.DEFAULT_RESELECT_OFFSET + " dBm");
		
		this.slider_reselectHysteresis = new JSlider(CellGSM.MIN_RESELECT_HYSTERESIS, CellGSM.MAX_RESELECT_HYSTERESIS, CellGSM.DEFAULT_RESELECT_HYSTERESIS);
		this.label_reselectHysteresis = new JLabel(CellGSM.DEFAULT_RESELECT_HYSTERESIS + " dBm");
		
		ComboOption[] options_QS = {new ComboOption(0, "0"), new ComboOption(1, "1"), new ComboOption(2, "2"), new ComboOption(3, "3"), new ComboOption(4, "4"), new ComboOption(5, "5"), new ComboOption(6, "6"), new ComboOption(7, "7"), new ComboOption(8, "8"), new ComboOption(9, "9"), new ComboOption(10, "10"), new ComboOption(11, "11"), new ComboOption(12, "12"), new ComboOption(13, "13"), new ComboOption(14, "14"), new ComboOption(15, "15")};
		this.combo_QSI = new JComboBox<>(options_QS);
		this.combo_QSC = new JComboBox<>(options_QS);
		
		this.label_neighbors = new JLabel("neighbors    ");
		//String[] options_neighbors = {"Antenna 13 - GSM", "Antenna 14 - GSM", "Antenna 15 - GSM", "Antenna 16 - GSM"};
		this.list_neighbors_model = new DefaultListModel<>();
		this.list_neighbors = new JList<>(this.list_neighbors_model);
		this.fillNeighbors(null);
		this.list_neighbors.setVisibleRowCount(4);
		this.list_neighbors.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		this.list_neighbors.setPreferredSize(new Dimension(200, (int) this.list_neighbors.getPreferredSize().getHeight()));
		
		this.setLayout(new BorderLayout());
		
		JPanel centerPanel = new JPanel(new SpringLayout());
		
		centerPanel.add(new JLabel("Power"));
		centerPanel.add(this.slider_power);
		centerPanel.add(this.label_power);
		
		centerPanel.add(new JLabel("Access min"));
		centerPanel.add(this.slider_rxAccessMin);
		centerPanel.add(this.label_rxAccessMin);
		
		centerPanel.add(new JLabel("Offset"));
		centerPanel.add(this.slider_reselectOffset);
		centerPanel.add(this.label_reselectOffset);
		
		centerPanel.add(new JLabel("Hysteresis"));
		centerPanel.add(this.slider_reselectHysteresis);
		centerPanel.add(this.label_reselectHysteresis);
		
		SpringUtilities.makeGrid(centerPanel, 4, 3, 5, 5, 0, 0);
		
		JPanel qsiPanel = new JPanel(new BorderLayout());
		qsiPanel.add(new JLabel("QSI"), BorderLayout.WEST);
		qsiPanel.add(this.combo_QSI, BorderLayout.CENTER);
		
		JPanel qscPanel = new JPanel(new BorderLayout());
		qscPanel.add(new JLabel("QSC"), BorderLayout.WEST);
		qscPanel.add(this.combo_QSC, BorderLayout.CENTER);
		
		JPanel bottomTopPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		bottomTopPanel.add(qsiPanel);
		bottomTopPanel.add(qscPanel);
		
		
		JPanel bottomBottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 10));
		JScrollPane neighborsScrollPane = new JScrollPane(this.list_neighbors);
		JPanel neighborsPanel = new JPanel(new BorderLayout());
		neighborsPanel.add(neighborsScrollPane, BorderLayout.NORTH);
		this.label_neighbors.setVerticalAlignment(SwingConstants.TOP);
		bottomBottomPanel.add(label_neighbors);
		bottomBottomPanel.add(neighborsPanel);
		
		JPanel bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.add(bottomTopPanel, BorderLayout.NORTH);
		bottomPanel.add(bottomBottomPanel, BorderLayout.SOUTH);
		
		this.add(checkbox_GSM, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(bottomPanel, BorderLayout.SOUTH);
		
		this.setBorder(BorderFactory.createTitledBorder("Cell GSM"));
		
	}
	
	public JSlider getSliderPower() {
		return this.slider_power;
	}
	
	public JLabel getLabelPower() {
		return this.label_power;
	}
	
	public JSlider getSliderRxAccessMin() {
		return this.slider_rxAccessMin;
	}
	
	public JLabel getLabelRxAccessMint() {
		return this.label_rxAccessMin;
	}
	
	public JSlider getSliderReselectOffset() {
		return this.slider_reselectOffset;
	}
	
	public JLabel getLabelReselectOffset() {
		return this.label_reselectOffset;
	}
	
	public JSlider getSliderReselectHysteresis() {
		return this.slider_reselectHysteresis;
	}
	
	public JLabel getLabelReselectHysteresis() {
		return this.label_reselectHysteresis;
	}
	
	public JCheckBox getCheckBoxGSM() {
		return this.checkbox_GSM;
	}
	
	public JComboBox<ComboOption> getComboQSI() {
		return this.combo_QSI;
	}
	
	public JComboBox<ComboOption> getComboQSC() {
		return this.combo_QSC;
	}
	
	public JLabel getLabelNeighbors() {
		return this.label_neighbors;
	}
	
	public JList<ComboOption> getListNeighbors() {
		return this.list_neighbors;
	}
	
	public DefaultListModel<ComboOption> getListNeighborsModel() {
		return this.list_neighbors_model;
	}
	
	public void fillNeighbors(Cell activeCell) {
		this.getListNeighborsModel().removeAllElements();
		
		List<Antenna> listAntenna = new ArrayList<>(AntennaManager.Instance().getAntennas());
		Collections.sort(listAntenna);
		
		for(Antenna antenna: listAntenna) {
			
			if (antenna.getCellGSM() != null && (activeCell == null || antenna.getCellGSM() !=  activeCell)) {
				ComboOption comboOptionGSM = new ComboOption(antenna.getCellGSM().getId(), "Antenna " + antenna.getId() + " (GSM)");
				this.getListNeighborsModel().add(this.getListNeighborsModel().getSize(), comboOptionGSM);
				//ComboOption comboOptionUMTS = new ComboOption(antenna.getCellUMTS().getId(), "Antenna " + antenna.getId() + " (UMTS)");
				//this.getListNeighborsModel().add(this.getListNeighborsModel().getSize(), comboOptionUMTS);
				
			}
		}
	}
}