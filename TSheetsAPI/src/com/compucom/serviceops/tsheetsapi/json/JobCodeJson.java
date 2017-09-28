/**
 * 
 */
package com.compucom.serviceops.tsheetsapi.json;

import java.math.BigDecimal;
import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * 
 * @author Mark Gottschling on Nov 14, 2016
 *
 */
public class JobCodeJson {
	private long id;
	@SerializedName("parent_id") private long parentId;
	@SerializedName("short_code") private String shortCode;
	private String type;
	private boolean billable;
	@SerializedName("billable_rate") private BigDecimal billableRate;
	@SerializedName("has_children") private boolean children;
	@SerializedName("assigned_to_all") private boolean assignedToAll;
	private boolean active;
	@SerializedName("last_modified") private String lastModified;
	private String created;
		
	/**
	 * 
	 */
	public JobCodeJson() {}
	
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
	 * @return the parentId
	 */
	public long getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(long parentId) {
		this.parentId = parentId;
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
	 * @return the billable
	 */
	public boolean isBillable() {
		return billable;
	}

	/**
	 * @param billable the billable to set
	 */
	public void setBillable(boolean billable) {
		this.billable = billable;
	}

	/**
	 * @return the billableRate
	 */
	public BigDecimal getBillableRate() {
		return billableRate;
	}

	/**
	 * @param billableRate the billableRate to set
	 */
	public void setBillableRate(BigDecimal billableRate) {
		this.billableRate = billableRate;
	}

	/**
	 * @return the assignedToAll
	 */
	public boolean isAssignedToAll() {
		return assignedToAll;
	}

	/**
	 * @param assignedToAll the assignedToAll to set
	 */
	public void setAssignedToAll(boolean assignedToAll) {
		this.assignedToAll = assignedToAll;
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
	 * 
	 * @return
	 */
	public boolean getChildren() {
		return this.children;
	}
	
	/**
	 * 
	 * @return the children
	 */
	public boolean hasChildren() {
		return getChildren();
	}
	
	/**
	 * @param children the children to set
	 */
	public void setChildren(boolean children) {
		this.children = children;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "JobCodeJson [id=" + id + ", parentId=" + parentId + ", shortCode=" + shortCode + ", type=" + type
				+ ", billable=" + billable + ", billableRate=" + billableRate + ", children=" + children
				+ ", assignedToAll=" + assignedToAll + ", active=" + active + ", lastModified=" + lastModified
				+ ", created=" + created + "]";
	}
}
