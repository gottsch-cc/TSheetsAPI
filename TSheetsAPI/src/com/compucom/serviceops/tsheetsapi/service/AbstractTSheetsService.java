/**
 * 
 */
package com.compucom.serviceops.tsheetsapi.service;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.MalformedURLException;

import javax.net.ssl.HttpsURLConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.compucom.serviceops.tsheetsapi.io.ConnectionHelper;
import com.compucom.serviceops.tsheetsapi.json.TimesheetJson;
import com.compucom.serviceops.tsheetsapi.json.UserJson;
import com.compucom.serviceops.tsheetsapi.json.deserializer.TimesheetJsonDeserializer;
import com.compucom.serviceops.tsheetsapi.json.deserializer.UserJsonDeserializer;
import com.compucom.serviceops.tsheetsapi.json.response.IResults;
import com.compucom.serviceops.tsheetsapi.json.response.ISupplementalData;
import com.compucom.serviceops.tsheetsapi.json.response.ITSheetsResponse;
import com.compucom.serviceops.tsheetsapi.json.response.TSheetsResponse;
import com.compucom.serviceops.tsheetsapi.json.response.UsersResults;
import com.compucom.serviceops.tsheetsapi.json.response.UsersSupData;
import com.compucom.serviceops.tsheetsapi.model.Account;
import com.compucom.serviceops.tsheetsapi.model.Timesheet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * @author Mark Gottschling on Oct 28, 2016
 *
 */
@BaseUrl("https://rest.tsheets.com/api/v1/")
public class AbstractTSheetsService {
	private static final Logger logger = LogManager.getLogger(AbstractTSheetsService.class);
	
	public static final int MAX_PER_PAGE = 50;
	public static final String BASE_URL = "https://rest.tsheets.com/api/v1/";
	
	/**
	 * 
	 * @param account
	 * @param uri
	 * @param type
	 * @return
	 */
	public TSheetsResponse<? extends IResults, ? extends ISupplementalData> getResponse(Account account, String uri, Type type)
			throws MalformedURLException, IOException {
		TSheetsResponse<? extends IResults, ? extends ISupplementalData> sheetResponse = null;
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + account.getToken());
		// create a request with the headers
		HttpEntity<String> request = new HttpEntity<String>(headers);
		
		// set up the restful template and response
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = null;

		try {
			// send the request and get the response
			responseEntity = restTemplate.exchange(uri, HttpMethod.GET, request, String.class);
			// extract the json payload from the body
			String payload = responseEntity.getBody();
//			logger.debug("payload: " + payload);
			// setup gson
			final GsonBuilder builder = new GsonBuilder();
			Gson gson = builder
					.registerTypeAdapter(TimesheetJson.class, new TimesheetJsonDeserializer())
					.registerTypeAdapter(UserJson.class, new UserJsonDeserializer())
					.create();

			// convert payload to json
		    sheetResponse  = gson.fromJson(payload, type);

		}
		 catch(HttpStatusCodeException e) {
			 String errorpayload = e.getResponseBodyAsString();
			 logger.info("error payload:" + errorpayload);

			 }
		catch(RestClientException e){
			 //no response payload, tell the user sth else 
			 e.printStackTrace();
		 }
	    
	    return sheetResponse;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getBaseUrl() {
		Annotation a = this.getClass().getAnnotation(BaseUrl.class);
		if (a != null) return ((BaseUrl)a).value();
		return "";
	}
}
