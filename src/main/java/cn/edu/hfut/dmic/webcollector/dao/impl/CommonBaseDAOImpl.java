package cn.edu.hfut.dmic.webcollector.dao.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.edu.hfut.dmic.webcollector.dao.ICommonBaseDAO;



@SuppressWarnings({ "rawtypes", "unchecked" })
public class CommonBaseDAOImpl extends HibernateDaoSupport implements ICommonBaseDAO{
	//private static final Logger log = LoggerFactory.getLogger(CommonBaseDAOImpl.class);
	//超时阈值
	private static final long TIMEOUT = 50;
	//批量插入阈值
	private static final long BATCH = 500;
	
	//why add this?
	@Autowired  
    public void setSessionFactoryOverride(SessionFactory sessionFactory) 
    {   
      super.setSessionFactory(sessionFactory);   
    } 
	
	public <T> List<T> findAllBy(final Class<? extends T> clazz, final String name,
			final Object value) {
		// 实现根据属性名和属性值查询对象，返回唯一对象
		HibernateCallback hibernateCallback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(clazz);
				criteria.add(Restrictions.eq(name, value));
				criteria.setCacheable(true);
				return criteria.list();

			}
		};
		
		return getHibernateTemplate().executeFind(hibernateCallback);
	}
	
	public <T> T findOnlyBy(final Class<? extends T> clazz, final String name,
			final Object value) {
		List<T> list = findAllBy(clazz,name,value);
		
		return list.size() > 0 ? list.get(0) : null;
	}

	public <T> T get(final Class<? extends T> clazz, final Long id) {
		final long tb = System.currentTimeMillis();
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Object entity = session.get(clazz, id);
				return entity;
			}
		};
		Object obj = getHibernateTemplate().execute(callback);
//		if (log.isDebugEnabled()) {
//			Object[] logValue = new Object[] { clazz, id, obj == null ? null : obj.getClass() };
//			//log.debug("get - class:{}, id:{}, ret:{}", logValue);
//		}
		final long te = System.currentTimeMillis();
		if (te - tb > TIMEOUT) {
			final Object[] logValue = new Object[] { te - tb, clazz, id };
			//log.warn("get - Time:{} clazz:{} id:{}", logValue);
		}
		return (T) obj;
	}
	
	public <T> Long save(final T e) {
		final long tb = System.currentTimeMillis();
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Serializable id = session.save(e);
				return id;
			}
		};
		
		Long id = (Long) getHibernateTemplate().execute(callback);
		
		//log.info("save - class:{}, id:{}", e.getClass(), id == null ? null : id);
		final long te = System.currentTimeMillis();
		if (te - tb > TIMEOUT) {
			final Object[] logValue = new Object[] { te - tb, e.getClass(), id };
			//log.warn("save - Time:{} clazz:{} id:{}", logValue);
		}
		return id;
	}
	
	public <T> List<Long> batchSave(final List<T> list) {
		final long tb = System.currentTimeMillis();
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session) {
				List<Serializable> idList = new ArrayList<Serializable>();
				for(int i=0; i<list.size(); i++) {
					Serializable id = session.save(list.get(i));
					idList.add(id);
					if(i%BATCH == 0) {
						session.flush();
						session.clear();
					}
				}
				return idList;
			}
		};
		List<Long> idList = (List<Long>)getHibernateTemplate().execute(callback);
		//log.info("batchSave - class:{}, idList:{}", list.getClass(), idList==null ? null : idList);
		final long te = System.currentTimeMillis();
		if (te - tb > TIMEOUT) {
			final Object[] logValue = new Object[] {te-tb, list.getClass(), idList};
			//log.warn("batchSave - Time:{} clazz:{} id:{}", logValue);
		}
		return idList;
	}
	
	
	public <T> void update(final T e) {
		final long tb = System.currentTimeMillis();
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session) {
				try{
					session.update(e);
				}catch(Exception e2){
					session.merge(e);
				}
				return null;
			}
		};
		getHibernateTemplate().execute(callback);
//		log.debug("update - class:{}, id:{}", e.getClass(), e.getUid());
		final long te = System.currentTimeMillis();
		if (te - tb > TIMEOUT) {
			final Object[] logValue = new Object[] { te - tb, e.getClass() };
			//log.warn("update - Time:{} clazz:{}", logValue);
		}
	}
	
	public <T> void delete(final T e) {
		final long tb = System.currentTimeMillis();
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session) {
				session.delete(e);
				return null;
			}
		};
		getHibernateTemplate().execute(callback);
		//log.debug("delete - class:{}", e.getClass());
		final long te = System.currentTimeMillis();
		if (te - tb > TIMEOUT) {
			final Object[] logValue = new Object[] { te - tb, e.getClass() };
			//log.warn("delete - Time:{} clazz:{}", logValue);
		}
	}

	public <T> void delete(final Class<? extends T> clazz, final Long id) {
		final long tb = System.currentTimeMillis();
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session) {
				T e = get(clazz, id);
				if (e != null) {
					session.delete(e);
				}
				return null;
			}
		};
		getHibernateTemplate().execute(callback);
		//log.debug("deleteById - class:{}, id:{}", clazz, id);
		final long te = System.currentTimeMillis();
		if (te - tb > TIMEOUT) {
			final Object[] logValue = new Object[] { te - tb, clazz, id };
			//log.warn("deleteById - Time:{} clazz:{} id:{}", logValue);
		}
	}

	public <T> void saveOrUpdate(final T e) {
		final long tb = System.currentTimeMillis();
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session) {
				try{
					session.saveOrUpdate(e);
				}catch(Exception e2){
					session.merge(e);
				}
				return null;
			}
		};
		getHibernateTemplate().execute(callback);
		//log.debug("saveOrUpdate - class:{}", e.getClass());
		final long te = System.currentTimeMillis();
		if (te - tb > TIMEOUT) {
			final Object[] logValue = new Object[] { te - tb, e.getClass()};
			//log.warn("saveOrUpdate - Time:{} clazz:{}", logValue);
		}
	}

	public <T> int batchUpdate(final List<T> list) {
		final long tb = System.currentTimeMillis();
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session) {
				for(int i=0; i<list.size(); i++) {
					session.update(list.get(i));
					if(i%BATCH == 0) {
						session.flush();
						session.clear();
					}
				}
				return list.size();
			}
		};
		Object obj = getHibernateTemplate().execute(callback);
		//log.info("batchUpdate - class:{}, idList:{}", list.getClass(), list==null ? null : list);
		final long te = System.currentTimeMillis();
		if (te - tb > TIMEOUT) {
			final Object[] logValue = new Object[] {te-tb, list.getClass(), list};
			//log.warn("batchUpdate - Time:{} clazz:{} id:{}", logValue);
		}
		return (Integer)obj;
	}
	
	
	
	public int batchUpdate(final String sql, final Object[] params) {
		final long tb = System.currentTimeMillis();
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Query query = session.createSQLQuery(sql);
				int k = 0;
				for (int i = 0; params != null && i < params.length; i++) {
					query.setParameter(k++, params[i]);
				}
				return query.executeUpdate();
			}
		};
		Object obj = getHibernateTemplate().execute(callback);
		//log.debug("batchUpdate - sql:{}, updated:{}", sql, obj);
		final long te = System.currentTimeMillis();
//		if (te - tb > TIMEOUT) {
//			final Object[] logValue = new Object[] { te - tb, sql, params };
//			log.warn("batchUpdate - Time:{} sql:{} params:{}", logValue);
//		}
		return (Integer) obj;
	}
	
	public <T> List<T> getSQLList(final String hql,final Object[] params, final int maxResults) {
		final long tb = System.currentTimeMillis();
		HibernateCallback<List<T>> callback = new HibernateCallback<List<T>>() {
			public List<T> doInHibernate(Session session) {
				Query query = session.createQuery(hql);
				int k = 0;
				for (int i = 0; params != null && i < params.length; i++) {
					query.setParameter(k++, params[i]);
				}
				if (maxResults > -1) {
					query.setMaxResults(maxResults);
				}
				return query.list();
			}
		};
		List<T> list = getHibernateTemplate().execute(callback);
//		if (log.isDebugEnabled()) {
//			Object[] logValue = new Object[] { hql, params, maxResults, list.size() };
//			log.debug("getHQLList - hql:{}, params:{}, maxResults:{}, list.len:{}", logValue);
//		}
//		final long te = System.currentTimeMillis();
//		if (te - tb > TIMEOUT) {
//			final Object[] logValue = new Object[] { te - tb, hql, params, maxResults };
//			log.warn("getHQLList - Time:{} sql:{} params:{} maxResults:{}", logValue);
//		}
		return new LinkedList<T>(list);
	}
	
	public <T> List<T> getSQLList(final String hql, final Object[] params, final int start, final int size) {
		final long tb = System.currentTimeMillis();
		HibernateCallback<List<T>> callback = new HibernateCallback<List<T>>() {
			public List<T> doInHibernate(Session session) {
				Query query = session.createQuery(hql);
				int k = 0;
				for (int i = 0; params != null && i < params.length; i++) {
					query.setParameter(k++, params[i]);
				}
				query.setFirstResult(start);
				query.setMaxResults(size);
				return query.list();
			}
		};
		List<T> list = getHibernateTemplate().execute(callback);
//		if (log.isDebugEnabled()) {
//			Object[] logValue = new Object[] { hql, params, start, size, list.size() };
//			log.debug("getHQLList - hql:{}, params:{}, start:{}, size:{}, list.len:{}", logValue);
//		}
//		final long te = System.currentTimeMillis();
//		if (te - tb > TIMEOUT) {
//			final Object[] logValue = new Object[] { te - tb, hql, params, start, size };
//			log.warn("getHQLList - Time:{} sql:{} params:{} start:{} size:{}", logValue);
//		}
		return new LinkedList<T>(list);
	}

	public <T> List<Long> getHQLList(final String hql, final Object[] params, final int maxResults) {
		final long tb = System.currentTimeMillis();
		HibernateCallback<List<Long>> callback = new HibernateCallback<List<Long>>() {
			public List<Long> doInHibernate(Session session) {
				Query query = session.createQuery(hql);
				int k = 0;
				for (int i = 0; params != null && i < params.length; i++) {
					query.setParameter(k++, params[i]);
				}
				if (maxResults > -1) {
					query.setMaxResults(maxResults);
				}
				return query.list();
			}
		};
		List<Long> list = getHibernateTemplate().execute(callback);
//		if (log.isDebugEnabled()) {
//			Object[] logValue = new Object[] { hql, params, maxResults, list.size() };
//			log.debug("getHQLList - hql:{}, params:{}, maxResults:{}, list.len:{}", logValue);
//		}
//		final long te = System.currentTimeMillis();
//		if (te - tb > TIMEOUT) {
//			final Object[] logValue = new Object[] { te - tb, hql, params, maxResults };
//			log.warn("getHQLList - Time:{} sql:{} params:{} maxResults:{}", logValue);
//		}
		return new LinkedList<Long>(list);
	}

	public List<Long> getHQLList(final String hql, final Object[] params, final int start, final int size) {
		final long tb = System.currentTimeMillis();
		HibernateCallback<List<Long>> callback = new HibernateCallback<List<Long>>() {
			public List<Long> doInHibernate(Session session) {
				Query query = session.createQuery(hql);
				int k = 0;
				for (int i = 0; params != null && i < params.length; i++) {
					query.setParameter(k++, params[i]);
				}
				query.setFirstResult(start);
				query.setMaxResults(size);
				return query.list();
			}
		};
		List<Long> list = getHibernateTemplate().execute(callback);
//		if (log.isDebugEnabled()) {
//			Object[] logValue = new Object[] { hql, params, start, size, list.size() };
//			log.debug("getHQLList - hql:{}, params:{}, start:{}, size:{}, list.len:{}", logValue);
//		}
//		final long te = System.currentTimeMillis();
//		if (te - tb > TIMEOUT) {
//			final Object[] logValue = new Object[] { te - tb, hql, params, start, size };
//			log.warn("getHQLList - Time:{} sql:{} params:{} start:{} size:{}", logValue);
//		}
		return new LinkedList<Long>(list);
	}
	
}