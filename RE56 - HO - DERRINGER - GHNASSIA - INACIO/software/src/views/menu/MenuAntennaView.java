package views.menu;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import models.network.Antenna;
import models.network.Cell;
import models.network.CellGSM;
import models.network.CellManager;
import models.network.CellUMTS;
import models.network.LocationArea;
import models.network.LocationAreaManager;

public class MenuAntennaView extends JPanel{
	
	private Antenna modelAntenna;
	
	private JLabel label_areas;
	private JComboBox<ComboOption> combo_areas;
	
	private MenuCellGSMView menuCellGSMView;
	private MenuCellUMTSView menuCellUMTSView;
	
	public MenuAntennaView() {
		
		this.menuCellGSMView = new MenuCellGSMView();
		this.menuCellUMTSView = new MenuCellUMTSView();
		
		this.combo_areas = new JComboBox<>();
		this.label_areas = new JLabel("Location area");
		
		this.setLayout(new BorderLayout());
		
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
		topPanel.add(label_areas);
		topPanel.add(combo_areas);
		
		this.add(topPanel, BorderLayout.NORTH);
		this.add(this.menuCellGSMView, BorderLayout.CENTER);
		this.add(this.menuCellUMTSView, BorderLayout.SOUTH);
		
		this.setBorder(BorderFactory.createTitledBorder("No antenna selected"));
		
		this.update();
		this.updateComboAreas();
	}

	public Antenna getModelAntenna() {
		return this.modelAntenna;
	}

	public void setModelAntenna(Antenna modelAntenna) {
		this.modelAntenna = modelAntenna;
		if (modelAntenna != null) {
			this.getMenuCellGSMView().fillNeighbors(modelAntenna.getCellGSM());
			this.getMenuCellUMTSView().fillNeighbors(modelAntenna.getCellUMTS());
		}
		else {
			this.getMenuCellGSMView().fillNeighbors(null);
			this.getMenuCellUMTSView().fillNeighbors(null);
		}
		this.update();
		this.updateComboAreas();
	}
	
	public JComboBox<ComboOption> getComboArea() {
		return this.combo_areas;
	}
	
	public void update() {
		
		if(this.modelAntenna != null) {	
			
			this.setBorder(BorderFactory.createTitledBorder("Parametres for antenna " + this.modelAntenna.getId()));
			this.label_areas.setEnabled(true);
			this.combo_areas.setEnabled(true);
			
			if( this.getModelAntenna().getCellGSM() != null) {
				
				this.getMenuCellGSMView().getListNeighbors().setEnabled(true);
				this.getMenuCellGSMView().getLabelNeighbors().setEnabled(true);
				
				CellGSM cellGSM = this.getModelAntenna().getCellGSM();
				
				this.getMenuCellGSMView().setActivated(cellGSM.isEnabled(), true);
				
				this.updateGSMCheckbox(cellGSM.isEnabled());
				this.updateGSMPower(cellGSM.getPower());
				this.updateGSMRxAccessMin(cellGSM.getRxAccessMin());
				this.updateGSMReselectOffset(cellGSM.getReselectOffset());
				this.updateGSMReselectHysteresis(cellGSM.getReselectHysteresis());
				this.updateGSMFrequency(cellGSM.getFrequency());
				this.updateGSMFrequencyOffset(cellGSM.getFrequencyOffset());
				this.updateGSMQSI(cellGSM.getQSI());
				this.updateGSMQSC(cellGSM.getQSC());
				
				DefaultListModel<ComboOption> neighborsListModel = this.getMenuCellGSMView().getListNeighborsModel();
				List<Integer> selection = new ArrayList<>();
				for(int i = 0; i < neighborsListModel.getSize(); i++) {
					ComboOption comboOption = neighborsListModel.get(i);
					if(cellGSM.getNeighbors().contains(CellManager.Instance().getCell(comboOption.getId()))) {
						selection.add(i);
					}
				}
				
				int[] indices = new int[selection.size()];
				for(int i = 0; i < selection.size(); i++) {
					indices[i] = selection.get(i);
				}
				this.getMenuCellGSMView().getListNeighbors().setSelectedIndices(indices);
			}
			else {
				this.getMenuCellGSMView().setActivated(false, false);
			}
			
			if(this.getModelAntenna().getCellUMTS() != null) {
				
				
				this.getMenuCellUMTSView().getListNeighbors().setEnabled(true);
				this.getMenuCellUMTSView().getLabelNeighbors().setEnabled(true);
				
				CellUMTS cellUMTS = this.getModelAntenna().getCellUMTS();
				
				this.getMenuCellUMTSView().setActivated(cellUMTS.isEnabled(), true);
				
				this.updateUMTSCheckbox(cellUMTS.isEnabled());
				this.updateUMTSPower(cellUMTS.getFullPower());
				this.updateUMTSRxAccessMin(cellUMTS.getQRxLevMin());
				this.updateUMTSQualityMin(cellUMTS.getQQualMin());
				this.updateUMTSActiveSetRange(cellUMTS.getActiveSetRange());
				this.updateUMTSFrequency(cellUMTS.getFrequency());
				
				DefaultListModel<ComboOption> neighborsListModel = this.getMenuCellUMTSView().getListNeighborsModel();
				List<Integer> selection = new ArrayList<>();
				for(int i = 0; i < neighborsListModel.getSize(); i++) {
					ComboOption comboOption = neighborsListModel.get(i);
					if(cellUMTS.getNeighbors().contains(CellManager.Instance().getCell(comboOption.getId()))) {
						selection.add(i);
					}
				}
				
				int[] indices = new int[selection.size()];
				for(int i = 0; i < selection.size(); i++) {
					indices[i] = selection.get(i);
				}
				this.getMenuCellUMTSView().getListNeighbors().setSelectedIndices(indices);
			}
			else {
				this.getMenuCellUMTSView().setActivated(false, false);
			}
		}
		else {
			this.getMenuCellGSMView().setActivated(false, false);
			this.getMenuCellUMTSView().setActivated(false, false);
			
			this.setBorder(BorderFactory.createTitledBorder("No antenna selected"));
			this.label_areas.setEnabled(false);
			this.combo_areas.setEnabled(false);
			
			/*this.getMenuCellGSMView().getListNeighbors().setEnabled(false);
			this.getMenuCellGSMView().getLabelNeighbors().setEnabled(false);
			this.getMenuCellGSMView().getListNeighbors().setSelectedIndices(new int[0]);
			this.getMenuCellGSMView().getComboFrequency().setSelectedIndex(0);
			this.getMenuCellGSMView().getComboQSI().setSelectedIndex(0);
			this.getMenuCellGSMView().getComboQSC().setSelectedIndex(0);*
			
			this.getMenuCellGSMView().getLabelFrequencyOffset().setEnabled(false);
			this.getMenuCellGSMView().getComboFrequencyOffset().setEnabled(false);
			this.getMenuCellUMTSView().getListNeighbors().setEnabled(false);
			this.getMenuCellUMTSView().getLabelNeighbors().setEnabled(false);
			this.getMenuCellUMTSView().getListNeighbors().setSelectedIndices(new int[0]);
			//this.getMenuCellUMTSView().getComboFrequency().setSelectedIndex(1);*/
		}
	}
	
	public MenuCellGSMView getMenuCellGSMView() {
		return this.menuCellGSMView;
	}
	
	public MenuCellUMTSView getMenuCellUMTSView() {
		return this.menuCellUMTSView;
	}
	
	public void updateGSMCheckbox(boolean value) {
		this.getMenuCellGSMView().getCheckBoxGSM().setSelected(value);
	}
	
	public void updateGSMPower(int value) {
		this.getMenuCellGSMView().getSliderPower().setValue(value);
		this.getMenuCellGSMView().getLabelPower().setText(value + " dBm");
	}
	
	public void updateGSMRxAccessMin(int value) {
		this.getMenuCellGSMView().getSliderRxAccessMin().setValue(value);
		this.getMenuCellGSMView().getValueRxAccessMint().setText(value + " dBm");
	}
	
	public void updateGSMReselectOffset(int value) {
		this.getMenuCellGSMView().getSliderReselectOffset().setValue(value);
		this.getMenuCellGSMView().getValueReselectOffset().setText(value + " dB");
	}
	
	public void updateGSMReselectHysteresis(int value) {
		this.getMenuCellGSMView().getSliderReselectHysteresis().setValue(value);
		this.getMenuCellGSMView().getValueReselectHysteresis().setText(value + " dB");
	}
	
	public void updateGSMFrequency(int value) {
		for (int i = 0; i < this.getMenuCellGSMView().getComboFrequency().getItemCount(); i++) {
			if (this.getMenuCellGSMView().getComboFrequency().getItemAt(i).getId() == value) {
				this.getMenuCellGSMView().getComboFrequency().setSelectedIndex(i);
				break;
			}
		}
	}
	
	public void updateGSMFrequencyOffset(int value) {
		this.getMenuCellGSMView().getComboFrequencyOffset().setSelectedIndex(value);
	}
	
	public void updateGSMQSI(int value) {
		this.getMenuCellGSMView().getComboQSI().setSelectedIndex(value);
	}
	
	public void updateGSMQSC(int value) {
		this.getMenuCellGSMView().getComboQSC().setSelectedIndex(value);
	}
	
	public void updateUMTSCheckbox(boolean value) {
		this.getMenuCellUMTSView().getCheckBoxUMTS().setSelected(value);
	}
	
	public void updateUMTSPower(int value) {
		this.getMenuCellUMTSView().getSliderPower().setValue(value);
		this.getMenuCellUMTSView().getValuePower().setText(value + " dBm");
	}
	
	public void updateUMTSRxAccessMin(int value) {
		this.getMenuCellUMTSView().getSliderRxAccessMin().setValue(value);
		this.getMenuCellUMTSView().getValueRxAccessMint().setText(value + " dBm");
	}
	
	public void updateUMTSQualityMin(int value) {
		this.getMenuCellUMTSView().getSliderQualityMin().setValue(value);
		this.getMenuCellUMTSView().getValueQualityMin().setText(value + " dB");
	}
	
	public void updateUMTSActiveSetRange(int value) {
		this.getMenuCellUMTSView().getSliderActiveSetRanges().setValue(value);
		this.getMenuCellUMTSView().getValueReselectHysteresis().setText(value + " dB");
	}
	
	public void updateUMTSFrequency(int value) {
		for (int i = 0; i < this.getMenuCellUMTSView().getComboFrequency().getItemCount(); i++) {
			if (this.getMenuCellUMTSView().getComboFrequency().getItemAt(i).getId() == value) {
				this.getMenuCellUMTSView().getComboFrequency().setSelectedIndex(i);
				break;
			}
		}
	}
	
	private void updateComboAreas() {
				
		if(this.combo_areas.getItemCount() != LocationAreaManager.Instance().getLocationAreas().size()) {
			this.combo_areas.removeAllItems();
			
			List<LocationArea> areaList = new ArrayList<>(LocationAreaManager.Instance().getLocationAreas());
			Collections.sort(areaList);
			
			boolean isFirst = true;
			for(LocationArea area: areaList) {
				ComboOption option = new ComboOption(area.getId(), "Area " + area.getId());
				this.combo_areas.addItem(option);
				
				if(this.getModelAntenna() == null && isFirst) {
					this.combo_areas.setSelectedItem(option);
					isFirst = false;
				}
				
				if(this.getModelAntenna() != null && this.getModelAntenna().getLocationArea() == area) {
					this.combo_areas.setSelectedItem(option);
				}
			}
		}
		
		else if (this.getModelAntenna() != null) {
			
			for (int i = 0; i < this.combo_areas.getItemCount(); i++) {
				ComboOption option = this.combo_areas.getItemAt(i);
				if (option.getId() == this.modelAntenna.getLocationArea().getId()) {
					this.combo_areas.setSelectedIndex(i);
					break;
				}
			}
		}
	}
}
