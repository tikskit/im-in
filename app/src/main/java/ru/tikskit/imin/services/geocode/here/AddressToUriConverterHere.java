package ru.tikskit.imin.services.geocode.here;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import ru.tikskit.imin.services.dto.AddressDto;
import ru.tikskit.imin.services.geocode.AddressToUriConverter;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Component
public class AddressToUriConverterHere implements AddressToUriConverter {

    public static final String LIMIT_PARAM_NAME = "limit";
    public static final String QUERY_PARAM_NAME = "q";
    public static final String API_KEY_PARAM_NAME = "apiKey";

    private final String apiKey;

    public AddressToUriConverterHere(@Value("${geocoding.here.apikey}") String apiKey) {
        this.apiKey = apiKey;
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

    @Override
    public URI convert(AddressDto address) {
        final String addressStr = address2Query(address);
        return UriComponentsBuilder
                .fromHttpUrl("https://geocode.search.hereapi.com/")
                .path("v1/geocode")
                .queryParam(QUERY_PARAM_NAME, addressStr)
                .queryParam(API_KEY_PARAM_NAME, apiKey)
                .queryParam(LIMIT_PARAM_NAME, 1)
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUri();
    }
}
