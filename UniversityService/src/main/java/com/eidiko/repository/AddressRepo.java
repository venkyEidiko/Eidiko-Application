package com.eidiko.repository;

import com.eidiko.exception_handler.UserNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eidiko.entity.Address;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepo extends JpaRepository<Address, Integer>{


    @Query("SELECT a FROM Address a WHERE a.employee.employeeId = :employeeId")
    List<Address> findAddressesByEmployeeId(@Param("employeeId") Long employeeId);


    @Query("SELECT a FROM Address a WHERE a.employee.id = :employeeId AND a.addressType = :addressType")
    Optional<Address> findByEmployeeIdAndAddressType(@Param("employeeId") Integer employeeId, @Param("addressType") String addressType);
}
