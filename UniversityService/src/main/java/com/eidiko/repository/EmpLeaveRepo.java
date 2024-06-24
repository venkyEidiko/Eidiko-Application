package com.eidiko.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eidiko.entity.EmpLeave;
@Repository
public interface EmpLeaveRepo extends JpaRepository<EmpLeave, Long> {

	Optional<EmpLeave> findByleaveIdAndStatus(Long leaveId, String status);

	List<EmpLeave> findAllByEmployeeId(Long employeeId);





	List<EmpLeave> findByLeaveTypeAndFromDateLessThanEqualAndToDateGreaterThanEqual(String leaveType, LocalDate fromDate, LocalDate toDate);

	List<EmpLeave> findByLeaveType(String leaveType);

}
