package com.eidiko.repository;

import com.eidiko.entity.WorkFromHomeRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WorkFromHomeRequestRepository extends JpaRepository<WorkFromHomeRequest, Long> {

    List<WorkFromHomeRequest> findByRequestTypeAndFromDateLessThanOrFromDateGreaterThanEqual(String requestType, LocalDate fromDate, LocalDate toDate);

    List<WorkFromHomeRequest> findByStatusAndToDateGreaterThanEqual(String status, LocalDate toDate);


   public List<WorkFromHomeRequest> findAllByNotifyAndStatus(String notify,String status);

   public List<WorkFromHomeRequest> findAllByEmployeeIDAndStatus(Long empId, String pending);
}