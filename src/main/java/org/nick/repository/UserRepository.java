package org.nick.repository;

import java.io.File;
import java.util.Date;

import org.nick.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User,Long> {

	User findByUsername(String username);
	
	@Modifying
    @Transactional(readOnly=false)
	@Query(value = "update users  set  enabled = ?1, password = ?3 WHERE username =?2", nativeQuery = true)
	void activateUser(boolean enabled, String username, String password);
	
	
}
