/**
 * 
 */
package com.compucom.serviceops.tsheetsapi.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.compucom.serviceops.tsheetsapi.model.CustomField;
import com.compucom.serviceops.tsheetsapi.model.CustomFieldItem;
import com.compucom.serviceops.tsheetsapi.model.Group;
import com.compucom.serviceops.tsheetsapi.model.User;

/**
 * 
 * @author Mark Gottschling on Dec 5, 2016
 *
 */
@Repository("customFieldItemDao")
public class CustomFieldItemDao extends AbstractTSheetsDao<CustomFieldItem> {
	private static final Logger logger = LogManager.getLogger(CustomFieldItemDao.class);

	/**
	 * @param name
	 * @param l
	 * @return
	 */
	public CustomFieldItem findByNameAndCustomFieldId(String name, long l) {
	   	CustomFieldItem record;
			try {
				Criteria criteria = getSession().createCriteria(this.persistentClass);
				criteria.add(Restrictions.eq("name", name));
				criteria.createCriteria("customField").add(Restrictions.eq("id", l));
				record = (CustomFieldItem) criteria.uniqueResult();
			}
			catch(Exception e) {
				logger.error("An error occurred: ", e);
	    		e.printStackTrace();
	    		return null;			
			}
			return record;
	}

}
