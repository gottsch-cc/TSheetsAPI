/**
 * 
 */
package com.compucom.serviceops.tsheetsapi.model;

import org.springframework.stereotype.Component;


/**
 * @author Mark Gottschling on Oct 28, 2016
 *
 */
@Component
public class Account {
	private String clientUrl;
	private String username;
	private String token;

	/**
	 * 
	 */
	public Account() {
		
	}
	
	/**
	 * 
	 * @param username
	 * @param token
	 */
	public Account(String clientUrl, String username, String token) {
		// NOTE where does the token come from, and is it encrypted - no it is not at this point
		// it is previously checked in the service or config to see if the hashes match.
		setClientUrl(clientUrl);
		setUsername(username);
		setToken(token);
	}

	/**
	 * @return the clientUrl
	 */
	public String getClientUrl() {
		return clientUrl;
	}

	/**
	 * @param clientUrl the clientUrl to set
	 */
	public void setClientUrl(String clientUrl) {
		this.clientUrl = clientUrl;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Account [clientUrl=" + clientUrl + ", username=" + username + ", token=" + token + "]";
	}

}
