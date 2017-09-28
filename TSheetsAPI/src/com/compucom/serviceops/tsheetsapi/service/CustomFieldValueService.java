/**
 * 
 */
package com.compucom.serviceops.tsheetsapi.service;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.compucom.serviceops.tsheetsapi.dao.CustomFieldDao;
import com.compucom.serviceops.tsheetsapi.dao.CustomFieldValueDao;
import com.compucom.serviceops.tsheetsapi.json.CustomFieldJson;
import com.compucom.serviceops.tsheetsapi.json.response.CustomFieldsResults;
import com.compucom.serviceops.tsheetsapi.json.response.CustomFieldsSupData;
import com.compucom.serviceops.tsheetsapi.json.response.TSheetsResponse;
import com.compucom.serviceops.tsheetsapi.model.Account;
import com.compucom.serviceops.tsheetsapi.model.CustomField;
import com.compucom.serviceops.tsheetsapi.model.CustomFieldValue;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * @author Mark Gottschling on Dec 5, 2016
 *
 */
@Service("customFieldValueService")
@Transactional(transactionManager="tsheetsTxMgr")
public class CustomFieldValueService extends AbstractTSheetsService {
	private static final Logger logger = LogManager.getLogger(CustomFieldValueService.class);

	
	@Autowired CustomFieldValueDao customFieldValueDao;
	

	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public CustomFieldValue getById(long id) {
		return customFieldValueDao.findById(id);
	}
		
	/**
	 * 
	 * @param CustomField
	 */
	public CustomFieldValue save(CustomFieldValue c) {
		return customFieldValueDao.save(c);
	}
	
	/**
	 * 
	 * @param CustomField
	 */
	public void persist(CustomFieldValue customField) {
		customFieldValueDao.persist(customField);
	}
	
	/**
	 * 
	 * @param entity
	 */
	public void merge(CustomFieldValue entity) {
		customFieldValueDao.merge(entity);
	}

	/**
	 * @return
	 */
	public List<CustomFieldValue> getAll() {
		return customFieldValueDao.findAll();
	}

	/**
	 * @param timesheetID
	 * @param customFieldID
	 * @return
	 */
	public List<CustomFieldValue> getByTimeSheetAndCustomField(long timesheetID, long customFieldID) {
		return customFieldValueDao.findByTimeSheetIdAndCustomFieldId(timesheetID, customFieldID);
	}

	/**
	 * @param v
	 */
	public void delete(CustomFieldValue entity) {
		customFieldValueDao.delete(entity);		
	}
}
