package com.qa.gorest.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.HttpStatusCodes.APIHttpStatusCode;
import com.qa.gorest.utils.XmlPathValidatorUtils;

import io.restassured.response.Response;

public class getUserXmlGoRest extends BaseTest {

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
	
	@Test (priority=1)
	public void getALlUsersXMLTest()
	{
		Response response=rc.getRequest(GOREST_ENDPOINT_XML, true, true);
		
		response.prettyPrint();
		
		int statusCode=response.statusCode();
		
		Assert.assertEquals(statusCode, APIHttpStatusCode.OK_200.getCode());
		
		XmlPathValidatorUtils xmlPath=new XmlPathValidatorUtils();
		
		List<String> nameList=xmlPath.readXmlList(response, "objects.object.name");
		
		System.out.println("Name List is : "+nameList);
		
		Assert.assertTrue(nameList.contains("Srishti"));
		
	}
	
}
