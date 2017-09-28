/**
 * 
 */
package com.compucom.serviceops.tsheetsapi;

import java.lang.reflect.Type;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.compucom.serviceops.tsheetsapi.json.response.ITSheetsResponse;
import com.compucom.serviceops.tsheetsapi.json.response.TSheetsResponse;
import com.compucom.serviceops.tsheetsapi.json.response.UsersResults;
import com.compucom.serviceops.tsheetsapi.json.response.UsersSupData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * @author Mark Gottschling on Oct 27, 2016
 *
 */
public class TestRest {
	private static final Logger logger = LogManager.getLogger(TestRest.class);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer S.3__9a25ef985e5185ccb7adc6c46468fbf08c812056");
		HttpEntity<String> request = new HttpEntity<String>(headers);
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = null;

		try {
			response = restTemplate.exchange(
				"https://rest.tsheets.com/api/v1/users?per_page=5&page=1",
				HttpMethod.GET, request, String.class);
//			logger.info("response: " + response);
			String s = response.getBody();
//	        logger.info("s: " + s.toString());
	        
			ITSheetsResponse<UsersResults, UsersSupData> sheetResponse;
			final GsonBuilder builder = new GsonBuilder();
			Gson gson = new Gson();
			Type collectionType = new TypeToken<TSheetsResponse<UsersResults, UsersSupData>>() {}.getType();
		    sheetResponse = gson.fromJson(s, collectionType);
			System.out.println(sheetResponse);
			String json = gson.toJson(sheetResponse);
			System.out.println(json);	        
		}
		 catch(HttpStatusCodeException e){
			 String errorpayload = e.getResponseBodyAsString();
			 logger.info("error payload:" + errorpayload);
			 // TODO read error payload into ErrorResponse
//			 Gson gson = new GsonBuilder().create();
//			 ErrorResponse error = gson.fromJson(errorpayload, ErrorResponse.class);
//			 logger.info("error obj:" + error);
			 //to whatever you want
			 } catch(RestClientException e){
			 //no response payload, tell the user sth else 
				 e.printStackTrace();
			 }
	}

}
