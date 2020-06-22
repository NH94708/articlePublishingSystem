package com.article.publishingSystem.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@ApiModel(description="Maintain all the version of article and marks the active article as well")
public class VersionDetails {
	
	@Id
	@GeneratedValue
	private int versionId;
	
	@Min(value = 0L, message = "Vote must be positive") 
	@ApiModelProperty(notes="Specify the article version and is a mandatory field")
	private int version;
	
	private boolean isDefault;
	private boolean isActiveArticle;
	
	@ManyToOne()
	@JoinColumn(name = "articleId", insertable=true, updatable=true)
	private ArticleDetails articleDetails;
	
	public VersionDetails() {
		super();
	}

	public VersionDetails(int versionId, int version, boolean isDefault, boolean isActiveArticle) {
		super();
		this.versionId = versionId;
		this.version = version;
		this.isDefault = isDefault;
		this.isActiveArticle = isActiveArticle;
	}

	@JsonIgnore
	public int getVersionId() {
		return versionId;
	}

	public void setVersionId(int versionId) {
		this.versionId = versionId;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public boolean isActiveArticle() {
		return isActiveArticle;
	}

	public void setActiveArticle(boolean isActiveArticle) {
		this.isActiveArticle = isActiveArticle;
	}

	@JsonIgnore
	public ArticleDetails getArticleDetails() {
		return articleDetails;
	}

	public void setArticleDetails(ArticleDetails articleDetails) {
		this.articleDetails = articleDetails;
	}

	@Override
	public String toString() {
		return "VersionDetails [versionId=" + versionId + ", version=" + version
				+ ", isDefault=" + isDefault + ", isActiveArticle=" + isActiveArticle + "]";
	}
		
}