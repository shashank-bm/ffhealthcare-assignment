package com.ffhealthcare.crud.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.ffhealthcare.crud.security.config.JwtTokenUtil;
import com.ffhealthcare.crud.security.model.JwtRequest;
import com.ffhealthcare.crud.security.model.JwtResponse;
import com.ffhealthcare.crud.security.model.User;
import com.ffhealthcare.crud.security.service.JwtUserDetailsService;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	/**
	 * Rest API to get token
	 * 
	 * @param authenticationRequest
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
	}
/** Rest API to add a user
 * 
 * @param user
 * @return ResponseEntity<?>
 */
	@PostMapping(value = "/register")
	public ResponseEntity<?> saveUser(@RequestBody User user) {
		try {
			return ResponseEntity.ok(userDetailsService.save(user));
		} catch (Exception ex) {
			// Handle other exceptions here
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
		}
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		} catch (Exception e) {
			// Handle other exceptions here
			throw new Exception("An error occurred.", e);
		}
	}
}
