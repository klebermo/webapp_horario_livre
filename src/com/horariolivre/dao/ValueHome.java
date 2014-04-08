package com.horariolivre.dao;

// Generated 24/03/2014 06:50:21 by Hibernate Tools 3.4.0.CR1

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.horariolivre.entity.Usuario;
import com.horariolivre.entity.Value;

/**
 * Home object for domain model class Value.
 * @see com.horariolivre.dao.Value
 * @author Hibernate Tools
 */
@Repository
public class ValueHome {

	private static final Log log = LogFactory.getLog(ValueHome.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Transactional
	public void persist(Value transientInstance) {
		log.debug("persisting Value instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	@Transactional
	public void remove(Value persistentInstance) {
		log.debug("removing Value instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	@Transactional
	public Value merge(Value detachedInstance) {
		log.debug("merging Value instance");
		try {
			Value result = (Value) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Value> findByUser(Usuario user) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				"select * from value v, atributo_usuario a where a.fk_usuario = :id_usuario")
				.addEntity(Value.class)
				.setParameter("id_usuario", user.getId());
		List<Value> allValues = query.list();
		return allValues;
	}
	
}
