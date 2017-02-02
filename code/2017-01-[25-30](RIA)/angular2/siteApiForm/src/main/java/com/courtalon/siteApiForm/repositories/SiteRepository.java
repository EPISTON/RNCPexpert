package com.courtalon.siteApiForm.repositories;

import org.springframework.data.repository.CrudRepository;

import com.courtalon.siteApiForm.metier.Site;

public interface SiteRepository extends CrudRepository<Site, Integer> {

}
