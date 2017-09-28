/**
 * 
 */
package com.compucom.serviceops.tsheetsapi.json;

import java.util.Arrays;

import com.google.gson.annotations.SerializedName;

/**
 * @author Mark Gottschling on Dec 5, 2016
 *
 */
public class CustomFieldJson {
	private int id;
	private boolean active;
	private String name;
	@SerializedName("short_code") private String shortCode;
	private boolean required;
	@SerializedName("applies_to") private String appliesTo;
	private String type;
	@SerializedName("ui_preference") private String uiPref;
	@SerializedName("regex_filter") private String regexFilter;
	private String created;
	@SerializedName("last_modified") private String lastModified;
	@SerializedName("required_customfields") private Integer[] requiredCustomFields;
	
	/**
	 * 
	 */
	public CustomFieldJson() {}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the shortCode
	 */
	public String getShortCode() {
		return shortCode;
	}

	/**
	 * @param shortCode the shortCode to set
	 */
	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}

	/**
	 * @return the required
	 */
	public boolean isRequired() {
		return required;
	}

	/**
	 * @param required the required to set
	 */
	public void setRequired(boolean required) {
		this.required = required;
	}

	/**
	 * @return the appliesTo
	 */
	public String getAppliesTo() {
		return appliesTo;
	}

	/**
	 * @param appliesTo the appliesTo to set
	 */
	public void setAppliesTo(String appliesTo) {
		this.appliesTo = appliesTo;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the uiPref
	 */
	public String getUiPref() {
		return uiPref;
	}

	/**
	 * @param uiPref the uiPref to set
	 */
	public void setUiPref(String uiPref) {
		this.uiPref = uiPref;
	}

	/**
	 * @return the regexFilter
	 */
	public String getRegexFilter() {
		return regexFilter;
	}

	/**
	 * @param regexFilter the regexFilter to set
	 */
	public void setRegexFilter(String regexFilter) {
		this.regexFilter = regexFilter;
	}

	/**
	 * @return the created
	 */
	public String getCreated() {
		return created;
	}

	/**
	 * @param created the created to set
	 */
	public void setCreated(String created) {
		this.created = created;
	}

	/**
	 * @return the lastModified
	 */
	public String getLastModified() {
		return lastModified;
	}

	/**
	 * @param lastModified the lastModified to set
	 */
	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}

	/**
	 * @return the requiredCustomFields
	 */
	public Integer[] getRequiredCustomFields() {
		return requiredCustomFields;
	}

	/**
	 * @param requiredCustomFields the requiredCustomFields to set
	 */
	public void setRequiredCustomFields(Integer[] requiredCustomFields) {
		this.requiredCustomFields = requiredCustomFields;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CustomFieldJson [id=" + id + ", active=" + active + ", name=" + name + ", shortCode=" + shortCode
				+ ", required=" + required + ", appliesTo=" + appliesTo + ", type=" + type + ", uiPref=" + uiPref
				+ ", regexFilter=" + regexFilter + ", created=" + created + ", lastModified=" + lastModified
				+ ", requiredCustomFields=" + Arrays.toString(requiredCustomFields) + "]";
	}
	
}
