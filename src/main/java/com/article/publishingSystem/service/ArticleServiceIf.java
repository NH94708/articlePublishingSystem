package com.article.publishingSystem.service;

import java.util.List;
import java.util.Optional;

import com.article.publishingSystem.domain.ArticleDetails;
import com.article.publishingSystem.exception.ArticleNotFoundException;
import com.article.publishingSystem.exception.BadRequestException;

public interface ArticleServiceIf {
	
	void add(ArticleDetails articleDetails);
	
	List<ArticleDetails> getArticles();
	
	Optional<ArticleDetails> findArticleById(int id) throws ArticleNotFoundException;
	
	void update(ArticleDetails articleDetails) throws ArticleNotFoundException;
	
	void addToFavorites(ArticleDetails articleDetails) throws ArticleNotFoundException;

}
