package com.courtalon.firstSecurityForm.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Transactional;

import com.courtalon.firstSecurityForm.metier.Message;

public class MessageRepositoryImpl implements MessageRepositoryCustom {

	@PersistenceContext
	private EntityManager em;
	public EntityManager getEm() {return em;}
	public void setEm(EntityManager em) {this.em = em;}


	@Override
	@Transactional(readOnly=true)
	public List<Message> findUnsecureByTitre(String titre) {
		/*
		 * attention, non securisé!!!
		 * 
		 *  a%' OR 1=1 OR m.titre='a
		 * select m from Message as m where m.titre like'%a%'
		 *						OR 1=1
		 *						OR m.titre='a%'
		 *						AND m.published=true
		 * 
		 */
		String jpaRequette = "select m from Message as m where m.titre like '%"
					+ titre + "%' and m.published=true";
		return em.createQuery(jpaRequette, Message.class).getResultList();
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Message> findsecureByTitre(String titre) {
		String jpaRequette = "select m from Message as m where m.titre"
							+ " like :searchTerm and m.published=true";
		TypedQuery<Message> q = em.createQuery(jpaRequette, Message.class);
		q.setParameter("searchTerm", titre);
		return q.getResultList();
	}

}
