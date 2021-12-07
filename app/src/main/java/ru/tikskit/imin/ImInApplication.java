package ru.tikskit.imin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.tikskit.imin.model.Address;
import ru.tikskit.imin.model.Event;
import ru.tikskit.imin.model.EventPlace;
import ru.tikskit.imin.model.EventStatus;
import ru.tikskit.imin.model.Organizer;
import ru.tikskit.imin.repositories.event.EventRepository;
import ru.tikskit.imin.repositories.event.OrganizerRepository;
import ru.tikskit.imin.services.event.EventService;
import ru.tikskit.imin.services.geocode.LanLngToPointConverter;
import ru.tikskit.imin.services.geocode.LatLng;

import java.time.OffsetDateTime;
import java.util.Set;

@SpringBootApplication
@EnableCaching
public class ImInApplication {


    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ImInApplication.class, args);
        PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);

        System.out.println("Supeuser's pass: " + passwordEncoder.encode("QWEasd"));

        EventRepository eventRepository = context.getBean(EventRepository.class);
        LanLngToPointConverter lanLngToPointConverter = context.getBean(LanLngToPointConverter.class);
        Set<Event> byDistance = eventRepository.findByDistance(lanLngToPointConverter.convert2Point(new LatLng(38.12, 55.123)), 100d);
        byDistance.forEach(System.out::println);


/*        OrganizerRepository organizerRepository = context.getBean(OrganizerRepository.class);
        Organizer org = organizerRepository.save(new Organizer());


        EventService eventService = context.getBean(EventService.class);
        Event event = new Event(0,
                org,
                "test event",
                OffsetDateTime.now(),
                EventStatus.ARRANGED,
                new EventPlace(
                        Address.builder()
                                .country("Россия")
                                .state("Московская область")
                                .city("Москва")
                                .street("Одесская")
                                .building("23к1")
                                .build()),
                null);
        eventService.arrange(event);*/

/*
        AddressResolverService resolver = context.getBean(AddressResolverService.class);
        AddressDto addressDto = new AddressDto("Россия", "Новосибирская область", "Новосибирск",
                "Лазурная", "10");
        Optional<LatLng> latLng = resolver.resolve(addressDto);
        System.out.println(latLng.orElse(null));
        latLng = resolver.resolve(addressDto);
        System.out.println(latLng.orElse(null));

        latLng = resolver.resolve(new AddressDto("xxx", "sdsd", "dfdf",
                "dfdf", "rrt"));
        System.out.println(latLng.orElse(null));
*/
    }

}
