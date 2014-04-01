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

import com.horariolivre.entity.Evento;

/**
 * Home object for domain model class Evento.
 * @see com.horariolivre.dao.Evento
 * @author Hibernate Tools
 */
@Repository
public class EventoHome {

	private static final Log log = LogFactory.getLog(EventoHome.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Transactional
	public boolean persist(Evento transientInstance) {
		log.debug("persisting Evento instance");
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
	public boolean remove(Evento persistentInstance) {
		log.debug("removing Evento instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("remove successful");
			return true;
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			return false;
		}
	}

	public Evento merge(Evento detachedInstance) {
		log.debug("merging Evento instance");
		try {
			Evento result = (Evento) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@Transactional
	public Evento findById(int id) {
		log.debug("getting Evento instance with id: " + id);
		try {
			Evento instance = (Evento) sessionFactory.getCurrentSession().get(Evento.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Evento> findALL() {
		log.debug("getting all Evento instance");
		try {
			List<Evento> instance = sessionFactory.getCurrentSession().createCriteria(Evento.class).list();
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
