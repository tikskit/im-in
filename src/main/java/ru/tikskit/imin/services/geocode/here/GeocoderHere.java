package ru.tikskit.imin.services.geocode.here;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.tikskit.imin.services.dto.AddressDto;
import ru.tikskit.imin.services.geocode.AddressToUriConverter;
import ru.tikskit.imin.services.geocode.Geocoder;
import ru.tikskit.imin.services.geocode.RequestResult;
import ru.tikskit.imin.services.geocode.here.dto.Result;


/**
 * Геокодер, предоставляемый HERE Technologies
 * https://www.here.com/
 */
@Service
public class GeocoderHere implements Geocoder {

    private static final Logger logger = LoggerFactory.getLogger(GeocoderHere.class);

    private final RestTemplate restTemplate;
    private final PositionConverter positionConverter;
    private final AddressToUriConverter addressToUriConverter;

    public GeocoderHere(RestTemplate restTemplate, PositionConverter positionConverter,
                        @Qualifier("addressToUriConverterHere") AddressToUriConverter addressToUriConverter) {
        this.restTemplate = restTemplate;
        this.positionConverter = positionConverter;
        this.addressToUriConverter = addressToUriConverter;
    }

    // todo Подключить нормальный кэш
    @Override
    @Cacheable("hereGeodata")
    public RequestResult request(AddressDto address) {

        try {
            ResponseEntity<Result> responce = restTemplate.getForEntity(addressToUriConverter.convert(address),
                    Result.class);
            Result body = responce.getBody();

            if (body == null || body.getItems() == null || body.getItems().isEmpty()) {
                return RequestResult.empty();
            } else {
                return RequestResult.received(positionConverter.convert(body.getItems().get(0).getPosition()));
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
