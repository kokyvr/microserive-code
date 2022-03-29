package com.user.userservice.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.userservice.entity.User;
import com.user.userservice.model.Bike;
import com.user.userservice.model.Car;
import com.user.userservice.service.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping(path = "user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping
	public ResponseEntity<List<User>> getAll(){
		List<User> users = userService.getAll();
		if(users.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(users);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getUserByID(@PathVariable("id") int id){
		User user = userService.getUserById(id);
		if(user == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(user);
	}
	
	@PostMapping
	public ResponseEntity<User> save(@RequestBody User user){
		User userNew = userService.save(user);
			return ResponseEntity.ok(userNew);
	
	}
	
	
	@CircuitBreaker(name = "carsCB",fallbackMethod = "fallBackGetCars")
	@GetMapping("/cars/{userId}")
	public ResponseEntity<List<Car>> getCars(@PathVariable("userId") int userID){
		User user = userService.getUserById(userID);
		if(user == null)
			return ResponseEntity.notFound().build();
		
	List<Car> cars = userService.getCars(userID);
	return ResponseEntity.ok(cars);
	}
	@CircuitBreaker(name = "carsCB",fallbackMethod = "fallBackSaveCar")
	@PostMapping("/savecar/{userId}")
	public ResponseEntity<Car> saveCar(@PathVariable("userId")int userID,@RequestBody Car car){
		if(userService.getUserById(userID)==null) 
			return ResponseEntity.notFound().build();
		Car carNew = userService.saveCar(userID, car);
		return ResponseEntity.ok(carNew);
	}
	
	
	
	@CircuitBreaker(name = "bikesCB",fallbackMethod = "fallBackGetBikes")
	@GetMapping("/bikes/{userId}")
	public ResponseEntity<List<Bike>> getBikes(@PathVariable("userId") int userID){
		User user = userService.getUserById(userID);
		if(user == null)
			return ResponseEntity.notFound().build();
		
	List<Bike> bikes = userService.getBikes(userID);
	return ResponseEntity.ok(bikes);
	}
	@CircuitBreaker(name = "bikesCB",fallbackMethod = "fallBackSaveBike")
	@PostMapping("/savebike/{userId}")
	public ResponseEntity<Bike> saveBike(@PathVariable("userId")int userId,@RequestBody Bike car){
		if(userService.getUserById(userId)==null) 
			return ResponseEntity.notFound().build();
		Bike carNew = userService.saveBike(userId, car);
		return ResponseEntity.ok(carNew);
	}

	@CircuitBreaker(name = "allCB",fallbackMethod = "fallBackGetAll")
	@GetMapping("/getAll/{userId}")
	public ResponseEntity<Map<String,Object>> getAllVehicles(@PathVariable("userId")int id){
		Map<String,Object> result = userService.getUserAndVehicles(id);
		
		return ResponseEntity.ok(result);
	}
	
	private ResponseEntity<List<Car>> fallBackGetCars(@PathVariable("userId") int userID,RuntimeException e){
		return new ResponseEntity("El usuario "+userID + "tiene los coches en el taller",HttpStatus.OK);
	}
	private ResponseEntity<List<Car>> fallBackSaveCar(@PathVariable("userId")int userID,@RequestBody Car car,RuntimeException e){
		return new ResponseEntity("El usuario "+userID + "no tiene dinero para comprar coches",HttpStatus.OK);
	}
	
	
	
	private ResponseEntity<List<Bike>> fallBackGetBikes(@PathVariable("userId") int userID,RuntimeException e){
		return new ResponseEntity("El usuario "+userID + "tiene las motos en el taller",HttpStatus.OK);
	}
	private ResponseEntity<List<Bike>> fallBackSaveBike(@PathVariable("userId")int userId,@RequestBody Bike bike,RuntimeException e){
		return new ResponseEntity("El usuario "+userId + "no tiene dinero para comprar motos",HttpStatus.OK);
	}
	
	public ResponseEntity<Map<String,Object>> fallBackGetAll(@PathVariable("userId")int userId){
		return new ResponseEntity("El usuario "+userId + " tiene los vehiculos en el talles RuntimeException e",HttpStatus.OK);
	}
	
}
