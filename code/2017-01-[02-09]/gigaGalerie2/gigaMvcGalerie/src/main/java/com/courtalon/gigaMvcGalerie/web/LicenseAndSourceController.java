package com.courtalon.gigaMvcGalerie.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.courtalon.gigaMvcGalerie.metier.AssetSource;
import com.courtalon.gigaMvcGalerie.metier.AssetSource.AssetSourceOnly;
import com.courtalon.gigaMvcGalerie.metier.LicenseType;
import com.courtalon.gigaMvcGalerie.metier.LicenseType.LicenseOnly;
import com.courtalon.gigaMvcGalerie.repositories.AssetSourceRepository;
import com.courtalon.gigaMvcGalerie.repositories.LicenseTypeRepository;
import com.fasterxml.jackson.annotation.JsonView;

@Controller
@RequestMapping("/rest")
public class LicenseAndSourceController {
	@Autowired
	private LicenseTypeRepository licenseTypeRepository;
	public LicenseTypeRepository getLicenseTypeRepository() {return licenseTypeRepository;}
	public void setLicenseTypeRepository(LicenseTypeRepository licenseTypeRepository) {this.licenseTypeRepository = licenseTypeRepository;}
	
	@Autowired
	private AssetSourceRepository assetSourceRepository;
	public AssetSourceRepository getAssetSourceRepository() {return assetSourceRepository;}
	public void setAssetSourceRepository(AssetSourceRepository assetSourceRepository) {this.assetSourceRepository = assetSourceRepository;}
	
	
	@RequestMapping(value="/licenses", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	@JsonView(LicenseOnly.class)
	public Page<LicenseType> listeLicenses(@PageableDefault(page=0, size=50) Pageable pageRequest) {
		return getLicenseTypeRepository().findAll(pageRequest);
	}

	
	@RequestMapping(value="/assetsources", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	@JsonView(AssetSourceOnly.class)
	public Page<AssetSource> listeSources(@PageableDefault(page=0, size=50) Pageable pageRequest) {
		return getAssetSourceRepository().findAll(pageRequest);
	}

}
