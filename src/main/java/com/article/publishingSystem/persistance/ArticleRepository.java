package com.article.publishingSystem.persistance;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.article.publishingSystem.domain.ArticleDetails;

@Repository
public interface ArticleRepository extends CrudRepository<ArticleDetails, Integer>{
	
	@Query(value = "SELECT * FROM article_details ORDER BY votes DESC", nativeQuery = true)
	List<ArticleDetails> findByOrderByVotesDesc();
	
	@Modifying
	@Query(value = "UPDATE article_details set content = :content where article_id = :articleId", nativeQuery = true)
	void updateArticleByArticleId(@Param("content") String content, @Param("articleId") int articleId);
	
	@Modifying
	@Query(value = "UPDATE article_details set is_favourites = :isFavourites, votes = :votes where article_id = :articleId", nativeQuery = true)
	void updateArticleToFavourite(@Param("isFavourites") boolean favFlag, @Param("votes") int votes, 
			@Param("articleId") int articleId);

}
