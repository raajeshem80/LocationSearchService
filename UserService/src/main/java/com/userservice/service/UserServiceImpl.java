package com.userservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import com.userservice.model.User;
import com.userservice.repository.UserRepository;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository; 

	@Override
	public User getUserById(String id) {
		return userRepository.findOne(id);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public void saveUser(User user) {
//        User existing = userRepository.findOne(user.getId());
//        if (existing != null) {
//        }
        userRepository.save(user);
	}

	@Override
	public boolean isUserExist(User user) {
		return userRepository.findByEmail(user.getEmail()) != null;
	}

	@Override
	public User getUserByEmail(String email) {
		// TODO Auto-generated method stub
		System.out.println("^^^^^^^^^^^^^^6 userRepository "+userRepository);
		return userRepository.findByEmail(email);
	}

	@Override
	public List<User> findByAddressLocationNear(Point location, Distance distance) {
		// TODO Auto-generated method stub
		return userRepository.findByAddressLocationNear(location, distance);
	}

	@Override
	public List<User> findByNameAndAddressLocationNear(String name, Point location, Distance distance) {
		// TODO Auto-generated method stub
		return userRepository.findByFirstNameAndAddressLocationNear(name, location, distance);
	}

}