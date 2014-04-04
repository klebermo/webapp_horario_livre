package com.horariolivre.dao;

// Generated 24/03/2014 06:50:21 by Hibernate Tools 3.4.0.CR1

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.horariolivre.entity.ConfigHorarioLivre;

/**
 * Home object for domain model class ConfigHorarioLivre.
 * @see com.horariolivre.dao.ConfigHorarioLivre
 * @author Hibernate Tools
 */
@Repository
public class ConfigHorarioLivreHome {

	private static final Log log = LogFactory
			.getLog(ConfigHorarioLivreHome.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Transactional
	public boolean persist(ConfigHorarioLivre transientInstance) {
		log.debug("persisting ConfigHorarioLivre instance");
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
	public boolean remove(ConfigHorarioLivre persistentInstance) {
		log.debug("removing ConfigHorarioLivre instance");
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
	public ConfigHorarioLivre merge(ConfigHorarioLivre detachedInstance) {
		log.debug("merging ConfigHorarioLivre instance");
		try {
			ConfigHorarioLivre result = (ConfigHorarioLivre) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@Transactional
	public ConfigHorarioLivre findById(int id) {
		log.debug("getting ConfigHorarioLivre instance with id: " + id);
		try {
			ConfigHorarioLivre instance = (ConfigHorarioLivre) sessionFactory.getCurrentSession().get(
					ConfigHorarioLivre.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
