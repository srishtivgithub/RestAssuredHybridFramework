package com.qa.gorest.client;

import java.util.Map;
import java.util.Properties;

import com.qa.gorest.frameworkExceptions.APIFrameworkExceptions;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestClient {


	private static RequestSpecBuilder specBuilder;

	
	private Properties prop;
	private String baseUri;
	
	
	
	/**
	 * This is parameterized Contructor and called when @BeforeTest runs in
	 * BaseTest in setUp()
	 * @param prop
	 * @param baseUri
	 */
	public RestClient(Properties prop,String baseUri) {
		specBuilder=new RequestSpecBuilder();
		this.prop=prop;
		this.baseUri=baseUri;
	}

	/****************** Common Reusable Methods **********************/
	
	/**
	 * as Auth was getting added for other @Test thus used isAuthorizationAdded=false
	 */
	private boolean isAuthorizationAdded=false;
	public void addAuthorization() {
		//1st time       =>if(!false)=>if(true)=>if condition runs
		//2nd to nth time=>if(!true)=>if(false)=>if condition doesn't run
		if(!isAuthorizationAdded) {
			specBuilder.addHeader("Authorization", "Bearer " + prop.getProperty("tokenId"));
			//made true
			isAuthorizationAdded=true;
		}
		
	}

	/*
	 * to set content type->takes input as contenttype->created private as dont want
	 * to show this to user
	 */
	private void setContentType(String contentType) {
		System.out.println(contentType);

		switch (contentType.toLowerCase().trim()) {
		case "json":
			specBuilder.setContentType(ContentType.JSON);
			break;
		case "xml":
			specBuilder.setContentType(ContentType.XML);
			break;
		case "multipart":
			specBuilder.setContentType(ContentType.MULTIPART);
			break;

		case "text":
			specBuilder.setContentType(ContentType.TEXT);
			break;

		case "html":
			specBuilder.setContentType(ContentType.HTML);
			break;

		case "default":
			throw new APIFrameworkExceptions("<===Content type NOT SUPPORTED");
		}
	}

	/*************** Request Specification Methods ***************/

	/*
	 * This method is only for creating a basic Request Spec with baseUri and token
	 */

	public RequestSpecification createRequestSpec(boolean includeAuth) {
		specBuilder.setBaseUri(baseUri);
		if(includeAuth) {
			addAuthorization();
		}
		
		return specBuilder.build();
	}
	// overloaded method of createRequestSpec()

	/*
	 * This method is only for creating a basic Request Spec with baseUri, token and
	 * headerMap
	 */
	public RequestSpecification createRequestSpec(Map<String, String> headersMap,
			boolean includeAuth) {
		specBuilder.setBaseUri(baseUri);
		if(includeAuth) {
			addAuthorization();
		}

		if (headersMap != null) {
			specBuilder.addHeaders(headersMap);
		}
		return specBuilder.build();
	}

	/*
	 * This method is only for creating a basic Request Spec with baseUri, token,
	 * headerMap and querparamMap
	 */
	public RequestSpecification createRequestSpec(Map<String, String> headersMap, 
			Map<String, String> queryMap, boolean includeAuth) {
		specBuilder.setBaseUri(baseUri);
		if(includeAuth) {
			addAuthorization();
		}

		if (headersMap != null) {
			specBuilder.addHeaders(headersMap);
		}
		if (queryMap != null) {
			specBuilder.addQueryParams(queryMap);
		}
		return specBuilder.build();
	}

	/*
	 * This method is to create spec request builder with requestbody, contenttype
	 * for calls: where we send request body eg:POST
	 * 
	 * Request Body set as Object as it can be in any format
	 */

	public RequestSpecification createRequestSpec(Object requestBody, String contentType,
			boolean includeAuth) {
		specBuilder.setBaseUri(baseUri);
		if(includeAuth) {
			addAuthorization();
		}
		setContentType(contentType);

		if (requestBody != null) {
			specBuilder.setBody(requestBody);
		}
		return specBuilder.build();
	}

	/*
	 * This method is to create spec request builder with requestbody, contenttype
	 * and headersMap for calls: where we send request body eg:POST
	 * 
	 * Request Body set as Object as it can be in any format
	 */
	public RequestSpecification createRequestSpec(Object requestBody, String contentType,
			Map<String, String> headersMap, boolean includeAuth) {
		specBuilder.setBaseUri(baseUri);
		if(includeAuth) {
			addAuthorization();
		}
		setContentType(contentType);

		if (headersMap != null) {
			specBuilder.addHeaders(headersMap);
		}
		if (requestBody != null) {
			specBuilder.setBody(requestBody);
		}
		return specBuilder.build();
	}

	/*
	 * This method is to create spec request builder with requestbody, contenttype,
	 * queryMap and headersMap for calls: where we send request body eg:POST
	 * 
	 * Request Body set as Object as it can be in any format
	 */
	public RequestSpecification createRequestSpec(Object requestBody, String contentType, Map<String, String> headersMap,
			Map<String, String> queryMap, boolean includeAuth) {
		specBuilder.setBaseUri(baseUri);
		if(includeAuth) {
			addAuthorization();
		}
		setContentType(contentType);

		if (queryMap != null) {
			specBuilder.addQueryParams(queryMap);
		}
		if (headersMap != null) {
			specBuilder.addHeaders(headersMap);
		}
		if (requestBody != null) {
			specBuilder.setBody(requestBody);
		}
		return specBuilder.build();
	}

	/*******************HTTP GET Methods*******************/

	/**
	 * This method hits get request
	 * This method create request with baseUri, and log=true, request with log
	 * serviceUrl=endpoint
	 * @param serviceUrl
	 * @param log
	 * @return
	 */
	
	public Response getRequest(String serviceUrl, boolean log, boolean includeAuth) {

		if (log) {
			Response response = RestAssured.given().log().all()
					.spec(createRequestSpec(includeAuth))
					  .when()
					    .get(serviceUrl);

			return response;
		}

		else {
			Response response = RestAssured.given()
					.spec(createRequestSpec(includeAuth))
					  .when()
					    .get(serviceUrl);

			return response;
		}
	}
	
	/**
	 * This method hits get request
	 * @param serviceUrl
	 * @param log
	 * @param headersMap
	 * @return
	 */
	public Response getRequest(String serviceUrl, boolean log, Map<String,String> headersMap
			, boolean includeAuth) {

		if (log) {
			Response response = RestAssured.given().log().all()
					.spec(createRequestSpec(headersMap, includeAuth))
					  .when()
					    .get(serviceUrl);

			return response;
		}

		else {
			Response response = RestAssured.given()
					.spec(createRequestSpec(headersMap, includeAuth))
					  .when()
					    .get(serviceUrl);

			return response;
		}
	}
	
	/**
	 * This method hits get request
	 * @param serviceUrl
	 * @param log
	 * @param headersMap
	 * @param queryMap
	 * @return
	 */
	public Response getRequest(String serviceUrl, boolean log, Map<String,String> headersMap, 
			Map<String,String> queryMap, boolean includeAuth) {

		if (log) {
			Response response = RestAssured.given().log().all()
					.spec(createRequestSpec(headersMap, queryMap, includeAuth))
					  .when()
					    .get(serviceUrl);

			return response;
		}

		else {
			Response response = RestAssured.given()
					.spec(createRequestSpec(headersMap, queryMap, includeAuth))
					  .when()
					    .get(serviceUrl);

			return response;
		}
	}
	
	/*******************HTTP POST Methods *******************/
	
	
	/**
	 * This method hit post request
	 * @param serviceUrl
	 * @param contentType
	 * @param requestBody
	 * @param log
	 * @return
	 */
	public Response postRequest(String serviceUrl, String contentType, Object requestBody,
			boolean log, boolean includeAuth) {
		
		if(log) {
			Response response=RestAssured.given().log().all()
			  .spec(createRequestSpec(requestBody, contentType, includeAuth))
			    .when()
			      .post(serviceUrl);
			
			return response;
			
		}
		
		else {
			Response response=RestAssured.given()
					  .spec(createRequestSpec(requestBody, contentType, includeAuth))
					    .when()
					      .post(serviceUrl);
					
					return response;

		}
	}

	/**
	 * This method hit post request
	 * @param serviceUrl
	 * @param contentType
	 * @param requestBody
	 * @param log
	 * @param headersMap
	 * @return
	 */
    public Response postRequest(String serviceUrl, String contentType, Object requestBody, 
    		boolean log, Map<String,String> headersMap, boolean includeAuth) {
		
		if(log) {
			Response response=RestAssured.given().log().all()
			  .spec(createRequestSpec(requestBody, contentType, headersMap, includeAuth))
			    .when()
			      .post(serviceUrl);
			
			return response;
			
		}
		
		else {
			Response response=RestAssured.given()
					  .spec(createRequestSpec(requestBody, contentType, headersMap, includeAuth))
					    .when()
					      .post(serviceUrl);
					
					return response;

		}
	}
    
    /**
     * This method hit post request
     * @param serviceUrl
     * @param contentType
     * @param requestBody
     * @param log
     * @param headersMap
     * @param queryMap
     * @return
     */
  public Response postRequest(String serviceUrl, String contentType, Object requestBody, 
		  boolean log, Map<String,String> headersMap,Map<String,String> queryMap, boolean includeAuth) {
		
		if(log) {
			Response response=RestAssured.given().log().all()
			  .spec(createRequestSpec(requestBody, contentType, headersMap, queryMap, includeAuth))
			    .when()
			      .post(serviceUrl);
			
			return response;
			
		}
		
		else {
			Response response=RestAssured.given()
					  .spec(createRequestSpec(requestBody, contentType, headersMap, queryMap, includeAuth))
					    .when()
					      .post(serviceUrl);
					
					return response;

		}
	}
  
  /*******************HTTP PUT Methods *******************/
  
  /**
	 * This method hit put request
	 * @param serviceUrl
	 * @param contentType
	 * @param requestBody
	 * @param log
	 * @return
	 */
	public Response putRequest(String serviceUrl, String contentType, Object requestBody, boolean log
			, boolean includeAuth) {
		
		if(log) {
			Response response=RestAssured.given().log().all()
			  .spec(createRequestSpec(requestBody, contentType, includeAuth))
			    .when()
			      .put(serviceUrl);
			
			return response;
			
		}
		
		else {
			Response response=RestAssured.given()
					  .spec(createRequestSpec(requestBody, contentType, includeAuth))
					    .when()
					      .put(serviceUrl);
					
					return response;

		}
	}

	/**
	 * This method hit put request
	 * @param serviceUrl
	 * @param contentType
	 * @param requestBody
	 * @param log
	 * @param headersMap
	 * @return
	 */
  public Response putRequest(String serviceUrl, String contentType, Object requestBody, boolean log, 
		  Map<String,String> headersMap, boolean includeAuth) {
		
		if(log) {
			Response response=RestAssured.given().log().all()
			  .spec(createRequestSpec(requestBody, contentType, headersMap, includeAuth))
			    .when()
			      .put(serviceUrl);
			
			return response;
			
		}
		
		else {
			Response response=RestAssured.given()
					  .spec(createRequestSpec(requestBody, contentType, headersMap, includeAuth))
					    .when()
					      .put(serviceUrl);
					
					return response;

		}
	}
  
  /**
   * This method hit put request
   * @param serviceUrl
   * @param contentType
   * @param requestBody
   * @param log
   * @param headersMap
   * @param queryMap
   * @return
   */
  public Response putRequest(String serviceUrl, String contentType, Object requestBody, boolean log, Map<String,String> headersMap,
		  Map<String,String> queryMap, boolean includeAuth) {
		
		if(log) {
			Response response=RestAssured.given().log().all()
			  .spec(createRequestSpec(requestBody, contentType, headersMap, queryMap, includeAuth))
			    .when()
			      .put(serviceUrl);
			
			return response;
			
		}
		
		else {
			Response response=RestAssured.given()
					  .spec(createRequestSpec(requestBody, contentType, headersMap, queryMap, includeAuth))
					    .when()
					      .put(serviceUrl);
					
					return response;

		}
	}
  
  /*******************HTTP PATCH Methods *******************/
  
  /**
	 * This method hit patch request
	 * @param serviceUrl
	 * @param contentType
	 * @param requestBody
	 * @param log
	 * @return
	 */
	public Response patchRequest(String serviceUrl, String contentType, Object requestBody, 
			boolean log, boolean includeAuth) {
		
		if(log) {
			Response response=RestAssured.given().log().all()
			  .spec(createRequestSpec(requestBody, contentType, includeAuth))
			    .when()
			      .patch(serviceUrl);
			
			return response;
			
		}
		
		else {
			Response response=RestAssured.given()
					  .spec(createRequestSpec(requestBody, contentType, includeAuth))
					    .when()
					      .patch(serviceUrl);
					
					return response;

		}
	}

	/**
	 * This method hit patch request
	 * @param serviceUrl
	 * @param contentType
	 * @param requestBody
	 * @param log
	 * @param headersMap
	 * @return
	 */
   public Response patchRequest(String serviceUrl, String contentType, Object requestBody, 
		   boolean log, Map<String,String> headersMap, boolean includeAuth) {
		
		if(log) {
			Response response=RestAssured.given().log().all()
			  .spec(createRequestSpec(requestBody, contentType, headersMap, includeAuth))
			    .when()
			      .patch(serviceUrl);
			
			return response;
			
		}
		
		else {
			Response response=RestAssured.given()
					  .spec(createRequestSpec(requestBody, contentType, headersMap,includeAuth))
					    .when()
					      .patch(serviceUrl);
					
					return response;

		}
	}

	/**
	 	* This method hit patch request
	 	* @param serviceUrl
	 	* @param contentType
	 	* @param requestBody
	 	* @param log
	 	* @param headersMap
	 	* @param queryMap
	 	* @return
	 	*/
  public Response patchRequest(String serviceUrl, String contentType, Object requestBody, boolean log, Map<String,String> headersMap,
		  Map<String,String> queryMap, boolean includeAuth) {
		
		if(log) {
			Response response=RestAssured.given().log().all()
			  .spec(createRequestSpec(requestBody, contentType, headersMap, queryMap, includeAuth))
			    .when()
			      .patch(serviceUrl);
			
			return response;
			
		}
		
		else {
			Response response=RestAssured.given()
					  .spec(createRequestSpec(requestBody, contentType, headersMap, queryMap, includeAuth))
					    .when()
					      .patch(serviceUrl);
					
					return response;

		}
	}
  
  /*******************HTTP DELETE Methods *******************/
  
  /**
	 * This method hits delete request
	 * This method create request with baseUri, and log=true, request with log
	 * erviceUrl=endpoint
	 * @param serviceUrl
	 * @param log
	 * @return
	 */
	
	public Response deleteRequest(String serviceUrl, boolean log, boolean includeAuth) {

		if (log) {
			Response response = RestAssured.given().log().all()
					.spec(createRequestSpec(includeAuth))
					  .when()
					    .delete(serviceUrl);

			return response;
		}

		else {
			Response response = RestAssured.given()
					.spec(createRequestSpec(includeAuth))
					  .when()
					    .delete(serviceUrl);

			return response;
		}
	}
	
	/*****************Common Method for OAuth2.0 Workflow *****************/
	
	public String creatingOAuth2_Token(String serviceUrl, String grantType, String clientId, String clientSecret)
	{
		//RestAssured.baseURI="https://test.api.amadeus.com";
		
		// 1. Post call for fetching the access token:
		
		String accessToken=RestAssured.given().log().all()
		  .contentType(ContentType.URLENC)
		     .formParam("grant_type", grantType)
		       .formParam("client_id",clientId)
		         .formParam("client_secret",clientSecret)
		            .when()
		               .post(serviceUrl)
		                 .then().log().all()
		                    .assertThat()
		                      .statusCode(200)
		                        .extract().path("access_token");
		
		System.out.println("Access Token is : "+accessToken);
		return accessToken;
	
}
	
	

}
