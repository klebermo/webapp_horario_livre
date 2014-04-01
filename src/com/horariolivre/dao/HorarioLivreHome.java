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

import com.horariolivre.entity.HorarioLivre;
import com.horariolivre.entity.Usuario;

/**
 * Home object for domain model class HorarioLivre.
 * @see com.horariolivre.dao.HorarioLivre
 * @author Hibernate Tools
 */
@Repository
public class HorarioLivreHome {

	private static final Log log = LogFactory.getLog(HorarioLivreHome.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Transactional
	public boolean persist(HorarioLivre transientInstance) {
		log.debug("persisting HorarioLivre instance");
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
	public boolean remove(HorarioLivre persistentInstance) {
		log.debug("removing HorarioLivre instance");
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
	public HorarioLivre merge(HorarioLivre detachedInstance) {
		log.debug("merging HorarioLivre instance");
		try {
			HorarioLivre result = (HorarioLivre) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@Transactional
	public HorarioLivre findById(int id) {
		log.debug("getting HorarioLivre instance with id: " + id);
		try {
			HorarioLivre instance = (HorarioLivre) sessionFactory.getCurrentSession().get(HorarioLivre.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<HorarioLivre> findByUsuario(Usuario user) {
		Query query = sessionFactory.getCurrentSession().createSQLQuery(
				"select * from horario_livre where fk_usuario = :id_usuario")
				.addEntity(HorarioLivre.class)
				.setParameter("id_usuario", user.getId());
				List<HorarioLivre> allUsers = query.list();
	    return allUsers;
	}
}
