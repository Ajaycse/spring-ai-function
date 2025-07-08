package com.xapp.ai.function.config;


import com.xapp.ai.function.service.WeatherService;
import com.xapp.ai.function.service.WeatherService.Request;
import com.xapp.ai.function.service.WeatherService.Response;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {

  @Value("x-rapidapi-key")
  private String apiKey;

  @Value("x-rapidapi-host")
  private String apiHost;

  @Value("x-rapidapi-url")
  private String baseUrl;

  @Bean
  @Description(("Get the current weather in location in Celsius or Fahrenheit"))
  public Function<Request, Response> getCurrentWeather() {
    return new WeatherService(getRestClient());
  }

  @Bean
  public RestClient getRestClient() {
    MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
    headers.add("x-rapid-api-key", apiKey);
    headers.add("x-rapid-api-host", apiHost);

    RestClient restClient = RestClient.builder()
        .baseUrl(baseUrl)
        .defaultHeaders(httpHeaders ->  httpHeaders.addAll(headers))
        .build();

    return restClient;
  }

}
