import javax.swing.SwingUtilities;

import models.application.ApplicationModel;
import models.area.AreaModel;
import models.info.InfoModel;
import models.menu.MenuModel;
import models.utilities.ParseXMLFile;
import views.application.ApplicationView;
import views.area.AreaView;
import views.info.InfoView;
import views.menu.MenuView;
import config.MainConfig;
import controllers.ApplicationController;
import controllers.AreaController;
import controllers.InfoController;
import controllers.MenuController;


public class Main {

	public static void main(String[] args) {
		
		ParseXMLFile.parse(MainConfig.FILE_CONFIG);
		
		ApplicationController applicationController = ApplicationController.Instance();
		MenuController menuController = MenuController.Instance();
		InfoController infoController = InfoController.Instance();
		AreaController areaController = AreaController.Instance();

		applicationController.getApplicationView().setAreaView(areaController.getAreaView());
		applicationController.getApplicationView().setMenuView(menuController.getMenuView());
		applicationController.getApplicationView().setInfoView(infoController.getInfoView());
		
		applicationController.getApplicationView().setVisible(true);
		applicationController.getApplicationView().pack();
		
		Thread threadArea = new Thread(areaController, "Area");
		
		threadArea.start();
	}

}
