package com.nextop.nextoptaxi.server.repository;


import com.nextop.nextoptaxi.server.model.SchoolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  SchoolRepository extends JpaRepository<SchoolEntity, Long> {

}
