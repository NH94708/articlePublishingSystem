package com.article.publishingSystem.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.article.publishingSystem.domain.ArticleDetails;
import com.article.publishingSystem.exception.BadRequestException;
import com.article.publishingSystem.exception.ArticleNotFoundException;
import com.article.publishingSystem.service.ArticleServiceIf;

@RestController
@RequestMapping(value="/app/article", consumes="application/json", produces="application/json")
public class ArticleController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ArticleController.class);
	
	@Autowired
	private ArticleServiceIf articleService;

	/**
	 * POST API: To create new article. On success return HTTP Status and 
	 * on validator failure returns the error message.
	 * @param articleDetails
	 */
	@SuppressWarnings("rawtypes")
	@PostMapping("/add")
    public ResponseEntity add(@Valid @RequestBody ArticleDetails articleDetails) {
        LOGGER.debug("Adding a new article details: {}", articleDetails);
        articleService.add(articleDetails);
        
        URI location = ServletUriComponentsBuilder
        					.fromCurrentRequest()
        					.path("/{articleId}")
        					.buildAndExpand(articleDetails.getArticleId()).toUri();
        
        return ResponseEntity.created(location).build();
    }
	
	/**
	 * GET API: To get list of articles
	 * @throws BadRequestException
	 */
	@GetMapping("/articleList")
    public List<ArticleDetails> getArticles() throws BadRequestException {
        LOGGER.debug("Get all articles");
        return articleService.getArticles();
    }
	
	/**
	 * GET API: To get article details for particular id.
	 * @param article id
	 * @throws ArticleNotFoundException
	 */
	@GetMapping("/{id}")
	public ArticleDetails retrieveDetails(@PathVariable int id) throws ArticleNotFoundException {
		LOGGER.debug("Find article by id");
		Optional<ArticleDetails> obj = articleService.findArticleById(id);
		
		if(!obj.isPresent())
			throw new ArticleNotFoundException("article with id: "+id+" is not present"); 
		 return obj.get();		
	}
	
	/**
	 * POST API: To update the content of article and create new version of article for the update
	 * and mark the version as default.
	 * @param articleDetails
	 * @throws BadRequestException
	 */
	@SuppressWarnings("rawtypes")
	@PostMapping("/update")
    public ResponseEntity update(@RequestBody(required = true) ArticleDetails articleDetails) 
    		throws BadRequestException {
        LOGGER.debug("Updating article details: {}", articleDetails);
        articleService.update(articleDetails);
        
        URI location = ServletUriComponentsBuilder
        					.fromCurrentRequest()
        					.path("/{articleId}")
        					.buildAndExpand(articleDetails.getArticleId()).toUri();
        
        return ResponseEntity.ok(location);
    }
	
	/**
	 * POST API: Mark / unmark the article as favourite and add votes.
	 * @param articleDetails
	 * @throws BadRequestException
	 */
	@SuppressWarnings("rawtypes")
	@PostMapping("/addToFavorites")
    public ResponseEntity addToFavorites(@RequestBody(required = true) ArticleDetails articleDetails) 
    		throws BadRequestException {
        LOGGER.debug("Updating article details: {}", articleDetails);
        articleService.addToFavorites(articleDetails);
        
        URI location = ServletUriComponentsBuilder
        					.fromCurrentRequest()
        					.path("/{articleId}")
        					.buildAndExpand(articleDetails.getArticleId()).toUri();
        
        return ResponseEntity.ok(location);
    }
	
}