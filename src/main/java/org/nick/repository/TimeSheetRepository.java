package org.nick.repository;

import org.nick.model.TimeSheet;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TimeSheetRepository extends JpaRepository<TimeSheet, Long> {

}
