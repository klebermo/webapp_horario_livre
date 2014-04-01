package com.horariolivre.dao;

// Generated 24/03/2014 06:50:21 by Hibernate Tools 3.4.0.CR1

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.horariolivre.entity.AutorizacoesUsuario;
import com.horariolivre.entity.Usuario;

/**
 * Home object for domain model class AutorizacoesUsuario.
 * @see com.horariolivre.dao.AutorizacoesUsuario
 * @author Hibernate Tools
 */
@Repository
public class AutorizacoesUsuarioHome {

	private static final Log log = LogFactory
			.getLog(AutorizacoesUsuarioHome.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Transactional
	public void persist(AutorizacoesUsuario transientInstance) {
		log.debug("persisting AutorizacoesUsuario instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	@Transactional
	public void remove(AutorizacoesUsuario persistentInstance) {
		log.debug("removing AutorizacoesUsuario instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	@Transactional
	public AutorizacoesUsuario merge(AutorizacoesUsuario detachedInstance) {
		log.debug("merging AutorizacoesUsuario instance");
		try {
			AutorizacoesUsuario result = (AutorizacoesUsuario) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@Transactional
	public AutorizacoesUsuario findById(AutorizacoesUsuario id) {
		log.debug("getting AutorizacoesUsuario instance with id: " + id);
		try {
			AutorizacoesUsuario instance = (AutorizacoesUsuario) sessionFactory.getCurrentSession().get(
					AutorizacoesUsuario.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<AutorizacoesUsuario> findByUsuario(Usuario user) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				"select * from autorizacoes_usuario where fk_usuario = :id_usuario")
				.addEntity(AutorizacoesUsuario.class)
				.setParameter("id_usuario", user.getId());
				List<AutorizacoesUsuario> allUsers = query.list();
	    return allUsers;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<AutorizacoesUsuario> findALL() {
		log.debug("getting all Usuario instance");
		try {
			List<AutorizacoesUsuario> instance = sessionFactory.getCurrentSession().createCriteria(AutorizacoesUsuario.class).list();
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
