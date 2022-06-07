package com.ctc.dms.auth.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ctc.dms.auth.model.SiteUser;
import com.ctc.dms.auth.model.SiteUserDocument;
import com.ctc.dms.auth.model.SiteUserEntity;
/**
 * This class is proxy over selecting MySQL DB or Cosmo DB.
 * Selection of DB will be based on property value defined in local/aks application.yaml.
 * @author Shalu_Malhotra
 *
 */
@Component
public class SiteUserRepository{
	@Autowired
	private SiteUserDbRepository siteUserDbRepository;
	@Autowired
	private SiteUserCosmoRepository siteUserCosmoRepository;
	@Value("custom.repo")
	private String repo;
	
	/**
	 * find user entity by user name from Cosmo or MySql DB depending upon custom.repo property
	 * @param username
	 * @return
	 */
	public SiteUser findUserByUsername(String username){
		if(repo.equalsIgnoreCase("cosmo")) {
			return siteUserCosmoRepository.findUserByUsername(username);
		}else if(repo.equalsIgnoreCase("db")) {
			return siteUserDbRepository.findUserByUsername(username);
		}
		return null;
	}
	
	/**
	 * save user in Cosmo or MySql DB depending upon custom.repo property
	 * @param user
	 */
	public SiteUser save(SiteUser user){
		if(repo.equalsIgnoreCase("cosmo")) {
			return siteUserCosmoRepository.save((SiteUserDocument)user);
		}else if(repo.equalsIgnoreCase("db")) {
			return siteUserDbRepository.save((SiteUserEntity)user);
		}
		return null;
	}

}
