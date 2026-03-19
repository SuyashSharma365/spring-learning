package com.suyash.springlearning.services;


import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WeatherService {

    private final WebClient webClient;

    @Value("${weather.api.key}")
    private String apiKey;

    public WeatherService(WebClient.Builder builder){
        this.webClient = builder.baseUrl("http://api.weatherapi.com/v1").build();
    }

    public double getTemperature(String city){
        JsonNode response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                .path("/current.json")
                .queryParam("key" , apiKey).queryParam("q" , city).build())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        if(response == null){
            throw new RuntimeException("No response from API");
        }

        return response.get("current").get("temp_c").asDouble();
    }
}
