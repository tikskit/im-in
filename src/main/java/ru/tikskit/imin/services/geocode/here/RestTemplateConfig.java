package ru.tikskit.imin.services.geocode.here;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    /**
     * Настраиваем RestTemplate для работы с Here
     */
    @Bean
    public RestTemplate hereRestTemplate() {
        return new RestTemplate();
    }
}
