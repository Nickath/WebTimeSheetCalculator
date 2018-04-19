package org.nick.service;

import org.nick.model.User;

public interface UserDetailsService {

	User loadUserByUsername(String username) throws Exception;

}
