package com.article.publishingSystem.persistance;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.article.publishingSystem.domain.VersionDetails;

@Repository
public interface VersionRepository extends CrudRepository<VersionDetails, Integer>{
	
	@Modifying
	@Query(value = "UPDATE version_details set is_active_article = :activeArticle, is_default = :defaultFlag "
			+ "where version_id = :versionId", nativeQuery = true)
	void updatePreviousVersion(@Param("versionId") int versionId, @Param("activeArticle") boolean activeFlag,
			@Param("defaultFlag") boolean defaultFlag);

}