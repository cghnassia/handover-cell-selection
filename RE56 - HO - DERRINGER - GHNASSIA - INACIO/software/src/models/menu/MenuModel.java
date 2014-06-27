package models.menu;

import models.application.ApplicationModel;


public class MenuModel {
	
	private int menuWidth;
	private int menuHeight;
	
	public static final int MENU_WIDTH = 350;
	
	private static MenuModel menuModel;
	
	private MenuModel() {
		this.setMenuWidth(MenuModel.MENU_WIDTH);
		this.setMenuHeight(ApplicationModel.Instance().getApplicationHeight());
	}
	
	public static MenuModel Instance() {
		
		if(MenuModel.menuModel == null) {
			MenuModel.menuModel = new MenuModel();
		}
		
		return MenuModel.menuModel;
	}
	
	public int getMenuWidth() {
		return this.menuWidth;
	}

	public void setMenuWidth(int menuWidth) {
		this.menuWidth = menuWidth;
	}

	public int getMenuHeight() {
		return this.menuHeight;
	}

	public void setMenuHeight(int menuHeight) {
		this.menuHeight = menuHeight;
	}
}