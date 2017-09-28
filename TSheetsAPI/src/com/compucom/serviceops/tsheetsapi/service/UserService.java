/**
 * 
 */
package com.compucom.serviceops.tsheetsapi.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.compucom.serviceops.tsheetsapi.dao.UserDao;
import com.compucom.serviceops.tsheetsapi.date.DateHelper;
import com.compucom.serviceops.tsheetsapi.json.GroupJson;
import com.compucom.serviceops.tsheetsapi.json.UserJson;
import com.compucom.serviceops.tsheetsapi.json.response.TSheetsResponse;
import com.compucom.serviceops.tsheetsapi.json.response.UsersResults;
import com.compucom.serviceops.tsheetsapi.json.response.UsersSupData;
import com.compucom.serviceops.tsheetsapi.model.Account;
import com.compucom.serviceops.tsheetsapi.model.CustomField;
import com.compucom.serviceops.tsheetsapi.model.CustomFieldValue;
import com.compucom.serviceops.tsheetsapi.model.Group;
import com.compucom.serviceops.tsheetsapi.model.User;
import com.google.gson.reflect.TypeToken;

/**
 * @author Mark Gottschling on Oct 27, 2016
 *
 */
@Service("userService")
@Transactional(transactionManager="tsheetsTxMgr")
public class UserService extends AbstractTSheetsService {
	private static final Logger logger = LogManager.getLogger(UserService.class);
	
	private static final String USERS_URL = "users?active=%s&per_page=%d";
	
	private static final String USERS_SINCE_URL = "users?active=%s&modified_since=%s&per_page=%d";
	// TODO add a url that has a condition for last modified date (greater than) - use to compare what users need to be updated.
	
	@Autowired UserDao userDao;
	@Autowired GroupService groupService;
	
	@Autowired CustomFieldService customFieldService;
	@Autowired CustomFieldItemService customFieldItemService;
	@Autowired CustomFieldValueService customFieldValueService;
	
	@SuppressWarnings("unchecked")
	public boolean importUsers(Account account, Date date, boolean active) {
		logger.info("Importing users...");
		
		int offset = 0;
		TSheetsResponse<UsersResults, UsersSupData> response = null;
		String uri = "";

		if (date == null) {
			uri = String.format(getBaseUrl() + USERS_URL, String.valueOf(active), MAX_PER_PAGE);
		}
		else {
			uri = String.format(getBaseUrl() + USERS_SINCE_URL,
					String.valueOf(active), DateHelper.toString(date, DateHelper.ISO_8601_EPOCH_DATE_PATTERN), MAX_PER_PAGE);
		}
		
		logger.info("\t...using base URL of " + uri);
		
		do {
			logger.debug("\t...processing page " + offset);
			// update the uri with the offset
			String url = uri + "&page=" + offset;

//			logger.info("page offset: " + offset);
//			logger.debug("Full URL:" + url);
			try {
					response = (TSheetsResponse<UsersResults, UsersSupData>) getResponse(
							account, url, 	
							new TypeToken<TSheetsResponse<UsersResults, UsersSupData>>() {}.getType());
			}
			catch(IOException e) {
				logger.error("An IO Exception has occurred: " + url, e);
				// TODO return a custom error, such as TaskHaltingException that indicates the rest of the should
				// not proceed.
				/* TODO or the service should contain some sort of Error/Message List
				 * that contains info like the last record updated, etc.
				 */
				/*
				 * TODO or this service should record the last record or page & count that was successful
				 */
				// TODO use log4j Message templating and Categories
				return false;
			}
			
			// process each User
			for (UserJson json : response.getResults().getUsers().values()) {
//				logger.debug("user json:" + json);
				// copy props to model			
				User user = fromJson(account, json, response.getSupplementalData());
//				logger.debug("user:" + user);
				// persist
				this.merge(user);
			}

			// update the offset
			offset++;	
			
			if (response == null) {
				logger.error("Unable to retrieve Users data from TSheets.");
				return false;
			}
		} while (response != null && response.getResults().size() > 0);
		
		if (logger.isInfoEnabled()) {
			logger.info(String.format("Processed %d pages.", offset-1));
		}
		
		return true;
	}
	
	/**
	 * 
	 * @param account
	 * @param json
	 * @return
	 */
	private User fromJson(Account account, UserJson json, UsersSupData sup) {
		User user = User.mapFromJson(json);
		String key = String.valueOf(json.getGroupId());
		// add the group from the supplemental data
		if (sup.getGroups() != null && sup.getGroups().containsKey(key)) {
//			logger.debug("Group Key: " + key);
			GroupJson groupJson = sup.getGroups().get(key);
//			logger.debug("GroupJson: " + groupJson);
			Group group = Group.mapFromJson(groupJson);
			groupService.merge(group);
			user.setGroup(group);
		}
		
		this.merge(user);
		
		// for every customfield item
		if (json.getCustomFields() != null && json.getCustomFields().size() > 0) {
//			logger.debug("User Has Custom Fields");
			for (Entry<String, String> entry : json.getCustomFields().entrySet()) {
//				logger.debug(String.format("%s: %s", entry.getKey(), entry.getValue()));
				
//				logger.debug("Processing custom field: " + entry.getKey() + ": " + entry.getValue());
				// if the custom field has a value set
				if (entry.getValue() != null && !entry.getValue().trim().equals("")) {
					// fetch the Custom Field
//					logger.debug("Fetching custom field...");
					CustomField cf = customFieldService.getById(Long.valueOf(entry.getKey()));
					if (cf == null) {
						// fetch from TSheets
//						logger.warn("Didn't find the custom field in database");
						cf = customFieldService.getByIdAndAppliesTo(account, entry.getKey(), "user");
					}
					
					if (cf != null /*&& cfi != null*/) {
						// TODO Check if the custom value already exists!
//						logger.debug(String.format("Fetching Custom Field Value for user: %d, custom field: %d...", user.getId(), cf.getId()));
						List<CustomFieldValue> vals = customFieldValueService.getByTimeSheetAndCustomField(user.getId(), cf.getId());
						CustomFieldValue val = null;
						if (vals == null || vals.isEmpty()) {
//							logger.debug(String.format("%d: Adding custom field value: %s ", user.getId(), entry.getValue()));
// 4/26/217							val = new CustomFieldValue(entry.getValue(), timesheet, cf, cfi);
							val = new CustomFieldValue();
							val.setValue(entry.getValue());
							val.setUser(user);
							val.setCustomField(cf);
							
							customFieldValueService.merge(val);
							user.getCustomFieldValue().add(val);
						}
						// test if the same value
						else {
//							logger.debug("vals.size: " + vals.size());
//							logger.debug("Deleting old custom field values...");
//							timesheet.getCustomFieldValue().clear(); // <-- why am i clearing this
							for (CustomFieldValue v : vals) {
								customFieldValueService.delete(v);
//								logger.debug("Deleting " + v.getId() + ": " + v.getValue());
								user.getCustomFieldValue().remove(v);
							}
//														logger.debug("User: " + user.getId() + ": Updating custom field value: " + entry.getValue());
							//							timesheet.getCustomFieldValue().remove(val);
// 4/26/17							val = new CustomFieldValue(entry.getValue(), timesheet, cf, cfi);
							val = new CustomFieldValue();
							val.setValue(entry.getValue());
							val.setUser(user);
							
							val.setCustomField(cf);							
//							logger.debug(String.format("Updated custom field: %s", val));
							customFieldValueService.merge(val);
							user.getCustomFieldValue().add(val);
						}
					}
				}
				
			}
		}		
				
		// map in custom fields
		user.mapCustomFields(json.getCustomFields());
		
		return user;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public User getById(long id) {
		return userDao.findById(id);
	}
	
	/**
	 * 
	 * @param User
	 */
	public User save(User user) {
		return userDao.save(user);
	}
	
	/**
	 * 
	 * @param User
	 */
	public void persist(User user) {
		userDao.persist(user);
	}
	
	/**
	 * 
	 * @param entity
	 */
	public void merge(User entity) {
		userDao.merge(entity);
	}

	/**
	 * @param filters
	 * @return
	 */
	public Long countAll(Map<String, Object> filters) {
		return userDao.countAll(filters);
	}

	/**
	 * @param page
	 * @param count
	 * @param filters
	 * @param sorts
	 * @return
	 */
	public List<User> findAll(int page, int count, Map<String, Object> filters, Map<String, String> sorting) {
		return userDao.findAll(page, count, filters, sorting);
	}
}
