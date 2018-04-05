package com.userservice.repository;

import java.util.List;

import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.userservice.model.User;

@Repository("userRepository")
public interface UserRepository extends MongoRepository<User, String> {

	User findByEmail(String email);
	
	List<User> findByAddressLocationNear(Point location, Distance distance);
    List<User> findByFirstNameAndAddressLocationNear(String name,Point location, Distance distance);


	
}