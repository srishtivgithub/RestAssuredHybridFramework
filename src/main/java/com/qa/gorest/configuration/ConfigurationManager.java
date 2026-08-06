package com.qa.gorest.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class ConfigurationManager {

	private Properties prop;
	FileInputStream fis;
	
	public Properties initProperties() throws IOException {
		prop=new Properties();
		fis=new FileInputStream("src/test/resources/config/config.properties");
		prop.load(fis);
		return prop;
	}
}
