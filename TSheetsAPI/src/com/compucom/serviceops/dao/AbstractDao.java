/**
 * 
 */
package com.compucom.serviceops.dao;



import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import com.compucom.serviceops.tsheetsapi.date.DateHelper;

/**
 * 
 * @author Mark Gottschling on Apr 8, 2016
 *
 * @param <T>
 */
public abstract class AbstractDao<T> {
	final protected Logger logger = LogManager.getLogger(AbstractDao.class);
	final Map<String, Object> EMPTY_FILTER = new HashMap<>();
	
	protected Class<T> persistentClass;

    abstract protected Session getSession();
 
    @SuppressWarnings("unchecked")
	public AbstractDao() {
        this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    
	/*
	 * Created this method so that Generic Daos could be created having a parameterized type of Object or IPesistable.
	 */
	public AbstractDao(Class<T> type) {
        this.persistentClass = type;
    }
	
	/**
	 * Save
	 * @param entity
	 */
    public void persist(T entity) {
        getSession().persist(entity);
    }
 
    /**
     * 
     * @param entity
     * @return
     */
    public T save(T entity) {
    	getSession().save(entity);
    	return entity;
    }
    
    /**
     * 
     * @param entity
     * @return
     */
    public T merge(T entity) {
    	@SuppressWarnings("unchecked")
		T newEntity = (T) getSession().merge(entity);
    	return entity;
    }
    
    /**
     * 
     * @param entity
     */
    public void update(T entity) {
    	getSession().update(entity);
    }
    
	/**
	 * 
	 */
	public void rollback() {
		Transaction tx = getSession().getTransaction();
		
		// roll back the transaction if it hasn't been already
		if (tx.isActive() && !tx.wasCommitted() && !tx.wasRolledBack()) {
			logger.debug("Rolling back transaction.");
			tx.rollback();
		}
	}
	
    /** Delete
     * 
     * @param entity
     */
    public void delete(Object entity) {
        getSession().delete(entity);
    }
    
    /**
     * 
     */
	public void deleteAll() {
		/*
		 * NOTE this is fine if there is nothing to cascade as hibernate handles cascades internally - not the db.
		 * If there is a need to cascade then deleting over a collection or explicitly deleting all cascades is requried.
		 */
	    String hql = String.format("delete from " + this.persistentClass.getSimpleName());
	    Query query = getSession().createQuery(hql);
	    query.executeUpdate();
	}
	
    /**
     * 
     * @param id
     * @return
     */
    public T findById(Long id) {
    	T record;
		try {
			Criteria criteria = getSession().createCriteria(this.persistentClass);
			criteria.add( Restrictions.eq("id", id));
			record = (T) criteria.uniqueResult();
		}
		catch(Exception e) {
			logger.error("An error occurred: ", e);
    		e.printStackTrace();
    		return null;			
		}
		return record;
    }
    
    /**
     * 
     * @return
     */
    public List<T> findAll() {
    	Criteria criteria = getSession().createCriteria(this.persistentClass);
    	try {
    		return (List<T>) criteria.list();
    	}
		catch(Exception e) {
			logger.error("An error occurred in findAll:", e);
			e.printStackTrace();
			return null;
		}
    }
    
	@SuppressWarnings("unchecked")
	public List<T> findAll(int page, int count, Map<String, Object> filters, Map<String, String> sorting) {
    	try {
           Criteria criteria = getCriteria(page, count, filters, sorting);
            
//            CriteriaImpl criteriaImpl = (CriteriaImpl)criteria;
//            SessionImplementor session = criteriaImpl.getSession();
//            SessionFactoryImplementor factory = session.getFactory();
//            CriteriaQueryTranslator translator=new CriteriaQueryTranslator(factory,criteriaImpl,criteriaImpl.getEntityOrClassName(),CriteriaQueryTranslator.ROOT_SQL_ALIAS);
//            String[] implementors = factory.getImplementors( criteriaImpl.getEntityOrClassName() );
//
//            CriteriaJoinWalker walker = new CriteriaJoinWalker((OuterJoinLoadable)factory.getEntityPersister(implementors[0]), 
//                                    translator,
//                                    factory, 
//                                    criteriaImpl, 
//                                    criteriaImpl.getEntityOrClassName(), 
//                                    session.getLoadQueryInfluencers()   );
//
//            String sql=walker.getSQLString();
//            
//            logger.info("Generated SQL:" + sql);
            
            return (List<T>) criteria.list();
    	}
    	catch(Exception e) {
    		logger.error("An error occurred in findAll:", e);
    		e.printStackTrace();
    		return null;
    	}
	}
	
	/**
	 * 
	 * @param page
	 * @param count
	 * @param filters
	 * @param sorting
	 * @param c
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll(int page, int count, Map<String, Object> filters, Map<String, String> sorting, Class<T> c) {
    	try {
           Criteria criteria = getCriteria(page, count, filters, sorting);
           
			// transform result set to LeaseRequest object
			criteria.setResultTransformer(Transformers.aliasToBean(c));
			
			// return list
            return (List<T>) criteria.list();
    	}
    	catch(Exception e) {
    		logger.error("An error occurred in findAll:", e);
    		e.printStackTrace();
    		return null;
    	}
	}
	
	/**
	 * 
	 * @param page
	 * @param count
	 * @param filters
	 * @param sorting
	 * @return
	 */
	public Criteria getCriteria(int page, int count, Map<String, Object> filters, Map<String, String> sorting) {	
		Criteria criteria = getSession().createCriteria(this.persistentClass);
		// add filters
		if (filters != null && !filters.isEmpty()) {
			for (Entry<String, Object> e : filters.entrySet()) {
				if (e.getValue() instanceof Date) {
					// add a gt and lt Restriction
					Date d = DateHelper.getDateWithoutTime((Date) e.getValue());
					criteria.add(Restrictions.gt(e.getKey(), d));
					criteria.add(Restrictions.lt(e.getKey(), DateHelper.getTomorrowDate(d)));
				}
				else {
					criteria.add( Restrictions.eq(e.getKey(), e.getValue()));
				}
			}
		}
		// add sorting
		if (sorting != null && !sorting.isEmpty()) {
			for (Entry<String, String> e : sorting.entrySet()) {
				if (e.getValue().equalsIgnoreCase("desc")) {
					criteria.addOrder(Order.desc(e.getKey()));
				}
				else {
					criteria.addOrder(Order.asc(e.getKey()));
				}
			}
		}
		applyPaging(criteria, page, count);
        
        return criteria;
	}
	
	/**
	 * 
	 * @param criteria
	 * @param filters
	 * @return
	 */
	public Criteria applyFilters(Criteria criteria, Map<String, Object> filters) {
		// add filters
		if (filters != null && !filters.isEmpty()) {
			for (Entry<String, Object> e : filters.entrySet()) {
				if (e.getValue() instanceof Date) {
					// add a gt and lt Restriction
					Date d = DateHelper.getDateWithoutTime((Date) e.getValue());
					criteria.add(Restrictions.gt(e.getKey(), d));
					criteria.add(Restrictions.lt(e.getKey(), DateHelper.getTomorrowDate(d)));
				}
				else {
					criteria.add( Restrictions.eq(e.getKey(), e.getValue()));
				}
			}
		}
		return criteria;
	}
	
	/**
	 * 
	 * @param criteria
	 * @param sorting
	 * @return
	 */
	public Criteria applySorting(Criteria criteria, Map<String, String> sorting) {
		// add sorting
		if (sorting != null && !sorting.isEmpty()) {
			for (Entry<String, String> e : sorting.entrySet()) {
				if (e.getValue().equalsIgnoreCase("desc")) {
					criteria.addOrder(Order.desc(e.getKey()));
				}
				else {
					criteria.addOrder(Order.asc(e.getKey()));
				}
			}
		}
		return criteria;
	}
	
	/**
	 * 
	 * @param criteria
	 * @param page
	 * @param count
	 * @return
	 */
	public Criteria applyPaging(Criteria criteria, int page, int count) {
        criteria.setFirstResult((page) * count);
        criteria.setMaxResults(count);
        return criteria;
	}
	
	/**
	 * 
	 * @param filters
	 * @return
	 */
	public Long countAll(Map<String, Object> filters) {
		Long count = 0L;
		try {
			Criteria criteria = getSession().createCriteria(this.persistentClass);
			if (filters != null && !filters.isEmpty()) {
				for (Entry<String, Object> e : filters.entrySet()) {
					/*
					 *  if the filter is a Date object
					 *  the default behaviour is to compare if it is the same day (regardless of time)
					 */
					if (e.getValue() instanceof Date) {
						// add a gt and lt Restriction
						Date d = DateHelper.getDateWithoutTime((Date) e.getValue());
						criteria.add(Restrictions.gt(e.getKey(), d));
						criteria.add(Restrictions.lt(e.getKey(), DateHelper.getTomorrowDate(d)));
					}
					else {
						criteria.add( Restrictions.eq(e.getKey(), e.getValue()));
					}
				}
			}
			criteria.setProjection(Projections.rowCount());
			count = (Long) criteria.uniqueResult();
			if (count == null) {
				count = new Long(0);
			}
		}
		catch(Exception e) {
			logger.error("An error occurred: ", e);
    		e.printStackTrace();
    		return 0L;			
		}
		return count;
	}
	
	/**
	 * Cleans input filter map, selecting only those that are valid to the Dao
	 * by interogatting the Dao's @Filters annotation.
	 * @param filters
	 * @return
	 */
	public Map<String, Object> getValidFilters(Map<String, Object> filters) {
		Annotation annotation = this.getClass().getAnnotation(Filters.class);
		if (annotation == null)	 {
			return EMPTY_FILTER;
		}
		
		Filter[] allowableFilters = this.getClass().getAnnotation(Filters.class).value();
//		logger.info("filter[0].filtername:" + allowableFilters[0].filterName());
//		logger.info("filter.propertyName:" + allowableFilters[0].propertyName());

		Map<String, Object> validFilters = new HashMap<>();
		for (Filter f : allowableFilters) {
			if (filters.containsKey(f.filterName())) {
				// use the property name as key to validFilters and the value from the web filter
				validFilters.put(f.propertyName(),  filters.get(f.filterName()));
			}
		}		
		return validFilters;
	}
}
