/**
 * 
 */
package com.compucom.serviceops.tsheetsapi.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.compucom.serviceops.tsheetsapi.dao.CustomFieldDao;
import com.compucom.serviceops.tsheetsapi.date.DateHelper;
import com.compucom.serviceops.tsheetsapi.json.CustomFieldJson;
import com.compucom.serviceops.tsheetsapi.json.response.CustomFieldsResults;
import com.compucom.serviceops.tsheetsapi.json.response.CustomFieldsSupData;
import com.compucom.serviceops.tsheetsapi.json.response.TSheetsResponse;
import com.compucom.serviceops.tsheetsapi.model.Account;
import com.compucom.serviceops.tsheetsapi.model.CustomField;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * @author Mark Gottschling on Dec 5, 2016
 *
 */
@Service("customFieldService")
@Transactional(transactionManager="tsheetsTxMgr")
public class CustomFieldService extends AbstractTSheetsService {
	private static final Logger logger = LogManager.getLogger(CustomFieldService.class);

	private static final String CFS_URL = "customfields?per_page=%d&page=%d";
	private static final String CFS_SINCE_URL = "customfields?modified_since=%s&per_page=%d&page=%d";
	
	private static final String CFS_APPLIES_TO_URL = "customfields?applies_to=%s&per_page=%d&page=%d";
	private static final String CFS_APPLIES_TO_SINCE_URL = "customfields?applies_to=%s&modified_since=%s&per_page=%d&page=%d";
	
	private static final String CFS_ID_URL = "customfields?ids=%s";
	private static final String CFS_ID_APPLIES_TO_URL = "customfields?ids=%s&applies_to=%s";
	
	public CustomField EMPTY_CUSTOM_FIELD = new CustomField();
	
	@Autowired CustomFieldDao customFieldDao;
	
	/**
	 * 
	 * @param account
	 */
	@SuppressWarnings("unchecked")
	public boolean importCustomFields(Account account, Date date, String appliesTo) {
		int offset = 0;

		TSheetsResponse<CustomFieldsResults, CustomFieldsSupData> response = null;
		do {
			String uri = "";

			if (date == null) {
				if (appliesTo != null && !appliesTo.equals("")) {
					uri = String.format(getBaseUrl() + CFS_APPLIES_TO_URL, "user", MAX_PER_PAGE, offset);
				}
				else {
					uri = String.format(getBaseUrl() + CFS_URL, MAX_PER_PAGE, offset);
				}
			}
			else {
				if (appliesTo != null && !appliesTo.equals("")) {
					uri = String.format(getBaseUrl() + CFS_APPLIES_TO_SINCE_URL, "user", 
							DateHelper.toString(date, DateHelper.ISO_8601_EPOCH_DATE_PATTERN), MAX_PER_PAGE, offset);

				}
				else {
					uri = String.format(getBaseUrl() + CFS_SINCE_URL, 
							DateHelper.toString(date, DateHelper.ISO_8601_EPOCH_DATE_PATTERN), MAX_PER_PAGE, offset);
				}
			}	
			
//			logger.info("page offset: " + offset);
//			logger.debug("URL:" + uri);
			try {
					response = (TSheetsResponse<CustomFieldsResults, CustomFieldsSupData>) getResponse(
							account, uri, 	
							new TypeToken<TSheetsResponse<CustomFieldsResults, CustomFieldsSupData>>() {}.getType());
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
			
			// process each CustomField
			for (CustomFieldJson json : response.getResults().getCustomFields().values()) {
				logger.debug("user json:" + json);
				// copy props to model			
				CustomField cf = fromJson(account, json, response.getSupplementalData());
				// persist
				this.merge(cf);
			}

			// update the offset
			offset++;	

		} while (response != null && response.getResults().size() > 0);
		
		return true;
	}
	
	/**
	 * 
	 * @param account
	 * @param json
	 * @return
	 */
	private CustomField fromJson(Account account, CustomFieldJson json, CustomFieldsSupData sup) {
		CustomField cf = CustomField.mapFromJson(json);
		return cf;
	}
	
	/**
	 * Basically has to do the same things as importCustomFields
	 * @param account
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public CustomField getTSheetById(Account account, String id) {

		TSheetsResponse<CustomFieldsResults, CustomFieldsSupData> response = null;

		String uri = String.format(getBaseUrl() + CFS_ID_URL, String.valueOf(id));

		logger.debug("URL:" + uri);
		try {
			response = (TSheetsResponse<CustomFieldsResults, CustomFieldsSupData>) getResponse(
				account, uri, new TypeToken<TSheetsResponse<CustomFieldsResults, CustomFieldsSupData>>() {}.getType());
		}
		catch(IOException e) {
			logger.error("An IO Exception has occurred: " + uri, e);
			return EMPTY_CUSTOM_FIELD;
		}
		
		// process each CustomField
		CustomFieldJson json = response.getResults().getCustomFields().get(id);
//		logger.debug("user json:" + json);
		// copy props to model			
		CustomField cf = fromJson(account, json, response.getSupplementalData());
		if (cf != null) {
			// persist
			this.merge(cf);
		}
		return cf;
	}
	
	/**
	 * Basically has to do the same things as importCustomFields
	 * @param account
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public CustomField getByIdAndAppliesTo(Account account, String id, String appliesTo) {

		TSheetsResponse<CustomFieldsResults, CustomFieldsSupData> response = null;

		String uri = String.format(getBaseUrl() + CFS_ID_APPLIES_TO_URL, String.valueOf(id), appliesTo);

		logger.debug("URL:" + uri);
		try {
			response = (TSheetsResponse<CustomFieldsResults, CustomFieldsSupData>) getResponse(
				account, uri, new TypeToken<TSheetsResponse<CustomFieldsResults, CustomFieldsSupData>>() {}.getType());
		}
		catch(IOException e) {
			logger.error("An IO Exception has occurred: " + uri, e);
			return EMPTY_CUSTOM_FIELD;
		}
		
		// process each CustomField
		CustomFieldJson json = response.getResults().getCustomFields().get(id);
//		logger.debug("user json:" + json);
		// copy props to model			
		CustomField cf = fromJson(account, json, response.getSupplementalData());
		if (cf != null) {
			// persist
			this.merge(cf);
		}
		return cf;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public CustomField getById(Long id) {
		return customFieldDao.findById(id);
	}
	
	
	
	/**
	 * 
	 * @param CustomField
	 */
	public CustomField save(CustomField c) {
		return customFieldDao.save(c);
	}
	
	/**
	 * 
	 * @param CustomField
	 */
	public void persist(CustomField customField) {
		customFieldDao.persist(customField);
	}
	
	/**
	 * 
	 * @param entity
	 */
	public void merge(CustomField entity) {
		customFieldDao.merge(entity);
	}

	/**
	 * @return
	 */
	public List<CustomField> getAll() {
		return customFieldDao.findAll();
	}
}
