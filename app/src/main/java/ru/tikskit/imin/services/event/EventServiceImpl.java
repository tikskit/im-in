package ru.tikskit.imin.services.event;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tikskit.imin.model.Event;
import ru.tikskit.imin.model.EventPlaceType;
import ru.tikskit.imin.repositories.event.EventRepository;
import ru.tikskit.imin.services.AddressConverter;
import ru.tikskit.imin.services.dto.AddressDto;
import ru.tikskit.imin.services.geocode.AddressResolverService;
import ru.tikskit.imin.services.geocode.LanLngToPointConverter;
import ru.tikskit.imin.services.geocode.LatLng;

import java.util.Optional;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {

    private final AddressResolverService addressResolverService;
    private final AddressConverter addressConverter;
    private final LanLngToPointConverter lanLngToPointConverter;
    private final EventRepository repository;

    @Override
    public void arrange(Event event) {
        if (event.getEventPlace().getPlaceType() == EventPlaceType.ADDRESS && event.getEventPlace().getGeo() == null) {
            AddressDto addressDto = addressConverter.toDto(event.getEventPlace().getAddress());
            Optional<LatLng> latLng = addressResolverService.resolve(addressDto);
            latLng.ifPresent(lng -> event.getEventPlace().setGeo(lanLngToPointConverter.convert2Point(lng)));
        }
        repository.save(event);
    }

    @Override
    public void update(Event event) {

    }

    @Override
    public void delete(long eventId) {

    }
}
