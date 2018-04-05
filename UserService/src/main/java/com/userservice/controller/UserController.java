package com.userservice.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.userservice.exception.ErrorInfo;
import com.userservice.model.Address;
import com.userservice.model.User;
import com.userservice.repository.UserRepository;
import com.userservice.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

@RestController
@RequestMapping("/v1/api")
@Api(value="userservice", description="user service detail")
public class UserController {
	
	@Autowired
	private UserService userService;
		
	@ApiOperation(value = "View a list of available users",response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
	@CrossOrigin(origins = "http://localhost:8100")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public List<User> getUsers() {
		return userService.getAllUsers();
	}

	@ApiOperation(value = "Search a user with an ID",response = User.class)
	@ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "User's id", required = false, dataType = "string", paramType = "query", defaultValue="1234")
      })
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable("id") String id) {
		System.out.println("^^^^^^^^^^^^^^6 userService "+userService);
		User user = userService.getUserById(id);
		
		System.out.println("^^^^^^^^^^^^^^6 "+user);
		return user;
	}
	
	 /* Create a user */
	@ApiOperation(value = "Create a user",response = User.class)
    @RequestMapping(
            value = "user",
            method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        if (userService.isUserExist(user)) {
        	return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        
        user.getAddress().setLocation(new GeoJsonPoint(Double.valueOf(user.getAddress().getLatitude()),Double.valueOf(user.getAddress().getLongitude())));
        userService.saveUser(user);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<>(user, headers, HttpStatus.CREATED);
    }
	
	@RequestMapping("/**")
    public ResponseEntity<ErrorInfo> unmappedRequest(HttpServletRequest request) {
        String uri = request.getRequestURI();
        //throw new UnknownResourceException("There is no resource for path " + uri);
        HttpHeaders headers = new HttpHeaders();
        ErrorInfo errorInfo = new ErrorInfo("There is no resource for path " + uri);
        return new ResponseEntity<>(errorInfo, headers, HttpStatus.BAD_REQUEST);
    }
	
	@RequestMapping(path = "business",method = RequestMethod.GET)
    public final List<User> getBusinessLocations(@RequestParam("latitude") String longitude,
                                                   @RequestParam("longitude") String latitude,
                                                   @RequestParam("distance") double distance,
                                                   @RequestParam(value = "type", required = false) String type)


    {
        //log.info("********"+longitude+latitude+distance+type);
        if(type==null || type.isEmpty()){
           return userService.findByAddressLocationNear( new Point(Double.valueOf(longitude), Double.valueOf(latitude)),
                   new Distance(distance, Metrics.KILOMETERS));
        }else
        return userService.findByNameAndAddressLocationNear(type,
                new Point(Double.valueOf(longitude), Double.valueOf(latitude)),
                new Distance(distance, Metrics.KILOMETERS));
    }

    @RequestMapping(path="user/create",method=RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public final User createUser(@RequestBody User userReq){
    	User user =new User();
    	user.setFirstName(userReq.getFirstName());
    	user.setLastName(userReq.getLastName());
    	user.setEmail(userReq.getEmail());
    	user.setUserName(userReq.getUserName());
    	user.setPassword(userReq.getPassword());
    	user.setRepeatPassword(userReq.getRepeatPassword());
    	user.setPhoneNumber(userReq.getPhoneNumber());
    	user.setActive(userReq.isActive());
        Address address = new Address();
        address.setAddressLine1(userReq.getAddress().getAddressLine1());
        address.setAddressLine2(userReq.getAddress().getAddressLine2());
        address.setCity(userReq.getAddress().getCity());
        address.setState(userReq.getAddress().getState());
        address.setCountry(userReq.getAddress().getCountry());
        address.setPostalCode(userReq.getAddress().getPostalCode());
        address.setState(userReq.getAddress().getState());
        address.setLocation(new GeoJsonPoint(Double.valueOf(userReq.getAddress().getLongitude()),Double.valueOf(userReq.getAddress().getLatitude())));
        user.setAddress(address);
        userService.saveUser(user);
        return user;
    }

}