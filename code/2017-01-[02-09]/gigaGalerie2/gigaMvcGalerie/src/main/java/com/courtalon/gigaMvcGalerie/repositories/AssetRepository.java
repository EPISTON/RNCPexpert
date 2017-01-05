package com.courtalon.gigaMvcGalerie.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.courtalon.gigaMvcGalerie.metier.Asset;



public interface AssetRepository extends PagingAndSortingRepository<Asset, Integer> {
	
	@Query("select a from Asset as a left join fetch a.tags where a.id=:assetId")
	Asset findOneIncludingTags(@Param("assetId") int assetId);
}
