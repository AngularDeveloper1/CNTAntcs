package com.es.wfx.service.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.es.wfx.analytics.exception.BadDataException;
import com.mongodb.MongoSocketOpenException;
import com.mongodb.MongoTimeoutException;

@ControllerAdvice  
@RestController 
public class GlobalExceptionHandler {
	
	@Value("${MongoDBException.message}")
	private String MONGODBENEXCEPTION_MESSAGE;
	
	@Value("${GeneralException.message}")
	private String GENERALEXCEPTION_MESSAGE;
	
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = MongoSocketOpenException.class)  
    public String handleException(MongoSocketOpenException e){
    	log.error(MONGODBENEXCEPTION_MESSAGE, e);
    	return MONGODBENEXCEPTION_MESSAGE;
    }
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = MongoTimeoutException.class)  
    public String handleException(MongoTimeoutException e){
    	log.error(MONGODBENEXCEPTION_MESSAGE, e);
    	return MONGODBENEXCEPTION_MESSAGE;
    }
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BadDataException.class)  
    public String handleException(BadDataException e){
    	log.error("Bad Request", e);
    	return e.getMessage();
    }
	
	
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)  
    public String handleException(Exception e){
    	log.error(GENERALEXCEPTION_MESSAGE, e);
    	return GENERALEXCEPTION_MESSAGE;
    } 
}
