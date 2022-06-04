package com.ctc.dms.auth.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * This filter is used to filter all incoming requests. 
 * The filter is implemented to validate JWT token for every request and if validated, inform spring security about it.
 * JWT token is found in Authorization header of incoming request from where it is extracted and parsed using JWT library.
 * @author Shalu_Malhotra
 *
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	@Autowired
	private UserDetailsService jwtUserDetailsService;
	@Autowired
	private JwtUtil jwtUtil;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String authHeader = request.getHeader("Authorization");
		String username = null;
		String jwt = null;
		if(authHeader != null && authHeader.startsWith("Bearer ")) {
			jwt = authHeader.substring(7);
			username = jwtUtil.extractUsername(jwt);
		}
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
			if(jwtUtil.validateToken(jwt, userDetails)) {
				UsernamePasswordAuthenticationToken userPwdAuthToken = new 
						UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				userPwdAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(userPwdAuthToken);
			}
		}
		filterChain.doFilter(request, response);
	}
	

}
