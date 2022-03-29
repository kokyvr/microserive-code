package com.user.userservice.feignclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.user.userservice.model.Car;

@FeignClient(name = "car-service",path = "/car")
public interface CarFeignClient {
	
	@PostMapping
	Car save(@RequestBody Car car);
	
	
	@GetMapping("/byuser/{userId}")
	List<Car> getCars(@PathVariable("userId") int id);

}
