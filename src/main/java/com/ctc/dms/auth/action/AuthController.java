package com.ctc.dms.auth.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ctc.dms.auth.security.AuthRequest;
import com.ctc.dms.auth.security.AuthResponse;
import com.ctc.dms.auth.security.JwtUtil;
/**
 * Controller to authenticate user and issues JWT token if user is successfully authenticated.
 * @author Shalu_Malhotra
 *
 */
@RestController
public class AuthController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailsService jwtUserDetailsService;
	@Autowired
	private JwtUtil jwtUtil;
	@RequestMapping("/authenticate")
	public ResponseEntity<?> authenticateToken(@RequestBody AuthRequest authRequest)throws Exception{
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		}catch (BadCredentialsException e) {
			e.printStackTrace();
			throw new Exception("Incorrect username or password");
		} 
		catch (AuthenticationException e) {
			e.printStackTrace();
		}
		final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(authRequest.getUsername());
		final String jwt = jwtUtil.generateToken(userDetails);
		return ResponseEntity.ok(new AuthResponse(jwt));
	}
}
