package org.nick.repository;

import org.nick.model.TimeSheet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface TimeSheetRepository extends CrudRepository<TimeSheet, Long> {

}
