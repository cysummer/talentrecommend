package com.talentrecd.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.talentrecd.dao.IBaseDao;
import com.talentrecd.util.Pager;
import com.talentrecd.util.Pagination;


@SuppressWarnings("unchecked")
public class BaseDaoImpl<T>  implements IBaseDao<T> {
	private Class<T> clz;
	
	private SessionFactory sessionFactory;
	
	private final int pageSize=10;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	@Resource(name="sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	protected Session getcurrentSession(){
		return sessionFactory.getCurrentSession();
	}
	
	private Class<T> getClz() {
		if(clz==null) {
			//获取泛型的Class对象
			clz = ((Class<T>)
					(((ParameterizedType)(this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]));
		}
		return clz;
	}

	@Override
	public void add(T t) {
		getcurrentSession().save(t);
	}

	@Override
	public void update(T t) {
		getcurrentSession().update(t);
	}

	@Override
	public void delete(int id) {
		T t = this.load(id);
		getcurrentSession().delete(t);
	}

	@Override
	public T load(int id) {
		return (T) getcurrentSession().load(getClz(), id);
	}
	
	private Query setParamterQuery(String hql,Object[] args) {
		Query q = getcurrentSession().createQuery(hql);
		if(args!=null) {
			for(int i=0;i<args.length;i++) {
				q.setParameter(i, args[i]);
			}
		}
		return q;
	}

	@Override
	public List<T> list(String hql, Object[] args) {
		Query q = setParamterQuery(hql, args);
		return q.list();
	}

	@Override
	public List<T> list(String hql) {
		return this.list(hql, null);
	}

	@Override
	public List<T> list(String hql, Object obj) {
		return this.list(hql,new Object[]{obj});
	}

	
	
	@Override
	public Pagination<T> find(String hql,int pageNo, Object[] args) {
		Query q = setParamterQuery(hql, args);
		//从第几条记录�?��，取出几条记�?
		q.setFirstResult((pageNo-1)*pageSize).setMaxResults(pageSize);
		//得到查询�?��的记录�?数的sql
		String cHql = getCountHql(hql);
		Query cq = setParamterQuery(cHql, args);
		List<T> datas = q.list();
		long x =(Long) cq.uniqueResult();
		int totalRecord= (int) x;
		return new Pagination(pageNo,pageSize,totalRecord,datas);
	}
	
	private String getCountHql(String hql) {
		String s = hql.substring(0,hql.indexOf("from"));
		if(s==null||s.trim().equals("")) {
			hql = "select count(*) "+hql;
		} else {
			hql = hql.replace(s, "select count(*) ");
		}
		hql = hql.replace("fetch", "");
		return hql;
	}

	@Override
	public Pagination<T> find(String hql,int pageNo, Object obj) {
		return this.find(hql, pageNo,new Object[]{obj});
	}

	@Override
	public Pagination<T> find(String hql,int pageNo) {
		return this.find(hql,pageNo,null);
	}

	@Override
	public Object queryByHql(String hql, Object[] args) {
		Query q = setParamterQuery(hql, args);
		return q.uniqueResult();
	}

	@Override
	public Object queryByHql(String hql, Object arg) {
		return this.queryByHql(hql, new Object[]{arg});
	}

	@Override
	public Object queryByHql(String hql) {
		return this.queryByHql(hql,null);
	}

	@Override
	public void executeByHql(String hql, Object[] args) {
		Query q = setParamterQuery(hql, args);
		q.executeUpdate();
	}

	@Override
	public void executeByHql(String hql, Object arg) {
		this.executeByHql(hql,new Object[]{arg});
	}

	@Override
	public void executeByHql(String hql) {
		this.executeByHql(hql,null);
	}

	@Override
	public List<Object> listByObj(String hql, Object[] args) {
		Query q = setParamterQuery(hql, args);
		return q.list();
	}

	@Override
	public List<Object> listByObj(String hql) {
		return this.listByObj(hql,null);
	}

	@Override
	public List<Object> listByObj(String hql, Object obj) {
		return this.listByObj(hql, new Object[]{obj});
	}

	/*@Override
	public Pager<Object> findByObj(String hql, Object[] args) {
		int pageSize = SystemContext.getPageSize();
		int pageOffset = SystemContext.getPageOffset();
		if(pageSize<=0) pageSize = 10;
		if(pageOffset<0) pageOffset = 0;
		Query q = setParamterQuery(hql, args);
		q.setFirstResult(pageOffset).setMaxResults(pageSize);
		String cHql = getCountHql(hql);
		Query cq = setParamterQuery(cHql, args);
		Pager<Object> pager = new Pager<Object>();
		pager.setPageSize(pageSize);
		List<Object> datas = q.list();
		pager.setDatas(datas);
		long totalRecord = (Long)cq.uniqueResult();
		pager.setTotalRecord(totalRecord);
		return pager;
	}*/

	@Override
	public Pager<Object> findByObj(String hql, Object obj) {
		return this.findByObj(hql, new Object[]{obj});
	}

	@Override
	public Pager<Object> findByObj(String hql) {
		return this.findByObj(hql,null);
	}

	@Override
	public void addObj(Object obj) {
		getcurrentSession().save(obj);
	}

	@Override
	public void updateObj(Object obj) {
		getcurrentSession().update(obj);
	}

	@Override
	public void delete(Object obj) {
		getcurrentSession().delete(obj);
	}

	@Override
	public Pager<Object> findByObj(String hql, Object[] args) {
		// TODO Auto-generated method stub
		return null;
	}

}
