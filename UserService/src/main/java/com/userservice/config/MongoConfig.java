//package com.userservice.config;
//
//import com.mongodb.Mongo;
//import com.mongodb.MongoClient;
//import com.userservice.repository.RepositoryPackage;
//
//import org.bson.BsonUndefined;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.core.convert.converter.ConverterFactory;
//import org.springframework.data.convert.ReadingConverter;
//import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
//import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
//
//@Configuration
//   @EnableMongoRepositories(basePackageClasses=RepositoryPackage.class)
////@EnableMongoRepositories(basePackages = {"com.userservice.repository"})
//   //@ComponentScan(basePackageClasses=TemplatePackage.class)
//public class MongoConfig extends AbstractMongoConfiguration {
//
//
//    @Override
//    protected String getDatabaseName() {
//        return mongoDB;
//        //return "test";
//    }
//
//    @Override
//    public Mongo mongo() throws Exception {
//
//       // return new MongoClient(mongoHost + ":" + mongoPort);
//        return  new MongoClient();
//    }
//    
//    @ReadingConverter
//	public class BsonUndefinedToNullObjectConverterFactory implements ConverterFactory<BsonUndefined, Object> {
//	    @Override
//	    public <T extends Object> Converter<BsonUndefined, T> getConverter(Class<T> targetType) {
//	        return o -> null;
//	    }
//	}
//    
//    @Override
//    protected String getMappingBasePackage() {
//        return "com.userservice.repository";
//    }
//}
