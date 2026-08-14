package com.qa.gorest.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.qa.gorest.frameworkExceptions.APIFrameworkExceptions;


public class ConfigurationManager {

	private Properties prop;
	FileInputStream fis;
	
	public Properties initProperties() throws IOException {
		prop=new Properties();
		
		/*******fetching env name from System:maven command from jenkins, cmd,
		 * cmd: mvn clean instal -Denv="uat"   ****/
		
		String envName=System.getProperty("env");
		
		if(envName==null) {
			System.out.println("Since env name is not passed running the TCs in default env.");
			fis=new FileInputStream("src/test/resources/config/config.properties");
		}
		else {
			switch(envName.toLowerCase().trim()) {
			case "uat" : System.out.println("Running the test cases in environment:" +envName);
							fis=new FileInputStream("src/test/resources/config/config_uat.properties");
							break;
							
			case "dev" : System.out.println("Running the test cases in environment:" +envName);
							fis=new FileInputStream("src/test/resources/config/config_dev.properties");
							break;
							
			case "stage" : System.out.println("Running the test cases in environment:" +envName);
							fis=new FileInputStream("src/test/resources/config/config_stage.properties");
							break;
							
			default :   		System.out.println("Environment is not supported : "+envName);
								throw new APIFrameworkExceptions("===Incorrect Environment===");
							
			}
		}
	
		prop.load(fis);
		return prop;
		
		
	}
}
