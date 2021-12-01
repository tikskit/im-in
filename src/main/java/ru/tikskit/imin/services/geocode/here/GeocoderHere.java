package ru.tikskit.imin.services.geocode;

import com.fasterxml.jackson.core.json.UTF8DataInputJsonParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.tikskit.imin.services.dto.AddressDto;
import ru.tikskit.imin.services.dto.GeoPointDto;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Геокодер, предоставляемый HERE Technologies
 * https://www.here.com/
 */
@Service
public class GeocoderHere implements Geocoder {


    private String address2Query(AddressDto address) {
        List<String> fields = new ArrayList<>();

        if (address.getCountry() != null) {

        }

        return String.join("+", fields);
    }

    private String getRequestStr(AddressDto address, @Value("${geocoding.here.apikey}") String apiKey) {
        StringBuilder sb = new StringBuilder();
        return String.format(
                "https://geocode.search.hereapi.com/v1/geocode?q=%s&apiKey=%s",
                URLEncoder.encode("", UTF_8),
                apiKey
        );
    }

    @Override
    public GeoPointDto resolve(AddressDto address) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForObject("", )
        return null;
    }
}
