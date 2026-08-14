package com.qa.gorest.utils;

import java.util.List;

import com.jayway.jsonpath.JsonPath;

import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class XmlPathValidatorUtils {

	/**
	 * This method converts XML Response to String
	 * @param response
	 * @return
	 */
	public String getXmlResponseAsString(Response response) {
		String res=response.asString();
		return res;
	}
	
	/***
	 * This method help extract XML response parameter using XmlPath
	 * XmlPath->is class provided by RestAssured library to extract data from XML Response
	 * Instead of Object as return type -> use <T> T which is generic
	 * If value extracted is in integer, boolean etc->generic return type is to be used
	 * as object return type may cause error 
	 * @param response
	 * @param path
	 * @return 
	 */
	public <T> T readXml(Response response, String path) {
		String res=getXmlResponseAsString(response);
		XmlPath xml=new XmlPath(res);
		return xml.get(path);
	}
	
	/***
	 * This method help extract XML response list of parameter using XmlPath
	 * XmlPath->is class provided by RestAssured library to extract data from XML Response
	 * Instead of Object as return type -> use <T> List<T> which is generic
	 * If value extracted is in integer, boolean etc->generic return type is to be used
	 * as object return type may cause error 
	 * @param <T>
	 * @param response
	 * @param path
	 * @return
	 */
	public <T> List<T> readXmlList(Response response, String path) {
		String res=getXmlResponseAsString(response);
		XmlPath xml=new XmlPath(res);
		return xml.getList(path);
	}
	
}
