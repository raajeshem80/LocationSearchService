package com.usersearchservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/v1/api")
@Api(value="userservice", description="user service detail")
public class UserSearchController {
	
}