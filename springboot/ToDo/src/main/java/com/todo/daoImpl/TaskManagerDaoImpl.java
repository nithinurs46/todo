package com.todo.daoImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.todo.dao.TaskManagerDao;
import com.todo.forms.TaskForm;

@Repository
public class TaskManagerDaoImpl implements TaskManagerDao {

	@Autowired
	private EntityManager entityManager;
	
	@Value("${page.size}")
	private int pageSize;

	private Session getSession() {
		return entityManager.unwrap(Session.class);
	}

	@Override
	public Optional<TaskForm> getTask(String taskName) {
		Session session = getSession();
		TaskForm taskForm = session.get(TaskForm.class, taskName);
		session.beginTransaction();
		return Optional.of(taskForm);
	}

	@Override
	public boolean addNewTask(TaskForm taskForm) {
		taskForm.setCompleted("false");
		Session session = getSession();
		session.beginTransaction();
		session.save(taskForm);
		session.getTransaction().commit();
		session.close();
		return true;
	}

	@Override
	public boolean updateTask(TaskForm taskForm) {
		Session session = getSession();
		session.beginTransaction();
		session.update(taskForm);
		session.getTransaction().commit();
		session.close();
		return false;
	}

	@Override
	public boolean deleteTask(String taskName) {
		Session session = getSession();
		session.beginTransaction();
		TaskForm taskForm = session.load(TaskForm.class, taskName);
		if (taskForm != null) {
			session.delete(taskForm);
		}
		session.getTransaction().commit();
		return false;
	}

	@Override
	@Transactional
	public List<TaskForm> getAllTasks() {
		Session session = getSession();
		List<TaskForm> tasksList = session.createQuery("from TODO_TASKS").list();
		return tasksList;
	}

	@Override
	@Transactional
	public Map<String, Object> getAllTasksOfUser(int pageNo, String userId) {
		Session session = getSession();
		
		Long size = getTotalCount();
		Query query = session.createQuery("from TODO_TASKS t where t.userId=:userId");
		query.setParameter("userId", userId);
		
		int startPos = getStartPos(pageNo-1);
		query.setFirstResult(startPos);
		query.setMaxResults(pageSize);
		List<TaskForm> tasksList = query.getResultList();
		Map<String, Object> tasksData = new HashMap<String, Object>();
		tasksData.put("recSize", size-1);
		tasksData.put("recList", tasksList);
		return tasksData;
		
		/*String hql = "from TODO_TASKS t where t.userId=:userId";
		Query query = session.createQuery(hql);
		query.setParameter("userId", userId);
		ScrollableResults resultScroll = query.scroll(ScrollMode.SCROLL_INSENSITIVE);
		resultScroll.first();
		resultScroll.scroll(pageNo-1);
		List<TaskForm> page = new ArrayList<TaskForm>();
		int i = 0;
		while (pageSize > i++) {
			page.add((TaskForm) resultScroll.get(0));
		    if (!resultScroll.next())
		        break;
		}
		resultScroll.last();
		int size = resultScroll.getRowNumber() + 1;
		System.out.println("size:: "+size);*/
		
		/*CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<TaskForm> cr = cb.createQuery(TaskForm.class);
		Root<TaskForm> root = cr.from(TaskForm.class);
		cr.select(root).where(cb.equal(root.get("userId"), userId));
		Query<TaskForm> query = session.createQuery(cr);
		List<TaskForm> results = query.getResultList();*/
		
	}

	@Override
	public Map<String, Object> updateStatus(TaskForm taskForm, String userName) {
		Session session = getSession();
		session.beginTransaction();
		// Query query = session.createQuery("from TODO_TASKS where completed =
		// :completed ");
		// query.setParameter("completed", taskForm.getCompleted());
		// session.update(taskForm);
		String hqlUpdate = "update TODO_TASKS set completed = :status where taskName = :name";
		int updatedEntities = session.createQuery(hqlUpdate).setParameter("status", taskForm.getCompleted())
				.setParameter("name", taskForm.getTaskName()).executeUpdate();
		session.getTransaction().commit();
		Map<String, Object> tasksData = getAllTasksOfUser(1,userName);
		session.close();
		return tasksData;
	}
	
	@Override
	@Transactional
	public List<TaskForm> getCompletedTasksOfUser(String userId) {
		Session session = getSession();
		Query query = session.createQuery("from TODO_TASKS t where t.userId=:userId and t.completed=:completed");
		query.setParameter("userId", userId);
		query.setParameter("completed", "false");
		List<TaskForm> tasksList = query.getResultList();
		return tasksList;
	}

	@Override
	@Transactional
	public Date getSysDate() {
		Date sysDate = null;
		Session session = getSession();
		String sqlOracle = "SELECT SYSDATE FROM DUAL";
		String sqlPostgre = "SELECT CURRENT_TIMESTAMP";
		Query query = session.createSQLQuery(sqlPostgre);
		sysDate = (Date) query.uniqueResult();
		return sysDate;
	}
	
	private int getStartPos(int pageNo) {
		int startPos = (pageSize+1) * pageNo;
		return startPos;
	}

	private Long getTotalCount() {
		Session session = getSession();
		String countQ = "Select count (t.taskName) from TODO_TASKS t";
	    Query countQuery = session.createQuery(countQ);
	    Long countResults = (Long) countQuery.uniqueResult();
	    return countResults;
	}
}
