package ru.tikskit.imin.services.geocode.here;

import lombok.AllArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

@Configuration
@AllArgsConstructor
public class RestTemplateConfig {
    private final RestTemplateBuilder builder;

    @Bean
    @Scope("prototype") // для безопасной работы в многопоточке каждый раз создаем новый экземпляр
    public RestTemplate hereRestTemplate() {
         // Настраиваем RestTemplate для работы с Here
        return builder.build();
    }
}
