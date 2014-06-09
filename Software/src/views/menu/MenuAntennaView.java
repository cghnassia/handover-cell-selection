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
import models.network.LocationArea;
import models.network.LocationAreaManager;

public class MenuAntennaView extends JPanel{
	
	private Antenna modelAntenna;
	
	private JLabel label_areas;
	private JComboBox<ComboOption> combo_areas;
	
	private MenuCellGSMView menuCellGSMView;
	
	public MenuAntennaView() {
		
		this.menuCellGSMView = new MenuCellGSMView();
		
		this.combo_areas = new JComboBox<>();
		this.label_areas = new JLabel("Location area");
		
		this.setLayout(new BorderLayout());
		
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
		topPanel.add(label_areas);
		topPanel.add(combo_areas);
		
		this.add(topPanel, BorderLayout.NORTH);
		this.add(menuCellGSMView, BorderLayout.CENTER);
		
		//temporary -> need UMTS
		this.add(new MenuCellGSMView(), BorderLayout.SOUTH);
		
		this.setBorder(BorderFactory.createTitledBorder("Default Antenna parameters"));

		this.update();
	}

	public Antenna getModelAntenna() {
		return this.modelAntenna;
	}

	public void setModelAntenna(Antenna modelAntenna) {
		this.modelAntenna = modelAntenna;
		if (modelAntenna != null) {
			this.getMenuCellGSMView().fillNeighbors(modelAntenna.getCellGSM());
		}
		else {
			this.getMenuCellGSMView().fillNeighbors(null);
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
			this.getMenuCellGSMView().getListNeighbors().setEnabled(true);
			this.getMenuCellGSMView().getLabelNeighbors().setEnabled(true);
			
			if( this.getModelAntenna().getCellGSM() != null) {
				CellGSM cellGSM = this.getModelAntenna().getCellGSM();
				this.updateGSMPower(cellGSM.getPower());
				this.updateGSMRxAccessMin(cellGSM.getRxAccessMin());
				this.updateGSMReselectOffset(cellGSM.getReselectOffset());
				this.updateGSMReselectHysteresis(cellGSM.getReselectHysteresis());
				
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
		}
		else {
			this.setBorder(BorderFactory.createTitledBorder("Default Antenna parameters"));
			this.label_areas.setEnabled(false);
			this.combo_areas.setEnabled(false);
			this.getMenuCellGSMView().getListNeighbors().setEnabled(false);
			this.getMenuCellGSMView().getLabelNeighbors().setEnabled(false);
			this.getMenuCellGSMView().getListNeighbors().setSelectedIndices(new int[0]);
		}
	}
	
	public MenuCellGSMView getMenuCellGSMView() {
		return this.menuCellGSMView;
	}
	
	public void updateGSMPower(int value) {
		this.getMenuCellGSMView().getSliderPower().setValue(value);
		this.getMenuCellGSMView().getLabelPower().setText(value + " dBm");
	}
	
	public void updateGSMRxAccessMin(int value) {
		this.getMenuCellGSMView().getSliderRxAccessMin().setValue(value);
		this.getMenuCellGSMView().getLabelRxAccessMint().setText(value + " dBm");
	}
	
	public void updateGSMReselectOffset(int value) {
		this.getMenuCellGSMView().getSliderReselectOffset().setValue(value);
		this.getMenuCellGSMView().getLabelReselectOffset().setText(value + " dBm");
	}
	
	public void updateGSMReselectHysteresis(int value) {
		this.getMenuCellGSMView().getSliderReselectHysteresis().setValue(value);
		this.getMenuCellGSMView().getLabelReselectHysteresis().setText(value + " dBm");
	}
	
	private void updateComboAreas() {
		
		if(this.combo_areas.getItemCount() != LocationAreaManager.Instance().getLocationAreas().size()) {
			this.combo_areas.removeAllItems();
			
			List<LocationArea> areaList = new ArrayList(LocationAreaManager.Instance().getLocationAreas());
			Collections.sort(areaList);
			
			for(LocationArea area: areaList) {
				ComboOption option = new ComboOption(area.getId(), "Area " + area.getId());
				this.combo_areas.addItem(option);
				
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
