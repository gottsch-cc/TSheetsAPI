/**
 * 
 */
package com.compucom.serviceops.tsheetsapi.json;

import java.util.Map;

import com.google.gson.annotations.SerializedName;

/**
 * @author Mark Gottschling on Oct 27, 2016
 *
 */
public class TimesheetJson {
	private long id;
	@SerializedName("user_id") private long userId;
	@SerializedName("jobcode_id") private long jobCodeId;
	private int locked;
	private String notes;
	private String created;
	@SerializedName("last_modified") private String lastModified;
	private String type;
	@SerializedName("on_the_clock") private boolean onTheClock;
	private String start;
	private String end;
	private String date;
	private long duration;
	private String location;
	@SerializedName("tz_str")	private String timeZone;
	@SerializedName("tz") private Double timeZoneNum;
	
	
	/*
	 *  map of custom field ID to the custom field value of the selected custom field item
	 *  "customfields": {
     *  "20717": "IMAC",
     *  "19144": "Item 2"
     *  }
	 */
	/*
	 * NOTE no longer annotated - a custom deserializer is used to set this property
	 * @SerializedName("customfields")
	 */
	private Map<String, String> customFields;
	
	/**
	 * 
	 */
	public TimesheetJson() {}

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
	 * @return the userId
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}

	/**
	 * @return the jobCodeId
	 */
	public long getJobCodeId() {
		return jobCodeId;
	}

	/**
	 * @param jobCodeId the jobCodeId to set
	 */
	public void setJobCodeId(long jobCodeId) {
		this.jobCodeId = jobCodeId;
	}

	/**
	 * @return the locked
	 */
	public int getLocked() {
		return locked;
	}

	/**
	 * @param locked the locked to set
	 */
	public void setLocked(int locked) {
		this.locked = locked;
	}

	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
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
	 * @return the onTheClock
	 */
	public boolean isOnTheClock() {
		return onTheClock;
	}

	/**
	 * @param onTheClock the onTheClock to set
	 */
	public void setOnTheClock(boolean onTheClock) {
		this.onTheClock = onTheClock;
	}

	/**
	 * @return the start
	 */
	public String getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(String start) {
		this.start = start;
	}

	/**
	 * @return the end
	 */
	public String getEnd() {
		return end;
	}

	/**
	 * @param end the end to set
	 */
	public void setEnd(String end) {
		this.end = end;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the duration
	 */
	public long getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(long duration) {
		this.duration = duration;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TimesheetJson [id=" + id + ", userId=" + userId + ", jobCodeId=" + jobCodeId + ", locked=" + locked
				+ ", notes=" + notes + ", created=" + created + ", lastModified=" + lastModified + ", type=" + type
				+ ", onTheClock=" + onTheClock + ", start=" + start + ", end=" + end + ", date=" + date + ", duration="
				+ duration + ", customFields=" + customFields + "]";
	}

	/**
	 * @return the customFields
	 */
	public Map<String, String> getCustomFields() {
		return customFields;
	}

	/**
	 * @param customFields the customFields to set
	 */
	public void setCustomFields(Map<String, String> customFields) {
		this.customFields = customFields;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the timeZone
	 */
	public String getTimeZone() {
		return timeZone;
	}

	/**
	 * @param timeZone the timeZone to set
	 */
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	/**
	 * @return the timeZoneNum
	 */
	public Double getTimeZoneNum() {
		return timeZoneNum;
	}

	/**
	 * @param timeZoneNum the timeZoneNum to set
	 */
	public void setTimeZoneNum(Double timeZoneNum) {
		this.timeZoneNum = timeZoneNum;
	}
}
