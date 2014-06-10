package views.application;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;

import views.area.AreaView;
import views.info.InfoView;
import views.menu.MenuView;
import models.application.ApplicationModel;
import models.network.Cell;
import models.network.CellGSM;
import models.network.CellUMTS;
import config.MainConfig;

public class ApplicationView extends JFrame  {
	
	private ApplicationModel applicationModel;
	
	private JPanel containerPanel;
	private JPanel centerPanel;
	
	public ApplicationView(ApplicationModel applicationModel) {
		
		this.setApplicationModel(applicationModel);
		
		this.containerPanel = new JPanel(new BorderLayout());
		this.centerPanel = new JPanel(new BorderLayout());
		
		this.containerPanel.add(this.centerPanel, BorderLayout.CENTER);
		this.setContentPane(this.containerPanel);
		
		this.setTitle("Handover and Selection/reselection");
		this.setSize(this.getApplicationModel().getApplicationWidth(), this.applicationModel.getApplicationHeight());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public ApplicationModel getApplicationModel() {
		return this.applicationModel;
	}
	
	public void setApplicationModel(ApplicationModel applicationModel) {
		this.applicationModel = applicationModel;
	}
	
	public void setAreaView(AreaView areaView) {
		this.centerPanel.add(areaView, BorderLayout.CENTER);
	}
	
	public void setMenuView(MenuView menuView) {
		this.containerPanel.add(menuView, BorderLayout.EAST);
	}
	
	public void setInfoView(InfoView infoView) {
		this.centerPanel.add(infoView, BorderLayout.SOUTH);
	}
	
	/*@Override
	public void run() {
		while(true) {
			/*try {
				this.area.refreshMobile();
				this.area.refreshCells();
				Thread.currentThread().sleep(1000 / MainConfig.FPS_RATE);
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}*/
}
