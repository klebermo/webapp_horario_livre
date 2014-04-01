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
import com.horariolivre.entity.Autorizacoes;

/**
 * Home object for domain model class Autorizacoes.
 * @see com.horariolivre.dao.Autorizacoes
 * @author Hibernate Tools
 */
@Repository
public class AutorizacoesHome {

	private static final Log log = LogFactory.getLog(AutorizacoesHome.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Transactional
	public void persist(Autorizacoes transientInstance) {
		log.debug("persisting Autorizacoes instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	@Transactional
	public void remove(Autorizacoes persistentInstance) {
		log.debug("removing Autorizacoes instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	@Transactional
	public Autorizacoes merge(Autorizacoes detachedInstance) {
		log.debug("merging Autorizacoes instance");
		try {
			Autorizacoes result = (Autorizacoes) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@Transactional
	public Autorizacoes findById(int id) {
		log.debug("getting Autorizacoes instance with id: " + id);
		try {
			Autorizacoes instance = (Autorizacoes) sessionFactory.getCurrentSession().get(Autorizacoes.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Autorizacoes> findALL() {
		log.debug("getting all Usuario instance");
		try {
			List<Autorizacoes> instance = sessionFactory.getCurrentSession().createCriteria(Autorizacoes.class).list();
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
