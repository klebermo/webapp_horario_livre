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
import com.horariolivre.entity.DadosUsuario;
import com.horariolivre.entity.Usuario;

/**
 * Home object for domain model class DadosUsuario.
 * @see com.horariolivre.dao.DadosUsuario
 * @author Hibernate Tools
 */
@Repository
public class DadosUsuarioHome {

	private static final Log log = LogFactory.getLog(DadosUsuarioHome.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Transactional
	public void persist(DadosUsuario transientInstance) {
		log.debug("persisting DadosUsuario instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	@Transactional
	public void remove(DadosUsuario persistentInstance) {
		log.debug("removing DadosUsuario instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	@Transactional
	public DadosUsuario merge(DadosUsuario detachedInstance) {
		log.debug("merging DadosUsuario instance");
		try {
			DadosUsuario result = (DadosUsuario) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@Transactional
	public DadosUsuario findById(int id) {
		log.debug("getting DadosUsuario instance with id: " + id);
		try {
			DadosUsuario instance = (DadosUsuario) sessionFactory.getCurrentSession().get(DadosUsuario.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<DadosUsuario> findByUsuario(Usuario user) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				"select * from dados_usuario where fk_usuario = :id_usuario")
				.addEntity(DadosUsuario.class)
				.setParameter("id_usuario", user.getId());
				List<DadosUsuario> allUsers = query.list();
	    return allUsers;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<DadosUsuario> findALL() {
		log.debug("getting all Usuario instance");
		try {
			List<DadosUsuario> instance = sessionFactory.getCurrentSession().createCriteria(DadosUsuario.class).list();
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
