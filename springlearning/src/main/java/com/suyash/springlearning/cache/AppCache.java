package com.suyash.springlearning.cache;
import com.suyash.springlearning.entity.ConfigEntity;
import com.suyash.springlearning.repository.ConfigEntryRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    public enum keys{
        WEATHER_API;
    }


    @Autowired
    private ConfigEntryRepository configEntryRepository;

    public Map<String , String > appConfigCache;

    @PostConstruct
    public void init(){
        appConfigCache = new HashMap<>();
        List<ConfigEntity> all = configEntryRepository.findAll();
        for(ConfigEntity configEntity : all){
            appConfigCache.put(configEntity.getKey() , configEntity.getValue());
        }
    }


}
