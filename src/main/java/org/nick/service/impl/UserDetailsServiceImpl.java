package org.nick.service.impl;

import org.nick.model.User;
import org.nick.repository.UserRepository;
import org.nick.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
    @Transactional(readOnly = true)
    public User loadUserByUsername(String username) throws Exception {
        User user = userRepository.findByUsername(username);
        return user;
    }
}
