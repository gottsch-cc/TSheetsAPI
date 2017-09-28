/**
 * 
 */
package com.compucom.serviceops.tsheetsapi.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.compucom.serviceops.tsheetsapi.model.CustomField;
import com.compucom.serviceops.tsheetsapi.model.Group;
import com.compucom.serviceops.tsheetsapi.model.User;

/**
 * 
 * @author Mark Gottschling on Dec 5, 2016
 *
 */
@Repository("customFieldDao")
public class CustomFieldDao extends AbstractTSheetsDao<CustomField> {
	private static final Logger logger = LogManager.getLogger(CustomFieldDao.class);

}
