package com.courtalon.gigaMvcGalerie.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.courtalon.gigaMvcGalerie.metier.Tag;


public interface TagRepositoryCustom {
	
	Page<Tag> searchNotRelatedToAsset(String searchTerm, int assetId, Pageable page);
	Page<Tag> findRelatedToAsset(int assetId, Pageable page);
}
