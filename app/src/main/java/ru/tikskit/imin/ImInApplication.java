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
import ru.tikskit.imin.model.Tag;
import ru.tikskit.imin.repositories.event.EventRepository;
import ru.tikskit.imin.repositories.event.OrganizerRepository;
import ru.tikskit.imin.repositories.event.TagRepository;
import ru.tikskit.imin.services.event.EventService;
import ru.tikskit.imin.services.geocode.LanLngToPointConverter;
import ru.tikskit.imin.services.geocode.LatLng;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

@SpringBootApplication
@EnableCaching
public class ImInApplication {


    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ImInApplication.class, args);
        PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);

        System.out.println("Supeuser's pass: " + passwordEncoder.encode("QWEasd"));

        TagRepository tagRepository = context.getBean(TagRepository.class);
        OrganizerRepository organizerRepository = context.getBean(OrganizerRepository.class);
        EventRepository eventRepository = context.getBean(EventRepository.class);
        LanLngToPointConverter lanLngToPointConverter = context.getBean(LanLngToPointConverter.class);
        EventService eventService = context.getBean(EventService.class);

//        Set<Tag> tags = Set.of(new Tag(0, "someTag1"), new Tag(0, "someTag2"));
        List<Tag> tags = tagRepository.findAll();
/*
        Organizer org = organizerRepository.save(new Organizer());


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
                tags);

        eventService.arrange(event);
*/
        List<Event> byDistance = eventRepository.findByDistance(
                lanLngToPointConverter.convert2Point(new LatLng(38.12, 55.123)),
                100d, tags);

        byDistance.forEach(System.out::println);


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
