/**
 * 
 */
package com.compucom.serviceops.tsheetsapi.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;

import com.compucom.serviceops.tsheetsapi.date.DateHelper;
import com.compucom.serviceops.tsheetsapi.json.UserJson;
import com.google.gson.annotations.SerializedName;

/**
 * @author Mark Gottschling on Oct 27, 2016
 *
 */
@Entity
@Table(name="[tsheets_user]")
public class User {
	public static final String CUSTOM_FIELD_SUPPORT_TYPE = "supportType";
	public static final String CUSTOM_FIELD_SUPPORT_TYPE_ID = "25659";
	
	public static final String CUSTOM_FIELD_ROLE = "role";
	public static final String CUSTOM_FIELD_ROLE_ID = "68053";
	
	/*
	 * A cached map of User Custom Fields in the form of <FieldID, FieldName>.
	 * Currently hard-coded
	 */
	public static Map<String, String> customFields = new HashMap<>(5);
	
	static {
		customFields.put(CUSTOM_FIELD_SUPPORT_TYPE_ID, CUSTOM_FIELD_SUPPORT_TYPE);
		customFields.put(CUSTOM_FIELD_ROLE_ID, CUSTOM_FIELD_ROLE);
	}
	
	@Id
	@Column(name="id")
	private long id;
	
	@Column(name="username")
	private String username;
	@Column(name="email")
	private String email;
	@Column(name="client_url")
	private String clientUrl;
	@Column(name="company_name")
	private String companyName;
	@Column(name="first_name")
	private String firstName;
	@Column(name="last_name")
	private String lastName;
	@Column(name="employee_id")
	private int employeeId;
	@Column(name="salaried")
	private boolean salaried;
	@Column(name="exempt")
	private boolean exempt;	
	@Column(name="mobile_number")
	private String mobileNumber;
	@Column(name="payroll_id")
	private String payrollId;
	
//	private List<Integer> managedGroupIds;
	
	@ManyToOne
	@JoinColumn(name="group_id")
	private Group group;
	
	@Column(name="support_type")
	private String supportType;
	
	@Column(name="role")
	private String role;
	
	@Column(name="created")
	private Date created;
	
	@Column(name="last_modified")
	private Date lastModified;
	
	@Column(name="active")
	private Boolean active;
	
	@OneToMany(mappedBy="user")
	private List<CustomFieldValue> customFieldValue;
	
	/**
	 * 
	 */
	public User() {}
	
	/**
	 * Copy constructor
	 * @param user
	 */
	public User(User user) {
		this.setClientUrl(user.getClientUrl());
		this.setCompanyName(user.getCompanyName());
		this.setEmail(user.getEmail());
		this.setEmployeeId(user.getEmployeeId());
		this.setExempt(user.isExempt());
		this.setFirstName(user.getFirstName());
		this.setGroup(new Group(user.getGroup()));
		this.setId(user.getId());
		this.setLastName(user.getLastName());
		this.setMobileNumber(user.getMobileNumber());
		this.setSalaried(user.isSalaried());
		this.setUsername(user.getUsername());
		this.setCreated(user.getCreated());
		this.setLastModified(user.getLastModified());
		this.setActive(user.getActive());
	}
	
	/**
	 * 
	 * @return
	 */
	public User copy() {
		return new User(this);
	}
	
	/**
	 * 
	 * @param json
	 * @return
	 */
	public static User mapFromJson(UserJson json) {
		User user = new User();
		BeanUtils.copyProperties(json, user, "created", "lastModified");
		// copy beans that require processing
		user.setCreated(DateHelper.parseIsoDateTime(json.getCreated()));
		user.setLastModified(DateHelper.parseIsoDateTime(json.getLastModified()));
		// NOTE have to update group elsewhere programmatically (need to retrieve from persistence)
		return user;
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
	 * @return the group
	 */
	public Group getGroup() {
		return group;
	}

	/**
	 * @param group the group to set
	 */
	public void setGroup(Group group) {
		this.group = group;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", email=" + email + ", clientUrl=" + clientUrl
				+ ", companyName=" + companyName + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", employeeId=" + employeeId + ", salaried=" + salaried + ", exempt=" + exempt + ", mobileNumber="
				+ mobileNumber + ", payrollId=" + payrollId + ", group=" + group + ", supportType=" + supportType
				+ ", role=" + role + ", created=" + created + ", lastModified=" + lastModified + ", active=" + active
				+ "]";
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
	 * @return the customFieldValue
	 */
	public List<CustomFieldValue> getCustomFieldValue() {
		if (customFieldValue == null) {
			customFieldValue = new ArrayList<CustomFieldValue>();
		}
		return customFieldValue;
	}

	/**
	 * @param customFieldValue the customFieldValue to set
	 */
	public void setCustomFieldValue(List<CustomFieldValue> customFieldValue) {
		this.customFieldValue = customFieldValue;
	}

	/**
	 * @return the supportType
	 */
	public String getSupportType() {
		return supportType;
	}

	/**
	 * @param supportType the supportType to set
	 */
	public void setSupportType(String supportType) {
		this.supportType = supportType;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @param map
	 */
	public void mapCustomFields(Map<String, String> map) {
		for (Map.Entry<String, String> customFieldEntry : map.entrySet()) {
			if (User.customFields.containsKey(customFieldEntry.getKey())) {
				/*
				 * NOTE not the best implementation (very hard-coded)
				 */
				String fieldName = User.customFields.get(customFieldEntry.getKey());
				if (fieldName.equalsIgnoreCase(CUSTOM_FIELD_SUPPORT_TYPE)) {
					setSupportType(customFieldEntry.getValue());
				}
				else if (fieldName.equalsIgnoreCase(CUSTOM_FIELD_ROLE)) {
					setRole(customFieldEntry.getValue());
				}
			}
		}
	}
}
