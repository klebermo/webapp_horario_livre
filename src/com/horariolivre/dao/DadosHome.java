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

import com.horariolivre.entity.Dados;

/**
 * Home object for domain model class Dados.
 * @see com.horariolivre.dao.Dados
 * @author Hibernate Tools
 */
@Repository
public class DadosHome {

	private static final Log log = LogFactory.getLog(DadosHome.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Transactional
	public void persist(Dados transientInstance) {
		log.debug("persisting Dados instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	@Transactional
	public void remove(Dados persistentInstance) {
		log.debug("removing Dados instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	@Transactional
	public Dados merge(Dados detachedInstance) {
		log.debug("merging Dados instance");
		try {
			Dados result = (Dados) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@Transactional
	public Dados findById(int id) {
		log.debug("getting Dados instance with id: " + id);
		try {
			Dados instance = (Dados) sessionFactory.getCurrentSession().get(Dados.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Dados> findALL() {
		log.debug("finding Usuario instance by example");
		try {
			List<Dados> results = sessionFactory.getCurrentSession().createCriteria(Dados.class).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
