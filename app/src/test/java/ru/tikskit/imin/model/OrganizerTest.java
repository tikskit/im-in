package ru.tikskit.imin.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import ru.tikskit.imin.services.geocode.LanLngToPointConverterImpl;
import ru.tikskit.imin.services.geocode.LatLng;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@DataJpaTest()
@Import(LanLngToPointConverterImpl.class)
@ComponentScan(basePackages = {"ru.tikskit.imin.config"})
public class OrganizerTest {

    @Autowired
    TestEntityManager em;
    @Autowired
    LanLngToPointConverterImpl lanLngToPointConverter;

    @Test
    @DisplayName("Должны сохраняться пространственные точки в H2")
    public void shouldSavePoints() {
        Organizer organizer = em.persist(new Organizer());
        OffsetDateTime dateTime = Calendar.getInstance().toInstant().atOffset(ZoneOffset.of("+07:00"));

        Event event = new Event(0, organizer, "My first event", dateTime, EventStatus.ARRANGED,
                new EventPlace(EventPlaceType.GEO, Address.builder().flat("404").extra("some extra info").build(),
                        null, lanLngToPointConverter.convert2Point(new LatLng(2, 5))), null);

        em.persist(event);
        em.flush();
    }

    @Test
    @DisplayName("Должно сохраняться нормально, если EventPlaceType.ADDRESS и все необходимые поля заданы")
    public void shouldSaveWhenAddressSetForPlaceTypeADDRESS() {
        Organizer organizer = em.persist(new Organizer());
        OffsetDateTime dateTime = Calendar.getInstance().toInstant().atOffset(ZoneOffset.of("+07:00"));

        Event event = new Event(0, organizer, "My first event", dateTime, EventStatus.ARRANGED,
                new EventPlace(
                        Address
                                .builder()
                                .country("Россия")
                                .state("Новосибирская область")
                                .city("Новосибирск")
                                .street("Тюленина")
                                .building("404")
                                .build()),
                null);

        em.persist(event);
        em.flush();
    }

    @Test
    @DisplayName("Если тип места проведения EventPlaceType.ADDRESS и некоторые из обязательных полей не заданы, то вываливается ошибка")
    public void shouldThrowWhenAddressNotSetForPlaceTypeADDRESS() {
        Organizer organizer = em.persist(new Organizer());
        OffsetDateTime dateTime = Calendar.getInstance().toInstant().atOffset(ZoneOffset.of("+07:00"));


        Event event = new Event(0, organizer, "My first event", dateTime, EventStatus.ARRANGED,
                new EventPlace(EventPlaceType.ADDRESS, Address.builder().flat("404").extra("some extra info").build(),
                        "uri", null), null);

        assertThatThrownBy(() -> {
            em.persist(event);
            em.flush();
        })
                .isInstanceOf(Exception.class)
                .getCause()
                .getCause()
                .hasMessageContaining("CK_PLACETYPE");
    }

    @Test
    @DisplayName("Должно сохраняться нормально, если EventPlaceType.URI и все необходимые поля заданы")
    public void shouldSaveWhenUriSetForPlaceTypeURI() {
        Organizer organizer = em.persist(new Organizer());
        OffsetDateTime dateTime = Calendar.getInstance().toInstant().atOffset(ZoneOffset.of("+07:00"));

        Event event = new Event(0, organizer, "My first event", dateTime, EventStatus.ARRANGED,
                new EventPlace("http://youtube.com"), null);

        em.persist(event);
        em.flush();
    }

    @Test
    @DisplayName("Если тип места проведения EventPlaceType.URI и некоторые из обязательных полей не заданы, то вываливается ошибка")
    public void shouldThrowWhenUriNotSetForPlaceTypeURI() {
        Organizer organizer = em.persist(new Organizer());
        OffsetDateTime dateTime = Calendar.getInstance().toInstant().atOffset(ZoneOffset.of("+07:00"));

        Event event = new Event(0, organizer, "My first event", dateTime, EventStatus.ARRANGED,
                new EventPlace(EventPlaceType.URI, Address.builder().flat("404").extra("some extra info").build(),
                        null, lanLngToPointConverter.convert2Point(new LatLng(2, 5))), null);

        assertThatThrownBy(() -> {
            em.persist(event);
            em.flush();
        })
                .isInstanceOf(Exception.class)
                .getCause()
                .getCause()
                .hasMessageContaining("CK_PLACETYPE");
    }

    @Test
    @DisplayName("Должно сохраняться нормально, если EventPlaceType.GEO и все необходимые поля заданы")
    public void shouldSaveWhenGeoSetForPlaceTypeGEO() {
        Organizer organizer = em.persist(new Organizer());
        OffsetDateTime dateTime = Calendar.getInstance().toInstant().atOffset(ZoneOffset.of("+07:00"));

        Event event = new Event(0, organizer, "My first event", dateTime, EventStatus.ARRANGED,
                new EventPlace(lanLngToPointConverter.convert2Point(new LatLng(2, 5))), null);

        em.persist(event);
        em.flush();
    }

    @Test
    @DisplayName("Если тип места проведения EventPlaceType.URI и некоторые из обязательных полей не заданы, то вываливается ошибка")
    public void shouldThrowWhenGeoNotSetForPlaceTypeGEO() {
        Organizer organizer = em.persist(new Organizer());
        OffsetDateTime dateTime = Calendar.getInstance().toInstant().atOffset(ZoneOffset.of("+07:00"));

        Event event = new Event(0, organizer, "My first event", dateTime, EventStatus.ARRANGED,
                new EventPlace(EventPlaceType.GEO, Address.builder().flat("404").extra("some extra info").build(),
                        "https://youtube.com", null), null);

        assertThatThrownBy(() -> {
            em.persist(event);
            em.flush();
        })
                .isInstanceOf(Exception.class)
                .getCause()
                .getCause()
                .hasMessageContaining("CK_PLACETYPE");
    }

    @Test
    @DisplayName("Должна вываливаться ошибка, если не задан статус")
    public void shouldThrowWhenStatusNotSet() {
        Organizer organizer = em.persist(new Organizer());
        OffsetDateTime dateTime = Calendar.getInstance().toInstant().atOffset(ZoneOffset.of("+07:00"));
        Event event = new Event(0, organizer, "My first event", dateTime, null,
                new EventPlace("https://youtube.com"), null);

        assertThatThrownBy(() -> {
            em.persist(event);
            em.flush();
        })
                .isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("Если удаляем из БД событие, то организатор не удаляется")
    public void shouldRemainOrganizerWhenEventGetsDeleted() {
        Organizer organizer = em.persist(new Organizer());
        OffsetDateTime dateTime = Calendar.getInstance().toInstant().atOffset(ZoneOffset.of("+07:00"));
        Event event = em.persist(new Event(0, organizer, "My first event", dateTime, EventStatus.ARRANGED,
                new EventPlace(lanLngToPointConverter.convert2Point(new LatLng(2, 5))), null));
        em.flush();
        long orgId = organizer.getId();
        em.remove(event);
        em.flush();
        em.clear();
        Organizer eventOrganizer = em.find(Organizer.class, orgId);
        assertThat(eventOrganizer).isNotNull();

    }

    @Test
    @DisplayName("Если из БД удаляется организатор, то удаляются и все его события")
    public void shouldDeleteEventWhenOrganizerGetsDeleted() {
        Organizer organizer = em.persist(new Organizer());
        OffsetDateTime dateTime = Calendar.getInstance().toInstant().atOffset(ZoneOffset.of("+07:00"));
        Event event = em.persist(new Event(0, organizer, "My first event", dateTime, EventStatus.ARRANGED,
                new EventPlace(lanLngToPointConverter.convert2Point(new LatLng(2, 5))), null));
        em.flush();
        long eventId = event.getId();
        em.remove(organizer);
        em.flush();
        em.clear();
        Event event1 = em.find(Event.class, eventId);
        assertThat(event1).isNull();
    }

    @Test
    @DisplayName("Если из БД удаляется организатор, то из БД удаляются только его события, а не его остаются")
    public void shouldDeleteEventOfOrganizerWhenItGetsDeleted() {
        Organizer organizer1 = em.persist(new Organizer());
        Organizer organizer2 = em.persist(new Organizer());

        OffsetDateTime dateTime = Calendar.getInstance().toInstant().atOffset(ZoneOffset.of("+07:00"));
        Event event1 = em.persist(new Event(0, organizer1, "My first event", dateTime, EventStatus.ARRANGED,
                new EventPlace(lanLngToPointConverter.convert2Point(new LatLng(2, 5))), null));
        Event event2 = em.persist(new Event(0, organizer2, "My first event", dateTime, EventStatus.ARRANGED,
                new EventPlace(lanLngToPointConverter.convert2Point(new LatLng(2, 5))), null));
        em.flush();
        long eventId1 = event1.getId();
        long eventId2 = event2.getId();
        em.remove(organizer1);
        em.flush();
        em.clear();
        assertThat(em.find(Event.class, eventId1)).isNull();
        assertThat(em.find(Event.class, eventId2)).isNotNull();
    }
}
