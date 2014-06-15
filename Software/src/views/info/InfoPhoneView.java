package views.info;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import models.application.ApplicationModel;
import models.info.InfoModel;
import models.mobile.Mobile;
import models.mobile.ModuleGSM;
import models.network.Cell;
import models.network.CellGSM;
import models.network.CellUMTS;
import models.utilities.Formulas;

public class InfoPhoneView extends JPanel implements Runnable {
	
	private Mobile mobile;
	
	private JLabel labelAntenna;
	private JLabel labelCellType;
	
	private JLabel labelPower;
	private JProgressBar barPower;
	private JLabel valuePower;
	
	private JLabel labelInterference;
	private JProgressBar barInterference;
	private JLabel valueInterference;
	
	private JLabel labelRate;
	private JProgressBar barRate;
	private JLabel valueRate;
	
	public InfoPhoneView() {
		
		super(new GridLayout(5, 1));
		
		this.mobile = Mobile.Instance();
		
		JPanel panelTop1 = new JPanel(new FlowLayout());
		this.labelAntenna = new JLabel(" Antenna : ");
		this.labelCellType = new JLabel(" Cellule : ");
		panelTop1.add(this.labelAntenna, BorderLayout.WEST);
		panelTop1.add(this.labelCellType, BorderLayout.EAST);
		
		JPanel panelTop2 = new JPanel(new FlowLayout());
		this.labelPower = new JLabel(" Rx received ");
		this.labelPower.setPreferredSize(new Dimension(80, 20));
		this.barPower = new JProgressBar(-120, -20);
		this.barPower.setPreferredSize(new Dimension(135, 20));
		this.valuePower = new JLabel(" 0 dBm");
		this.valuePower.setPreferredSize(new Dimension(65, 20));
		panelTop2.add(this.labelPower);
		panelTop2.add(this.barPower);
		panelTop2.add(this.valuePower);
		
		JPanel panelTop3 = new JPanel(new FlowLayout());
		this.labelInterference = new JLabel(" SINR ");
		this.labelInterference.setPreferredSize(new Dimension(80, 20));
		this.barInterference = new JProgressBar(0, 100);
		this.barInterference.setPreferredSize(new Dimension(135, 20));
		this.valueInterference = new JLabel(" 0 dBm");
		this.valueInterference.setPreferredSize(new Dimension(65, 20));
		panelTop3.add(this.labelInterference);
		panelTop3.add(this.barInterference);
		panelTop3.add(this.valueInterference);
		
		JPanel panelTop4 = new JPanel(new FlowLayout());
		this.labelRate = new JLabel(" Througput ");
		this.labelRate.setPreferredSize(new Dimension(80, 20));
		this.barRate = new JProgressBar(0, 2000);
		this.barRate.setPreferredSize(new Dimension(135, 20));
		this.valueRate = new JLabel(" 0 kb/s");
		this.valueRate.setPreferredSize(new Dimension(65, 20));
		
		panelTop4.add(this.labelRate);
		panelTop4.add(this.barRate);
		panelTop4.add(this.valueRate);
		
		
		this.add(panelTop1);
		this.add(panelTop2);
		this.add(panelTop3);
		this.add(panelTop4);
		
		this.setPreferredSize(new Dimension(300, InfoModel.Instance().getInfoHeight()));
		
		this.update();
	}
	
	private void update() {
		
		boolean isService = (mobile.getService() != null);
		//this.labelAntenna.setEnabled(isService);
		//this.labelCellType.setEnabled(isService);
		
		this.labelPower.setEnabled(isService);
		this.barPower.setEnabled(isService);
		this.valuePower.setEnabled(isService);
		
		this.labelInterference.setEnabled(isService);
		this.barInterference.setEnabled(isService);
		this.valueInterference.setEnabled(isService);
		
		this.labelRate.setEnabled(isService && this.mobile.isData());
		this.barRate.setEnabled(isService && this.mobile.isData());
		this.valueRate.setEnabled(isService && this.mobile.isData());
		
		if(isService) {
			
			Cell service = this.mobile.getService();
			
			//Top1
			this.labelAntenna.setText("Antenna : " + service.getAntenna().getId());
			if (service.getType() == Cell.CELLTYPE_GSM) {
				this.labelCellType.setText(" (Cellule : GSM) ");
			}
			else {
				this.labelCellType.setText(" (Cellule : UMTS) ");
			}
			
			//Top2
			this.valuePower.setText(" " + service.getStrength(this.mobile.getX(), this.mobile.getY()) + " dBm");
			this.barPower.setValue(service.getStrength(this.mobile.getX(), this.mobile.getY()));
			
			//Top3
			if (service.getType() == Cell.CELLTYPE_GSM) {
				this.labelInterference.setText(" C/I ");
				this.valueInterference.setText(" " + this.mobile.getModuleGSM().getSINR((CellGSM)service) + " dB (" + Formulas.noiseToRxQUAL(this.mobile.getModuleGSM().getSINR((CellGSM)service))  + ")");
				this.barInterference.setValue(this.mobile.getModuleGSM().getSINR((CellGSM)service));
			}
			else {
				this.labelInterference.setText(" Ec/Io ");
				this.valueInterference.setText(" " + this.mobile.getModuleUMTS().getEcIo((CellUMTS) service) + " dB");
				this.barInterference.setValue(this.mobile.getModuleUMTS().getEcIo((CellUMTS) service));
			}
			
			//Top4
			if (service.getType() == Cell.CELLTYPE_GSM) {
				
				if(this.mobile.isData()) {
					
					int value = 0;
					if (mobile.isEDGE()) {
						value = (int) Math.round(this.mobile.getModuleGSM().getDataThroughput((CellGSM) service, ModuleGSM.EDGE));
					}
					else if(mobile.isGPRS()) {
						value = (int) Math.round(this.mobile.getModuleGSM().getDataThroughput((CellGSM) service, ModuleGSM.GPRS));
					}
					
					this.valueRate.setText(" " + value + " Kb/s");
					this.barRate.setValue(value);
				}
			}
			else {
				//througput for UMTS
				if(this.mobile.isData()) {
					
					int value = (int) Math.round(this.mobile.getModuleUMTS().getDataThroughput((CellUMTS) service));
					
					String valueTxt = value + " Kb/s";
					if(value >= 1000) {
						valueTxt = value / 1000 + " Mb/s";
					}
							
					this.valueRate.setText(" " + valueTxt);
					
					this.barRate.setValue((int) (Math.round(value)));
				}
			}
		}
		else {	//No service Cell
			
			if(this.mobile.isPower()) {
				this.labelAntenna.setText(" No Connection ");
			}
			else {
				this.labelAntenna.setText(" Mobile is switched off");
			}
				
			this.labelCellType.setText("");
			
			this.barPower.setValue(this.barPower.getMinimum());
			this.valuePower.setText("");
			
			this.barInterference.setValue(this.barInterference.getMinimum());
			this.valueInterference.setText("");
		}
		
		if(! isService || ! this.mobile.isData()) {
			this.barRate.setValue(this.barRate.getMinimum());
			this.valueRate.setText("");
		}
		
	}

	@Override
	public void run() {
		while(true) {
			try {
				this.update();
				Thread.currentThread().sleep(1000 / ApplicationModel.Instance().getFpsRate());
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
