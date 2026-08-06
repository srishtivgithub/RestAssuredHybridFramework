package com.qa.gorest.base;

import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.qa.gorest.client.RestClient;
import com.qa.gorest.configuration.ConfigurationManager;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;

public class BaseTest {
	// Keeping Endpoints for the Framework :
	
		public final static String GOREST_ENDPOINT="/public/v2/users/";
		public final static String HTTPBIN_ENDPOINT="/get";
		public final static String AMADEUS_TOKEN_ENDPOINT="/v1/security/oauth2/token";
		public final static String AMADEUS_FLIGHT_BOOK_ENDPOINT="/v2/reference-data/urls/checkin-links";

	public ConfigurationManager cm;
	public Properties prop;
	public RestClient rc;
	//define here so that this can be passed to every @BeforeMethod present in every Testclasses
	public String baseurl;
	
	@Parameters({"baseURI"})
	@BeforeTest
	public void setUp(String baseURI) throws IOException {
		//for reporting
		RestAssured.filters(new AllureRestAssured());
		cm=new ConfigurationManager();
		prop=cm.initProperties();
		//initialize url from @paramters to class variable baseurl
		this.baseurl=baseURI;
		
		//String baseUri=prop.getProperty("baseUri");
		//rc=new RestClient(prop, baseurl);
	}
}
