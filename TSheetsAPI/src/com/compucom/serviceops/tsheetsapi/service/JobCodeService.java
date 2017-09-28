/**
 * 
 */
package com.compucom.serviceops.tsheetsapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.compucom.serviceops.tsheetsapi.dao.JobCodeDao;
import com.compucom.serviceops.tsheetsapi.model.JobCode;

/**
 * @author Mark Gottschling on Nov 14, 2016
 *
 */
@Service("jobCodeService")
@Transactional(transactionManager="tsheetsTxMgr")
public class JobCodeService {

	@Autowired JobCodeDao jobCodeDao;
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public JobCode getById(long id) {
		return jobCodeDao.findById(id);
	}
	
	/**
	 * 
	 * @param JobCode
	 */
	public JobCode save(JobCode jobCode) {
		return jobCodeDao.save(jobCode);
	}
	
	/**
	 * 
	 * @param JobCode
	 */
	public void persist(JobCode jobCode) {
		jobCodeDao.persist(jobCode);
	}
	
	/**
	 * 
	 * @param entity
	 */
	public void merge(JobCode entity) {
		jobCodeDao.merge(entity);
	}
}
