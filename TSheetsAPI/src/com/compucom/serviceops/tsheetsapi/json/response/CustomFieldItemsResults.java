/**
 * 
 */
package com.compucom.serviceops.tsheetsapi.json.response;

import java.util.Map;

import com.compucom.serviceops.tsheetsapi.json.CustomFieldItemJson;
import com.google.gson.annotations.SerializedName;

/**
 * NOTE will have to make a concrete xxxResults class for every type of data.
 * @author Mark Gottschling on Oct 27, 2016
 *
 */
public class CustomFieldItemsResults implements IResults {
	@SerializedName("customfielditems") private Map<String, CustomFieldItemJson> items;

	/**
	 * 
	 */
	public int size() {
		if (getItems() == null) return 0;
		return getItems().size();
	}

	/**
	 * @return the items
	 */
	public Map<String, CustomFieldItemJson> getItems() {
		return items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems(Map<String, CustomFieldItemJson> items) {
		this.items = items;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CustomFieldItemsResults [items=" + items + "]";
	}
		
}
