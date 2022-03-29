package com.user.userservice.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.user.userservice.entity.User;
import com.user.userservice.feignclients.BikeFeignClient;
import com.user.userservice.feignclients.CarFeignClient;
import com.user.userservice.model.Bike;
import com.user.userservice.model.Car;
import com.user.userservice.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private RestTemplate resTemplate;
	
	@Autowired
	private CarFeignClient carClient;
	
	@Autowired
	private BikeFeignClient bikeClient;
	
	public List<User> getAll(){
		return this.repository.findAll();
	}
	
	public User getUserById(int id) {
		return this.repository.findById(id).orElse(null);
	}
	
	public User save(User user) {
		User userNew = this.repository.save(user);
		return userNew;
	}
	public List<Car> getCars(int userId){
		Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer" + jwt.getTokenValue());
		ResponseEntity<List> cars = resTemplate.exchange("http://car-service/car/byuser/"+userId,HttpMethod.GET,new HttpEntity<>(httpHeaders),List.class);
		return cars.getBody();
	}
	
	public List<Bike> getBikes(int userId){
		Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer" + jwt.getTokenValue());
		
		ResponseEntity<List> bikes = resTemplate.exchange("http://bike-service/bike/byuser/"+userId,HttpMethod.GET,new HttpEntity<>(httpHeaders),List.class);
		return bikes.getBody();
	}
	public Car saveCar(int userId,Car car) {
		car.setUserId(userId);
		Car carNew = carClient.save(car);
		
		return carNew;
	}
	
	public Bike saveBike(int userId,Bike bike) {
		bike.setUserId(userId);
		Bike bikeNew = bikeClient.save(bike);
		return bikeNew;
	}
	public Map<String,Object> getUserAndVehicles(int id){
		Map<String,Object> result = new HashMap<>();
		User user = getUserById(id);
		if(user == null) {
			result.put("Mensaje","No existe el usuario");
			return result;
		}
		result.put("User",user);
		List<Car> cars = carClient.getCars(id);
		if(cars.isEmpty()) {
			result.put("Cars","Usuario no tiene coches");
		}else {
			result.put("Cars", cars);
		}
		List<Bike> bikes = bikeClient.getBikes(id);
		if(bikes.isEmpty()) {
			result.put("Bikes","Usuario no tiene motos");
		}else {
			result.put("Bikes", bikes);
		}
		return result;
		
	}
	
}
