package com.domain.codegenerator.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class PropertyUtil {

	private static Properties prop = null;

    private static final Logger logger = LoggerFactory.getLogger(PropertyUtil.class);

	public static String getProperties(String propStrs) {
		if (prop == null) {
			prop = new Properties();
			try {
				prop.load(PropertyUtil.class.getClassLoader().getResourceAsStream("configuration.properties"));
			} catch (Exception e) {
                logger.error("load config fail",e);
				throw new RuntimeException("config.properties load error:", e);
			}
            logger.info("load {} success", "config.properties");

		}
		return prop.getProperty(propStrs);
	}


	public static void main(String[] args) {
		System.out.println("cron.syncService:" + PropertyUtil.getProperties("datacenter.cache.redis"));
	}
	
}