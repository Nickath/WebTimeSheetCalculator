/*package com.custom.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.nick.model.User;
import org.nick.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	UserRepository userRepository;
	
	private static List<User> users = new ArrayList();

	public CustomAuthenticationProvider() {
		users.add(new User("mpaourdas", "12345678"));
        users.add(new User("mpaou", "123"));
    }
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String name = authentication.getName();
        Object credentials = authentication.getCredentials();
        System.out.println("credentials class: " + credentials.getClass());
        if (!(credentials instanceof String)) {
            return null;
        }
        String password = credentials.toString();

        Optional<User> userOptional = users.stream()
                                           .filter(u -> u.match(name, password))
                                           .findFirst();

        if (!userOptional.isPresent()) {
            throw new BadCredentialsException("Authentication failed for " + name);
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(userOptional.get().getRole().getRole()));
        Authentication auth = new
                UsernamePasswordAuthenticationToken(name, password, grantedAuthorities);
        return auth;
	}

	@Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
	

}
*/