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
import models.network.CellUMTS;
import models.utilities.SpringUtilities;

public class MenuCellUMTSView extends JPanel {
	
	private JCheckBox checkbox_UMTS;
	
	private JLabel label_power;
	private JSlider slider_power;
	private JLabel value_power;
	
	private JLabel label_rxAccessMin;
	private JSlider slider_rxAccessMin;
	private JLabel value_rxAccessMin;
	
	private JLabel label_qualityMin;
	private JSlider slider_qualityMin;
	private JLabel value_qualityMin;
	
	private JLabel label_activeSetRange;
	private JSlider slider_activeSetRange;
	private JLabel value_activeSetRange;
	
	private JComboBox<ComboOption> combo_frequency;
	
	private JLabel label_neighbors;
	private JList<ComboOption> list_neighbors;
	private DefaultListModel<ComboOption> list_neighbors_model;
	
	
	public MenuCellUMTSView() {
		
		this.checkbox_UMTS = new JCheckBox("UMTS", true);
		
		this.label_power = new JLabel("Power");
		this.slider_power = new JSlider(CellUMTS.MIN_STRENGTH, CellUMTS.MAX_STRENGTH, CellUMTS.DEFAULT_STRENGTH);
		this.value_power = new JLabel(CellGSM.DEFAULT_STRENGTH + " dBm");
		
		this.label_rxAccessMin = new JLabel("Access min");
		this.slider_rxAccessMin = new JSlider(CellUMTS.MIN_QRXLEV_MIN, CellUMTS.MAX_QRXLEV_MIN, CellUMTS.DEFAULT_QRXLEV_MIN);
		this.value_rxAccessMin= new JLabel(CellGSM.DEFAULT_RX_ACCESS_MIN + " dBm");
		
		this.label_qualityMin = new JLabel("Quality min");
		this.slider_qualityMin = new JSlider(CellUMTS.MIN_QQUAL_MIN, CellUMTS.MAX_QQUAL_MIN, CellUMTS.DEFAULT_QQUAL_MIN);
		this.value_qualityMin = new JLabel(CellGSM.DEFAULT_RESELECT_OFFSET + " dB");
		
		this.label_activeSetRange = new JLabel("Active range");
		this.slider_activeSetRange = new JSlider(CellUMTS.MIN_ACTIVESET_RANGE, CellUMTS.MAX_ACTIVESET_RANGE, CellUMTS.DEFAULT_ACTIVESET_RANGE);
		this.value_activeSetRange = new JLabel(CellUMTS.DEFAULT_ACTIVESET_RANGE + " dB");
		
		ComboOption[] options_frequency = {new ComboOption(900, "900 MHz"), new ComboOption(2100, "2100 MHz")};
		this.combo_frequency = new JComboBox<>(options_frequency);
		
		this.label_neighbors = new JLabel("neighbors    ");
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
		
		topPanel.add(this.checkbox_UMTS, BorderLayout.WEST);
		topPanel.add(frequencyPanel, BorderLayout.EAST);
		
		
		JPanel centerPanel = new JPanel(new SpringLayout());
		
		centerPanel.add(this.label_power);
		centerPanel.add(this.slider_power);
		centerPanel.add(this.value_power);
		
		centerPanel.add(this.label_rxAccessMin);
		centerPanel.add(this.slider_rxAccessMin);
		centerPanel.add(this.value_rxAccessMin);
		
		centerPanel.add(this.label_qualityMin);
		centerPanel.add(this.slider_qualityMin);
		centerPanel.add(this.value_qualityMin);
		
		centerPanel.add(this.label_activeSetRange);
		centerPanel.add(this.slider_activeSetRange);
		centerPanel.add(this.value_activeSetRange);
		
		SpringUtilities.makeGrid(centerPanel, 4, 3, 5, 5, 0, 0);
		
		/*JPanel qsiPanel = new JPanel(new FlowLayout());
		qsiPanel.add(new JLabel("QSI"), BorderLayout.WEST);
		qsiPanel.add(this.combo_QSI, BorderLayout.CENTER);
		
		JPanel qscPanel = new JPanel(new BorderLayout());
		qscPanel.add(new JLabel("QSC"), BorderLayout.WEST);
		qscPanel.add(this.combo_QSC, BorderLayout.CENTER);
		
		JPanel bottomTopPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
		bottomTopPanel.add(qsiPanel);
		bottomTopPanel.add(qscPanel);*/
		
		
		JPanel bottomBottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 10));
		JScrollPane neighborsScrollPane = new JScrollPane(this.list_neighbors);
		JPanel neighborsPanel = new JPanel(new BorderLayout());
		neighborsPanel.add(neighborsScrollPane, BorderLayout.NORTH);
		this.label_neighbors.setVerticalAlignment(SwingConstants.TOP);
		bottomBottomPanel.add(label_neighbors);
		bottomBottomPanel.add(neighborsPanel);
		
		JPanel bottomPanel = new JPanel(new BorderLayout());
		//bottomPanel.add(bottomTopPanel, BorderLayout.NORTH);
		bottomPanel.add(bottomBottomPanel, BorderLayout.SOUTH);
		
		this.add(topPanel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(bottomPanel, BorderLayout.SOUTH);
		
	}
	
	public JSlider getSliderPower() {
		return this.slider_power;
	}
	
	public JLabel getValuePower() {
		return this.value_power;
	}
	
	public JSlider getSliderRxAccessMin() {
		return this.slider_rxAccessMin;
	}
	
	public JLabel getValueRxAccessMint() {
		return this.value_rxAccessMin;
	}
	
	public JSlider getSliderQualityMin() {
		return this.slider_qualityMin;
	}
	
	public JLabel getValueQualityMin() {
		return this.value_qualityMin;
	}
	
	public JSlider getSliderActiveSetRanges() {
		return this.slider_activeSetRange;
	}
	
	public JLabel getValueReselectHysteresis() {
		return this.value_activeSetRange;
	}
	
	public JCheckBox getCheckBoxUMTS() {
		return this.checkbox_UMTS;
	}
	
	public JComboBox<ComboOption> getComboFrequency() {
		return this.combo_frequency;
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
			
			if (antenna.getCellGSM() != null) {
				ComboOption comboOptionGSM = new ComboOption(antenna.getCellGSM().getId(), "Antenna " + antenna.getId() + " (GSM)");
				this.getListNeighborsModel().add(this.getListNeighborsModel().getSize(), comboOptionGSM);
			}
			
			if (antenna.getCellUMTS() != null &&  (activeCell == null || antenna.getCellUMTS() !=  activeCell)) {
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
		this.setBorder(BorderFactory.createTitledBorder(null, "Cell UMTS", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, color));
		
		this.checkbox_UMTS.setEnabled(isActivated || isCheckBox);
		
		this.label_power.setEnabled(isActivated);
		this.slider_power.setEnabled(isActivated);
		this.value_power.setEnabled(isActivated);
		
		this.label_rxAccessMin.setEnabled(isActivated);
		this.slider_rxAccessMin.setEnabled(isActivated);
		this.value_rxAccessMin.setEnabled(isActivated);
		
		this.label_qualityMin.setEnabled(isActivated);
		this.slider_qualityMin.setEnabled(isActivated);
		this.value_qualityMin.setEnabled(isActivated);
		
		this.label_activeSetRange.setEnabled(isActivated);
		this.slider_activeSetRange.setEnabled(isActivated);
		this.value_activeSetRange.setEnabled(isActivated);
		
		this.combo_frequency.setEnabled(isActivated);
		
		this.list_neighbors.setEnabled(isActivated);
		this.label_neighbors.setEnabled(isActivated);
	}
}