package org.nick.repository;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Date;

import org.nick.model.TimeSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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
	@Query(value = "update timesheets  set  file = ?1, desiredmean = ?2, restmean = ?3, last_update = ?4 WHERE month_id = ?5 AND user_id = ?6", nativeQuery = true)
	void updateTimeSheetByID(File file,String desiredmean, String restmean,  Date now, Long monthID,  Long userId);
	
	
	
}
