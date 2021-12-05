package ru.tikskit.imin.services.geocode.here;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.tikskit.imin.services.dto.AddressDto;
import ru.tikskit.imin.services.geocode.AddressToUriConverter;
import ru.tikskit.imin.services.geocode.Geocoder;
import ru.tikskit.imin.services.geocode.LatLng;
import ru.tikskit.imin.services.geocode.RequestResult;
import ru.tikskit.imin.services.geocode.ResultStatus;
import ru.tikskit.imin.services.geocode.here.dto.GeoData;
import ru.tikskit.imin.services.geocode.here.dto.Position;
import ru.tikskit.imin.services.geocode.here.dto.Result;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@SpringBootTest
@DisplayName("Геокодер для HERE Technologies должен")
class GeocoderHereTest {

    @Configuration
    @EnableCaching
    @Import({GeocoderHere.class})
    public static class Config{
        @Bean
        public CacheManager cacheManager() {
            SimpleCacheManager cacheManager = new SimpleCacheManager();
            List<Cache> caches = new ArrayList<>();
            caches.add(new ConcurrentMapCache("hereGeodata"));
            cacheManager.setCaches(caches);
            return cacheManager;
        }
    }

    @MockBean
    RestTemplateBuilder builder;
    @MockBean
    PositionConverter positionConverter;
    @MockBean
    RestTemplate restTemplate;
    @MockBean(name = "addressToUriConverterHere")
    AddressToUriConverter addressToUriConverter;
    @Autowired
    Geocoder geocoder;
    @Autowired
    CacheManager cacheManager;

    private final static String QUERY = "https://geocode.search.hereapi.com/v1/geocode?q=5+Rue+Daunou%2C+75000+Paris%2C+France";

    @BeforeEach
    public void setUp() {
        Objects.requireNonNull(cacheManager.getCache("hereGeodata")).clear();
    }

    @SuppressWarnings("unchecked")
    @Test
    @DisplayName("возвращать корректные результаты, когда запрос успешно выполнился")
    public void shouldReturnCorrectResultsWhenSuccess() {
        final double lat = 10.2232d;
        final double lng = 23.43434d;
        URI uri = UriComponentsBuilder
                .fromHttpUrl("https://geocode.search.hereapi.com/")
                .build()
                .toUri();
        when(addressToUriConverter.convert(any())).thenReturn(uri);
        when(positionConverter.convert(new Position(lat, lng))).thenReturn(new LatLng(lat, lng));

        Result res = new Result();
        Position pos = new Position(lat, lng);
        GeoData geoData = new GeoData();
        geoData.setPosition(pos);
        res.setItems(List.of(geoData));
        when(builder.build()).thenReturn(restTemplate);

        ResponseEntity<Result> re = (ResponseEntity<Result>) mock(ResponseEntity.class);
        when(re.getBody()).thenReturn(res);

        when(restTemplate.getForEntity(uri, Result.class)).thenReturn(re);

        RequestResult result = geocoder.request(createExistingAddress());
        assertThat(result.getStatus()).isNotNull().isEqualTo(ResultStatus.RECEIVED);
        assertThat(result.getLatLng()).isNotNull().isEqualTo(new LatLng(lat, lng));
        assertThat(result.getException()).isNull();

        verify(restTemplate, times(1)).getForEntity(uri, Result.class);
    }

    @SuppressWarnings("unchecked")
    @Test
    @DisplayName("использовать кеширование")
    public void shouldUseCache() {
        final double lat = 10.2232d;
        final double lng = 23.43434d;
        URI uri = UriComponentsBuilder
                .fromHttpUrl("https://geocode.search.hereapi.com/")
                .build()
                .toUri();
        when(addressToUriConverter.convert(any())).thenReturn(uri);
        when(positionConverter.convert(new Position(lat, lng))).thenReturn(new LatLng(lat, lng));

        Result res = new Result();
        Position pos = new Position(lat, lng);
        GeoData geoData = new GeoData();
        geoData.setPosition(pos);
        res.setItems(List.of(geoData));
        when(builder.build()).thenReturn(restTemplate);


        ResponseEntity<Result> re = (ResponseEntity<Result>) mock(ResponseEntity.class);
        when(re.getBody()).thenReturn(res);

        when(restTemplate.getForEntity(uri, Result.class)).thenReturn(re);

        AddressDto existingAddress = createExistingAddress();
        geocoder.request(existingAddress);
        geocoder.request(existingAddress);
        geocoder.request(existingAddress);
        geocoder.request(existingAddress);

        verify(restTemplate, times(1)).getForEntity(uri, Result.class);
    }

    @SuppressWarnings("unchecked")
    @Test
    @DisplayName("нормально обрабатывать ситуацию, когда возвращается пустой набор данных")
    public void shouldHandleNoItems() {
        final double lat = 10.2232d;
        final double lng = 23.43434d;
        URI uri = UriComponentsBuilder
                .fromHttpUrl("https://geocode.search.hereapi.com/")
                .build()
                .toUri();
        when(addressToUriConverter.convert(any())).thenReturn(uri);
        when(positionConverter.convert(new Position(lat, lng))).thenReturn(new LatLng(lat, lng));

        Result res = new Result();
        when(builder.build()).thenReturn(restTemplate);


        ResponseEntity<Result> re = (ResponseEntity<Result>) mock(ResponseEntity.class);
        when(re.getBody()).thenReturn(res);

        when(restTemplate.getForEntity(uri, Result.class)).thenReturn(re);

        AddressDto existingAddress = createExistingAddress();
        RequestResult result = geocoder.request(existingAddress);

        assertThat(result.getStatus()).isEqualTo(ResultStatus.EMPTY);
    }

    @SuppressWarnings({"unchecked"})
    @Test
    @DisplayName("нормально обрабатывать ситуацию, когда запрос выполнился с ошибкой HttpClientErrorException")
    public void shouldHandleHttpClientErrorException() {
        final double lat = 10.2232d;
        final double lng = 23.43434d;
        URI uri = UriComponentsBuilder
                .fromHttpUrl("https://geocode.search.hereapi.com/")
                .build()
                .toUri();
        when(addressToUriConverter.convert(any())).thenReturn(uri);
        when(positionConverter.convert(new Position(lat, lng))).thenReturn(new LatLng(lat, lng));

        Result res = new Result();
        when(builder.build()).thenReturn(restTemplate);


        ResponseEntity<Result> re = (ResponseEntity<Result>) mock(ResponseEntity.class);
        when(re.getBody()).thenReturn(res);

        when(restTemplate.getForEntity(uri, Result.class)).thenThrow(HttpClientErrorException.create(
                HttpStatus.BAD_REQUEST, "status", new HttpHeaders(), null, null));

        AddressDto existingAddress = createExistingAddress();
        RequestResult result = geocoder.request(existingAddress);

        assertThat(result.getStatus()).isEqualTo(ResultStatus.EXCEPTION);
        assertThat(result.getException()).isNotNull().isInstanceOf(HttpClientErrorException.BadRequest.class);
    }

    @SuppressWarnings({"unchecked"})
    @Test
    @DisplayName("нормально обрабатывать ситуацию, когда запрос выполнился с ошибкой RuntimeException")
    public void shouldHandleRuntimeException() {
        final double lat = 10.2232d;
        final double lng = 23.43434d;
        URI uri = UriComponentsBuilder
                .fromHttpUrl("https://geocode.search.hereapi.com/")
                .build()
                .toUri();
        when(addressToUriConverter.convert(any())).thenReturn(uri);
        when(positionConverter.convert(new Position(lat, lng))).thenReturn(new LatLng(lat, lng));

        Result res = new Result();
        when(builder.build()).thenReturn(restTemplate);


        ResponseEntity<Result> re = (ResponseEntity<Result>) mock(ResponseEntity.class);
        when(re.getBody()).thenReturn(res);

        when(restTemplate.getForEntity(uri, Result.class)).thenThrow(RestClientException.class);

        AddressDto existingAddress = createExistingAddress();
        RequestResult result = geocoder.request(existingAddress);

        assertThat(result.getStatus()).isEqualTo(ResultStatus.EXCEPTION);
        assertThat(result.getException()).isNotNull().isInstanceOf(RestClientException.class);
    }

    @SuppressWarnings({"unchecked"})
    @Test
    @DisplayName("нормально обрабатывать ситуацию, когда сервер сообщил, что превышен лимит для ключа")
    public void shouldHandleLimitExceeded() {
        final double lat = 10.2232d;
        final double lng = 23.43434d;
        URI uri = UriComponentsBuilder
                .fromHttpUrl("https://geocode.search.hereapi.com/")
                .build()
                .toUri();
        when(addressToUriConverter.convert(any())).thenReturn(uri);
        when(positionConverter.convert(new Position(lat, lng))).thenReturn(new LatLng(lat, lng));

        Result res = new Result();
        when(builder.build()).thenReturn(restTemplate);


        ResponseEntity<Result> re = (ResponseEntity<Result>) mock(ResponseEntity.class);
        when(re.getBody()).thenReturn(res);

        when(restTemplate.getForEntity(uri, Result.class)).thenThrow(HttpClientErrorException.create(
                HttpStatus.TOO_MANY_REQUESTS, "status", new HttpHeaders(), null, null));

        AddressDto existingAddress = createExistingAddress();
        RequestResult result = geocoder.request(existingAddress);

        assertThat(result.getStatus()).isEqualTo(ResultStatus.LIMIT_EXCEEDED);
        assertThat(result.getException()).isNull();
    }

    private AddressDto createExistingAddress() {
        return new AddressDto("France", "Île-de-France", "Paris", "Rue Daunou", "5");
    }
}