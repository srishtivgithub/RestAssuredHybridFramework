package com.qa.gorest.tests;

import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.HttpStatusCodes.APIHttpStatusCode;
import com.qa.gorest.pojo.UserPOJO;
import com.qa.gorest.utils.ExcelUtils;
import com.qa.gorest.utils.StringUtils;
public class CreateUserTest extends BaseTest {

	/**
	 * This method has @BeforeMethod
	 * This method runs before every @Test
	 * This methos creates RestClient object for every @Test
	 * as Every test needs its own RestClient object 
	 */
	@BeforeMethod
	public void createUserSetUp() {
		rc=new RestClient(prop, baseurl);
	}
	@DataProvider
	public Object[][] getData() {
		Object[][] obj= {{"Srishti","female","active"}
		               ,{"Tom","male","inactive"}
		               ,{"Sara","female","active"}};
		
		return obj;
	}
	
	/**
	 * fetching data from excel via @dataprovider
	 * @return
	 */
	@DataProvider
	public Object[][] getDataFromSheet()
	{
		Object obj[][]=ExcelUtils.getTestDataFromSheet("Users");
		return obj;
	}
	
	/**
	 * passing testdata from @dataprovider
	 * @param name
	 * @param gender
	 * @param status
	 */
	@Test(dataProvider = "getData")
	public void createUserTest(String name, String gender, String status) {
		UserPOJO pojo=new UserPOJO(name, StringUtils.generateEmailId(),gender, status);
		
		
		int userId=rc.postRequest(GOREST_ENDPOINT, "json", pojo, true, true)
		 .then().log().all()
		   .assertThat()
		     .statusCode(APIHttpStatusCode.CREATED_201.getCode())
		      .extract()
		       .path("id");
		
		System.out.println("User ID:"+userId);
		
		//Fetching the created user
		
		rc.getRequest(GOREST_ENDPOINT+userId, true, true)
		  .then().log().all()
		    .assertThat()
		      .statusCode(APIHttpStatusCode.OK_200.getCode())
		        .and()
		          .body("id", equalTo(userId))
		            .and()
		              .assertThat()
		                .body("name", equalTo(pojo.getName()));
		 
		  
	}
}
