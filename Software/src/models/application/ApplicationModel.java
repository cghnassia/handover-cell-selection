package models.application;

import java.util.HashSet;
import java.util.Set;

import views.application.ApplicationView;
import models.menu.MenuModel;
import models.mobile.Mobile;
import models.network.Cell;
import models.network.CellGSM;
import models.network.CellUMTS;
import models.network.Network;
import models.utilities.ParseXMLFile;
import config.MainConfig;

public class ApplicationModel implements Runnable {

	//private Mobile mobile;
	//private Network network;
	
	private int applicationWidth;
	private int applicationHeight;
	private int fpsRate;
	
	private static ApplicationModel applicationModel;
	
	private static final int WINDOW_WIDTH = 1300;
	private static final int WINDOW_HEIGHT = 1000;

	private static final int FPS_RATE = 24;
	
	private ApplicationModel() {
		
		this.setApplicationWidth(ApplicationModel.WINDOW_WIDTH);
		this.setApplicationHeight(ApplicationModel.WINDOW_HEIGHT);
		this.setFpsRate(ApplicationModel.FPS_RATE);
		
		//this.mobile = new Mobile((MainConfig.WINDOW_WIDTH - MainConfig.MENU_WIDTH) * MainConfig.AREA_SCALE / 2, (MainConfig.WINDOW_HEIGHT - MainConfig.INFO_HEIGHT) * MainConfig.AREA_SCALE / 2, MainConfig.SPEED_MIN);
		//this.setNetwork(new Network());
	}
	
	public static ApplicationModel Instance() {
		
		if(ApplicationModel.applicationModel == null) {
			ApplicationModel.applicationModel = new ApplicationModel();
		}
		
		return ApplicationModel.applicationModel;
	}
	
	public void getConfig() {
		ParseXMLFile.parse(MainConfig.FILE_CONFIG);
	}
	
	/*public Mobile getMobile() {
		return this.mobile;
	}*/
	
	public void run() {
		/*while(true) {
			try {
				this.getMobile().updatePosition();
				this.getMobile().getModuleGSM().updateModule();
				Thread.currentThread().sleep(1000 / MainConfig.FPS_RATE);
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
		}*/
	}
	/*
	public Network getNetwork() {
		return this.network;
	}

	public void setNetwork(Network network) {
		this.network = network;
	}
	
	public MenuModel getMenuModel() {
		return new MenuModel();
	}*/

	public int getApplicationWidth() {
		return this.applicationWidth;
	}

	public void setApplicationWidth(int applicationWidth) {
		this.applicationWidth = applicationWidth;
	}

	public int getApplicationHeight() {
		return this.applicationHeight;
	}

	public void setApplicationHeight(int applicationHeight) {
		this.applicationHeight = applicationHeight;
	}

	public int getFpsRate() {
		return this.fpsRate;
	}

	public void setFpsRate(int fpsRate) {
		this.fpsRate = fpsRate;
	}
}
	
