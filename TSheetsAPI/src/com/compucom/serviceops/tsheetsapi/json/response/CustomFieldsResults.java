/**
 * 
 */
package com.compucom.serviceops.tsheetsapi.json.response;

import java.util.Map;

import com.compucom.serviceops.tsheetsapi.json.CustomFieldItemJson;
import com.compucom.serviceops.tsheetsapi.json.CustomFieldJson;
import com.google.gson.annotations.SerializedName;

/**
 * 
 * @author Mark Gottschling on Dec 5, 2016
 *
 */
public class CustomFieldsResults implements IResults {
	@SerializedName("customfields") private Map<String, CustomFieldJson> customFields;

	/**
	 * 
	 */
	public int size() {
		if (getCustomFields() == null) return 0;
		return getCustomFields().size();
	}

	/**
	 * @return the customFields
	 */
	public Map<String, CustomFieldJson> getCustomFields() {
		return customFields;
	}

	/**
	 * @param customFields the customFields to set
	 */
	public void setCustomFields(Map<String, CustomFieldJson> customFields) {
		this.customFields = customFields;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CustomFieldsResults [customFields=" + customFields + "]";
	}


		
}
