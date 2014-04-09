package com.horariolivre.dao;

// Generated 24/03/2014 06:50:21 by Hibernate Tools 3.4.0.CR1

import java.util.List;

import org.hibernate.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.horariolivre.entity.Key;

/**
 * Home object for domain model class Key.
 * @see com.horariolivre.dao.Key
 * @author Hibernate Tools
 */
@Repository
public class KeyHome {

	private static final Log log = LogFactory.getLog(KeyHome.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Transactional
	public boolean persist(Key transientInstance) {
		log.debug("persisting Key instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
			return true;
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			return false;
		}
	}

	@Transactional
	public boolean remove(Key persistentInstance) {
		log.debug("removing Key instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("remove successful");
			return true;
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			return false;
		}
	}

	@Transactional
	public Key merge(Key detachedInstance) {
		log.debug("merging Key instance");
		try {
			Key result = (Key) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@Transactional
	public Key findById(int id) {
		log.debug("getting Key instance with id: " + id);
		try {
			Key instance = (Key) sessionFactory.getCurrentSession().get(Key.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Key> findALL() {
		log.debug("finding Usuario instance by example");
		try {
			List<Key> results = sessionFactory.getCurrentSession().createCriteria(Key.class).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
