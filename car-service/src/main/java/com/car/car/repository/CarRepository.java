package com.car.car.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.car.car.entity.Car;

@Repository
public interface CarRepository extends JpaRepository<Car,Integer>{

	List<Car> findByUserId(int id);
}
