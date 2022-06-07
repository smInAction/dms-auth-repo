package com.ctc.dms.auth.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ctc.dms.auth.model.SiteUserEntity;
/*
 * CrudRepository: saves given entity, returns entity identified by id, returns all entities, deletes/updates given entity
 * PagingAndSortingRepository: on top of CrudRepository, additional methods for paging and sorting
 * JPARepository: extends CrudRespository & JPARespository
 */
@Repository
public interface SiteUserDbRepository extends JpaRepository<SiteUserEntity, Integer>{
	SiteUserEntity findUserByUsername(String username);

}
