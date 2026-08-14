package com.qa.gorest.tests;

import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.HttpStatusCodes.APIHttpStatusCode;
import com.qa.gorest.utils.JsonPathValidatorUtils;

import io.restassured.response.Response;
import junit.framework.Assert;

public class GetProductFakerStoreApiTest extends BaseTest{
	
	@BeforeClass
	public void getProductSetUp() {
		rc=new RestClient(prop, baseurl);
	}

	@Test
	public void getProducts() {
		Response response=rc.getRequest(FAKERSTORE_ENDPOINT, true, false);
		int statusCode=response.statusCode();
		Assert.assertEquals(statusCode, APIHttpStatusCode.OK_200.getCode());
		
		//using JsonPathValidatorUtils for complex json extraction
		JsonPathValidatorUtils jsonPV=new JsonPathValidatorUtils();
		List<Float> rateList=jsonPV.readJsonList(response,"$..[?(@.rate>3)].rate");
		System.out.println(rateList);
		Assert.assertTrue(rateList.contains(4.8));
		
		
	}
}
