package com.javatpoint.microservices.limitsservice;  
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;  
import org.springframework.web.bind.annotation.RestController;
import com.javatpoint.microservices.limitservice.bean.LimitConfiguration;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;  

@RestController  
public class LimitsConfigurationController   
{  
	@Autowired
	Configuration configuration;
	
	@GetMapping("/limits")  
	public LimitConfiguration retriveLimitsFromConfigurations()  
	{  
		//return new LimitConfiguration(1000, 1);  
		//getting values from the properties file  
		return new LimitConfiguration(configuration.getMaximum(), configuration.getMinimum());  
	}  
	
	@GetMapping("/fault-tolerance-example")
	// configuring a fallback method
	@HystrixCommand(fallbackMethod = "fallbackRetrieveConfigurations")
	public LimitConfiguration retrieveConfigurations() {
		throw new RuntimeException("Not Available");
	}

	// defining the fallback method
	public LimitConfiguration fallbackRetrieveConfigurations() {
		// returning the default configuration
		return new LimitConfiguration(999, 9);
	} 
}  