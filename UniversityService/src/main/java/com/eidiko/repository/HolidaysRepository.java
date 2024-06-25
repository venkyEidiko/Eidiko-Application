package com.eidiko.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eidiko.entity.Holidays;
@Repository
public interface HolidaysRepository extends JpaRepository<Holidays, Long>{

}
