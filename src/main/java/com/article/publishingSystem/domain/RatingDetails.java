package com.article.publishingSystem.domain;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Range;
import io.swagger.annotations.ApiModelProperty;

@MappedSuperclass
public class RatingDetails {
	
	@Range(min=1L, max=10L, message="Rating must be in the range of 1 to 10")
	@ApiModelProperty(notes="Rating must be in the range of 1 to 10")
	private double rating;
	
	@Min(value = 0L, message = "Vote must be positive") 
	private int votes;
	
	public RatingDetails() {
		super();
	}

	public RatingDetails(double rating, int votes) {
		super();
		this.rating = rating;
		this.votes = votes;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public int getVotes() {
		return votes;
	}

	public void setVotes(int votes) {
		this.votes = votes;
	}

	@Override
	public String toString() {
		return "Rating [rating=" + rating + ", votes=" + votes + "]";
	}

}