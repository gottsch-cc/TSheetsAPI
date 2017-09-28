/**
 * 
 */
package com.compucom.serviceops.tsheetsapi.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.compucom.serviceops.tsheetsapi.model.Group;
import com.compucom.serviceops.tsheetsapi.model.User;

/**
 * 
 * @author Mark Gottschling on Oct 31, 2016
 *
 */
@Repository("groupDao")
public class GroupDao extends AbstractTSheetsDao<Group> {
	private static final Logger logger = LogManager.getLogger(GroupDao.class);

}
