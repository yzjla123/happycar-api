package com.happycar.api.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ResourcesUtil {
	
	public static Properties readResources(String file) throws IOException{
		Properties p = new Properties();
		
		try {
			p.load(new FileInputStream(System.getProperty("user.dir")+File.separator+"resources"+File.separator+file));
		} catch (IOException e) {
			p.load(ResourcesUtil.class.getResourceAsStream("/"+file));
		}
		
		return p;
		
	}

}
