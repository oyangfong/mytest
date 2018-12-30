package org.slsale.pojo;

import java.util.List;
/**
 * Menu
 * @author bdqn_hl
 * @date 2014-2-21
 */
public class Menu{

	private Function mainMenu;//主功能模块
	private List<Function> subMenus;//子菜单项列表

	public List<Function> getSubMenus() {
		return subMenus;
	}

	public void setSubMenus(List<Function> subMenus) {
		this.subMenus = subMenus;
	}

	public Function getMainMenu() {
		return mainMenu;
	}

	public void setMainMenu(Function mainMenu) {
		this.mainMenu = mainMenu;
	}
	
}
