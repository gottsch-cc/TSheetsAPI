/**
 * 
 */
package com.compucom.serviceops.tsheetsapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.compucom.serviceops.tsheetsapi.dao.UpdateHistoryDao;
import com.compucom.serviceops.tsheetsapi.model.UpdateHistory;

/**
 * @author Mark Gottschling on Jan 26, 2017
 *
 */
@Service("updateHistoryService")
@Transactional(transactionManager="tsheetsTxMgr")
public class UpdateHistoryService {

	@Autowired private UpdateHistoryDao dao;
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public UpdateHistory getByName(String name) {
		return dao.findByName(name);
	}
	
	/**
	 * 
	 * @param UpdateHistory
	 */
	public UpdateHistory save(UpdateHistory updateHistory) {
		return dao.save(updateHistory);
	}
	
	/**
	 * 
	 * @param UpdateHistory
	 */
	public void persist(UpdateHistory updateHistory) {
		dao.persist(updateHistory);
	}
	
	/**
	 * 
	 * @param entity
	 */
	public void merge(UpdateHistory entity) {
		dao.merge(entity);
	}
}
