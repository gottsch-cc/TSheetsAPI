/**
 * 
 */
package com.compucom.serviceops.tsheetsapi.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Mark Gottschling on Jan 26, 2017
 *
 */
@Entity
@Table(name="tsheets_update_history")
public class UpdateHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="last_update")
	private Date lastUpdate;
	
	@Column(name="status")
	private String status;
	
	@Column(name="last_attempt")
	private Date lastAttempt;
	
	/**
	 * 
	 */
	public UpdateHistory() {
		this.status = "";
	}

	/**
	 * 
	 * @param name
	 */
	public UpdateHistory(String name) {
		setName(name);
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
	 * @return the lastUpdate
	 */
	public Date getLastUpdate() {
		return lastUpdate;
	}

	/**
	 * @param lastUpdate the lastUpdate to set
	 */
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the lastAttempt
	 */
	public Date getLastAttempt() {
		return lastAttempt;
	}

	/**
	 * @param lastAttempt the lastAttempt to set
	 */
	public void setLastAttempt(Date lastAttempt) {
		this.lastAttempt = lastAttempt;
	}
}
