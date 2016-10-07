package com.talentrecd.util;

import java.util.ResourceBundle;

public class SystemUtil {
	
	public static final String APPLICATION_FILE = "env";
	
	/**
	 * 取得系统配置信息
	 * @param configItem	配置�?
	 * @return
	 */
	public static String getAppConfig(String configItem){
		
		ResourceBundle resourceBundle =
		      ResourceBundle.getBundle(APPLICATION_FILE);

		return resourceBundle.getString(configItem);
	}

}
