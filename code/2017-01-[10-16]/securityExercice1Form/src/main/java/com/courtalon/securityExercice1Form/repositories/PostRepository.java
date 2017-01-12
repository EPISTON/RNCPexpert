package com.courtalon.securityExercice1Form.repositories;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.courtalon.securityExercice1Form.metier.Post;

public interface PostRepository extends PagingAndSortingRepository<Post, Integer> {

	public List<Post> findByPublished(boolean published);
	public List<Post> findByTitreContainingAndPublished(String titre, boolean published);
	
	
}
