package com.article.publishingSystem.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@ApiModel(description="Contains the information about article.")
public class ArticleDetails extends RatingDetails {
	
	@Id
	@GeneratedValue
	private int articleId;
	
	@NotBlank(message="title of the article is missing")
	@ApiModelProperty(notes="is a mandortory field")
	private String title;
	
	@NotBlank(message="content of the article is missing")
	@ApiModelProperty(notes="Specify what article contains and is a mandortory field")
	private String content;
	
	@NotBlank(message="authorName of the article is missing")
	@ApiModelProperty(notes="Can be author name or publisher name and is a mandortory field")
	private String authorName;
	
	private boolean isFavourites;
	
	@OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.PERSIST)
	@JoinColumn(name = "articleId")
	private List<VersionDetails> versionDetails;
	
	public ArticleDetails() {
		super();
	}

	public ArticleDetails(int articleId, String title, String content, String authorName, boolean isFavourites) {
		super();
		this.articleId = articleId;
		this.title = title;
		this.content = content;
		this.authorName = authorName;
		this.isFavourites = isFavourites;
	}

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public boolean isFavourites() {
		return isFavourites;
	}

	public void setFavourites(boolean isFavourites) {
		this.isFavourites = isFavourites;
	}
	

	public List<VersionDetails> getVersionDetails() {
		return versionDetails;
	}

	public void setVersionDetails(List<VersionDetails> versionDetails) {
		this.versionDetails = versionDetails;
	}

	@Override
	public String toString() {
		return "ArticleDetails [articleId=" + articleId + ", title=" + title + ", content=" + content + ", authorName="
				+ authorName + ", isFavourites=" + isFavourites + "]";
	}

}