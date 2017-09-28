/**
 * 
 */
package com.compucom.serviceops.tsheetsapi.json.response;

import java.util.Map;

import com.compucom.serviceops.tsheetsapi.json.GroupJson;
import com.compucom.serviceops.tsheetsapi.json.JobCodeJson;
import com.compucom.serviceops.tsheetsapi.json.UserJson;
import com.google.gson.annotations.SerializedName;

/**
 * 
 * @author Mark Gottschling on Nov 14, 2016
 *
 */
public class TimesheetsSupData extends AbstractSupData {
	@SerializedName("users") private Map<String, UserJson> users;
	@SerializedName("jobcodes") private Map<String, JobCodeJson> jobCodes;
	
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
	
	/**
	 * @return the jobCodes
	 */
	public Map<String, JobCodeJson> getJobCodes() {
		return jobCodes;
	}
	
	/**
	 * @param jobCodes the jobCodes to set
	 */
	public void setJobCodes(Map<String, JobCodeJson> jobCodes) {
		this.jobCodes = jobCodes;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TimesheetsSupData [users=" + users + ", jobCodes=" + jobCodes + ", getCustomFields()="
				+ getCustomFields() + "]";
	}

}
