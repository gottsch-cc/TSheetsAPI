/**
 * 
 */
package com.compucom.serviceops.tsheetsapi.io;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMethod;

import com.compucom.serviceops.tsheetsapi.model.Account;


/**
 * 
 * @author Mark Gottschling on Oct 28, 2016
 *
 */
public class ConnectionHelper {

	/**
	 * 
	 * @param account
	 * @param uri
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static HttpsURLConnection getConnection(Account account, String uri, HttpMethod method)
			throws MalformedURLException, IOException {
		
		URL url = new URL(uri);
		HttpsURLConnection connection;

		connection = (HttpsURLConnection) url.openConnection();
		connection.setRequestMethod(method.name());
		connection.setRequestProperty("Authorization", "Bearer " + account.getToken());
		connection.setRequestProperty("User-Agent", "Mozilla/5.0");
		return connection;
	}
}
