package com.courtalon.gigaMvcGalerie.repositories;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.courtalon.gigaMvcGalerie.metier.Tag;



public class TagRepositoryImpl implements TagRepositoryCustom {

	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	@Transactional(readOnly=true)
	public Page<Tag> searchNotRelatedToAsset(String searchTerm, int assetId, Pageable page) {
		TypedQuery<Tag> qdata;
		TypedQuery<Long> qcount;
		if (searchTerm == null || searchTerm.trim().isEmpty()) {
			qdata = em.createQuery("select t from Tag as t where t.systemTag = false AND (NOT EXISTS ("
					+ "select a from Asset as a, in a.tags as ts where a.id=:aid AND ts MEMBER OF t.tags)",
					Tag.class);
			qcount = em.createQuery("select count(t.id) from Tag as t where t.systemTag = false AND (NOT EXISTS ("
					+ "select a from Asset as a, in a.tags as ts where a.id=:aid AND ts MEMBER OF t.tags)",
					Long.class);
		}
		else {
			qdata = em.createQuery("select t from Tag as t where t.systemTag = false AND t.libelle like :searchterm AND (NOT EXISTS ("
					+ "select a from Asset as a, in a.tags as ts where a.id=:aid AND ts MEMBER OF t.tags)",
					Tag.class);
			qcount = em.createQuery("select count(t.id) from Tag as t where t.systemTag = false AND t.libelle like :searchterm AND (NOT EXISTS ("
					+ "select a from Asset as a, in a.tags as ts where a.id=:aid AND ts MEMBER OF t.tags)",
					Long.class);
			final String st = "%" + searchTerm.trim() + "%";
			qdata.setParameter("searchterm", st);
			qcount.setParameter("searchterm", st);
		}
		qdata.setParameter("aid", assetId);
		qcount.setParameter("aid", assetId);
		qdata.setFirstResult(page.getOffset());
		qdata.setMaxResults(page.getPageSize());
		return new PageImpl<>(qdata.getResultList(), page, qcount.getSingleResult());
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Tag> findRelatedToAsset(int assetId, Pageable page) {

		TypedQuery<Tag> qdata = em.createQuery("select t from Tag as t left join t.assets as a where t.systemTag = false AND a.id=:aid", Tag.class);
		TypedQuery<Long> qcount = em.createQuery("select count(t.id) from Tag as t left join t.assets as a where t.systemTag = false AND a.id=:aid",	Long.class);
		qdata.setParameter("aid", assetId);
		qcount.setParameter("aid", assetId);
		qdata.setFirstResult(page.getOffset());
		qdata.setMaxResults(page.getPageSize());
		return new PageImpl<>(qdata.getResultList(), page, qcount.getSingleResult());
	}

	

	
}
