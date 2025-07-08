package com.xapp.ai.function.service;

import com.xapp.ai.function.service.WeatherService.Request;
import com.xapp.ai.function.service.WeatherService.Response;
import java.util.List;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class WeatherService  implements Function<Request, Response> {

  @Value("x-rapidapi-key")
  private String apiKey;

  @Value("x-rapidapi-host")
  private String apiHost;

  @Value("x-rapidapi-url")
  private String baseUrl;

  private final RestClient restClient;


  public record  Request(String location) {}
  public record Response(Main main, List<Weather> weathers) {}
  public record Main(double temp, double feels_like, double temp_min, double temp_max) {}

  public record Weather(String description) {}

    @Override
    public Response apply(Request request) {
        //return new Response(30.5, Unit.C);

      Response response = restClient.get()
          .uri("/{city}/EN", request.location())
          .retrieve()
          .body(Response.class);
      return response;
    }

}
