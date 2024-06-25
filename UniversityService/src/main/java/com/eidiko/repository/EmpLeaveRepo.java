package com.eidiko.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eidiko.entity.EmpLeave;
@Repository
public interface EmpLeaveRepo extends JpaRepository<EmpLeave, Long> {

	Optional<EmpLeave> findByleaveIdAndStatus(Long leaveId, String status);

	List<EmpLeave> findAllByEmployeeId(Long employeeId);
	 List<EmpLeave> findByLeaveType(String leaveType);
	 List<EmpLeave> findByLeaveTypeIn(List<String> leaveTypes);
	 
	  @Query("SELECT e FROM EmpLeave e WHERE (:leaveTypes IS NULL OR e.leaveType IN :leaveTypes) AND (:statuses IS NULL OR e.status IN :statuses)")
	    Page<EmpLeave> findByLeaveTypeInAndStatusIn(List<String> leaveTypes, List<String> statuses, Pageable pageable);


}
