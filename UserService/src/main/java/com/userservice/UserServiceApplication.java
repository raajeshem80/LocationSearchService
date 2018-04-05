package com.userservice;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.userservice.config.BsonUndefinedToNullObjectConverterFactory;
import com.userservice.model.Address;
import com.userservice.model.User;
import com.userservice.repository.UserRepository;

@SpringBootApplication
@EnableMongoRepositories(basePackages = {"com.userservice.repository"})
public class UserServiceApplication {
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceApplication.class);


	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}
	
	
//	@Bean
//	public CommandLineRunner setup(UserRepository userRepository) {
//		return (args) -> {
//			
//			userRepository.deleteAll();
//			
//			User user = new User("Rajesh", "Engimoori", true);
//			user.setEmail("test@test.com");
//			user.setUserName("raajesh");
//			user.setPhoneNumber(12345678);
//			user.setPassword("rajesh");
//			user.setRepeatPassword("rajesh");
//			Address address = new Address();
//			address.setAddressLine1("VTM Rama");
//			address.setAddressLine2("NGR Layout");
//			address.setCity("Bangalore");
//			address.setCountry("India");
//			address.setPostalCode("560068");
//			address.setState("Karnataka");
//			address.setLocation(new GeoJsonPoint(Double.valueOf("12.912135"),Double.valueOf("77.622971")));
//			user.setAddress(address);
//			userRepository.save(user);
//			
//			user = new User("Jaya", "Singh", true);
//			user.setEmail("test1@test1.com");
//			user.setUserName("jayasingh");
//			user.setPhoneNumber(12345678);
//			user.setPassword("jayasingh");
//			user.setRepeatPassword("jayasingh");
//			address = new Address();
//			address.setAddressLine1("Blidvädersgatan");
//			address.setAddressLine2("Sweeden ST");
//			address.setCity("Göteborg");
//			address.setCountry("Sweeden");
//			address.setPostalCode("45678");
//			address.setState("Göteborg");
//			address.setLocation(new GeoJsonPoint(Double.valueOf("57.709852"),Double.valueOf("11.891843")));
//			
//			user.setAddress(address);
//			
//			userRepository.save(user);
//			System.out.println("Customers found with findByLastName('Smith'):");
//			System.out.println("--------------------------------");
//			user = userRepository.findByEmail("test1@test1.com");
//				System.out.println(user.getFirstName());
//			logger.info("The sample data has been generated");
//		};
//	}
	
	//remove _class
    @Bean
    public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory,
                                       MongoMappingContext context) {

        MappingMongoConverter converter =
                new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory), context);
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));

        
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory, converter);
        
        MappingMongoConverter mongoMapping = (MappingMongoConverter) mongoTemplate.getConverter();
        mongoMapping.setCustomConversions(customConversions()); // tell mongodb to use the custom converters
        mongoMapping.afterPropertiesSet();
       
        return mongoTemplate;

    }
    
    /**
     * Returns the list of custom converters that will be used by the MongoDB template
     * 
     **/
     public CustomConversions customConversions() {
         return new CustomConversions(Arrays.asList(new BsonUndefinedToNullObjectConverterFactory()));
     }

}