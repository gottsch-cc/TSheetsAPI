/**
 * 
 */
package com.compucom.serviceops.tsheetsapi.json.response;

import java.util.HashMap;
import java.util.Map;

import com.compucom.serviceops.tsheetsapi.json.CustomFieldJson;
import com.google.gson.annotations.SerializedName;

/**
 * @author Mark Gottschling on Dec 5, 2016
 *
 */
public abstract class AbstractSupData implements ISupplementalData {
	@SerializedName("customfields") private Map<String, CustomFieldJson> customFields;

	/* (non-Javadoc)
	 * @see com.compucom.serviceops.tsheetsapi.json.response.ISupplementalData#getCustomFields()
	 */
	@Override
	public Map<String, CustomFieldJson> getCustomFields() {
		if (customFields == null) {
			customFields = new HashMap<String, CustomFieldJson>();
		}
		return customFields;
	}
	
	/* (non-Javadoc)
	 * @see com.compucom.serviceops.tsheetsapi.json.response.ISupplementalData#setCustomFields(java.util.Map)
	 */
	@Override
	public void setCustomFields(Map<String, CustomFieldJson> customFields) {
		this.customFields = customFields;
	}
}
