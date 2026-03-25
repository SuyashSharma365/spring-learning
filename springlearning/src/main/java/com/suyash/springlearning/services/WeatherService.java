package com.suyash.springlearning.services;


import com.fasterxml.jackson.databind.JsonNode;
import com.suyash.springlearning.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WeatherService {

    private final WebClient webClient;

    private AppCache appCache;

    @Value("${weather.api.key}")
    private String apiKey;

    public WeatherService(WebClient.Builder builder , AppCache appCache){
        this.appCache = appCache;
        String apiUrl = appCache.appConfigCache.get(AppCache.keys.WEATHER_API);
        this.webClient = builder.baseUrl(apiUrl).build();
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
