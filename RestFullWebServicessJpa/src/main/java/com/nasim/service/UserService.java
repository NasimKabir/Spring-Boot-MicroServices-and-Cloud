package com.nasim.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nasim.model.User;
import com.nasim.model.UserPrincipal;
import com.nasim.repository.UserRepository;
import com.nasim.response.LoginResponse;
import com.nasim.resquest.LoginRequest;
import com.nasim.utility.JwtTokenProvider;

@Service
@Transactional
@Qualifier("userDetailsService")
public class UserService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	public ResponseEntity<?> login(LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		User user = findUserByUsername(loginRequest.getUsername());
		UserPrincipal userPrincipal = new UserPrincipal(user);
		String jwtToken = jwtTokenProvider.generateJwtToken(userPrincipal);
		/*String username = jwtTokenProvider.getSubject(jwtToken);
		String[] roles=jwtTokenProvider.getClaimsFromUser(userPrincipal);*/
		
		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setAccessToken(jwtToken);
		/*loginResponse.setUsername(username);
	    loginResponse.setRoles(roles);
*/
		return ResponseEntity.ok(loginResponse);

	}

	public List<User> findAll() {
		List<User> userList = userRepository.findAll();
		return userList;
	}

	public User save(User user) {
		if (userRepository.existsByUsername(user.getUsername())) {
			throw new RuntimeException(user.getUsername() + " doesn't exists !");
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		return user;
	}

	public User update(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		return user;
	}

	public void deleteByTodoId(long id) {
		Long user = userRepository.findById(id).get().getId();
		if (user != null) {
			userRepository.deleteById(id);
		}
	}

	public User findByTodoId(long id) {
		return userRepository.findById(id).get();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findUserByUsername(username);
		if (user == null) {
			LOGGER.error("NO_USER_FOUND_BY_USERNAME" + username);
			throw new UsernameNotFoundException("NO_USER_FOUND_BY_USERNAME" + username);
		} else {
			userRepository.save(user);
			UserPrincipal userPrincipal = new UserPrincipal(user);
			LOGGER.info("FOUND_BY_USERNAME " + username);
			return userPrincipal;
		}
	}

	public User findUserByUsername(String username) {
		return userRepository.findUserByUsername(username);
	}

}
