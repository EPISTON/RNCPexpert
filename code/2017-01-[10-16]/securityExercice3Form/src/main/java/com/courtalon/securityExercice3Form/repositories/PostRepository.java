package com.courtalon.securityExercice3Form.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.courtalon.securityExercice3Form.metier.Post;

public interface PostRepository extends CrudRepository<Post, Integer> {

	List<Post> findByPublished(boolean published);
	List<Post> findByTitreContainingAndPublished(String titre, boolean published);
}
