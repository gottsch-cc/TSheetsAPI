/**
 * 
 */
package com.compucom.serviceops.tsheetsapi.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.compucom.serviceops.tsheetsapi.model.UpdateHistory;
import com.compucom.serviceops.tsheetsapi.model.User;

/**
 * @author Mark Gottschling on Oct 28, 2016
 *
 */
@Repository("updateHistoryDao")
public class UpdateHistoryDao extends AbstractTSheetsDao<UpdateHistory> {
	private static final Logger logger = LogManager.getLogger(UpdateHistoryDao.class);

	
    /**
     * 
     * @param id
     * @return
     */
    public UpdateHistory findByName(String name) {
    	UpdateHistory record;
		try {
			Criteria criteria = getSession().createCriteria(this.persistentClass);
			criteria.add( Restrictions.eq("name", name));
			record = (UpdateHistory) criteria.uniqueResult();
		}
		catch(Exception e) {
			logger.error("An error occurred: ", e);
    		e.printStackTrace();
    		return null;			
		}
		return record;
    }
}
