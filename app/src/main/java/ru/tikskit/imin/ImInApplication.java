package ru.tikskit.imin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import ru.tikskit.imin.model.Address;
import ru.tikskit.imin.model.Event;
import ru.tikskit.imin.model.EventPlace;
import ru.tikskit.imin.model.EventStatus;
import ru.tikskit.imin.model.Organizer;
import ru.tikskit.imin.model.Tag;
import ru.tikskit.imin.repositories.event.EventRepository;
import ru.tikskit.imin.repositories.event.OrganizerRepository;
import ru.tikskit.imin.repositories.event.TagRepository;
import ru.tikskit.imin.services.AddressConverter;
import ru.tikskit.imin.services.event.EventService;
import ru.tikskit.imin.services.geocode.AddressResolverService;
import ru.tikskit.imin.services.geocode.LanLngToPointConverter;
import ru.tikskit.imin.services.geocode.LatLng;

import javax.persistence.EntityManager;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
@EnableCaching
public class ImInApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ImInApplication.class, args);
        PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);

        System.out.println("Supeuser's pass: " + passwordEncoder.encode("QWEasd"));
        new Exec(context).exec();
    }

    static class Exec {
        private final ConfigurableApplicationContext context;

        Exec(ConfigurableApplicationContext context) {
            this.context = context;
        }

        @Transactional
        public void exec() {
            TagRepository tagRepository = context.getBean(TagRepository.class);
            OrganizerRepository organizerRepository = context.getBean(OrganizerRepository.class);
            EventRepository eventRepository = context.getBean(EventRepository.class);
            LanLngToPointConverter lanLngToPointConverter = context.getBean(LanLngToPointConverter.class);
            EventService eventService = context.getBean(EventService.class);
            AddressResolverService addressResolver = context.getBean(AddressResolverService.class);
            AddressConverter addressConverter = context.getBean(AddressConverter.class);
            EntityManager em = context.getBean(EntityManager.class);

            Address odesskaya21 = Address.builder()
                    .country("Россия").state("Центральный федеральный округ").county("Москва").city("Москва")
                    .street("Одесская улица").building("21").build();
            Address bolotnikovskaya30 = Address
                    .builder().country("Россия").state("Центральный федеральный округ").county("Москва").city("Москва")
                    .street("Болотниковская улица").building("30").build();
            Address azovskaya6k3 = Address
                    .builder().country("Россия").state("Центральный федеральный округ").county("Москва").city("Москва")
                    .street("Азовская").building("6к3").build();
/*
            Tag partyTag = tagRepository.save(new Tag(0, "Party"));
            Tag shoppingTag = tagRepository.save(new Tag(0, "Shopping"));
            List<Tag> bolTags = List.of(partyTag, shoppingTag);

            Organizer org = organizerRepository.save(new Organizer());

            Event bolParty = new Event(0, org, "Party in bolotnik-ya", OffsetDateTime.now(),
                    EventStatus.ARRANGED, new EventPlace(bolotnikovskaya30), bolTags);
            eventService.arrange(bolParty);

            Event job = new Event(0, org, "job place", OffsetDateTime.now(), EventStatus.ARRANGED,
                    new EventPlace(azovskaya6k3), List.of(partyTag));
            eventService.arrange(job);
*/
            Optional<LatLng> bolLatLng = addressResolver.resolve(addressConverter.toDto(bolotnikovskaya30));

            Tag partyTag = tagRepository.getById(22L);
            Tag shoppingTag = tagRepository.getById(23L);

            List<Event> byDistance = eventRepository.findByDistance(
                    lanLngToPointConverter.convert2Point(bolLatLng.get()),
                    600d, List.of(partyTag));

/*
        List<Event> byAddress = eventRepository.findByAddress(
                Address.builder().country("Россия").state("Московская область").city("Москва").build(), null);
*/

            byDistance.forEach(e -> System.out.println(e.getDescription()));


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

}
