/**
 * 
 */
package com.compucom.serviceops.tsheetsapi.json.response;

import java.util.Map;

import com.compucom.serviceops.tsheetsapi.json.UserJson;
import com.google.gson.annotations.SerializedName;

/**
 * NOTE will have to make a concrete xxxResults class for every type of data.
 * @author Mark Gottschling on Oct 27, 2016
 *
 */
public class UsersResults implements IResults {
	@SerializedName("users") private Map<String, UserJson> users;

	/**
	 * 
	 */
	public int size() {
		if (getUsers() == null) return 0;
		return getUsers().size();
	}
	
	/**
	 * @return the users
	 */
	public Map<String, UserJson> getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(Map<String, UserJson> users) {
		this.users = users;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UsersResults [users=" + users + "]";
	}
	
}
