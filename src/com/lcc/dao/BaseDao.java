package com.lcc.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.lcc.util.ReflectUtil;

public class BaseDao<T,PK extends Serializable> {

	private SessionFactory sessionFactory;
	private Class<?> enClass;
	
	public BaseDao(){
		enClass = ReflectUtil.getGenericParmeterType(this.getClass());
	}
	
	public void save(T t){
		getSession().saveOrUpdate(t);
	}
	
	@SuppressWarnings("unchecked")
	public T findById(PK id){
		return (T)getSession().get(enClass, id);
	}
	
	public void del(PK id){
		getSession().delete(findById(id));
	}
	
	public void del(T t){
		getSession().delete(t);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findAll(){
		Criteria c = getSession().createCriteria(enClass);
		return c.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findByPage(int start,int count){
		Criteria c = getSession().createCriteria(enClass);
		c.setFirstResult(start);
		c.setMaxResults(count);
		return c.list();
	}
	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
}
