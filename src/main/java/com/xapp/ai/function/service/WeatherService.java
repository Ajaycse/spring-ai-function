package com.xapp.ai.function.service;

import com.xapp.ai.function.pojo.WeatherConfigProperties;
import com.xapp.ai.function.service.WeatherService.Request;
import com.xapp.ai.function.service.WeatherService.Response;
import java.util.List;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Component
@Slf4j
public class WeatherService implements Function<Request, Response> {

  private static final Logger log = LoggerFactory.getLogger(WeatherService.class);
  private final WeatherConfigProperties weatherConfigProperties;
  private final RestClient restClient;

  public WeatherService(WeatherConfigProperties weatherConfigProperties) {
    this.weatherConfigProperties = weatherConfigProperties;
    this.restClient = RestClient.create(weatherConfigProperties.url());
  }


  public record Request(String location) {

  }

  public record Response(Main main, List<Weather> weathers) {

  }

  public record Main(double temp, double feels_like, double temp_min, double temp_max,
                     int humidity) {

  }

  public record Weather(String description) {

  }

  @Override
  public Response apply(Request request) {
    MultiValueMap<String, String> map = new LinkedMultiValueMap();
    map.add("x-rapidapi-key", weatherConfigProperties.key());
    map.add("x-rapidapi-host", weatherConfigProperties.host());

    log.debug("rest call to weather service started");

    Response response = restClient.get()
        .uri("/{city}/EN", request.location())
        .headers(headers -> headers.addAll(map))
        .retrieve()
        .body(Response.class);
    log.debug("rest call to weather service ended");
    return response;
  }

}
