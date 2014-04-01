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
import com.horariolivre.entity.TipoUsuario;

/**
 * Home object for domain model class TipoUsuario.
 * @see com.horariolivre.dao.TipoUsuario
 * @author Hibernate Tools
 */
@Repository
public class TipoUsuarioHome {

	private static final Log log = LogFactory.getLog(TipoUsuarioHome.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Transactional
	public void persist(TipoUsuario transientInstance) {
		log.debug("persisting TipoUsuario instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	@Transactional
	public void remove(TipoUsuario persistentInstance) {
		log.debug("removing TipoUsuario instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	@Transactional
	public TipoUsuario merge(TipoUsuario detachedInstance) {
		log.debug("merging TipoUsuario instance");
		try {
			TipoUsuario result = (TipoUsuario) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@Transactional
	public TipoUsuario findById(int id) {
		log.debug("getting TipoUsuario instance with id: " + id);
		try {
			TipoUsuario instance = (TipoUsuario) sessionFactory.getCurrentSession().get(TipoUsuario.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<TipoUsuario> findALL() {
		log.debug("getting all Usuario instance");
		try {
			List<TipoUsuario> instance = sessionFactory.getCurrentSession().createCriteria(TipoUsuario.class).list();
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
