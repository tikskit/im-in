package ru.tikskit.imin.services.geocode.here;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.tikskit.imin.services.dto.AddressDto;
import ru.tikskit.imin.services.geocode.Geocoder;
import ru.tikskit.imin.services.geocode.RequestResult;
import ru.tikskit.imin.services.geocode.here.dto.Result;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Objects;


/**
 * Геокодер, предоставляемый HERE Technologies
 * https://www.here.com/
 */
@Service
//@AllArgsConstructor
public class GeocoderHere implements Geocoder {

    private static final Logger logger = LoggerFactory.getLogger(GeocoderHere.class);

    private final String apiKey;
    private final RestTemplate restTemplate;
    private final PositionConverter positionConverter;

    public GeocoderHere(@Qualifier("hereRestTemplate") RestTemplate restTemplate,
                        @Value("${geocoding.here.apikey}") String apiKey,
                        PositionConverter positionConverter) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
        this.positionConverter = positionConverter;
    }

    private String address2Query(AddressDto address) {
        Objects.requireNonNull(address);
        return new QueryBuilderImpl()
                .addPart(address.getCountry())
                .addPart(address.getReqion())
                .addPart(address.getCity())
                .addPart(address.getStreet())
                .addPart(address.getBuilding())
                .build();
    }

    // todo Подключить нормальный кэш
    @Override
    @Cacheable("hereGeodata")
    public RequestResult request(AddressDto address) {

        final String addressStr = address2Query(address);
        URI uri = UriComponentsBuilder
                .fromHttpUrl("https://geocode.search.hereapi.com/")
                .path("v1/geocode")
                .queryParam("q", addressStr)
                .queryParam("apiKey", apiKey)
                .queryParam("limit", 1)
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUri();
        logger.debug("Request is sending for address {}", addressStr); // Не логируем apiKey!!!


        try {
            ResponseEntity<Result> responce = restTemplate.getForEntity(uri, Result.class);
            Result body = responce.getBody();

            if (body == null || body.getItems() == null || body.getItems().isEmpty()) {
                return RequestResult.empty();
            } else {
                return RequestResult.success(positionConverter.convert(body.getItems().get(0).getPosition()));
            }
        } catch (HttpStatusCodeException e) {
            logger.error("Request exception", e);
            if (e.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
                return RequestResult.limitExceeded();
            } else {
                return RequestResult.exception(e);
            }
        } catch (RestClientException e) {
            logger.error("Request exception", e);
            return RequestResult.exception(e);
        }
    }
}
