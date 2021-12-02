package ru.tikskit.imin.services.geocode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.HttpClientErrorException;
import ru.tikskit.imin.services.dto.AddressDto;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@SpringBootTest
@DisplayName("Резолвер адреса должен")
class AddressResolverServiceImplTest {

    @SpringBootConfiguration
    @Import({AddressResolverServiceImpl.class})
    @TestPropertySource
    public static class Config{
    }

    @MockBean
    Geocoder geocoder;
    @Autowired
    AddressResolverService resolver;

    @Test
    @DisplayName("получать данные из геокодера, если геокодер отработал без ошибок и вернул данные")
    public void shouldGetResponce() {
        final double lat = 10.2232d;
        final double lng = 23.43434d;
        LatLng latLng = new LatLng(lat, lng);
        AddressDto addressDto = new AddressDto("Russia", "Novosibirskaya olbast", "Novosibirsk",
                "Lenina", "1");
        when(geocoder.request(addressDto)).thenReturn(RequestResult.received(latLng));

        Optional<LatLng> result = resolver.resolve(addressDto);

        verify(geocoder, times(1)).request(addressDto);
        assertThat(result).isPresent().get().isEqualTo(latLng);
    }

    @Test
    @DisplayName("возвращать empty, если геокодер не вернул данные из-за ошибки")
    public void shouldReturnEmptyWhenExceptionOccurred() {
        AddressDto addressDto = new AddressDto("Russia", "Novosibirskaya olbast", "Novosibirsk",
                "Lenina", "1");
        when(geocoder.request(addressDto)).thenReturn(RequestResult.exception(HttpClientErrorException.create(
                HttpStatus.BAD_REQUEST, "status", null, null, null)));

        Optional<LatLng> result = resolver.resolve(addressDto);

        verify(geocoder, times(1)).request(addressDto);
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("возвращать empty, если геокодер вернул пустой набор")
    public void shouldReturnEmptyWhenNoData() {
        AddressDto addressDto = new AddressDto("Russia", "Novosibirskaya olbast", "Novosibirsk",
                "Lenina", "1");
        when(geocoder.request(addressDto)).thenReturn(RequestResult.empty());

        Optional<LatLng> result = resolver.resolve(addressDto);

        verify(geocoder, times(1)).request(addressDto);
        assertThat(result).isEmpty();
    }

}