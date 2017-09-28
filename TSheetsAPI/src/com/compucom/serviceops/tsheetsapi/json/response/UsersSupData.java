/**
 * 
 */
package com.compucom.serviceops.tsheetsapi.json.response;

import java.util.Map;

import com.compucom.serviceops.tsheetsapi.json.GroupJson;
import com.google.gson.annotations.SerializedName;

/**
 * @author Mark Gottschling on Oct 27, 2016
 *
 */
public class UsersSupData extends AbstractSupData {
	@SerializedName("groups") private Map<String, GroupJson> groups;

	/**
	 * @return the groups
	 */
	public Map<String, GroupJson> getGroups() {
		return groups;
	}

	/**
	 * @param groups the groups to set
	 */
	public void setGroups(Map<String, GroupJson> groups) {
		this.groups = groups;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UsersSupData [groups=" + groups + ", getCustomFields()=" + getCustomFields() + "]";
	}
}
