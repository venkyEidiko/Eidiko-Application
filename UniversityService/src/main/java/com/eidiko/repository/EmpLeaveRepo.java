package com.eidiko.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eidiko.entity.EmpLeave;

@Repository
public interface EmpLeaveRepo extends JpaRepository<EmpLeave, Long> {

	Optional<EmpLeave> findByleaveIdAndStatus(Long leaveId, String status);

	List<EmpLeave> findAllByEmployeeId(Long employeeId);

//	 List<EmpLeave> findByLeaveType(String leaveType);
	List<EmpLeave> findByLeaveTypeIn(List<String> leaveTypes);

//	@Query("SELECT e FROM EmpLeave e WHERE (:leaveTypes IS NULL OR e.leaveType IN :leaveTypes) AND (:statuses IS NULL OR e.status IN :statuses)")
//	Page<EmpLeave> findByLeaveTypeInAndStatusIn(List<String> leaveTypes, List<String> statuses, Pageable pageable);

	@Query("SELECT e FROM EmpLeave e WHERE e.employeeId = :employeeId AND (:leaveTypes IS NULL OR e.leaveType IN :leaveTypes) AND (:statuses IS NULL OR e.status IN :statuses)")
	Page<EmpLeave> findByEmployeeIdAndLeaveTypeInAndStatusIn(
	    @Param("employeeId") Long employeeId,
	    @Param("leaveTypes") List<String> leaveTypes,
	    @Param("statuses") List<String> statuses,
	    Pageable pageable
	);



	List<EmpLeave> findByFromDateLessThanEqualAndToDateGreaterThanEqual(LocalDate fromDate, LocalDate toDate);


//	List<EmpLeave> findByLeaveTypeAndFromDateLessThanEqualAndToDateGreaterThanEqual(String leaveType,
//			LocalDate fromDate, LocalDate toDate);


	List<EmpLeave> findByLeaveType(String leaveType);

}
