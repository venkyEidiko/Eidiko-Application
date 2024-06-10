package com.eidiko.userrepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eidiko.entity.Address;

@Repository
public interface AddressRepo extends JpaRepository<Address, Integer>{

}
