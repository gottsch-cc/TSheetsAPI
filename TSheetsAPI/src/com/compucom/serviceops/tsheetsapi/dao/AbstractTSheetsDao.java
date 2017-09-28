/**
 * 
 */
package com.compucom.serviceops.tsheetsapi.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.compucom.serviceops.dao.AbstractDao;

/**
 * 
 * @author Mark Gottschling on Oct 28, 2016
 *
 * @param <T>
 */
public abstract class AbstractTSheetsDao<T> extends AbstractDao<T> {
 
	@Autowired
	@Qualifier("tsheetsSessionFactory")
    private SessionFactory sessionFactory;
 
	@Override
    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }
	
	/**
	 * 
	 * @param lightspeedId
	 * @param idName
	 * @return
	 */
	public T findAllByTSheetsId(long tsheetsId, String idName) {
    	T record;
		try {
			Criteria criteria = getSession().createCriteria(this.persistentClass);
			criteria.add( Restrictions.eq(idName, tsheetsId));
			record = (T) criteria.uniqueResult();
		}
		catch(Exception e) {
			logger.error("An error occurred: ", e);
    		e.printStackTrace();
    		return null;			
		}
		return record;
	}
}
