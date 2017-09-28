/**
 * 
 */
package com.compucom.serviceops.tsheetsapi.service;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.compucom.serviceops.tsheetsapi.dao.GroupDao;
import com.compucom.serviceops.tsheetsapi.dao.UserDao;
import com.compucom.serviceops.tsheetsapi.json.GroupJson;
import com.compucom.serviceops.tsheetsapi.json.UserJson;
import com.compucom.serviceops.tsheetsapi.json.response.TSheetsResponse;
import com.compucom.serviceops.tsheetsapi.json.response.UsersResults;
import com.compucom.serviceops.tsheetsapi.json.response.UsersSupData;
import com.compucom.serviceops.tsheetsapi.model.Account;
import com.compucom.serviceops.tsheetsapi.model.Group;
import com.compucom.serviceops.tsheetsapi.model.User;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * @author Mark Gottschling on Oct 31, 2016
 *
 */
@Service("groupService")
@Transactional(transactionManager="tsheetsTxMgr")
public class GroupService extends AbstractTSheetsService {
	private static final Logger logger = LogManager.getLogger(GroupService.class);

	@Autowired GroupDao groupDao;
	
//	/**
//	 * 
//	 * @param account
//	 * @param json
//	 * @return
//	 */
//	private User fromJson(Account account, UserJson json, UsersSupData sup) {
//		User user = User.mapFromJson(json);
//		String key = String.valueOf(json.getGroupId());
//		// add the group from the supplemental data
//		if (sup.getGroups() != null && sup.getGroups().containsKey(key)) {
//			GroupJson groupJson = sup.getGroups().get(key);
//			Group group = Group.mapFromJson(groupJson);
//			groupDao.merge(group); // TODO change to groupService.merge() or save()
//			user.setGroup(group);
//		}
//		return user;
//	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Group getById(long id) {
		return groupDao.findById(id);
	}
	
	/**
	 * 
	 * @param Group
	 */
	public Group save(Group group) {
		return groupDao.save(group);
	}
	
	/**
	 * 
	 * @param Group
	 */
	public void persist(Group group) {
		groupDao.persist(group);
	}
	
	/**
	 * 
	 * @param entity
	 */
	public void merge(Group entity) {
		groupDao.merge(entity);
	}
}
