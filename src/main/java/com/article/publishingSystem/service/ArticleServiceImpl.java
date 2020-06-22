package com.article.publishingSystem.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.article.publishingSystem.domain.ArticleDetails;
import com.article.publishingSystem.domain.VersionDetails;
import com.article.publishingSystem.exception.ArticleNotFoundException;
import com.article.publishingSystem.exception.BadRequestException;
import com.article.publishingSystem.persistance.ArticleRepository;
import com.article.publishingSystem.persistance.VersionRepository;

@Service("articleService")
public class ArticleServiceImpl implements ArticleServiceIf{
	
	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private VersionRepository versionRepository;

	/* (non-Javadoc)
	 * Service call to persist the article in db.
	 * @see com.article.publishingSystem.service.ArticleServiceIf#add(com.article.publishingSystem.domain.ArticleDetails)
	 */
	@Override
	public void add(ArticleDetails articleDetails) {
		articleRepository.save(articleDetails);
	}

	/* (non-Javadoc)
	 * Service call to get the article list for db.
	 * @see com.article.publishingSystem.service.ArticleServiceIf#getArticles()
	 */
	@Override
	public List<ArticleDetails> getArticles() {
		return (List<ArticleDetails>) articleRepository.findByOrderByVotesDesc();
	}

	/* (non-Javadoc)
	 * Service call to find a particular article in db.
	 * @see com.article.publishingSystem.service.ArticleServiceIf#findArticleById(int)
	 */
	@Override
	public Optional<ArticleDetails> findArticleById(int id) throws ArticleNotFoundException {
		return articleRepository.findById(id);
	}

	/* (non-Javadoc)
	 * Service call for article details which involves below step.
	 * 1. Content of article is updated.
	 * 2. New version for article is created and mark as default / active.
	 * 3. Previous version of the article is unmarked as default / active.
	 * @see com.article.publishingSystem.service.ArticleServiceIf#update(com.article.publishingSystem.domain.ArticleDetails)
	 */
	@Transactional(rollbackOn=Exception.class)
	@Override
	public void update(ArticleDetails articleDetails) throws ArticleNotFoundException {
		Optional<ArticleDetails> obj = findArticleById(articleDetails.getArticleId());
		List<VersionDetails> list = obj.get().getVersionDetails();
		VersionDetails currentVersionObj = list.get(list.size() - 1);
		if(obj.isPresent()) {
			articleRepository.updateArticleByArticleId(articleDetails.getContent(), articleDetails.getArticleId());
			versionRepository.save(createNewVersionForContent(articleDetails, currentVersionObj));
			versionRepository.updatePreviousVersion(currentVersionObj.getVersionId(), false, false);
		} else {
			throw new ArticleNotFoundException("article with id: "+articleDetails.getArticleId()+" is not present");
		}	
	}
	
	/* (non-Javadoc)
	 * Service call to mark the article as favourite and update the votes.
	 * @see com.article.publishingSystem.service.ArticleServiceIf#addToFavorites(com.article.publishingSystem.domain.ArticleDetails)
	 */
	@Transactional
	@Override
	public void addToFavorites(ArticleDetails articleDetails) throws ArticleNotFoundException {
		Optional<ArticleDetails> obj = findArticleById(articleDetails.getArticleId());
		if(obj.isPresent()) {
			articleRepository.updateArticleToFavourite(articleDetails.isFavourites(), obj.get().getVotes() + articleDetails.getVotes(), 
					articleDetails.getArticleId());
		} else {
			throw new ArticleNotFoundException("article with id: "+articleDetails.getArticleId()+" is not present");
		}
	}
	
	private VersionDetails createNewVersionForContent(ArticleDetails articleDetails, VersionDetails currentVersionObj) {
		VersionDetails newVersionObj = new VersionDetails();
		newVersionObj.setArticleDetails(articleDetails);
		newVersionObj.setActiveArticle(true);
		newVersionObj.setDefault(true);
		newVersionObj.setVersion(currentVersionObj.getVersion() + 1);
	return newVersionObj;
	}
}