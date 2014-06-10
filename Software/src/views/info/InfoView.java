package views.info;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import models.info.InfoModel;

public class InfoView extends JPanel {
	
	private InfoModel infoModel;
	private InfoConsoleView infoConsoleView;
	private InfoPhoneView infoPhoneView;

	public InfoView(InfoModel infoModel) {
		
		this.infoConsoleView = new InfoConsoleView();
		this.infoPhoneView = new InfoPhoneView();
	
		this.setInfoModel(infoModel);
		this.setInfoConsoleView(this.infoConsoleView);
		this.setInfoPhoneView(this.infoPhoneView);
		this.setPreferredSize(new Dimension(infoModel.getInfoWidth(), infoModel.getInfoHeight()));
		
		this.setLayout(new BorderLayout());
		this.add(this.getInfoConsoleView(), BorderLayout.CENTER);
		this.add(this.getInfoPhoneView(), BorderLayout.WEST);
		
		Thread threadInfoPhoneView = new Thread(this.infoPhoneView, "InfoPhoneView");
		threadInfoPhoneView.start();
	}

	public InfoModel getInfoModel() {
		return this.infoModel;
	}

	public void setInfoModel(InfoModel infoModel) {
		this.infoModel = infoModel;
	}

	public InfoConsoleView getInfoConsoleView() {
		return infoConsoleView;
	}

	public void setInfoConsoleView(InfoConsoleView infoConsoleView) {
		this.infoConsoleView = infoConsoleView;
	}

	public InfoPhoneView getInfoPhoneView() {
		return infoPhoneView;
	}

	public void setInfoPhoneView(InfoPhoneView infoPhoneView) {
		this.infoPhoneView = infoPhoneView;
	}
}
