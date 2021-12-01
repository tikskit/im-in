package ru.tikskit.imin.services.geocode;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.tikskit.imin.services.dto.AddressDto;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AddressResolverServiceImpl implements AddressResolverService {

    private static final Logger logger = LoggerFactory.getLogger(AddressResolverServiceImpl.class);

    private final Geocoder geocoder;

    @Override
    public Optional<LatLng> resolve(AddressDto address) {
        RequestResult res = geocoder.request(address);
        logger.debug("Result from geo coder: {}", res);
        if (res.getStatus() == ResultStatus.RECEIVED) {
            return Optional.of(res.getLatLng());
        } else {
            return Optional.empty();
        }
    }
}
