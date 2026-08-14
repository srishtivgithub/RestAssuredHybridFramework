package com.qa.gorest.utils;

import java.util.List;

import com.jayway.jsonpath.JsonPath;

import io.restassured.response.Response;

public class JsonPathValidatorUtils {

	/**
	 * This method converts Json Response to String
	 * @param response
	 * @return
	 */
	public String getJsonResponseAsString(Response response) {
		String res=response.asString();
		return res;
	}
	
	/***
	 * This method help extract complex response parameter using Jayway Json
	 * instead of Object as return type -> use <T> T which is generic
	 * if value extracted is in integer, boolean etc->generic return type is to be used
	 * as object return type may cause error 
	 * @param response
	 * @param path
	 * @return 
	 */
	public <T> T readJson(Response response, String path) {
		String res=getJsonResponseAsString(response);
		return JsonPath.read(res, path);
	}
	
	/***
	 * This method help extract complex response list of parameter using Jayway Json
	 * instead of Object as return type -> use <T> List<T> which is generic
	 * if value extracted is in integer, boolean etc->generic return type is to be used
	 * as object return type may cause error 
	 * @param <T>
	 * @param response
	 * @param path
	 * @return
	 */
	public <T> List<T> readJsonList(Response response, String path) {
		String res=getJsonResponseAsString(response);
		return JsonPath.read(res, path);
	}
	
	
}
