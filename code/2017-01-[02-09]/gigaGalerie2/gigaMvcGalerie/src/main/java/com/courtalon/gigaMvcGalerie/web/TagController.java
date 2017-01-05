package com.courtalon.gigaMvcGalerie.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;

import com.courtalon.gigaMvcGalerie.metier.Asset;
import com.courtalon.gigaMvcGalerie.metier.Tag;
import com.courtalon.gigaMvcGalerie.metier.Tag.TagOnly;
import com.courtalon.gigaMvcGalerie.repositories.AssetRepository;
import com.courtalon.gigaMvcGalerie.repositories.TagRepository;
import com.courtalon.gigaMvcGalerie.utils.JsonPageable;
import com.fasterxml.jackson.annotation.JsonView;

@Controller
@RequestMapping("/rest")
public class TagController {

	private static Logger log = LogManager.getLogger(TagController.class);
	
	@Autowired
	private TagRepository tagRepository;
	public TagRepository getTagRepository() {return tagRepository;}
	public void setTagRepository(TagRepository tagRepository) {this.tagRepository = tagRepository;}

	@Autowired
	private AssetRepository assetRepository;
	public AssetRepository getAssetRepository() {return assetRepository;}
	public void setAssetRepository(AssetRepository assetRepository) {this.assetRepository = assetRepository;}
	
	
	@RequestMapping(value="/tags", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	@JsonView(TagOnly.class)
	public Page<Tag> liste(@PageableDefault(page=0, size=10, sort="libelle", direction=Direction.ASC) Pageable pageRequest) {
		return JsonPageable.fromPage(getTagRepository().findBySystemTag(false, pageRequest));
	}

	// recuperer le tag d'identifiant id
	@RequestMapping(value="/tags/{id:[0-9]+}", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	@JsonView(TagOnly.class)
	public Tag findById(@PathVariable("id")  int id) {
		return getTagRepository().findByIdAndSystemTag(id, false);
	}
	
	// savoir si un tag est disponnible (n'existe pas ?) ou editable (si non systeme)
	@RequestMapping(value="/tags/available/{libelle}", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public Map<String, Object> tagLibelleAvailable(@PathVariable("libelle") String libelle) {
		Tag t = getTagRepository().findByLibelle(libelle);
		HashMap<String, Object> result = new HashMap<>();
		result.put("exists", (t != null));
		result.put("editable", (t != null && !t.isSystemTag()));
		result.put("id", (t == null)? 0 : t.getId()); 
		return result;
	}
	// recherche de tag
	@RequestMapping(value="/tags/search/{libelle}", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	@JsonView(TagOnly.class)
	public Page<Tag> findByLibelle(@PathVariable("libelle") String libelle, @PageableDefault(page=0, size=10, sort="libelle", direction=Direction.ASC) Pageable pageRequest) {
		return JsonPageable.fromPage(getTagRepository().findByLibelleContainingAndSystemTag(libelle, false, pageRequest));
	}

	@RequestMapping(value="/tags/searchNotRelated/{assetId:[0-9]+}/{libelle}", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	@JsonView(TagOnly.class)
	public Page<Tag> findByLibelleNotRelated(@PathVariable("assetId") int assetId, @PathVariable("libelle") String libelle, @PageableDefault(page=0, size=10) Pageable pageRequest) {
		return JsonPageable.fromPage(getTagRepository().searchNotRelatedToAsset(libelle, assetId, pageRequest));
	}

	// trouver tag lié a une asset particuliere
	@RequestMapping(value="/tags/findRelated/{assetId:[0-9]+}", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	@JsonView(TagOnly.class)
	public Page<Tag> findRelated(@PathVariable("assetId") int assetId, @PageableDefault(page=0, size=10) Pageable pageRequest) {
		return JsonPageable.fromPage(getTagRepository().findRelatedToAsset(assetId, pageRequest));
	}


	@RequestMapping(value="/tags", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	@JsonView(TagOnly.class)
	public Tag save(@RequestBody Tag tag) {
		Tag original = getTagRepository().findOne(tag.getId());
		if (original == null) {
			tag.setSystemTag(false);
			return getTagRepository().save(tag);
		}
		else if (original.isSystemTag()) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "system tags not editable");
		}
		original.setLibelle(tag.getLibelle());
		original.setDescription(tag.getDescription());
		return getTagRepository().save(original);
	}

	@RequestMapping(value="/tags/unstage/{assetsId:[0-9,]+}", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	@JsonView(TagOnly.class)
		public HashMap<String, Object> unStageAsset(@PathVariable("assetsId") List<Integer> assetsId) {
		Tag t = getTagRepository().findByLibelle(TagRepository.UPLOADED);
		if (t == null) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "tag not found for unstaging operation");
		}
		int unstagedCount = 0;
		int unstagedAsked = 0;
		for (Integer assetId : assetsId) {
			log.info("retrait tag uploaded de asset no " + assetId);
			unstagedAsked++;
			Asset a = getAssetRepository().findOneIncludingTags(assetId);
			if (a != null) {
				a.removeTag(t);
				getAssetRepository().save(a);
				unstagedCount++;
			}
			else {
				log.info("retrait tag impossible, asset inexistant");
			}
		}
		HashMap<String, Object> result = new HashMap<>();
		result.put("unstagedCount", unstagedCount);
		result.put("unstagedAsked", unstagedAsked);
		return result;
	}

	
	@RequestMapping(value="/tags/add/{assetsId:[0-9,]+}/{tagId:[0-9]+}", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	@JsonView(TagOnly.class)
	public Tag AddTag(@PathVariable("assetsId") List<Integer> assetsId, @PathVariable("tagId") int tagId) {
		Tag t = getTagRepository().findByIdAndSystemTag(tagId, false);
		if (t == null) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "tag inconnu");
		}
		for (Integer assetId : assetsId) {
			log.info("ajout tag no " + tagId + "  sur asset no " + assetId);
			Asset a = getAssetRepository().findOneIncludingTags(assetId);
			if (a != null) {
				a.addTag(t);
				getAssetRepository().save(a);
			}
			else {
				log.info("ajout impossible, asset inexistant");
			}
		}
		return t;
	}

	@RequestMapping(value="/tags/remove/{assetsId:[0-9,]+}/{tagId:[0-9]+}", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	@JsonView(TagOnly.class)
		public Tag removeTag(@PathVariable("assetsId") List<Integer> assetsId, @PathVariable("tagId") int tagId) {
		Tag t = getTagRepository().findByIdAndSystemTag(tagId, false);
		if (t == null) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "tag inconnu");
		}
		for (Integer assetId : assetsId) {
			log.info("retrait tag no " + tagId + "  sur asset no " + assetId);
			Asset a = getAssetRepository().findOneIncludingTags(assetId);
			if (a != null) {
				a.removeTag(t);
				getAssetRepository().save(a);
			}
			else {
				log.info("retrait impossible,asset inexistant");
			}
		}
		return t;
	}
	
}
