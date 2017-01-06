package com.courtalon.gigaMvcGalerie.utils;

import java.util.List;

import javax.persistence.Query;

public class AssetQueryBuilder {
	
	
	public static void bindQueryParams(Query q, List<Integer> includedTags, List<Integer> exludedTags) {
		// les tags inclus
		for (int i = 1; i <= includedTags.size(); i++) {
			q.setParameter("ta" + i, includedTags.get(i - 1));
		}
		// les tags exclus
		for (int i = 1; i <= exludedTags.size(); i++) {
			q.setParameter("te" + i, exludedTags.get(i - 1));
		}
	}
	
	
	// exemple
	 // entree
	/*
	 * 	entityName -> "Image"
	 *  entityAlias -> "i"
	 *  nbTagInclude -> 2
	 *  nbTagexclude -> 2
	 *  
	 */
	public static String buildQuery(String entityName, String entityAlias, int nbTagInclude, int nbTagExclude) {
		StringBuilder query = new StringBuilder(" ");
		// si aucun tag, pas de jointuer/where etc a construire
		if ((nbTagInclude + nbTagExclude) == 0)
			return "";
		// generation des jointures pour les test d'inclusion
		/*
		 * 
		 *  iteration 1 -> ", In(i.tags) ta1"
		 *  iteration 2 -> ", In(i.tags) ta1, In(i.tags) ta2"
		 *  
		 *  comme j'ai deux tag a tester en inclusion, j'ai besoin de 2 jointures vers les tags
		 */
		for (int i = 1; i <= nbTagInclude; i++) {
			query.append(", IN(");
			query.append(entityAlias);
			query.append(".tags) ta");
			query.append(i);
		}
		query.append(" WHERE ");
		// generation des test d'inclusion eux-mêmes
		/*
		 *  a ce moment -> ", In(i.tags) ta1, In(i.tags) ta2 WHERE "
		 *  iteration 1 -> ", In(i.tags) ta1, In(i.tags) ta2 WHERE  ta1.id = :ta1"
		 *  iteration 2 -> ", In(i.tags) ta1, In(i.tags) ta2 WHERE  ta1.id = :ta1 AND ta2.id = :ta2"
		 *  
		 */
		for (int i = 1; i <= nbTagInclude; i++) {
			if (i > 1)
				query.append(" AND ");
			query.append(" ta");
			query.append(i);
			query.append(".id = :ta");
			query.append(i);
		}
		// generation de la sous requette not exist pour exclure les images etiquetées par ces tags
		/*
		 * NOT EXISTS ( 
		 * SELECT i2 
		 * FROM Image AS i2, IN(i2.tags) te1 
		 * WHERE ( te1.id = :te1 OR te1.id = :te2) 
			and i2.id = i.id 
		 * )
		 * 
		 * au demarrage -> ", In(i.tags) ta1, In(i.tags) ta2 WHERE  ta1.id = :ta1 AND ta2.id = :ta2 AND "
		 * 
		 * " NOT EXISTS (SELECT i2 FROM Image as i2, IN (i.tags) i2.tags te1 WHERE ( te1.id = :te1"
		 * iteration 2 -> "WHERE ( te1.id = :te1  OR te1.id = :te2"
		 * 
		 * fin -> "WHERE ( te1.id = :te1  OR te1.id = :te2) AND i2.id = i.id )
		 * 
		 */
		if (nbTagExclude > 0) {
			// sous requette d'exclusion
			if (nbTagInclude > 0)
				query.append(" AND");
			query.append(" NOT EXISTS (SELECT ");
			query.append(entityAlias);
			query.append(2);
			query.append(" FROM ");
			query.append(entityName);
			query.append(" AS ");
			query.append(entityAlias);
			query.append(2);
			query.append(" , IN(");
			query.append(entityAlias);
			query.append(2);
			query.append(".tags) te1 WHERE ( te1.id = :te1");
			for (int i = 2; i <= nbTagExclude; i++) {
				query.append(" OR te1.id = :te");
				query.append(i);
			}
			query.append(") AND ");
			query.append(entityAlias);
			query.append(2);
			query.append(".id = ");
			query.append(entityAlias);
			query.append(".id )");
		}
		return query.toString();
	}
	
}
