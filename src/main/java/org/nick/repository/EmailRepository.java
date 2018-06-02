package org.nick.repository;

import org.nick.model.EmailSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<EmailSubscription, Long> {

	
}
