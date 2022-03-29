package com.user.userservice.feignclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.user.userservice.model.Bike;

@FeignClient(name = "bike-service",path = "/bike")
public interface BikeFeignClient {

	@PostMapping
	Bike save(@RequestBody Bike bike);
	
	
	@GetMapping("/byuser/{userId}")
	List<Bike> getBikes(@PathVariable("userId") int id);
}
