package com.xapp.ai.function;

import com.xapp.ai.function.pojo.WeatherConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(WeatherConfigProperties.class)
public class SpringAiFunctionApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAiFunctionApplication.class, args);
	}

}
