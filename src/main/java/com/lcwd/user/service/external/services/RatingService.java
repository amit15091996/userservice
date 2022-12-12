package com.lcwd.user.service.external.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.lcwd.user.service.entity.Rating;

@FeignClient(name = "RATING-SERVICE")
public interface RatingService {

	@PostMapping("/rating")
	public ResponseEntity<Rating> createRating(@RequestBody Rating values);

	@PutMapping("/rating/{ratingId}")
	public ResponseEntity<Rating> updateRating(@PathVariable String ratingId, Rating values);

	@DeleteMapping("/rating/{ratingId}")
	public void deleteRating(@PathVariable String ratingId);
}
