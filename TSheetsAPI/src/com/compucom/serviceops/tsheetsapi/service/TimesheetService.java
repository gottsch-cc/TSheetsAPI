/**
 * 
 */
package com.compucom.serviceops.tsheetsapi.service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.compucom.serviceops.tsheetsapi.dao.TimesheetDao;
import com.compucom.serviceops.tsheetsapi.date.DateHelper;
import com.compucom.serviceops.tsheetsapi.dto.TimesheetReportDto;
import com.compucom.serviceops.tsheetsapi.json.JobCodeJson;
import com.compucom.serviceops.tsheetsapi.json.TimesheetJson;
import com.compucom.serviceops.tsheetsapi.json.UserJson;
import com.compucom.serviceops.tsheetsapi.json.response.TSheetsResponse;
import com.compucom.serviceops.tsheetsapi.json.response.TimesheetsDeletedResults;
import com.compucom.serviceops.tsheetsapi.json.response.TimesheetsResults;
import com.compucom.serviceops.tsheetsapi.json.response.TimesheetsSupData;
import com.compucom.serviceops.tsheetsapi.model.Account;
import com.compucom.serviceops.tsheetsapi.model.CustomField;
import com.compucom.serviceops.tsheetsapi.model.CustomFieldValue;
import com.compucom.serviceops.tsheetsapi.model.Group;
import com.compucom.serviceops.tsheetsapi.model.JobCode;
import com.compucom.serviceops.tsheetsapi.model.Timesheet;
import com.compucom.serviceops.tsheetsapi.model.User;
import com.google.gson.reflect.TypeToken;

import au.com.bytecode.opencsv.CSVReader;

/**
 * 
 * @author Mark Gottschling on Nov 11, 2016
 *
 */
@Service("timesheetService")
@Transactional(transactionManager="tsheetsTxMgr")
public class TimesheetService extends AbstractTSheetsService {
	private static final Logger logger = LogManager.getLogger(TimesheetService.class);

	private static final String TIMESHEETS = "timesheets?";
	private static final String TIMESHEETS_DELETED = "timesheets_deleted?supplemental_data=no&";
	private static final String TIMESHEETS_URL = "per_page=%d";
	private static final String TIMESHEETS_FROM_URL = "per_page=%d&start_date=%s&end_date=%s";
	//	private static final String TIMESHEETS_URL = "timesheets?ids=15172054";
	private static final String TIMESHEETS_SINCE_URL = "modified_since=%s&per_page=%d";
	private static final String TIMESHEETS_BY_IDS = "timesheets?ids=%s";
	private static final String TIMESHEETS_DELETED_BY_IDS = "timesheets_deleted?ids=%s";	
	//	private static final String TIMESHEETS_URL = "timesheets?per_page=%d&page=%d";
	//	private static final String TIMESHEETS_FROM_URL = "timesheets?per_page=%d&page=%d&start_date=%s&end_date=%s";
	////	private static final String TIMESHEETS_URL = "timesheets?ids=15172054";
	//	private static final String TIMESHEETS_SINCE_URL = "timesheets?modified_since=%s&per_page=%d&page=%d";

	@Autowired TimesheetDao timesheetDao;
	@Autowired UserService userService;
	@Autowired GroupService groupService;
	@Autowired JobCodeService jobCodeService;
	@Autowired CustomFieldService customFieldService;
	@Autowired CustomFieldItemService customFieldItemService;
	@Autowired CustomFieldValueService customFieldValueService;
	
	// TODO create a method to select the correct URI strings and not repeat the same fetch and process code.
	
	/**
	 * Retrieve timesheets from a given start and end period.
	 * @param account
	 * @param start
	 * @param end
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean importTimesheets(Account account, List<Integer> idsListOld) {
		logger.info("Importing timesheets...");
		int offset = 0;
		long count = MAX_PER_PAGE;		

		TSheetsResponse<TimesheetsResults, TimesheetsSupData> response = null;

		// open the csv file and process from there
		CSVReader csvReader = null;
		
		List<String> idsList = new ArrayList<>();
		String[] nextLine;
		try {
			csvReader = new CSVReader(new FileReader("C:\\Data\\Documents\\CompuCom\\Service Operations\\TSheets\\unupdated-timesheets3.csv"), ',');
			String[] headerRow = csvReader.readNext();
			while ((nextLine = csvReader.readNext()) != null) {
				idsList.add(nextLine[0]);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} // end of cvs read
		finally {
			// close the reader
			try {
				csvReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// convert ids list into comma delimited string
//		String ids = StringUtils.collectionToCommaDelimitedString(idsList);
//		String ids = String.join(",", idsList);
logger.debug("size:" + idsList.size());

		// TODO only select x ids, and remove them from the url each time
		int index = 0;
		int n = 50;
		int start = 0;
		String id = "";
		ArrayList<String> in = new ArrayList<>();
		do {
			response = null;
			if (start >= idsList.size()) break;
//			id = idsList.get(index);
			// grab the first 50 ids
			in.clear();
			logger.debug("start=" + start);
			logger.debug("n=" + n);
			for(int i = start; i < n; i++) {
				if (i < idsList.size()) {
					in.add(idsList.get(i));
				}
			}
			start += 50;
			n += 50;
			id = StringUtils.collectionToCommaDelimitedString(in);
			
			String uri = String.format(getBaseUrl() + TIMESHEETS_BY_IDS, id, count);
			
			logger.info("\t...using base URL of " + uri);
			logger.debug("\t...processing page " + offset);
			// update the uri with the offset
			uri += "&page=" + offset;
			try {
				response = (TSheetsResponse<TimesheetsResults, TimesheetsSupData>) getResponse(
						account, uri, 	
						new TypeToken<TSheetsResponse<TimesheetsResults, TimesheetsSupData>>() {}.getType());

				if (response == null) {
					logger.error("Unable to retrieve Timesheets data from TSheets.");
					return false;
				}
			}
			catch(IOException e) {
				logger.error("An IO Exception has occurred: " + uri, e);
				// TODO return a custom error, such as TaskHaltingException that indicates the rest of the should
				// not proceed.
				/* TODO or the service should contain some sort of Error/Message List
				 * that contains info like the last record updated, etc.
				 */
				/*
				 * TODO or this service should record the last record or page & count that was successful
				 */
				return false;
			}

			// process each Timesheet
			for (TimesheetJson json : response.getResults().getTimesheets().values()) {
				// copy props to model	
				Timesheet timesheet = fromJson(account, json, response.getSupplementalData());
				// persist
				this.merge(timesheet);
			}
			if (response.getResults().getTimesheets() == null || response.getResults().getTimesheets().size() == 0) {
				logger.warn("A timesheet doesn't exist for id: " + id);
			}
			
			// update the index
			index++;
		} while (response != null && !id.equals(""));
		
		if (logger.isInfoEnabled()) {
			logger.info(String.format("Processed %d ids.", index));
		}
		return true;		
	}
	
	@SuppressWarnings("unchecked")
	public boolean importDeletedTimesheetsByIDsFile(Account account) {
		logger.info("Importing deleted timesheets...");
		int offset = 0;
		long count = MAX_PER_PAGE;		

		TSheetsResponse<TimesheetsDeletedResults, TimesheetsSupData> response = null;

		// open the csv file and process from there
		CSVReader csvReader = null;
		
		List<String> idsList = new ArrayList<>();
		String[] nextLine;
		try {
			csvReader = new CSVReader(new FileReader("C:\\Data\\Documents\\CompuCom\\Service Operations\\TSheets\\unupdated-timesheets3.csv"), ',');
			String[] headerRow = csvReader.readNext();
			while ((nextLine = csvReader.readNext()) != null) {
				idsList.add(nextLine[0]);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} // end of cvs read
		finally {
			// close the reader
			try {
				csvReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// convert ids list into comma delimited string
//		String ids = StringUtils.collectionToCommaDelimitedString(idsList);
//		String ids = String.join(",", idsList);
logger.debug("size:" + idsList.size());

		// TODO only select x ids, and remove them from the url each time
		int index = 0;
		int n = 50;
		int start = 0;
		String id = "";
		ArrayList<String> in = new ArrayList<>();
		do {
			response = null;
			if (start >= idsList.size()) break;
//			id = idsList.get(index);
			// grab the first 50 ids
			in.clear();
			logger.debug("start=" + start);
			logger.debug("n=" + n);
			for(int i = start; i < n; i++) {
				if (i < idsList.size()) {
					in.add(idsList.get(i));
				}
			}
			start += 50;
			n += 50;
			id = StringUtils.collectionToCommaDelimitedString(in);
			
			String uri = String.format(getBaseUrl() + TIMESHEETS_DELETED_BY_IDS, id, count);
			
			logger.info("\t...using base URL of " + uri);
			logger.debug("\t...processing page " + offset);
			// update the uri with the offset
			uri += "&page=" + offset;
			try {
				response = (TSheetsResponse<TimesheetsDeletedResults, TimesheetsSupData>) getResponse(
						account, uri, 	
						new TypeToken<TSheetsResponse<TimesheetsDeletedResults, TimesheetsSupData>>() {}.getType());

				if (response == null) {
					logger.error("Unable to retrieve Timesheets data from TSheets.");
					return false;
				}
			}
			catch(IOException e) {
				logger.error("An IO Exception has occurred: " + uri, e);
				// TODO return a custom error, such as TaskHaltingException that indicates the rest of the should
				// not proceed.
				/* TODO or the service should contain some sort of Error/Message List
				 * that contains info like the last record updated, etc.
				 */
				/*
				 * TODO or this service should record the last record or page & count that was successful
				 */
				return false;
			}

			// process each Timesheet
			for (TimesheetJson json : response.getResults().getTimesheets().values()) {
				// copy props to model			
				Timesheet timesheet = fromJson(account, json, response.getSupplementalData());
//				logger.debug("timesheet:" + timesheet);
				// update timesheet to indicate it is deleted
				timesheet.setDeleted(true);
				// persist
				this.merge(timesheet);
			}
			if (response.getResults().getTimesheets() == null || response.getResults().getTimesheets().size() == 0) {
				logger.warn("A timesheet doesn't exist for id: " + id);
			}
			
			// update the index
			index++;
		} while (response != null && !id.equals(""));
		
		if (logger.isInfoEnabled()) {
			logger.info(String.format("Processed %d ids.", index));
		}
		return true;		
	}
	
	/**
	 * Retrieve timesheets from a given start and end period.
	 * @param account
	 * @param start
	 * @param end
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean importTimesheets(Account account, Date start, Date end) {
		logger.info("Importing timesheets...");
		int offset = 0;
		long count = MAX_PER_PAGE;		

		TSheetsResponse<TimesheetsResults, TimesheetsSupData> response = null;

		String uri = String.format(getBaseUrl() + TIMESHEETS + TIMESHEETS_FROM_URL, count, 
				DateHelper.toString(start, DateHelper.MYSQL_DATE_PATTERN),
				DateHelper.toString(end, DateHelper.MYSQL_DATE_PATTERN));

		logger.info("\t...using base URL of " + uri);

		do {
			logger.debug("\t...processing page " + offset);
			// update the uri with the offset
			uri += "&page=" + offset;
			try {
				response = (TSheetsResponse<TimesheetsResults, TimesheetsSupData>) getResponse(
						account, uri, 	
						new TypeToken<TSheetsResponse<TimesheetsResults, TimesheetsSupData>>() {}.getType());

				if (response == null) {
					logger.error("Unable to retrieve Timesheets data from TSheets.");
					return false;
				}
			}
			catch(IOException e) {
				logger.error("An IO Exception has occurred: " + uri, e);
				// TODO return a custom error, such as TaskHaltingException that indicates the rest of the should
				// not proceed.
				/* TODO or the service should contain some sort of Error/Message List
				 * that contains info like the last record updated, etc.
				 */
				/*
				 * TODO or this service should record the last record or page & count that was successful
				 */
				return false;
			}

			// process each Timesheet
			for (TimesheetJson json : response.getResults().getTimesheets().values()) {
				// copy props to model			
				Timesheet timesheet = fromJson(account, json, response.getSupplementalData());
				// persist
				this.merge(timesheet);
			}
			// update the offset
			offset++;	
		} while (response != null && response.getResults().size() > 0);

		if (logger.isInfoEnabled()) {
			logger.info(String.format("Processed %d pages.", offset-1));
		}
		return true;		
	}

	/**
	 * TODO this should be changed such that the Service is not transactional because it is all or nothing on the import of
	 * timesheets. It would be better if this method would loop, calling another @transactional method that processed the 
	 * set of records, then records the progress, ie record the dates and the last page successfully processed
	 * @param account
	 * @param date
	 * @return
	 */	
	@SuppressWarnings("unchecked")
	public boolean importTimesheets(Account account, Date date, RangeType rangeType) {
		logger.info("Importing timesheets...");
		int offset = 0;
		long count = MAX_PER_PAGE;

		DateTime local = new DateTime();
		if (rangeType == RangeType.FROM_DAY && date == null) {
			date = local.minusDays(7).toDate();
		}

		Date startDate = date;
		Date endDate = local.toDate();
		TSheetsResponse<TimesheetsResults, TimesheetsSupData> response = null;

		// build base (less the page) uri
		String uri = "";
		if (rangeType == RangeType.FROM_DAY) {
			uri = String.format(getBaseUrl() + TIMESHEETS + TIMESHEETS_FROM_URL, MAX_PER_PAGE, offset,
					DateHelper.toString(startDate, DateHelper.MYSQL_DATE_PATTERN),
					DateHelper.toString(endDate, DateHelper.MYSQL_DATE_PATTERN));
		}
		else if (rangeType == RangeType.SINCE_TIMESTAMP) {
			if (date == null) {
				uri = String.format(getBaseUrl() + TIMESHEETS + TIMESHEETS_URL, MAX_PER_PAGE, offset);
			}
			else {
				uri = String.format(getBaseUrl() + TIMESHEETS + TIMESHEETS_SINCE_URL, 
						DateHelper.toString(date, DateHelper.ISO_8601_EPOCH_DATE_PATTERN), 
						MAX_PER_PAGE, offset);
			}
		}

		logger.info("\t...using base URL of " + uri);

		do {
			logger.debug("\t...processing page " + offset);
			// update the uri with the offset
			uri += "&page=" + offset;

			// TEMP
			//			String uri = getBaseUrl() + TIMESHEETS_URL;

			//			logger.info("page offset: " + offset);
			//			logger.debug("URL:" + uri);

			try {
				response = (TSheetsResponse<TimesheetsResults, TimesheetsSupData>) getResponse(
						account, uri, 	
						new TypeToken<TSheetsResponse<TimesheetsResults, TimesheetsSupData>>() {}.getType());

				if (response == null) {
					logger.error("Unable to retrieve Timesheets data from TSheets.");
					return false;
				}
			}
			catch(IOException e) {
				logger.error("An IO Exception has occurred: " + uri, e);
				// TODO return a custom error, such as TaskHaltingException that indicates the rest of the should
				// not proceed.
				/* TODO or the service should contain some sort of Error/Message List
				 * that contains info like the last record updated, etc.
				 */
				/*
				 * TODO or this service should record the last record or page & count that was successful
				 */
				return false;
			}

			// process each Timesheet
			for (TimesheetJson json : response.getResults().getTimesheets().values()) {
				// copy props to model			
				Timesheet timesheet = fromJson(account, json, response.getSupplementalData());
				// persist
				this.merge(timesheet);
			}
			// update the offset
			offset++;	
		} while (response != null && response.getResults().size() > 0);

		if (logger.isInfoEnabled()) {
			logger.info(String.format("Processed %d pages.", offset-1));
		}
		return true;
	}
	
	/**
	 * 
	 * @param account
	 * @param date
	 * @param rangeType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean importDeletedTimesheets(Account account, Date date, RangeType rangeType) {
		logger.info("Importing deleted timesheets...");
		int offset = 0;

		DateTime local = new DateTime();
		if (rangeType == RangeType.FROM_DAY && date == null) {
			date = local.minusDays(7).toDate();
		}

		Date startDate = date;
		Date endDate = local.toDate();

		// build base (less the page) uri
		String uri = "";
		if (rangeType == RangeType.FROM_DAY) {
			uri = String.format(getBaseUrl() + TIMESHEETS_DELETED + TIMESHEETS_FROM_URL, MAX_PER_PAGE, offset,
					DateHelper.toString(startDate, DateHelper.MYSQL_DATE_PATTERN),
					DateHelper.toString(endDate, DateHelper.MYSQL_DATE_PATTERN));
		}
		else if (rangeType == RangeType.SINCE_TIMESTAMP) {
			if (date == null) {
				// TODO this is wrong to capture all timesheets.
				uri = String.format(getBaseUrl() + TIMESHEETS_DELETED + TIMESHEETS_URL, MAX_PER_PAGE, offset);
			}
			else {
				uri = String.format(getBaseUrl() + TIMESHEETS_DELETED + TIMESHEETS_SINCE_URL, 
						DateHelper.toString(date, DateHelper.ISO_8601_EPOCH_DATE_PATTERN), 
						MAX_PER_PAGE, offset);
			}
		}

		logger.info("\t...using base URL of " + uri);

		TSheetsResponse<TimesheetsDeletedResults, TimesheetsSupData> response = null;
		do {
			logger.debug("\t...processing page " + offset);
			// update the uri with the offset
			uri += "&page=" + offset;

			try {
				response = getTimesheetsDeletedResponse(account, uri);
			}
			catch(IOException e) {
				logger.error("An IO Exception has occurred: " + uri, e);
			}
			
			// process each Timesheet
			for (TimesheetJson json : response.getResults().getTimesheets().values()) {
				// copy props to model			
				Timesheet timesheet = fromJson(account, json, response.getSupplementalData());
				// update timesheet to indicate it is deleted
				timesheet.setDeleted(true);
				// persist
				this.merge(timesheet);
			}
			// update the offset
			offset++;	
		} while (response != null && response.getResults().size() > 0);

		if (logger.isInfoEnabled()) {
			logger.info(String.format("Processed %d pages.", offset-1));
		}
		return true;
	}
	
	/**
	 * TODO hook into all the non-deleted queries
	 * @param uri
	 */
	private boolean retrieveTimesheets(Account account, String uri, int offset) {
		TSheetsResponse<TimesheetsResults, TimesheetsSupData> response = null;
		
		do {
			logger.debug("\t...processing page " + offset);
			// update the uri with the offset
			uri += "&page=" + offset;

			try {
				response = getTimesheetResponse(account, uri);
			}
			catch(IOException e) {
				logger.error("An IO Exception has occurred: " + uri, e);
				return false;
			}
			
			// process each Timesheet
			for (TimesheetJson json : response.getResults().getTimesheets().values()) {
				// copy props to model			
				Timesheet timesheet = fromJson(account, json, response.getSupplementalData());
				// persist
				this.merge(timesheet);
			}
			// update the offset
			offset++;	
		} while (response != null && response.getResults().size() > 0);
		return true;
	}

	/**
	 * 
	 * @param account
	 * @param uri
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private TSheetsResponse<TimesheetsResults, TimesheetsSupData> getTimesheetResponse(Account account, String uri)
		throws IOException {
		TSheetsResponse<TimesheetsResults, TimesheetsSupData> response = null;

		response = (TSheetsResponse<TimesheetsResults, TimesheetsSupData>) getResponse(
				account, uri, 	
				new TypeToken<TSheetsResponse<TimesheetsResults, TimesheetsSupData>>() {}.getType());

		if (response == null) {
			logger.error("Unable to retrieve Timesheets data from TSheets.");
		}
		return response;
	}
	
	/**
	 * 
	 * @param account
	 * @param uri
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private TSheetsResponse<TimesheetsDeletedResults, TimesheetsSupData> getTimesheetsDeletedResponse(Account account, String uri)
		throws IOException {
		TSheetsResponse<TimesheetsDeletedResults, TimesheetsSupData> response = null;

		response = (TSheetsResponse<TimesheetsDeletedResults, TimesheetsSupData>) getResponse(
				account, uri, 	
				new TypeToken<TSheetsResponse<TimesheetsDeletedResults, TimesheetsSupData>>() {}.getType());

		if (response == null) {
			logger.error("Unable to retrieve TimesheetsDeleted data from TSheets.");
		}
		return response;
	}
	
	
	// TODO change to take startDate and endDate as params
	@SuppressWarnings("unchecked")
	public void importTimesheets(Account account) {
		int offset = 0;
		long count = MAX_PER_PAGE;

		DateTime local = new DateTime();
		Date startDate = local.minusDays(7).toDate();
		Date endDate = local.toDate();
		TSheetsResponse<TimesheetsResults, TimesheetsSupData> response = null;

		do {
			String uri = String.format(getBaseUrl() + TIMESHEETS_URL, MAX_PER_PAGE, offset,
					DateHelper.toString(startDate, DateHelper.MYSQL_DATE_PATTERN),
					DateHelper.toString(endDate, DateHelper.MYSQL_DATE_PATTERN));

			logger.info("page offset: " + offset);
			logger.debug("URL:" + uri);

			try {
				response = (TSheetsResponse<TimesheetsResults, TimesheetsSupData>) getResponse(
						account, uri, 	
						new TypeToken<TSheetsResponse<TimesheetsResults, TimesheetsSupData>>() {}.getType());

				if (response == null) {
					logger.error("Unable to retrieve Timesheets data from TSheets.");
					return;
				}
			}
			catch(IOException e) {
				logger.error("An IO Exception has occurred: " + uri, e);
				// TODO return a custom error, such as TaskHaltingException that indicates the rest of the should
				// not proceed.
				/* TODO or the service should contain some sort of Error/Message List
				 * that contains info like the last record updated, etc.
				 */
				/*
				 * TODO or this service should record the last record or page & count that was successful
				 */
				return;
			}

			// process each Timesheet
			for (TimesheetJson json : response.getResults().getTimesheets().values()) {
				logger.debug("timesheet json:" + json);
				//				logger.debug("sup data:" + response.getSupplementalData().toString());
				// copy props to model			
				Timesheet timesheet = fromJson(account, json, response.getSupplementalData());
				// persist
				this.merge(timesheet);
			}
			// update the offset
			offset++;	
			//			 >>>>>>>>>>>>> TEMP
			break;
		} while (response != null && response.getResults().size() > 0);
	}

	/**
	 * 
	 * @param account
	 * @param json
	 * @return
	 */
	private Timesheet fromJson(Account account, TimesheetJson json, TimesheetsSupData sup) {
		// get the timesheet from db		
		Timesheet persistedTimesheet = this.getById(json.getId());
		
		// build  a new timesheet
		Timesheet timesheet = Timesheet.mapFromJson(json);
		
//		logger.debug("timesheet:" + timesheet.getId());
		String userKey = String.valueOf(json.getUserId());
		// add the user from the supplemental data
		if (sup != null && sup.getUsers() != null && sup.getUsers().containsKey(userKey)) {
			UserJson userJson = sup.getUsers().get(userKey);
			
			// check if user exists in the db first
			User user = userService.getById(userJson.getId());
			if (user == null) {
				user = User.mapFromJson(userJson);
							
				// update group
				Group group = groupService.getById(userJson.getGroupId());
				if (group != null) {
					user.setGroup(group);					
				}
				else {
					logger.warn("Didn't find the group recrod in the database by id: " + userJson.getGroupId());		
				}
			
				// save the user
				userService.merge(user);
			}

			// update timesheet with user-derived data
			// NOTE want to avoid using the TSHEETS_CUSTOM_FIELD tables
			timesheet.setUser(user);
			// add supplemental data that is based on user
			timesheet.mapFromJson(persistedTimesheet, user);
			timesheet.mapCustomFieldsFromMap(persistedTimesheet, userJson.getCustomFields());
		}

		// get job codes
		String jobCodeKey = String.valueOf(json.getJobCodeId());
		if (sup != null && sup.getJobCodes() != null && sup.getJobCodes().containsKey(jobCodeKey)) {
			JobCodeJson jcJson = sup.getJobCodes().get(jobCodeKey);
			//			logger.debug("jobcode json: " + jcJson);
			JobCode code = JobCode.mapFromJson(jcJson);
			jobCodeService.merge(code);
			timesheet.setJobCode(code);
		}		

		// >>>>>>>
		this.merge(timesheet);

		// TODO this is creating multiple entries for the same custom id
		// for every customfield item
		if (json.getCustomFields() != null && json.getCustomFields().size() > 0) {
//			logger.debug("Has Custom Fields");
			for (Entry<String, String> entry : json.getCustomFields().entrySet()) {
//				logger.debug("Processing custom field: " + entry.getKey() + ": " + entry.getValue());
				// if the custom field has a value set
				if (entry.getValue() != null && !entry.getValue().trim().equals("")) {
					// fetch the Custom Field
//					logger.debug("Fetching custom field...");
					CustomField cf = customFieldService.getById(Long.valueOf(entry.getKey()));
					if (cf == null) {
						// fetch from TSheets
//						logger.warn("Didn't find the custom field in database");
						cf = customFieldService.getTSheetById(account, entry.getKey());
					}

/* DO NOT fetch the Item; just concern ourselves with the field and the value - 4/26/17
					// fetch the Custom Field Item by CF and CFI name
					CustomFieldItem cfi = customFieldItemService.getByNameAndCustomFieldId(entry.getValue(), cf.getId());
//					logger.debug("Fetching Custom Field Item...");
					if (cfi == null) {
						logger.warn("Didn't find the custom field item in the database by name/id: " + entry.getValue() + ", " + cf.getId());					
						customFieldItemService.importCustomFieldItems(account, cf, null);
					}
*/
					
					if (cf != null /*&& cfi != null*/) {
						// TODO Check if the custom value already exists!
//						logger.debug(String.format("Fetching Custom Field Value for timesheet: %d, custom field: %d...", timesheet.getId(), cf.getId()));
						List<CustomFieldValue> vals = customFieldValueService.getByTimeSheetAndCustomField(timesheet.getId(), cf.getId());
						CustomFieldValue val = null;
						if (vals == null || vals.isEmpty()) {
//							logger.debug(String.format("%d: Adding custom field value: %s ", timesheet.getId(), entry.getValue()));
// 4/26/217							val = new CustomFieldValue(entry.getValue(), timesheet, cf, cfi);
							val = new CustomFieldValue();
							val.setValue(entry.getValue());
							val.setTimesheet(timesheet);
							val.setCustomField(cf);
							
							customFieldValueService.merge(val);
							timesheet.getCustomFieldValue().add(val);
						}
						// test if the same value
						else {
//							logger.debug("vals.size: " + vals.size());
//							logger.debug("Deleting old custom field values...");
//							timesheet.getCustomFieldValue().clear(); // <-- why am i clearing this
							for (CustomFieldValue v : vals) {
								customFieldValueService.delete(v);
//								logger.debug("Deleting " + v.getId() + ": " + v.getValue());
								timesheet.getCustomFieldValue().remove(v);
							}
//														logger.debug(timesheet.getId() + ": Updating custom field value: " + entry.getValue());
							//							timesheet.getCustomFieldValue().remove(val);
// 4/26/17							val = new CustomFieldValue(entry.getValue(), timesheet, cf, cfi);
							val = new CustomFieldValue();
							val.setValue(entry.getValue());
							val.setTimesheet(timesheet);
							
							val.setCustomField(cf);							
//							logger.debug(String.format("Updated custom field: %s", val));
							customFieldValueService.merge(val);
							timesheet.getCustomFieldValue().add(val);
						}
					}
				}
			}
			// add custom fields explicit data
			timesheet.mapFromJson(timesheet.getCustomFieldValue());
		}

		// final processing of additional calculated fields/columns
		timesheet.updateCalcs();
//		logger.debug("Final timesheet:" + timesheet);
		return timesheet;
	}

	/**
	 * @param startDate
	 * @param endDate
	 * @param filters
	 * @param sorting
	 * @return
	 */
	public List<TimesheetReportDto> getTimesheetReport(Date startDate, Date endDate, Map<String, Object> filters,
			Map<String, String> sorting) {
		return timesheetDao.findTimesheetReport(startDate, endDate, filters, sorting);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	// NOTE not sure this is transactional will work since there is one on the class.
	@Transactional(transactionManager="tsheetsTxMgr", readOnly = true)
	public Timesheet getById(long id) {
		return timesheetDao.findById(id);
	}

	/**
	 * 
	 * @param Timesheet
	 */
	public Timesheet save(Timesheet timesheet) {
		return timesheetDao.save(timesheet);
	}

	/**
	 * 
	 * @param Timesheet
	 */
	public void persist(Timesheet timesheet) {
		timesheetDao.persist(timesheet);
	}

	/**
	 * 
	 * @param entity
	 */
	public void merge(Timesheet entity) {
		timesheetDao.merge(entity);
	}
}
