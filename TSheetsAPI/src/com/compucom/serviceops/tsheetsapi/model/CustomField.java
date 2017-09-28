/**
 * 
 */
package com.compucom.serviceops.tsheetsapi.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.compucom.serviceops.tsheetsapi.date.DateHelper;
import com.compucom.serviceops.tsheetsapi.json.CustomFieldJson;

/**
 * @author Mark Gottschling on Dec 5, 2016
 *
 */
@Entity
@Table(name="tsheets_custom_field")
public class CustomField {
	@Id
	@Column(name="id")
	private long id;
	
	@Column(name="active")
	private Boolean active;
	
	@Column(name="name")
	private String name;
	
	@Column(name="short_code")
	private String shortCode;
	
	@Column(name="required")
	private Boolean required;
	
	@Column(name="applies_to")
	private String appliesTo;
	
	@Column(name="ui_pref")
	private String uiPref;
	
	@Column(name="regex_filter")
	private String reqexFilter;
	
	@Column(name="created")
	private Date createdDate;
	@Column(name="last_modified")
	private Date lastModifiedDate;
	
	public CustomField() {}
	
	/**
	 * Copy constructor
	 * @param c
	 */
	public CustomField(CustomField c) {
		this.setId(c.getId());
		this.setActive(c.getActive());
		this.setAppliesTo(c.getAppliesTo());
		this.setCreatedDate(c.getCreatedDate());
		this.setLastModifiedDate(c.getLastModifiedDate());
		this.setName(c.getName());
		this.setReqexFilter(c.getReqexFilter());
		this.setRequired(c.getRequired());
		this.setShortCode(c.getShortCode());
		this.setUiPref(c.getUiPref());
	}
	
	/**
	 * 
	 * @return
	 */
	public CustomField copy() {
		return new CustomField(this);
	}
	
	/**
	 * 
	 * @param json
	 * @return
	 */
	public static CustomField mapFromJson(CustomFieldJson json) {
		CustomField c = new CustomField();
		c.setId(json.getId());
		c.setActive(json.isActive());
		c.setAppliesTo(json.getAppliesTo());
		c.setCreatedDate(DateHelper.parseIsoDateTime(json.getCreated()));
		c.setLastModifiedDate(DateHelper.parseIsoDateTime(json.getLastModified()));
		c.setName(json.getName());
		c.setReqexFilter(json.getRegexFilter());
		c.setRequired(json.isRequired());
		c.setShortCode(json.getShortCode());
		c.setUiPref(json.getUiPref());
		
		return c;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(Boolean active) {
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
	public Boolean getRequired() {
		return required;
	}

	/**
	 * @param required the required to set
	 */
	public void setRequired(Boolean required) {
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
	 * @return the reqexFilter
	 */
	public String getReqexFilter() {
		return reqexFilter;
	}

	/**
	 * @param reqexFilter the reqexFilter to set
	 */
	public void setReqexFilter(String reqexFilter) {
		this.reqexFilter = reqexFilter;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the lastModifiedDate
	 */
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	/**
	 * @param lastModifiedDate the lastModifiedDate to set
	 */
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
}
