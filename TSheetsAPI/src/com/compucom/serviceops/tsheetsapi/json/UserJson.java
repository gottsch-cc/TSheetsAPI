/**
 * 
 */
package com.compucom.serviceops.tsheetsapi.json;

import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

/**
 * @author Mark Gottschling on Oct 27, 2016
 *
 */
public class UserJson {
	private long id;
	private String username;
	private String email;
	@SerializedName("client_url") private String clientUrl;
	@SerializedName("company_name") private String companyName;
	@SerializedName("first_name") private String firstName;
	@SerializedName("last_name") private String lastName;
	@SerializedName("group_id") private int groupId;
	@SerializedName("employee_number") private int employeeId;
	@SerializedName("salaried") private boolean salaried;
	@SerializedName("excempt") private boolean exempt;
	@SerializedName("manager_of_group_ids") private List<Integer> managedGroupIDs;
	@SerializedName("payroll_id") private String payrollId;
	@SerializedName("mobile_number") private String mobileNumber;
	@SerializedName("created") private String created;
	@SerializedName("last_modified") private String lastModified;
	@SerializedName("active") private Boolean active;
	
	/*
	 * 9-1-2017 - adding custom fields
	 * NOTE not annotated. ie not deserialized by annotation, instead uses a custom deserializer
	 * because the field may be one of two types (String or Map).
	 */
	private Map<String, String> customFields;

	/**
	 * 
	 */
	public UserJson() {}
	
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserJson [id=" + id + ", username=" + username + ", email=" + email + ", clientUrl=" + clientUrl
				+ ", companyName=" + companyName + ", firstName=" + firstName + ", lastName=" + lastName + ", groupId="
				+ groupId + ", employeeId=" + employeeId + ", salaried=" + salaried + ", exempt=" + exempt
				+ ", managedGroupIDs=" + managedGroupIDs + ", payrollId=" + payrollId + ", mobileNumber=" + mobileNumber
				+ ", created=" + created + ", lastModified=" + lastModified + ", active=" + active + "]";
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the groupId
	 */
	public int getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the salaried
	 */
	public boolean isSalaried() {
		return salaried;
	}

	/**
	 * @param salaried the salaried to set
	 */
	public void setSalaried(boolean salaried) {
		this.salaried = salaried;
	}

	/**
	 * @return the clientUrl
	 */
	public String getClientUrl() {
		return clientUrl;
	}

	/**
	 * @param clientUrl the clientUrl to set
	 */
	public void setClientUrl(String clientUrl) {
		this.clientUrl = clientUrl;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @return the employeeId
	 */
	public int getEmployeeId() {
		return employeeId;
	}

	/**
	 * @param employeeId the employeeId to set
	 */
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	/**
	 * @return the exempt
	 */
	public boolean isExempt() {
		return exempt;
	}

	/**
	 * @param exempt the exempt to set
	 */
	public void setExempt(boolean exempt) {
		this.exempt = exempt;
	}

	/**
	 * @return the managedGroupIDs
	 */
	public List<Integer> getManagedGroupIDs() {
		return managedGroupIDs;
	}

	/**
	 * @param managedGroupIDs the managedGroupIDs to set
	 */
	public void setManagedGroupIDs(List<Integer> managedGroupIDs) {
		this.managedGroupIDs = managedGroupIDs;
	}

	/**
	 * @return the payrollId
	 */
	public String getPayrollId() {
		return payrollId;
	}

	/**
	 * @param payrollId the payrollId to set
	 */
	public void setPayrollId(String payrollId) {
		this.payrollId = payrollId;
	}

	/**
	 * @return the mobileNumber
	 */
	public String getMobileNumber() {
		return mobileNumber;
	}

	/**
	 * @param mobileNumber the mobileNumber to set
	 */
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
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
	
}
