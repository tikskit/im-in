package ru.tikskit.imin.services.geocode.here;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@SpringBootTest
@DisplayName("Геокодер для HERE Technologies должен")
class GeocoderHereTest {

    @SpringBootConfiguration
    @EnableCaching
    @Import({GeocoderHere.class})
    @TestPropertySource
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
        cacheManager.getCache("hereGeodata").clear();
    }

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

    private AddressDto createExistingAddress() {
        return new AddressDto("France", "Île-de-France", "Paris", "Rue Daunou", "5");
    }
}