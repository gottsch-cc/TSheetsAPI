/**
 * 
 */
package com.compucom.serviceops.tsheetsapi.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;

import com.compucom.serviceops.tsheetsapi.date.DateHelper;
import com.compucom.serviceops.tsheetsapi.json.JobCodeJson;

/**
 * @author Mark Gottschling on Nov 14, 2016
 *
 */
@Entity
@Table(name="tsheets_job_code")
public class JobCode {
	@Id
	@Column(name="id")
	private Long id;

	@Column(name="short_code")
	private String shortCode;
	@Column(name="type")
	private String type;
	@Column(name="billable")
	private Boolean billable;
	@Column(name="billable_rate")
	private BigDecimal billableRate;
	@Column(name="has_children")
	private Boolean children;
	@Column(name="assigned_to_all")
	private Boolean assignedToAll;
	@Column(name="active")
	private Boolean active;
	@Column(name="last_modified")
	private Date lastModifiedDate;
	@Column(name="created")
	private Date createdDate;
	
	@ManyToOne
	@JoinColumn(name="parent_id")
	private JobCode	parent;
	
	/**
	 * Empty constructor
	 */
	public JobCode() {}
	
	/**
	 * Copy constructor
	 * @param jc
	 */
	public JobCode(JobCode jc) {
		BeanUtils.copyProperties(jc, this);
		setParent(new JobCode(jc.getParent()));
	}
	
	/**
	 * 
	 * @return
	 */
	public JobCode copy() {
		return new JobCode(this);
	}
	
	/**
	 * 
	 * @param json
	 * @return
	 */
	public static JobCode mapFromJson(JobCodeJson json) {
		JobCode code = new JobCode();
		BeanUtils.copyProperties(json, code);
		code.setLastModifiedDate(DateHelper.parseIsoDateTime(json.getLastModified()));
		code.setCreatedDate(DateHelper.parseIsoDateTime(json.getCreated()));
		return code;		
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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
	public Boolean getBillable() {
		return billable;
	}

	/**
	 * @param billable the billable to set
	 */
	public void setBillable(Boolean billable) {
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
	 * @return the children
	 */
	public Boolean getChildren() {
		return children;
	}

	/**
	 * 
	 * @return
	 */
	public Boolean hasChildren() {
		return getChildren();
	}
	
	/**
	 * @param children the children to set
	 */
	public void setChildren(Boolean children) {
		this.children = children;
	}

	/**
	 * @return the assignedToAll
	 */
	public Boolean getAssignedToAll() {
		return assignedToAll;
	}

	/**
	 * @param assignedToAll the assignedToAll to set
	 */
	public void setAssignedToAll(Boolean assignedToAll) {
		this.assignedToAll = assignedToAll;
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
	 * @return the parent
	 */
	public JobCode getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(JobCode parent) {
		this.parent = parent;
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
}
