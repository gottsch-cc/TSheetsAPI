/**
 * 
 */
package com.compucom.serviceops.tsheetsapi.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.compucom.serviceops.tsheetsapi.date.DateHelper;
import com.compucom.serviceops.tsheetsapi.json.CustomFieldItemJson;

/**
 * @author Mark Gottschling on Dec 5, 2016
 *
 */
@Entity
@Table(name="tsheets_custom_field_item",
	indexes = {
			@Index(name="CUST_FIELD_ITEM_IDX1", unique=false, columnList = "name")
	})
public class CustomFieldItem {
	@Id
	@Column(name="id")
	private long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="short_code")
	private String shortCode;
	
	@Column(name="active")
	private Boolean active;
	
	@Column(name="created")
	private Date createdDate;
	
	@Column(name="last_modified")
	private Date lastModifiedDate;
	
	@ManyToOne
	@JoinColumn(name="customfield_id")
	private CustomField customField;
	
	/**
	 * 
	 */
	public CustomFieldItem() {	}
	
	/**
	 * 
	 * @param c
	 */
	public CustomFieldItem(CustomFieldItem c) {
		setId(c.getId());
		setActive(c.getActive());
		setCreatedDate(c.getCreatedDate());
		setLastModifiedDate(c.getLastModifiedDate());
		setName(c.getName());
		setShortCode(c.getShortCode());
		setCustomField(new CustomField(c.getCustomField()));
	}
	
	/**
	 * 
	 * @return
	 */
	public CustomFieldItem copy() {
		return new CustomFieldItem(this);
	}

	/**
	 * 
	 * @param json
	 * @return
	 */
	public static CustomFieldItem mapFromJson(CustomFieldItemJson json) {
		CustomFieldItem c = new CustomFieldItem();
		c.setId(json.getId());
		c.setActive(json.isActive());
		c.setCreatedDate(DateHelper.parseIsoDateTime(json.getCreated()));
		c.setLastModifiedDate(DateHelper.parseIsoDateTime(json.getLastModified()));
		c.setName(json.getName());
		c.setShortCode(json.getShortCode());
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

	/**
	 * @return the customField
	 */
	public CustomField getCustomField() {
		return customField;
	}

	/**
	 * @param customField the customField to set
	 */
	public void setCustomField(CustomField customField) {
		this.customField = customField;
	}
	

}
