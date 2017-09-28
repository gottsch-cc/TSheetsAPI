/**
 * 
 */
package com.compucom.serviceops.tsheetsapi.json.response;

import java.util.Map;

import com.compucom.serviceops.tsheetsapi.json.CustomFieldJson;

/**
 * @author Mark Gottschling on Oct 27, 2016
 *
 */
public interface ISupplementalData {
	public Map<String, CustomFieldJson> getCustomFields();
	public void setCustomFields(Map<String, CustomFieldJson> customFields);
}
