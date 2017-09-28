/**
 * 
 */
package com.compucom.serviceops.tsheetsapi.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.compucom.serviceops.tsheetsapi.model.JobCode;

/**
 * @author Mark Gottschling on Nov 14, 2016
 *
 */
@Repository("jobCodeDao")
public class JobCodeDao extends AbstractTSheetsDao<JobCode> {
	private static final Logger logger = LogManager.getLogger(JobCodeDao.class);

}
