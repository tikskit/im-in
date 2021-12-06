package ru.tikskit.imin.services.geocode.here;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import ru.tikskit.imin.services.dto.AddressDto;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Конвертер адреса в URI должен")
@SpringBootTest
@TestPropertySource
class AddressToUriConverterHereTest {

    @Configuration
    @Import(AddressToUriConverterHere.class)
    public static class Config {

    }

    @Autowired
    private AddressToUriConverterHere converter;
    @Value("${geocoding.here.apikey}")
    private String apiKey;

    @Test
    @DisplayName("добавлять в запрос ограничение на 1 записть")
    public void shouldAddLimitTo1Records() {
        URI uri = converter.convert(createExistingAddress());
        assertThat(uri).isNotNull();
        assertThat(uri.getQuery()).contains(String.format("%s=1", AddressToUriConverterHere.LIMIT_PARAM_NAME));
    }

    @Test
    @DisplayName("добавлять адрес в запрос")
    public void shouldAddAddress() {
        AddressDto address = createExistingAddress();
        URI uri = converter.convert(address);
        assertThat(uri).isNotNull();
        assertThat(uri.getQuery()).contains(address.getCountry(), address.getState(), address.getCity(),
                address.getStreet(), address.getBuilding());
    }

    @Test
    @DisplayName("добавлять apiKey")
    public void shouldAddApiKey() {
        URI uri = converter.convert(createExistingAddress());
        assertThat(uri).isNotNull();
        assertThat(uri.getQuery()).contains(String.format("%s=%s", AddressToUriConverterHere.API_KEY_PARAM_NAME, apiKey));
    }

    private AddressDto createExistingAddress() {
        return new AddressDto("France", "Île-de-France", "Paris", "Paris", "Rue Daunou", "5");
    }

}