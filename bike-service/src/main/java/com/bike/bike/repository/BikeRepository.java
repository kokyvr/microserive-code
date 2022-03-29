package com.bike.bike.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bike.bike.entity.Bike;

@Repository
public interface BikeRepository extends JpaRepository<Bike,Integer>{

	List<Bike> findByUserId(int id);
}
