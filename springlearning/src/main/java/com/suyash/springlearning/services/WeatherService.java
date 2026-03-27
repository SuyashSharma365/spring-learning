package com.suyash.springlearning.services;


import com.fasterxml.jackson.databind.JsonNode;
import com.suyash.springlearning.cache.AppCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class WeatherService {

    private final WebClient webClient;

    private AppCache appCache;

    @Value("${weather.api.key}")
    private String apiKey;

    @Autowired
    private RedisService redisService;

    public WeatherService(WebClient.Builder builder , AppCache appCache){
        this.appCache = appCache;
        String apiUrl = appCache.appConfigCache.get(AppCache.keys.WEATHER_API.name());
        System.out.println("FINAL URL:"+apiUrl);
        this.webClient = builder.baseUrl(apiUrl).build();
    }



    public double getTemperature(String city){

        JsonNode redisResponse = redisService.get("weather"+city.toLowerCase() , JsonNode.class);

        if(redisResponse != null){
            return redisResponse.get("current").get("temp_c").asDouble();
        }

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

        redisService.set("weather"+city.toLowerCase() , response , 300l);
        System.out.println(response);
        return response.get("current").get("temp_c").asDouble();
    }
}
