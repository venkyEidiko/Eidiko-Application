package com.eidiko.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eidiko.entity.ShiftRequest;

@Repository
public interface ShiftRequestRepo extends JpaRepository<ShiftRequest, Long> {
	List<ShiftRequest> findByEmployeeEmployeeId(Long employeeId);
	@Transactional
    void deleteByEmployeeEmployeeId(Long employeeId);

	 List<ShiftRequest> findByEmployeeEmployeeIdAndFromDateLessThanEqualAndToDateGreaterThanEqual(
		        Long employeeId, LocalDate toDate, LocalDate fromDate);
}
