package com.ctc.dms.auth.dao;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import com.azure.spring.data.cosmos.repository.Query;
import com.ctc.dms.auth.model.SiteUserDocument;
/*
 * CrudRepository: saves given entity, returns entity identified by id, returns all entities, deletes/updates given entity
 * PagingAndSortingRepository: on top of CrudRepository, additional methods for paging and sorting
 * JPARepository: extends CrudRespository & JPARespository
 */
@Repository
public interface SiteUserCosmoRepository extends CosmosRepository<SiteUserDocument, Integer>{
	@Query(value = "select * from c where c.username = @username")
	SiteUserDocument findUserByUsername(@Param("username") String username);

}
