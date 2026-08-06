package com.qa.gorest.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.HttpStatusCodes.APIHttpStatusCode;

public class AmadeusOAuthWorkflowTest extends BaseTest {
	
	String accessToken;

	/**
	 * This method has @BeforeMethod This method runs before every @Test This methos
	 * creates RestClient object for every @Test as Every test needs its own
	 * RestClient object
	 * This API needs extra 3 form parameters which is coming from testng.xml <parameter>
	 */
	

	@Parameters({ "grantType", "clientId", "clientSecret" })
	@BeforeMethod
	public void amedeousSetUp(String grantType, String clientId, String clientSecret) {
		rc = new RestClient(prop, baseurl);
		accessToken=rc.creatingOAuth2_Token(AMADEUS_TOKEN_ENDPOINT, grantType, clientId, clientSecret);
	}

	@Test
	public void getFlightDetails() {
		RestClient resClient2 = new RestClient(prop, baseurl);

		Map<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Authorization", "Bearer " + accessToken);

		Map<String, String> queryMap = new HashMap<String, String>();
		queryMap.put("airlineCode", "IB");

		resClient2.getRequest(AMADEUS_FLIGHT_BOOK_ENDPOINT, true, headerMap, queryMap, true)
		    .then().assertThat()
				.statusCode(APIHttpStatusCode.OK_200.getCode());
	}
}
