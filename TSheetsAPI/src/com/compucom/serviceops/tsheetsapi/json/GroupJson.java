/**
 * 
 */
package com.compucom.serviceops.tsheetsapi.json;

import com.google.gson.annotations.SerializedName;

/**
 * @author Mark Gottschling on Oct 27, 2016
 *
 */
public class GroupJson {
	private int id;
	private String name;
	private String created;
	@SerializedName("last_modified") private String lastModified;
	
	/**
	 * 
	 */
	public GroupJson() {}

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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GroupJson [id=" + id + ", name=" + name + ", created=" + created + ", lastModified=" + lastModified
				+ "]";
	}
	
}
