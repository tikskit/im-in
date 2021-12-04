package ru.tikskit.imin.health;

import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import ru.tikskit.imin.services.dto.AddressDto;
import ru.tikskit.imin.services.geocode.Geocoder;
import ru.tikskit.imin.services.geocode.RequestResult;
import ru.tikskit.imin.services.geocode.ResultStatus;

@Component
@AllArgsConstructor
public class GeocoderHealthIndicator implements HealthIndicator {

    private final Geocoder geocoder;

    private AddressDto createExistingAddress() {
        return new AddressDto("France", "Île-de-France", "Paris", "Rue Daunou", "5");
    }

    @Override
    public Health health() {
        AddressDto existingAddress = createExistingAddress();
        RequestResult res = geocoder.request(existingAddress);

        if (res.getStatus() == ResultStatus.RECEIVED) {
            return Health
                    .up()
                    .withDetail("Address", existingAddress)
                    .build();
        } else if (res.getStatus() == ResultStatus.EMPTY) {
            return Health
                    .down()
                    .withDetail("message", "Response with no geo data received")
                    .build();
        } else if (res.getStatus() == ResultStatus.LIMIT_EXCEEDED) {
            return Health
                    .down()
                    .withDetail("message", "limit exceeded")
                    .build();
        } else if (res.getStatus() == ResultStatus.EXCEPTION) {
            return Health
                    .down()
                    .withDetail("exception", res.getException().getMessage())
                    .build();
        } else {
            return Health
                    .down()
                    .withDetail("Unknown status", res.getStatus())
                    .build();
        }
    }
}
