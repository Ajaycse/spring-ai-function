package com.xapp.ai.function.config;


import com.xapp.ai.function.pojo.WeatherConfigProperties;
import com.xapp.ai.function.service.WeatherService;
import com.xapp.ai.function.service.WeatherService.Request;
import com.xapp.ai.function.service.WeatherService.Response;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

@Configuration
@RequiredArgsConstructor
public class Config {

  private final WeatherConfigProperties weatherConfigProperties;

  @Bean
  @Description(("Get the current weather in location in Celsius"))
  public Function<Request, Response> getCurrentWeather() {
    return new WeatherService(weatherConfigProperties);
  }

}
