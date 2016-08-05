package cn.edu.hfut.dmic.webcollector.dao;

import java.util.List;

public interface ICommonBaseDAO {

	public <T> Long save(T entity);
	public <T> List<Long> batchSave(final List<T> list);
	public <T> T get(final Class<? extends T> clazz, final Long id);
	public <T> void update(final T entity);
	public <T> void delete(final T entity);
	public <T> void delete(final Class<? extends T> clazz, final Long id);
	public <T> void saveOrUpdate(final T e);
	public int batchUpdate(final String sql, final Object[] params);
	public <T> int batchUpdate(final List<T> list);
	public <T> List<T> getSQLList(final String hql,final Object[] params, final int maxResults);
	public <T> List<T> getSQLList(final String hql, final Object[] params, final int start, final int size);
	public <T> List<Long> getHQLList(final String hql,final Object[] params, final int maxResults);
	public <T> List<Long> getHQLList(final String hql, final Object[] params, final int start, final int size);
	public <T> List<T> findAllBy(final Class<? extends T> clazz, final String name,	final Object value);
	public <T> T findOnlyBy(final Class<? extends T> clazz, final String name, final Object value);
	
}
