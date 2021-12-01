package ru.tikskit.imin.health;

import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import ru.tikskit.imin.services.dto.AddressDto;
import ru.tikskit.imin.services.geocode.AddressResolverService;
import ru.tikskit.imin.services.geocode.LatLng;

import java.util.Optional;

@Component
@AllArgsConstructor
public class GeocoderHealthIndicator implements HealthIndicator {

    private final AddressResolverService addressResolverService;

    private AddressDto createExistingAddress() {
        return new AddressDto("France", "Île-de-France", "Paris", "Rue Daunou", "5");
    }

    @Override
    public Health health() {
        AddressDto existingAddress = createExistingAddress();
        Optional<LatLng> latLng = addressResolverService.resolve(existingAddress);
        if (latLng.isEmpty()) {
            return Health
                    .down()
                    .withDetail("Can't resolve address", existingAddress)
                    .build();
        } else {
            return Health
                    .up()
                    .withDetail("Resolved address", existingAddress)
                    .build();
        }
    }
}
