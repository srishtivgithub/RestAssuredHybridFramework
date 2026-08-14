package com.qa.gorest.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.pojo.UserPOJO;
import com.qa.gorest.utils.StringUtils;

public class ApiSchemaValidationTest extends BaseTest{

	/**
	 * This method has @BeforeMethod
	 * This method runs before every @Test
	 * This methos creates RestClient object for every @Test
	 * as Every test needs its own RestClient object 
	 */
	@BeforeMethod
	public void schemaSetUp() {
		rc=new RestClient(prop, baseurl);
	}
	
	@Test
	public void checkSechemaValidationSingleUser() {
		UserPOJO pojo=new UserPOJO("Sris", StringUtils.generateEmailId(), "female", "inactive" );
		rc.postRequest(GOREST_ENDPOINT, "json", pojo, true, true)
		  .then().log().all()
		    .assertThat()
		      .body(matchesJsonSchemaInClasspath("createUserSchema.json"));
	}
}
