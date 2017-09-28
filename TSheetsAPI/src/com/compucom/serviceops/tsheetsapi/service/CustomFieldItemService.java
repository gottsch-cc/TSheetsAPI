/**
 * 
 */
package com.compucom.serviceops.tsheetsapi.service;

import java.io.IOException;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.compucom.serviceops.tsheetsapi.dao.CustomFieldItemDao;
import com.compucom.serviceops.tsheetsapi.json.CustomFieldItemJson;
import com.compucom.serviceops.tsheetsapi.json.CustomFieldJson;
import com.compucom.serviceops.tsheetsapi.json.response.CustomFieldItemsResults;
import com.compucom.serviceops.tsheetsapi.json.response.CustomFieldItemsSupData;
import com.compucom.serviceops.tsheetsapi.json.response.CustomFieldsResults;
import com.compucom.serviceops.tsheetsapi.json.response.CustomFieldsSupData;
import com.compucom.serviceops.tsheetsapi.json.response.TSheetsResponse;
import com.compucom.serviceops.tsheetsapi.model.Account;
import com.compucom.serviceops.tsheetsapi.model.CustomField;
import com.compucom.serviceops.tsheetsapi.model.CustomFieldItem;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * @author Mark Gottschling on Dec 5, 2016
 *
 */
@Service("customFieldItemService")
@Transactional(transactionManager="tsheetsTxMgr")
public class CustomFieldItemService extends AbstractTSheetsService {
	private static final Logger logger = LogManager.getLogger(CustomFieldItemService.class);

	private static final String CFIS_URL = "customfielditems?customfield_id=%d";
	private static final String CFI_URL = "customfielditems?customfield_id=%d&ids=%d";

	@Autowired CustomFieldItemDao customFieldItemDao;
	@Autowired CustomFieldService customFieldService;
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public CustomFieldItem getById(long id) {
		return customFieldItemDao.findById(id);
	}
	
	/**
	 * 
	 * @param name
	 * @param l
	 * @return
	 */
	public CustomFieldItem getByNameAndCustomFieldId(String name, long l) {
		return customFieldItemDao.findByNameAndCustomFieldId(name, l);
	}
	
	/**
	 * 
	 * @param account
	 * @param customFieldId
	 * @param customFieldItemId
	 */
	@SuppressWarnings("unchecked")
	public void getCFI(Account account, int customFieldId, int customFieldItemId) {
		TSheetsResponse<CustomFieldItemsResults, CustomFieldItemsSupData> response = null;

			String uri = String.format(getBaseUrl() + CFI_URL, customFieldId, customFieldItemId);
			logger.debug("URL:" + uri);
			try {
					response = (TSheetsResponse<CustomFieldItemsResults, CustomFieldItemsSupData>) getResponse(
							account, uri, 	
							new TypeToken<TSheetsResponse<CustomFieldItemsResults, CustomFieldItemsSupData>>() {}.getType());
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
			
			// process each CustomFieldItem
			for (CustomFieldItemJson json : response.getResults().getItems().values()) {
				logger.debug("cfi json:" + json);
				// copy props to model			
				CustomFieldItem cfi = fromJson(account, json, response.getSupplementalData());
				// persist
				this.merge(cfi);
			}

	}
	
	
	/**
	 * 
	 * @param account
	 */
	@SuppressWarnings("unchecked")
	public void importCustomFieldItems(Account account, CustomField cf, Date date) {
		int offset = 0;

		TSheetsResponse<CustomFieldItemsResults, CustomFieldItemsSupData> response = null;

		String uri = String.format(getBaseUrl() + CFIS_URL, cf.getId());
//		logger.info("page offset: " + offset);
//		logger.debug("URL:" + uri);
		try {
				response = (TSheetsResponse<CustomFieldItemsResults, CustomFieldItemsSupData>) getResponse(
						account, uri, 	
						new TypeToken<TSheetsResponse<CustomFieldItemsResults, CustomFieldItemsSupData>>() {}.getType());
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
		
		// process each CustomFieldItem
		for (CustomFieldItemJson json : response.getResults().getItems().values()) {
//			logger.debug("cfi json:" + json);
			// copy props to model			
			CustomFieldItem cfi = fromJson(account, json, response.getSupplementalData());
			// fetch the CustomField
//				CustomField cf = customFieldService.getById(customFieldId);
			if (cf != null) {
				cfi.setCustomField(cf);
			}
			// persist
			this.merge(cfi);
		}

	}
	
	/**
	 * 
	 * @param account
	 * @param json
	 * @param sup
	 * @return
	 */
	private CustomFieldItem fromJson(Account account, CustomFieldItemJson json, CustomFieldItemsSupData sup) {
		CustomFieldItem cfi = CustomFieldItem.mapFromJson(json);

		// get the custom field from the database
		CustomField cf = customFieldService.getById(Long.valueOf(json.getCustomFieldId()));
		
		// get from tsheets
		if (cf == null) {
			// TODO
		}
		// update the cfi with the cf
		cfi.setCustomField(cf);

		return cfi;
	}
	
	/**
	 * 
	 * @param CustomFieldItem
	 */
	public CustomFieldItem save(CustomFieldItem c) {
		return customFieldItemDao.save(c);
	}
	
	/**
	 * 
	 * @param CustomFieldItem
	 */
	public void persist(CustomFieldItem customFieldItem) {
		customFieldItemDao.persist(customFieldItem);
	}
	
	/**
	 * 
	 * @param entity
	 */
	public void merge(CustomFieldItem entity) {
		customFieldItemDao.merge(entity);
	}
}
