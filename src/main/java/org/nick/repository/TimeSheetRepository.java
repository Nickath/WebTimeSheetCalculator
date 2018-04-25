package org.nick.repository;

import java.io.File;

import org.nick.model.TimeSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface TimeSheetRepository extends JpaRepository<TimeSheet, Long> {

/*	@Modifying
	@Query("update timesheet  set dayspending = ?1, desiredmean = ?2, average_leaving = ?3,"
			+ "file = ?4, average_coming = ?5 ,average = ?6 , restmean = ?7, days= ?8, "
			+ "month_id = ?9, where user_id = ?10")
	void updateTimeSheetByID(@Param("dayspending") int dayspending, @Param("desiredmean")String desiredmean, String averageLeaving, File file, String averageComing,
			String average, String restmean, int days, Long monthId, Long userId);*/
	
	
	@Modifying
    @Transactional(readOnly=false)
	@Query(value = "update timesheets  set  desiredmean = ?1, restmean = ?2 WHERE month_id = ?3 AND user_id = ?4", nativeQuery = true)
	void updateTimeSheetByID(String desiredmean, String restmean,  Long monthID,  Long userId);
	
}
