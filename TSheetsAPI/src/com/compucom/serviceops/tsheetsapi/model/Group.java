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
import com.compucom.serviceops.tsheetsapi.json.GroupJson;

/**
 * @author Mark Gottschling on Oct 27, 2016
 *
 */
@Entity
//Table(name="\"group\"") // this is for MySQL
@Table(name="[tsheets_group]") // this is for SQL Server
public class Group {
	@Id
	@Column(name="id")
	private long id;
	@Column(name="name")
	private String name;
	@Column(name="created")
	private Date created;
	@Column(name="last_modified")
	private Date lastModified;
	
	/**
	 * 
	 */
	public Group() {}

	/**
	 * 
	 * @param group
	 */
	public Group (Group group) {
		
	}
	
	/**
	 * 
	 * @return
	 */
	public Group copy() {
		return new Group(this);
	}
	
	/**
	 * 
	 * @param json
	 * @return
	 */
	public static Group mapFromJson(GroupJson json) {
		Group group = new Group();
		group.setCreated(DateHelper.parseIsoDateTime(json.getCreated()));
		group.setId(json.getId());
		group.setLastModified(DateHelper.parseIsoDateTime(json.getLastModified()));
		group.setName(json.getName());
		return group;
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
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @param created the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @return the lastModified
	 */
	public Date getLastModified() {
		return lastModified;
	}

	/**
	 * @param lastModified the lastModified to set
	 */
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Group [id=" + id + ", name=" + name + ", created=" + created + ", lastModified=" + lastModified + "]";
	}
}
