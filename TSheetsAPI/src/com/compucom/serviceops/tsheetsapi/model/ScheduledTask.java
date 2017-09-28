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
import javax.persistence.Table;

/**
 * @author Mark Gottschling on Sep 29, 2016
 *
 */
//@Entity
//@Table(name="scheduled_task",
//		indexes = {
//				@Index(name="SCHED_TASK_IDX1", unique=true, columnList = "name")
//		})
public class ScheduledTask {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="command")
	private String command;
	
	@Column(name="params")
	private String params;
	
	@Column(name="enabled")
	private Boolean enabled;
	
	@Column(name="email_enabled")
	private Boolean emailEnabled;
	
	@Column(name="email_to")
	private String to;
	
	@Column(name="email_cc")
	private String cc;
	
	@Column(name="email_bcc")
	private String bcc;

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

	public Boolean isEnabled() {
		return getEnabled();
	}
	
	/**
	 * @return the enabled
	 */
	public Boolean getEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}

	/**
	 * @param to the to to set
	 */
	public void setTo(String to) {
		this.to = to;
	}

	/**
	 * @return the cc
	 */
	public String getCc() {
		return cc;
	}

	/**
	 * @param cc the cc to set
	 */
	public void setCc(String cc) {
		this.cc = cc;
	}

	/**
	 * @return the bcc
	 */
	public String getBcc() {
		return bcc;
	}

	/**
	 * @param bcc the bcc to set
	 */
	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	public Boolean isEmailEnabled() {
		return getEmailEnabled();
	}
	
	/**
	 * @return the emailEnabled
	 */
	public Boolean getEmailEnabled() {
		return emailEnabled;
	}

	/**
	 * @param emailEnabled the emailEnabled to set
	 */
	public void setEmailEnabled(Boolean emailEnabled) {
		this.emailEnabled = emailEnabled;
	}

	/**
	 * @return the command
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * @param command the command to set
	 */
	public void setCommand(String command) {
		this.command = command;
	}

	/**
	 * @return the params
	 */
	public String getParams() {
		return params;
	}

	/**
	 * @param params the params to set
	 */
	public void setParams(String params) {
		this.params = params;
	}
	
}
