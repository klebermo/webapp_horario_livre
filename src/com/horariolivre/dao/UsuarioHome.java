package com.horariolivre.dao;

// Generated 24/03/2014 06:50:21 by Hibernate Tools 3.4.0.CR1

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.horariolivre.entity.Usuario;

/**
 * Home object for domain model class Usuario.
 * @see com.horariolivre.dao.Usuario
 * @author Hibernate Tools
 */
@Repository
public class UsuarioHome {

	private static final Log log = LogFactory.getLog(UsuarioHome.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Transactional
	public boolean persist(Usuario transientInstance) {
		log.debug("persisting Usuario instance");
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
	public boolean remove(Usuario persistentInstance) {
		log.debug("removing Usuario instance");
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
	public Usuario merge(Usuario detachedInstance) {
		log.debug("merging Usuario instance");
		try {
			Usuario result = (Usuario) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@Transactional
	public Usuario findById(int id) {
		log.debug("getting Usuario instance with id: " + id);
		try {
			Usuario instance = (Usuario) sessionFactory.getCurrentSession().get(Usuario.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	@Transactional
	public Usuario findByUsername(String username) {
		log.debug("getting All Usuario instance");
		System.out.println("UsuarioHome.findByUsername("+username+")");
		try {
			Usuario instance = (Usuario) sessionFactory.getCurrentSession().createCriteria(Usuario.class, username).add(Restrictions.like("login", username)).list().get(0);
			log.debug("get successful");
			System.out.println("instance="+instance.getLogin());
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			System.out.println("instance=null");
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Usuario> findByExample(Usuario instance) {
		log.debug("finding Usuario instance by example");
		try {
			List<Usuario> results = sessionFactory.getCurrentSession().createCriteria(Usuario.class).add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Usuario> findALL() {
		log.debug("getting all Usuario instance");
		try {
			List<Usuario> instance = sessionFactory.getCurrentSession().createCriteria(Usuario.class).list();
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
