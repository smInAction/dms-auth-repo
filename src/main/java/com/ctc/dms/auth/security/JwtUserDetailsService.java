package com.ctc.dms.auth.security;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ctc.dms.auth.dao.SiteUserRepository;
import com.ctc.dms.auth.model.SiteUser;
import com.ctc.dms.auth.model.SiteUserDTO;
import com.ctc.dms.auth.model.SiteUserDocument;
import com.ctc.dms.auth.model.SiteUserEntity;

/**
 * This class overrides <i>UserDetailsService</i> used by Spring Security to load user details This is required while authenticating user.
 * <br>Here, its method which loads user details is overridden to load user details by looking into datastore using JPA repository.
 * @author Shalu_Malhotra
 *
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private SiteUserRepository userRepo;

	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Value("custom.repo")
	private String repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<SiteUser> user = Optional.of(userRepo.findUserByUsername(username));
		user.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
		return new User(user.get().getUsername(), user.get().getPassword(),
				new ArrayList<>());
		//return user.map(User::new).get();
	}

	public SiteUser save(SiteUserDTO user) {
		SiteUser newUser = repo.equalsIgnoreCase("cosmo") ? new SiteUserDocument() : new SiteUserEntity();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		return userRepo.save(newUser);
	}

}
