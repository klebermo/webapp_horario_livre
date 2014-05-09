package com.horariolivre.dao;

// Generated 24/03/2014 06:50:21 by Hibernate Tools 3.4.0.CR1

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.horariolivre.entity.Autorizacao;

/**
 * Home object for domain model class Autorizacao.
 * @see com.horariolivre.dao.Autorizacao
 * @author Hibernate Tools
 */
@Repository
public class AutorizacaoHome {

	private static final Log log = LogFactory.getLog(AutorizacaoHome.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Transactional
	public boolean persist(Autorizacao transientInstance) {
		log.debug("persisting Autorizacao instance");
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
	public void remove(Autorizacao persistentInstance) {
		log.debug("removing Autorizacao instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	@Transactional
	public Autorizacao merge(Autorizacao detachedInstance) {
		log.debug("merging Autorizacao instance");
		try {
			Autorizacao result = (Autorizacao) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@Transactional
	public Autorizacao findById(int id) {
		log.debug("getting Autorizacao instance with id: " + id);
		try {
			Autorizacao instance = (Autorizacao) sessionFactory.getCurrentSession().get(Autorizacao.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Autorizacao> findALL() {
		log.debug("getting all Usuario instance");
		try {
			List<Autorizacao> instance = sessionFactory.getCurrentSession().createCriteria(Autorizacao.class).list();
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
