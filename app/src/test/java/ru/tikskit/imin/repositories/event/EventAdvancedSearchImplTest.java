package ru.tikskit.imin.repositories.event;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.tikskit.imin.model.Address;
import ru.tikskit.imin.model.Event;
import ru.tikskit.imin.model.EventPlace;
import ru.tikskit.imin.model.EventStatus;
import ru.tikskit.imin.model.Organizer;
import ru.tikskit.imin.model.Tag;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Кастомный репозиторий для события должен")
class EventAdvancedSearchImplTest {
    @Autowired
    TestEntityManager em;
    @Autowired
    EventRepository eventRepository;

    @DisplayName("возвращать правильное количество событий при точном указании адреса и совпадении тэгов")
    @Test
    public void shouldFindRightAmountOfEnriesByExactAddressAndMatchingTags() {
        Address odesskaya21 = Address.builder()
                .country("Россия").state("Центральный федеральный округ").county("Москва").city("Москва")
                .street("Одесская улица").building("21").build();
        Address bolotnikovskaya30 = Address
                .builder().country("Россия").state("Центральный федеральный округ").county("Москва").city("Москва")
                .street("Болотниковская улица").building("30").build();
        Address azovskaya6k3 = Address
                .builder().country("Россия").state("Центральный федеральный округ").county("Москва").city("Москва")
                .street("Азовская").building("6к3").build();

        Tag partyTag = em.persist(new Tag(0, "Party"));
        Tag shoppingTag = em.persist(new Tag(0, "Shopping"));

        Organizer org = em.persist(new Organizer(0));

        Event bolParty = new Event(0, org, "Party in bolotnik-ya", OffsetDateTime.now(),
                EventStatus.ARRANGED, new EventPlace(bolotnikovskaya30), List.of(partyTag, shoppingTag));
        bolParty = em.persist(bolParty);

        Event job = new Event(0, org, "job place", OffsetDateTime.now(), EventStatus.ARRANGED,
                new EventPlace(azovskaya6k3), List.of(partyTag, shoppingTag));
        job = em.persist(job);

        List<Event> byAddress = eventRepository.findByAddress(
                Address.builder()
                        .city("Москва")
                        .country("Россия")
                        .state("Центральный федеральный округ")
                        .street("Азовская")
                        .building("6к3")
                        .build(),
                Set.of(partyTag, shoppingTag)
        );

        assertThat(byAddress)
                .isNotNull()
                .hasSize(1);
    }

    @DisplayName("возвращать правильное количество событий при частичном указании адреса и совпадении тэгов")
    @Test
    public void shouldFindRightAmountOfEnriesByPartialAddressAndMatchingTags() {
        Address odesskaya21 = Address.builder()
                .country("Россия").state("Центральный федеральный округ").county("Москва").city("Москва")
                .street("Одесская улица").building("21").build();
        Address azovskaya30 = Address
                .builder().country("Россия").state("Центральный федеральный округ").county("Москва").city("Москва")
                .street("Азовская").building("30").build();
        Address azovskaya6k3 = Address
                .builder().country("Россия").state("Центральный федеральный округ").county("Москва").city("Москва")
                .street("Азовская").building("6к3").build();

        Tag partyTag = em.persist(new Tag(0, "Party"));
        Tag shoppingTag = em.persist(new Tag(0, "Shopping"));
        Tag restingTag = em.persist(new Tag(0, "resting"));

        Organizer org = em.persist(new Organizer(0));

        Event home = new Event(0, org, "Watching TV", OffsetDateTime.now(),
                EventStatus.ARRANGED, new EventPlace(odesskaya21), List.of(partyTag, shoppingTag));
        home = em.persist(home);

        Event bolParty = new Event(0, org, "Party in azovskaya 30", OffsetDateTime.now(),
                EventStatus.ARRANGED, new EventPlace(azovskaya30), List.of(partyTag, shoppingTag));
        bolParty = em.persist(bolParty);

        Event job = new Event(0, org, "job place", OffsetDateTime.now(), EventStatus.ARRANGED,
                new EventPlace(azovskaya6k3), List.of(partyTag, shoppingTag));
        job = em.persist(job);

        List<Event> byAddress = eventRepository.findByAddress(
                Address.builder()
                        .city("Москва")
                        .country("Россия")
                        .state("Центральный федеральный округ")
                        .street("Азовская")
                        .build(),
                Set.of(partyTag, shoppingTag)
        );

        assertThat(byAddress)
                .isNotNull()
                .hasSize(2);
    }

    @DisplayName("не возвращать результатов, если отсутствует совпадение адресов")
    @Test
    public void shouldNotFindResultsWhenAddressIsWrong() {
        Address odesskaya21 = Address.builder()
                .country("Россия").state("Центральный федеральный округ").county("Москва").city("Новосибирск")
                .street("Одесская улица").building("21").build();
        Address bolotnikovskaya30 = Address
                .builder().country("Россия").state("Центральный федеральный округ").county("Москва").city("Новосибирск")
                .street("Болотниковская улица").building("30").build();
        Address azovskaya6k3 = Address
                .builder().country("Россия").state("Центральный федеральный округ").county("Москва").city("Новосибирск")
                .street("Азовская").building("6к3").build();

        Tag partyTag = em.persist(new Tag(0, "Party"));
        Tag shoppingTag = em.persist(new Tag(0, "Shopping"));

        Organizer org = em.persist(new Organizer(0));

        Event bolParty = new Event(0, org, "Party in bolotnik-ya", OffsetDateTime.now(),
                EventStatus.ARRANGED, new EventPlace(bolotnikovskaya30), List.of(partyTag, shoppingTag));
        bolParty = em.persist(bolParty);

        Event job = new Event(0, org, "job place", OffsetDateTime.now(), EventStatus.ARRANGED,
                new EventPlace(azovskaya6k3), List.of(partyTag, shoppingTag));
        job = em.persist(job);

        List<Event> byAddress = eventRepository.findByAddress(
                Address.builder()
                        .city("Москва")
                        .country("Россия")
                        .state("Центральный федеральный округ")
                        .street("Азовская")
                        .building("6к3")
                        .build(),
                Set.of(partyTag, shoppingTag)
        );

        assertThat(byAddress)
                .isNotNull()
                .hasSize(0);
    }

    @DisplayName("возвращать события у которых есть хотя бы некоторые из заданых тэгов")
    @Test
    public void shouldFindEventsWithAnyOfPassedTags() {
        Address addr = Address.builder()
                .country("Россия").state("Центральный федеральный округ").county("Москва").city("Москва")
                .street("Одесская улица").building("21").build();

        Tag tag1 = em.persist(new Tag(0, "Party"));
        Tag tag2 = em.persist(new Tag(0, "Shopping"));
        Tag tag3 = em.persist(new Tag(0, "home"));

        Organizer org = em.persist(new Organizer(0));

        em.persist(new Event(0, org, "3 tags event", OffsetDateTime.now(),
                EventStatus.ARRANGED, new EventPlace(addr), List.of(tag1, tag2, tag3)));
        em.persist(new Event(0, org, "2 tags event", OffsetDateTime.now(),
                EventStatus.ARRANGED, new EventPlace(addr), List.of(tag1, tag2)));
        em.persist(new Event(0, org, "1 tags event", OffsetDateTime.now(),
                EventStatus.ARRANGED, new EventPlace(addr), List.of(tag1)));


        List<Event> byAddress = eventRepository.findByAddress(
                addr,
                Set.of(tag1, tag2, tag3)
        );

        assertThat(byAddress)
                .isNotNull()
                .hasSize(3);
    }

}