/**
 * 
 */
package com.compucom.serviceops.tsheetsapi.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.compucom.serviceops.tsheetsapi.model.CustomField;
import com.compucom.serviceops.tsheetsapi.model.CustomFieldItem;
import com.compucom.serviceops.tsheetsapi.model.CustomFieldValue;
import com.compucom.serviceops.tsheetsapi.model.Group;
import com.compucom.serviceops.tsheetsapi.model.User;

/**
 * 
 * @author Mark Gottschling on Dec 6, 2016
 *
 */
@Repository("customFieldValueDao")
public class CustomFieldValueDao extends AbstractTSheetsDao<CustomFieldValue> {
	private static final Logger logger = LogManager.getLogger(CustomFieldValueDao.class);

	/**
	 * @param timesheetID
	 * @param customFieldID
	 */
	public List<CustomFieldValue> findByTimeSheetIdAndCustomFieldId(long timesheetID, long customFieldID) {
	   	List<CustomFieldValue> records;
		try {
			Criteria criteria = getSession().createCriteria(this.persistentClass);
			criteria.createCriteria("timesheet").add(Restrictions.eq("id", timesheetID));
			criteria.createCriteria("customField").add(Restrictions.eq("id", customFieldID));
//			logger.debug("sql: " +criteria.toString());
			records = criteria.list();
		}
		catch(Exception e) {
			logger.error(
					String.format("An error occurred retrieving custom field value by timesheetID %d customFieldID %d: ", timesheetID, customFieldID), e);
    		e.printStackTrace();
    		return null;			
		}
		return records;		
	}

}
