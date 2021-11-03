package com.equipe7.getserv.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.equipe7.getserv.model.RoleEntity;
import com.equipe7.getserv.model.UserEntity;
import com.equipe7.getserv.repository.RoleRepository;
import com.equipe7.getserv.repository.UserRepository;

@Service
public class UserServiceImplementation implements UserService, UserDetailsService{
	
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	
	public UserServiceImplementation(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder){
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByUsername(username);
		if (user == null)
			throw new UsernameNotFoundException("404\nUser not found: [ " + username + " ]");
		
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
		return new User(user.getUsername(), user.getPassword(), authorities);
	}

	@Override
	public UserEntity saveUser(UserEntity user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	/*@Override
	public RoleEntity createRole(RoleEntity role) {
		return roleRepository.save(role);
	}*/

	@Override
	public void addRoleToUser(String username, String roleName) {
		UserEntity user = userRepository.findByUsername(username);
		RoleEntity role = roleRepository.findByName(roleName);
		user.getRoles().add(role);
		userRepository.save(user);
	}

	@Override
	public UserEntity getUser(String username) {
		return userRepository.findByUsername(username);
	}

	/*@Override
	public List<UserEntity> getUsers() {
		return userRepository.findAll();
	}*/
	
}
