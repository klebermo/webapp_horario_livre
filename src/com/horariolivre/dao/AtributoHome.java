package com.horariolivre.dao;

// Generated 24/03/2014 06:50:21 by Hibernate Tools 3.4.0.CR1

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.horariolivre.entity.Atributo;

/**
 * Home object for domain model class Atributo.
 * @see com.horariolivre.dao.Atributo
 * @author Hibernate Tools
 */
@Repository
public class AtributoHome {

	private static final Log log = LogFactory.getLog(AtributoHome.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Transactional
	public boolean persist(Atributo transientInstance) {
		log.debug("persisting Atributo instance");
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
	public boolean remove(Atributo persistentInstance) {
		log.debug("removing Atributo instance");
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
	public Atributo merge(Atributo detachedInstance) {
		log.debug("merging Atributo instance");
		try {
			Atributo result = (Atributo) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			return null;
		}
	}

	@Transactional
	public Atributo findById(int id) {
		log.debug("getting Atributo instance with id: " + id);
		try {
			Atributo instance = (Atributo) sessionFactory.getCurrentSession().get(Atributo.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Atributo> findByExample(Atributo instance) {
		log.debug("finding Atributo instance by example");
		try {
			List<Atributo> results = sessionFactory.getCurrentSession().createCriteria(Atributo.class).add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Atributo> findALL() {
		log.debug("getting all Atributo instance");
		try {
			List<Atributo> instance = sessionFactory.getCurrentSession().createCriteria(Atributo.class).list();
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			return null;
		}
	}
	
}
