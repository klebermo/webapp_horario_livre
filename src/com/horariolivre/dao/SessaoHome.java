package com.horariolivre.dao;

// Generated 24/03/2014 06:50:21 by Hibernate Tools 3.4.0.CR1

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.horariolivre.entity.Sessao;

/**
 * Home object for domain model class Sessao.
 * @see com.horariolivre.dao.Sessao
 * @author Hibernate Tools
 */
@Repository
public class SessaoHome {

	private static final Log log = LogFactory.getLog(SessaoHome.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Transactional
	public void persist(Sessao transientInstance) {
		log.debug("persisting Sessao instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	@Transactional
	public void remove(Sessao persistentInstance) {
		log.debug("removing Sessao instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	@Transactional
	public Sessao merge(Sessao detachedInstance) {
		log.debug("merging Sessao instance");
		try {
			Sessao result = (Sessao) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@Transactional
	public Sessao findById(int id) {
		log.debug("getting Sessao instance with id: " + id);
		try {
			Sessao instance = (Sessao) sessionFactory.getCurrentSession().get(Sessao.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	@Transactional
	public Sessao findLast() {
		log.debug("finding Sessao instance by example");
		try {
			Sessao results = (Sessao) sessionFactory.getCurrentSession().createCriteria("from Sessao order by id DESC LIMIT 1").list().get(0);
			log.debug("find by example successful, result size: " + results.toString());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
