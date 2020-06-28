package com.nextop.nextoptaxi.server.repository;

import com.nextop.nextoptaxi.server.model.TaxiEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxiRepository extends JpaRepository<TaxiEntity, Long> {

}
