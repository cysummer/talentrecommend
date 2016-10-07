package com.talentrecd.dao;

import java.util.List;

import com.talentrecd.util.Pager;
import com.talentrecd.util.Pagination;



public interface IBaseDao<T> {
	public void add(T t);
	public void addObj(Object obj);
	public void update(T t);
	public void updateObj(Object obj);
	public void delete(Object obj);
	public void delete(int id);
	public T load(int id);
	/**
	 * 通过hql获取�?��对象，不进行分页
	 * @param hql
	 * @param args
	 * @return
	 */
	public List<T> list(String hql,Object[] args);
	public List<T> list(String hql);
	public List<T> list(String hql,Object obj);
	
	public List<Object> listByObj(String hql,Object[] args);
	public List<Object> listByObj(String hql);
	public List<Object> listByObj(String hql,Object obj);
	/**
	 * 通过hql获取�?��对象，进行分页处�?
	 * @param hql
	 * @param args
	 * @return
	 */
	public Pagination<T> find(String hql,int pageNo, Object[] args);
	public Pagination<T> find(String hql,int pageNo,Object obj);
	public Pagination<T> find(String hql,int pageNo);
	
	public Pager<Object> findByObj(String hql,Object[] args);
	public Pager<Object> findByObj(String hql,Object obj);
	public Pager<Object> findByObj(String hql);
	/**
	 * 通过hql获取�?��对象
	 * @param hql
	 * @param args
	 * @return
	 */
	public Object queryByHql(String hql,Object[] args);
	public Object queryByHql(String hql,Object arg);
	public Object queryByHql(String hql);
	/**
	 * 调用hql更新�?��对象
	 * @param hql
	 * @param args
	 */
	public void executeByHql(String hql,Object[] args);
	public void executeByHql(String hql,Object arg);
	public void executeByHql(String hql);
}	