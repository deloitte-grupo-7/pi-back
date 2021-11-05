package com.equipe7.getserv.resource;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.equipe7.getserv.model.UserEntity;
import com.equipe7.getserv.repository.UserRepository;

public abstract class Table {
	
	private static Set<String> usernames = new HashSet<>();
	private static Set<String> emails = new HashSet<>();
	private static Set<String> encodedCPFs = new HashSet<>();
	
	public static boolean getUsername(String username) {
		return usernames.contains(username);
	}
	
	public static void addUsername(String username) {
		usernames.add(username);
	}
	
	public static void changeUsername(String oldUser, String username) {
		if (getUsername(oldUser)) {
			usernames.remove(username);
			usernames.add(username);
		}
	}
	
	public static void removeUsername(String username) {
		if (getUsername(username))
			usernames.remove(username);
	}
	
	public static boolean getEmail(String email) {
		return emails.contains(email);
	}
	
	public static void addEmail(String email) {
		usernames.add(email);
	}
	
	public static void changeEmail(String oldEmail, String email) {
		if (getUsername(oldEmail)) {
			usernames.remove(email);
			usernames.add(email);
		}
	}
	
	public static void removeEmail(String email) {
		if (getUsername(email))
			usernames.remove(email);
	}
	
	public static void reset(UserRepository repository) {
		emails.clear();
		usernames.clear();
		encodedCPFs.clear();
		
		List<UserEntity> listUser = repository.findAll();
		listUser.forEach(user -> { usernames.add(user.getUsername());
			if (user.getRegister().getEmail() != null)
				emails.add(user.getRegister().getEmail());
			if (user.getRegister().getCpf() != null)
				encodedCPFs.add(user.getRegister().getCpf());
		});
	}
}
