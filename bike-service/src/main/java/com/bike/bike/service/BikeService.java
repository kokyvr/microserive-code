package com.bike.bike.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bike.bike.entity.Bike;
import com.bike.bike.repository.BikeRepository;

@Service
public class BikeService {

	@Autowired
	private BikeRepository repository;

	
	public List<Bike> getAll(){
		return this.repository.findAll();
	}
	
	public Bike getUserById(int id) {
		return this.repository.findById(id).orElse(null);
	}
	
	public Bike save(Bike car) {
		Bike carNew = this.repository.save(car);
		return carNew;
	}
	
	public List<Bike> byUserId(int userId){
		return repository.findByUserId(userId);
	}
}
