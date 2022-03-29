package com.bike.bike.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bike.bike.entity.Bike;
import com.bike.bike.service.BikeService;

@RestController
@RequestMapping(path = "bike")
public class BikeController {

	@Autowired
	private BikeService bikeService;
	
	@GetMapping
	public ResponseEntity<List<Bike>> getAll(){
		List<Bike> bikes = bikeService.getAll();
		if(bikes.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(bikes);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Bike> getUserByID(@PathVariable("id") int id){
		Bike bike = bikeService.getUserById(id);
		if(bike == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(bike);
	}
	
	@PostMapping
	public ResponseEntity<Bike> save(@RequestBody Bike bike){
		Bike bikeNew = bikeService.save(bike);
			return ResponseEntity.ok(bikeNew);
	
	}
	@GetMapping("/byuser/{userId}")
	public ResponseEntity<List<Bike>> getByUserID(@PathVariable("userId")int id){
		List<Bike> cars = bikeService.byUserId(id);
		return ResponseEntity.ok(cars);
	}
	
	
}
