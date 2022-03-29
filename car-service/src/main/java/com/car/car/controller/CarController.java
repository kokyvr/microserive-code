package com.car.car.controller;

import java.util.List;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.car.car.entity.Car;
import com.car.car.service.CarService;

@RestController
@RequestMapping(path = "car")
public class CarController {

	@Autowired
	private CarService carService;
	
	@GetMapping
	public ResponseEntity<List<Car>> getAll(){
		List<Car> cars = carService.getAll();
		if(cars.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(cars);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Car> getUserByID(@PathVariable("id") int id){
		Car car = carService.getUserById(id);
		if(car == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(car);
	}
	
	@PostMapping
	public ResponseEntity<Car> save(@RequestBody Car car){
		Car carNew = carService.save(car);
			return ResponseEntity.ok(carNew);
	
	}
	@GetMapping("/byuser/{userId}")
	public ResponseEntity<List<Car>> getByUserID(@PathVariable("userId")int id){
		List<Car> cars = carService.byUserId(id);
		
		return ResponseEntity.ok(cars);
	}
	


}
