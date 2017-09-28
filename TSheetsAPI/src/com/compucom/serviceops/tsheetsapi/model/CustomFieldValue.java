/**
 * 
 */
package com.compucom.serviceops.tsheetsapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * This class does not relate to a TSheets table. It serves to store the state of the selected value
 * of the Custom Field.
 * @author Mark Gottschling on Dec 6, 2016
 *
 */
@Entity
@Table(name="tsheets_custom_field_value")
public class CustomFieldValue {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private long id;
	
	@Column(name="value")
	private String value;
	
	@ManyToOne
	@JoinColumn(name="timesheet_id")
	private Timesheet timesheet;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="custom_field_id")
	private CustomField customField;

	@ManyToOne
	@JoinColumn(name="custom_field_item_id")
	private CustomFieldItem customFieldItem;

	/**
	 * 
	 */
	public CustomFieldValue() {}
	
	/**
	 * @param timesheet2
	 * @param cf
	 * @param cfi
	 */
	public CustomFieldValue(Timesheet timesheet, CustomField cf, CustomFieldItem cfi) {
		setTimesheet(timesheet);
		setCustomField(cf);
		setCustomFieldItem(cfi);
	}
	
	/**
	 * 
	 * @param value
	 * @param timesheet
	 * @param cf
	 * @param cfi
	 */
	public CustomFieldValue(String value, Timesheet timesheet, CustomField cf, CustomFieldItem cfi) {
		setValue(value);
		setTimesheet(timesheet);
		setCustomField(cf);
		setCustomFieldItem(cfi);
	}

	/**
	 * 
	 * @param c
	 */
	public CustomFieldValue(CustomFieldValue c) {
		setCustomField(new CustomField(c.getCustomField()));
		setCustomFieldItem(new CustomFieldItem(c.getCustomFieldItem()));
		setId(c.getId());
		setTimesheet(new Timesheet(c.getTimesheet()));
		setValue(c.getValue());
	}
	
	/**
	 * 
	 * @return
	 */
	public CustomFieldValue copy() {
		return new CustomFieldValue(this);
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
	 * @return the timesheet
	 */
	public Timesheet getTimesheet() {
		return timesheet;
	}

	/**
	 * @param timesheet the timesheet to set
	 */
	public void setTimesheet(Timesheet timesheet) {
		this.timesheet = timesheet;
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

	/**
	 * @return the customFieldItem
	 */
	public CustomFieldItem getCustomFieldItem() {
		return customFieldItem;
	}

	/**
	 * @param customFieldItem the customFieldItem to set
	 */
	public void setCustomFieldItem(CustomFieldItem customFieldItem) {
		this.customFieldItem = customFieldItem;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CustomFieldValue [id=" + id + ", value=" + value + ", timesheet.id=" + getTimesheet().getId() + "]";
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}


	
}

