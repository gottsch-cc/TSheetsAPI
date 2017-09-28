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
public class CustomFieldItemJson {
	private int id;
	@SerializedName("customfield_id") private int customFieldId;
	private String name;
	private boolean active;

	@SerializedName("short_code") private String shortCode;
	
	private String created;
	@SerializedName("last_modified") private String lastModified;
	@SerializedName("required_customfields") private Integer[] requiredCustomFields;
	
	/**
	 * 
	 */
	public CustomFieldItemJson() {}

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
	 * @return the customFieldId
	 */
	public int getCustomFieldId() {
		return customFieldId;
	}

	/**
	 * @param customFieldId the customFieldId to set
	 */
	public void setCustomFieldId(int customFieldId) {
		this.customFieldId = customFieldId;
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
		return "CustomFieldItemJson [id=" + id + ", customFieldId=" + customFieldId + ", name=" + name + ", active="
				+ active + ", shortCode=" + shortCode + ", created=" + created + ", lastModified=" + lastModified
				+ ", requiredCustomFields=" + Arrays.toString(requiredCustomFields) + "]";
	}
	
}
