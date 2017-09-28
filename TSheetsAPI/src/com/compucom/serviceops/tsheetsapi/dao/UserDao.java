/**
 * 
 */
package com.compucom.serviceops.tsheetsapi.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.compucom.serviceops.tsheetsapi.model.User;

/**
 * @author Mark Gottschling on Oct 28, 2016
 *
 */
@Repository("userDao")
public class UserDao extends AbstractTSheetsDao<User> {
	private static final Logger logger = LogManager.getLogger(UserDao.class);

}
