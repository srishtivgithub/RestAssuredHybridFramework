package com.qa.gorest.tests;

import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.HttpStatusCodes.APIHttpStatusCode;

public class GetUserTest extends BaseTest {

	/**
	 * This method has @BeforeMethod
	 * This method runs before every @Test
	 * This methos creates RestClient object for every @Test
	 * as Every test needs its own RestClient object 
	 */
	@BeforeMethod
	public void getUserSetUp() {
		rc=new RestClient(prop, baseurl);
	}
	
	@Test(priority=1)
	public void getAllUserTest() {
	    
		rc.getRequest(GOREST_ENDPOINT, true, true)
		  .then().log().all()
		    .assertThat()
		     .statusCode(APIHttpStatusCode.OK_200.getCode());
		
	}
	
	@Test(priority=3)
	public void getSingleUserTest() {
		
		rc.getRequest(GOREST_ENDPOINT+8558139, true, true)
		  .then().log().all()
		    .assertThat()
		     .statusCode(APIHttpStatusCode.OK_200.getCode())
		       .and()
		         .body("id", equalTo(8558139));
		
	}
	
	@Test(priority=2)
	public void getUserWithQueryParam() {
		Map<String,String> queryParam=new HashMap<String,String>();
		queryParam.put("gender", "female");
		queryParam.put("status", "active");
		
		 
		 rc.getRequest(GOREST_ENDPOINT, true, null, queryParam, true)
		  .then().log().all()
		   .assertThat()
		     .statusCode(APIHttpStatusCode.OK_200.getCode());
		
	}
}
