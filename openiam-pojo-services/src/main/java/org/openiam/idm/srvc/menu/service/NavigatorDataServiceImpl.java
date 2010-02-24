package org.openiam.idm.srvc.menu.service;

//import diamelle.common.continfo.*;
//import diamelle.base.prop.*;

import java.util.*;
import java.rmi.*;

import javax.jws.WebService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openiam.idm.srvc.menu.dto.Menu;
import org.openiam.idm.srvc.menu.dto.MenuId;

/**
 * <code>NavigatorDataServiceImpl</code> provides a service implementation to deliver the application
 * menu options that a user may be entititled to. NavigatorDataServiceImpl also provides basic 
 * data level functionality <br>
 * 
 * 
 * <B>Examples</B>
 * 
 * @author Suneet Shah
 * @version 2
 */

public class NavigatorDataServiceImpl implements NavigatorDataService{

	NavigatorDAO navigatorDao;
	private static final Log log = LogFactory.getLog(NavigatorDataServiceImpl.class);
	
	public Menu addMenu(Menu data) {
		  if (data == null)
			   throw new NullPointerException("Menu object is null");
		  if (data.getId() == null)
			  throw new NullPointerException("Menu id is null");
		 
		  navigatorDao.add(data);	 
		  return data;
	}

	/**
	 * Returns a <code>String</code> of MenuIds for all the menus in the
	 * parentMenuGroupId hierarchy. This includes menu options found several levels
	 * deep in the hierarchy. The String of Ids is comma separated.
	 * @param parentMenuGroupId
	 * @return String
	 */
	public String getAllMenuOptionIDs(String parentMenuGroupId, String languageCd) {
		StringBuffer allMenuIds = new StringBuffer();
		  allMenuIds.append( String.valueOf(parentMenuGroupId) );
		  String currentMenuId = allMenuIds.toString();
		  while (currentMenuId != null ) {
			 //   currentMenuId = getMenuIdString(currentMenuId, languageCd);
			 //   if (currentMenuId != null) {
			//      allMenuIds.append("," + currentMenuId);
			//    }
		  }
		  return allMenuIds.toString();

	}

	
	


	public Menu getMenu(String menuId, String languageCd) {
		  if (menuId == null)
			   throw new NullPointerException("MenuId  is null");
		
		  Menu m = new Menu();
		  MenuId id = new MenuId();
		  id.setMenuId(menuId);
		  id.setLanguageCd(languageCd);
		  m.setId(id);
		  return navigatorDao.findById(id);

	}



	public List<Menu> menuGroup(String menuGroup, String langCd) {
		if (menuGroup == null)
			   throw new NullPointerException("menuGroup  is null");
		if (langCd == null) {
			   langCd = "en";
		}
		List<Menu> menuList = navigatorDao.findByMenuGroup(menuGroup, langCd);
		if (menuList == null  || menuList.isEmpty())
			return null;
		return menuList;
		
	}



	public List<Menu> menuGroupByUser(String menuGroupId, String userId, 	String languageCd) {
		if (menuGroupId == null)
			   throw new NullPointerException("menuGroupId  is null");
		if (languageCd == null) {
			languageCd = "en";
		}
		List<Menu> menuList = navigatorDao.findMenuByUser(menuGroupId, userId, languageCd);
		if (menuList == null  || menuList.isEmpty())
			return null;
		return menuList;
		
	}
	
	public List<Menu> menuGroupSelectedByUser(String menuGroupId, java.lang.String userId, String languageCd) {
		
		List<Menu> menuForUser = menuGroupByUser(menuGroupId, userId, languageCd);
		List<Menu> menusForGroup = this.menuGroup(menuGroupId, languageCd);
	
		log.debug("menuForUser=" + menuForUser);
		log.debug("menusForGroup=" + menusForGroup);
		
		if (menusForGroup == null) {
			return null;
		}
		
		// nothing to select - return the menu group for the list with nothing selected.
		if (menuForUser == null) {
			return menusForGroup;
		}
		
		// find which menus a user can access
		for (Menu m : menusForGroup) {
			int ctr = 0;
			boolean found = false;
			while (ctr < menuForUser.size() && !found) {
				if (m.getId().getMenuId().equals(menuForUser.get(ctr).getId().getMenuId())  ) {
					m.setSelected(true);
					found = true;
				}
				ctr++;
			}	
		}
		log.info("menusForGroup after processing=" + menusForGroup);
		return menusForGroup;
		
		
	}
	
	
	
	/*
	 * select DISTINCT m.*
from Menu m, USER_GRP ug, USER_ROLE ur, GRP_ROLE gr, PERMISSIONS P
WHERE m.MENU_GROUP = 'SELFCENTER' AND m.LANGUAGE_CD = 'en' AND 
			((ug.USER_ID = '3101' and ug.GRP_ID = gr.GRP_ID  AND gr.ROLE_ID = P.ROLE_ID ) or
			(ur.USER_ID = '3101' and ur.ROLE_ID = P.ROLE_ID and ur.SERVICE_ID = 'USR_SEC_DOMAIN')) and
			P.MENU_ID = m.MENU_ID
order by DISPLAY_ORDER;

	 * (non-Javadoc)
	 * @see org.openiam.idm.srvc.menu.service.NavigatorDataService#removeMenu(java.lang.String, boolean)
	 */



	public int removeMenu(String menuId, boolean deleteChildren) {
		  if (menuId == null)
			   throw new NullPointerException("MenuId  is null");
		
		  Menu m = new Menu();
		  MenuId id = new MenuId();
		  id.setMenuId(menuId);
		  m.setId(id);
		  return navigatorDao.removeByMenuId(menuId);
		  
	}

	public void updateMenu(Menu data) {
		  if (data == null)
			   throw new NullPointerException("Menu object is null");
		  if (data.getId() == null)
			  throw new NullPointerException("Menu id is null");
		  
		  navigatorDao.update(data);	 
		
	}

	public NavigatorDAO getNavigatorDao() {
		return navigatorDao;
	}

	public void setNavigatorDao(NavigatorDAO navigatorDao) {
		this.navigatorDao = navigatorDao;
	}






}