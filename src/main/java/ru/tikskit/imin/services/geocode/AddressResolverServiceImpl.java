package ru.tikskit.imin.services.geocode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.tikskit.imin.services.dto.AddressDto;

import java.util.Optional;

@Service
public class AddressResolverServiceImpl implements AddressResolverService {

    private static final Logger logger = LoggerFactory.getLogger(AddressResolverServiceImpl.class);

    private final Geocoder geocoder;

    public AddressResolverServiceImpl(@Qualifier("geocoderHere") Geocoder geocoder) {
        this.geocoder = geocoder;
    }

    @Override
    public Optional<LatLng> resolve(AddressDto address) {
        RequestResult res = geocoder.request(address);
        logger.debug("Result from geo coder: {}", res);
        // todo тут надо придумать, как обрабатывать результаты
        if (res.getStatus() == ResultStatus.RECEIVED) {
            return Optional.of(res.getLatLng());
        } else if (res.getStatus() == ResultStatus.EMPTY) {
            return Optional.empty();
        } else if (res.getStatus() == ResultStatus.LIMIT_EXCEEDED) {
            return Optional.empty();
        } else if (res.getStatus() == ResultStatus.EXCEPTION) {
            return Optional.empty();
        } else {
            return Optional.empty();
        }
    }
}
