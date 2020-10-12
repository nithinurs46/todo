package com.todo.daoImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.todo.dao.UserDao;
import com.todo.forms.TaskForm;
import com.todo.forms.UserForm;

@Repository
public class UserDaoImpl implements UserDao {

	@Value("${page.size}")
	private int pageSize;

	
	@Autowired
	private EntityManager entityManager;

	private Session getSession() {
		return entityManager.unwrap(Session.class);
	}

	@Override
	public UserForm getUser(String userName) {
		Session session = getSession();
		UserForm form = (UserForm) session.get(UserForm.class, userName);
		return form;
	}

	@Override
	public void addUser(UserForm regForm) {
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		session.save(regForm);
		tx.commit();
	}

	@Override
	public UserForm updateUser(UserForm form) {
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		session.update(form);
		tx.commit();
		return form;
	}

	@Override
	public void deleteUser(String username) {
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		UserForm form = (UserForm) session.load(UserForm.class, username);
		if (null != form) {
			session.delete(form);
		}
		tx.commit();
	}

	@Override
	public Map<String, Object> getAllUsers(int pageNo) {
		Session session = getSession();
		Long size = getTotalCount();
		Query query = session.createQuery("from TODO_USERS");
		int startPos = getStartPos(pageNo-1);
		query.setFirstResult(startPos);
		query.setMaxResults(pageSize);
		List<UserForm> usersList = query.getResultList();
		Map<String, Object> usersData = new HashMap<String, Object>();
		usersData.put("recSize", size-1);
		usersData.put("recList", usersList);
		return usersData;
	}
	
	private int getStartPos(int pageNo) {
		int startPos = (pageSize+1) * pageNo;
		return startPos;
	}
	
	private Long getTotalCount() {
		Session session = getSession();
		String countQ = "Select count (u.username) from TODO_USERS u";
	    Query countQuery = session.createQuery(countQ);
	    Long countResults = (Long) countQuery.uniqueResult();
	    return countResults;
	}

}
