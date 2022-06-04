package com.ctc.dms.auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
/**
 * This class enables web securities defined by <i>WebSecurityConfigurerAdapter</i> automatically.
 * To override web securities defined by <i>WebSecurityConfigurerAdapter</i> in our Java configuration class, we need to extend this class and override its methods.
 * @EnableWebSecurity enables web securities in our application defined by <i>WebSecurityConfigurer</i> implementations(<i>WebSecurityConfigurerAdapter</i>).
 * @EnableGlobalMethodSecurity is for method level security. By default, Spring AOP proxying is used to apply method security(pre authorization & post authorization of methods).
 * Method security is disabled by default and method which needs special authorities to be accessible to outside class are protected with this security.
 * Here, this annotation is not required though.
 * @author Shalu_Malhotra
 *
 */
@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * Spring Security uses UserDetailsService to authenticate via JPA. 
	 * We've provided our own implementation as <i>jwtUserDetailsService</i> which is created as 
	 * Service component and which should be implementing <i>UserDetailsService</i> overriding <i>loadUserByUsername</i> method.
	 */
	@Autowired
	private UserDetailsService jwtUserDetailsService;

	/**
	 * This filter is required to filter all incoming requests except for URI <i>/authorize</i>(which should not be authenticated)
	 * This filter validates JWT token.
	 */
	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	/**
	 * This method is used to override Spring Security's default form based authentication implementation,
	 * where <i>username = admin</i> and <i>password = admin</i>
	 * username and password can be changed by providing them in application.properties:<br>
	 * <i>spring.security.user.name = foo
	 * spring.security.user.password = foo</i>
	 * <br>In this overridden version, authentication is being done here using JPA.
	 * <br>Authentication can also be done using JDBC, use code below:<br>
	 * <i>auth.jdbcAuthentication(dataSource)</i> and create bean <i>dataSource</i>
	 * <br>In both the approaches, you may either work with H2 DB which comes embedded and used by Spring as default
	 * or you may work with your own DB for which mention details in <i>application.properties</i>:<br>
	 * <i>spring.datasource.url =
	 * spring.datasource.username =
	 * spring.datasource.password =</i>
	 */
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		// configure AuthenticationManager so that it knows from where to load
		// user for matching credentials
		// Use BCryptPasswordEncoder
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}

	/**
	 * This method authorizes incoming requests and prevents unauthorized access to server resources.<br>
	 * As <i>/authenticate and /register</i> endpoints don't need authentication itself, so that's disabled for these endpoints.
	 * For all other endpoints, it uses <i>jwtRequestFilter</i> which first validates JWT token coming in request header and 
	 * then set the token in security context. 
	 */
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		// We don't need CSRF for this example
		httpSecurity.csrf().disable()
				// dont authenticate this particular request
				.authorizeRequests().antMatchers("/authenticate", "/register").permitAll().
				// all other requests need to be authenticated
				anyRequest().authenticated().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// Add a filter to validate the tokens with every request
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	/**
	 * This bean defines how password should be encoded.
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * This bean is required to be used in <i>AuthController</i> to kick in spring security process to authenticate user using overridden <i>configure(AuthenticationManagerBuilder auth)</i> method.
	 */
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
