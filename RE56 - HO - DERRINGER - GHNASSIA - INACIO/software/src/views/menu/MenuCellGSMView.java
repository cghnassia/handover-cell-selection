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
import javax.swing.border.TitledBorder;

import models.network.Antenna;
import models.network.AntennaManager;
import models.network.Cell;
import models.network.CellGSM;
import models.network.CellManager;
import models.utilities.SpringUtilities;

public class MenuCellGSMView extends JPanel {
	
	private JCheckBox checkbox_GSM;
	
	private JLabel label_power;
	private JSlider slider_power;
	private JLabel value_power;
	
	private JLabel label_rxAccessMin;
	private JSlider slider_rxAccessMin;
	private JLabel value_rxAccessMin;
	
	private JLabel label_reselectOffset;
	private JSlider slider_reselectOffset;
	private JLabel value_reselectOffset;
	
	private JLabel label_reselectHysteresis;
	private JSlider slider_reselectHysteresis;
	private JLabel value_reselectHysteresis;
	
	private JComboBox<ComboOption> combo_frequency;
	
	private JLabel label_frequencyOffset;
	private JComboBox<ComboOption> combo_frequencyOffset;
	
	private JLabel label_QSI;
	private JComboBox<ComboOption> combo_QSI;
	
	private JLabel label_QSC;
	private JComboBox<ComboOption> combo_QSC;
	
	private JLabel label_neighbors;
	private JList<ComboOption> list_neighbors;
	private DefaultListModel<ComboOption> list_neighbors_model;
	
	
	public MenuCellGSMView() {
		
		this.checkbox_GSM = new JCheckBox("GSM", true);
		
		this.label_power = new JLabel("Power");
		this.slider_power = new JSlider(CellGSM.MIN_STRENGTH, CellGSM.MAX_STRENGTH, CellGSM.DEFAULT_STRENGTH);
		this.value_power = new JLabel(CellGSM.DEFAULT_STRENGTH + " dBm");
		
		this.label_rxAccessMin = new JLabel("Access min");
		this.slider_rxAccessMin = new JSlider(CellGSM.MIN_RX_ACCESS_MIN, CellGSM.MAX_RX_ACCESS_MIN, CellGSM.DEFAULT_RX_ACCESS_MIN);
		this.value_rxAccessMin= new JLabel(CellGSM.DEFAULT_RX_ACCESS_MIN + " dBm");
		
		this.label_reselectOffset = new JLabel("Resel. Offset");
		this.slider_reselectOffset = new JSlider(CellGSM.MIN_RESELECT_OFFSET, CellGSM.MAX_RESELECT_OFFSET ,CellGSM.DEFAULT_RESELECT_OFFSET);
		this.value_reselectOffset = new JLabel(CellGSM.DEFAULT_RESELECT_OFFSET + " dB");
		
		this.label_reselectHysteresis= new JLabel("Hysteresis");
		this.slider_reselectHysteresis = new JSlider(CellGSM.MIN_RESELECT_HYSTERESIS, CellGSM.MAX_RESELECT_HYSTERESIS, CellGSM.DEFAULT_RESELECT_HYSTERESIS);
		this.value_reselectHysteresis = new JLabel(CellGSM.DEFAULT_RESELECT_HYSTERESIS + " dB");
		
		ComboOption[] options_frequency = {new ComboOption(900, "900 MHz"), new ComboOption(1800, "1800 MHz")};
		this.combo_frequency = new JComboBox<>(options_frequency);
		
		this.label_frequencyOffset = new JLabel("Offset");
		ComboOption[] options_frequencyOffset = {new ComboOption(0, "0"), new ComboOption(1, "1"),  new ComboOption(2, "2"),  new ComboOption(3, "3"),  new ComboOption(4, "4"),  new ComboOption(5, "5"),  new ComboOption(6, "6"), new ComboOption(7, "7"),  new ComboOption(8, "8"),  new ComboOption(9, "9"),  new ComboOption(10, "10"),  new ComboOption(11, "11"),  new ComboOption(12, "12"), new ComboOption(13, "13"),  new ComboOption(14, "14"),  new ComboOption(15, "15")};
		this.combo_frequencyOffset = new JComboBox<>(options_frequencyOffset);
		
		ComboOption[] options_QS = {new ComboOption(0, "0"), new ComboOption(1, "1"), new ComboOption(2, "2"), new ComboOption(3, "3"), new ComboOption(4, "4"), new ComboOption(5, "5"), new ComboOption(6, "6"), new ComboOption(7, "7"), new ComboOption(8, "8"), new ComboOption(9, "9"), new ComboOption(10, "10"), new ComboOption(11, "11"), new ComboOption(12, "12"), new ComboOption(13, "13"), new ComboOption(14, "14"), new ComboOption(15, "15")};
		this.label_QSI = new JLabel("QSI");
		this.combo_QSI = new JComboBox<>(options_QS);
		this.label_QSC = new JLabel("QSC");
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
		
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
		
		JPanel frequencyPanel = new JPanel(new BorderLayout());
		frequencyPanel.add(new JLabel(""), BorderLayout.WEST);
		frequencyPanel.add(this.combo_frequency, BorderLayout.CENTER);
		
		JPanel frequencyOffsetPanel = new JPanel(new BorderLayout());
		frequencyOffsetPanel.add(this.label_frequencyOffset, BorderLayout.WEST);
		frequencyOffsetPanel.add(this.combo_frequencyOffset, BorderLayout.CENTER);
		
		topPanel.add(this.checkbox_GSM);
		topPanel.add(frequencyPanel);
		topPanel.add(frequencyOffsetPanel);
		
		JPanel centerPanel = new JPanel(new SpringLayout());
		
		centerPanel.add(this.label_power);
		centerPanel.add(this.slider_power);
		centerPanel.add(this.value_power);
		
		centerPanel.add(this.label_rxAccessMin);
		centerPanel.add(this.slider_rxAccessMin);
		centerPanel.add(this.value_rxAccessMin);
		
		centerPanel.add(this.label_reselectOffset);
		centerPanel.add(this.slider_reselectOffset);
		centerPanel.add(this.value_reselectOffset);
		
		centerPanel.add(this.label_reselectHysteresis);
		centerPanel.add(this.slider_reselectHysteresis);
		centerPanel.add(this.value_reselectHysteresis);
		
		SpringUtilities.makeGrid(centerPanel, 4, 3, 5, 5, 0, 0);
		
		JPanel qsiPanel = new JPanel(new FlowLayout());
		qsiPanel.add(label_QSI, BorderLayout.WEST);
		qsiPanel.add(this.combo_QSI, BorderLayout.CENTER);
		
		JPanel qscPanel = new JPanel(new BorderLayout());
		qscPanel.add(label_QSC, BorderLayout.WEST);
		qscPanel.add(this.combo_QSC, BorderLayout.CENTER);
		
		JPanel bottomTopPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
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
		
		this.add(topPanel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(bottomPanel, BorderLayout.SOUTH);
		
	}
	
	public JSlider getSliderPower() {
		return this.slider_power;
	}
	
	public JLabel getLabelPower() {
		return this.value_power;
	}
	
	public JSlider getSliderRxAccessMin() {
		return this.slider_rxAccessMin;
	}
	
	public JLabel getValueRxAccessMint() {
		return this.value_rxAccessMin;
	}
	
	public JSlider getSliderReselectOffset() {
		return this.slider_reselectOffset;
	}
	
	public JLabel getValueReselectOffset() {
		return this.value_reselectOffset;
	}
	
	public JSlider getSliderReselectHysteresis() {
		return this.slider_reselectHysteresis;
	}
	
	public JLabel getValueReselectHysteresis() {
		return this.value_reselectHysteresis;
	}
	
	public JCheckBox getCheckBoxGSM() {
		return this.checkbox_GSM;
	}
	
	public JComboBox<ComboOption> getComboFrequency() {
		return this.combo_frequency;
	}
	
	public JLabel getLabelFrequencyOffset() {
		return this.label_frequencyOffset;
	}
	
	public JComboBox<ComboOption> getComboFrequencyOffset() {
		return this.combo_frequencyOffset;
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
			}
			
			if (antenna.getCellUMTS() != null) {
				ComboOption comboOptionUMTS = new ComboOption(antenna.getCellUMTS().getId(), "Antenna " + antenna.getId() + " (UMTS)");
				this.getListNeighborsModel().add(this.getListNeighborsModel().getSize(), comboOptionUMTS);
				
			}
		}
	}
	
	public void setActivated(boolean isActivated, boolean isCheckBox) {
		
		Color color;
		if(isActivated || isCheckBox) {
			color = Color.BLACK;
		}
		else {
			color = Color.GRAY;
		}
		this.setBorder(BorderFactory.createTitledBorder(null, "Cell GSM", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, color));
		
		
		this.checkbox_GSM.setEnabled(isActivated || isCheckBox);
		
		this.label_power.setEnabled(isActivated);
		this.slider_power.setEnabled(isActivated);
		this.value_power.setEnabled(isActivated);
		
		this.label_rxAccessMin.setEnabled(isActivated);
		this.slider_rxAccessMin.setEnabled(isActivated);
		this.value_rxAccessMin.setEnabled(isActivated);
		
		this.label_reselectOffset.setEnabled(isActivated);
		this.slider_reselectOffset.setEnabled(isActivated);
		this.value_reselectOffset.setEnabled(isActivated);
		
		this.label_reselectHysteresis.setEnabled(isActivated);
		this.slider_reselectHysteresis.setEnabled(isActivated);
		this.value_reselectHysteresis.setEnabled(isActivated);
		
		this.combo_frequency.setEnabled(isActivated);
		this.label_frequencyOffset.setEnabled(isActivated);
		this.combo_frequencyOffset.setEnabled(isActivated);
		
		this.label_QSI.setEnabled(isActivated);
		this.combo_QSI.setEnabled(isActivated);
		
		this.label_QSC.setEnabled(isActivated);
		this.combo_QSC.setEnabled(isActivated);
		
		this.list_neighbors.setEnabled(isActivated);
		this.label_neighbors.setEnabled(isActivated);
	}
}