package com.car.car.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.car.car.entity.Car;
import com.car.car.repository.CarRepository;

@Service
public class CarService {

	@Autowired
	private CarRepository repository;

	
	public List<Car> getAll(){
		return this.repository.findAll();
	}
	
	public Car getUserById(int id) {
		return this.repository.findById(id).orElse(null);
	}
	
	public Car save(Car car) {
		Car carNew = this.repository.save(car);
		return carNew;
	}
	
	public List<Car> byUserId(int userId){
		return repository.findByUserId(userId);
	}
}
