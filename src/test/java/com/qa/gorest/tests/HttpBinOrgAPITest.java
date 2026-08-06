package com.qa.gorest.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.HttpStatusCodes.APIHttpStatusCode;

public class HttpBinOrgAPITest extends BaseTest {

	/**
	 * This method has @BeforeMethod
	 * This method runs before every @Test
	 * This methos creates RestClient object for every @Test
	 * as Every test needs its own RestClient object 
	 */
	@BeforeMethod
	public void httpOrgzApiSetUp() {
		rc=new RestClient(prop, baseurl);
	}
	
	@Test
	public void getAllDataFromHttpBinApi()
	{
		
		rc.getRequest(HTTPBIN_ENDPOINT, true, false)
		   .then().log().all()
		      .assertThat()
		        .statusCode(APIHttpStatusCode.OK_200.getCode());
		    
	}
}
