/**
 * 
 */
package com.compucom.serviceops.tsheetsapi.json.response;

import java.util.Map;

import com.compucom.serviceops.tsheetsapi.json.TimesheetJson;
import com.google.gson.annotations.SerializedName;

/**
 * 
 * @author Mark Gottschling on Nov 14, 2016
 *
 */
public class TimesheetsResults implements IResults {
	@SerializedName("timesheets") private Map<String, TimesheetJson> timesheets;

	/**
	 * 
	 */
	public int size() {
		if (getTimesheets() == null) return 0;
		return getTimesheets().size();
	}
	
	/**
	 * @return the timesheets
	 */
	public Map<String, TimesheetJson> getTimesheets() {
		return timesheets;
	}

	/**
	 * @param timesheets the timesheets to set
	 */
	public void setTimesheets(Map<String, TimesheetJson> timesheets) {
		this.timesheets = timesheets;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TimesheetsResults [timesheets=" + timesheets + "]";
	}


	
}
