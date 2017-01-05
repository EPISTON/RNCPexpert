package com.courtalon.gigaMvcGalerie.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.courtalon.gigaMvcGalerie.metier.LicenseType;


// un repository sur LicenseType, avec une clé primaire Integer
public interface LicenseTypeRepository extends PagingAndSortingRepository<LicenseType, Integer>
{
	
}
