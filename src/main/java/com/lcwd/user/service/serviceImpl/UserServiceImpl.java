package com.lcwd.user.service.serviceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.lcwd.user.service.entity.Hotel;
import com.lcwd.user.service.entity.Rating;
import com.lcwd.user.service.entity.User;
import com.lcwd.user.service.exception.ResourceNotFoundException;
import com.lcwd.user.service.external.services.HotelService;
import com.lcwd.user.service.repository.UserRepository;
import com.lcwd.user.service.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private HotelService hotelService;

	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public User saveUser(User user) {

		// generate unique userId

		String randomUserId = UUID.randomUUID().toString();
		user.setUserId(randomUserId);
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUser() {

		return userRepository.findAll();
	}

	@Override
	public User getUser(String userId) {
		User user = userRepository.findById(userId).orElseThrow(
				() -> new ResourceNotFoundException("User with given id not found on server !! : " + userId));
		Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/rating/users/" + user.getUserId(),
				Rating[].class);

		List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();

		logger.info("-----------------" + ratingsOfUser);

		List<Rating> ratingList = ratings.stream().map(rating -> {
			// api call to hotel service to get hotel
			// http://localhost:4040/hotels/cc80f4c3-0332-4d20-9636-a0716962269d

//			ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/" + rating.getHotelId(), Hotel.class);

			Hotel hotel = hotelService.getHotel(rating.getHotelId());

//			logger.info("respone status code {} : " + forEntity.getStatusCode());

			// set the hotel to rating

			rating.setHotel(hotel);

			// return the rating
			return rating;
		}).collect(Collectors.toList());

		user.setRatings(ratingList);
		return user;
	}

	@Override
	public User updateUser(String userId) {
		User user = userRepository.findById(userId).orElseThrow(
				() -> new ResourceNotFoundException("User with given id not found on server !! : " + userId));
		user.setEmail(user.getName());
		user.setEmail(user.getEmail());
		user.setAbout(user.getAbout());

		return userRepository.save(user);

	}

	@Override
	public void deleteUser(String userId) {

		userRepository.deleteById(userId);
	}

}
