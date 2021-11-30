package ru.tikskit.imin.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@DataJpaTest
public class EventOrganizerTest {
    @Autowired
    TestEntityManager em;

    @Test
    @DisplayName("Должно сохраняться нормально, если EventPlaceType.ADDRESS и все необходимые поля заданы")
    public void shouldSaveWhenAddressSetForPlaceTypeADDRESS() {
        EventOrganizer organizer = em.persist(new EventOrganizer());
        OffsetDateTime dateTime = Calendar.getInstance().toInstant().atOffset(ZoneOffset.of("+07:00"));

        Event event = new Event(0, organizer, "My first event", dateTime, EventStatus.ARRANGED,
                new EventPlace(new Address("Russia", "Novosibirskaya oblast", "Novosibirsk",
                        "Tulenina", "404", null, null)), null);

        em.persist(event);
        em.flush();
    }

    @Test
    @DisplayName("Если тип места проведения EventPlaceType.ADDRESS и некоторые из обязательных полей не заданы, то вываливается ошибка")
    public void shouldThrowWhenAddressNotSetForPlaceTypeADDRESS() {
        EventOrganizer organizer = em.persist(new EventOrganizer());
        OffsetDateTime dateTime = Calendar.getInstance().toInstant().atOffset(ZoneOffset.of("+07:00"));

        Event event = new Event(0, organizer, "My first event", dateTime, EventStatus.ARRANGED,
                new EventPlace(EventPlaceType.ADDRESS, new Address(null, null, null,
                        null, null, "flat", "extra"), "uri", new GeoPoint()), null);

        assertThatThrownBy(() -> {
            em.persist(event);
            em.flush();
        }).isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("Должно сохраняться нормально, если EventPlaceType.URI и все необходимые поля заданы")
    public void shouldSaveWhenUriSetForPlaceTypeURI() {
        EventOrganizer organizer = em.persist(new EventOrganizer());
        OffsetDateTime dateTime = Calendar.getInstance().toInstant().atOffset(ZoneOffset.of("+07:00"));

        Event event = new Event(0, organizer, "My first event", dateTime, EventStatus.ARRANGED,
                new EventPlace("http://youtube.com"), null);

        em.persist(event);
        em.flush();
    }

    @Test
    @DisplayName("Если тип места проведения EventPlaceType.URI и некоторые из обязательных полей не заданы, то вываливается ошибка")
    public void shouldThrowWhenUriNotSetForPlaceTypeURI() {
        EventOrganizer organizer = em.persist(new EventOrganizer());
        OffsetDateTime dateTime = Calendar.getInstance().toInstant().atOffset(ZoneOffset.of("+07:00"));

        Event event = new Event(0, organizer, "My first event", dateTime, EventStatus.ARRANGED,
                new EventPlace(EventPlaceType.URI, new Address(null, null, null,
                        null, null, "flat", "extra"), null, new GeoPoint()), null);

        assertThatThrownBy(() -> {
            em.persist(event);
            em.flush();
        }).isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("Должно сохраняться нормально, если EventPlaceType.GEO и все необходимые поля заданы")
    public void shouldSaveWhenGeoSetForPlaceTypeGEO() {
        EventOrganizer organizer = em.persist(new EventOrganizer());
        OffsetDateTime dateTime = Calendar.getInstance().toInstant().atOffset(ZoneOffset.of("+07:00"));

        Event event = new Event(0, organizer, "My first event", dateTime, EventStatus.ARRANGED,
                new EventPlace(new GeoPoint(10f, 20f)), null);

        em.persist(event);
        em.flush();
    }

    @Test
    @DisplayName("Если тип места проведения EventPlaceType.URI и некоторые из обязательных полей не заданы, то вываливается ошибка")
    public void shouldThrowWhenGeoNotSetForPlaceTypeGEO() {
        EventOrganizer organizer = em.persist(new EventOrganizer());
        OffsetDateTime dateTime = Calendar.getInstance().toInstant().atOffset(ZoneOffset.of("+07:00"));

        Event event = new Event(0, organizer, "My first event", dateTime, EventStatus.ARRANGED,
                new EventPlace(EventPlaceType.GEO, new Address(null, null, null,
                        null, null, "flat", "extra"), "https://youtube.com", null), null);

        assertThatThrownBy(() -> {
            em.persist(event);
            em.flush();
        }).isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("Должна вываливаться ошибка, если не задан статус")
    public void shouldThrowWhenStatusNotSet() {
        EventOrganizer organizer = em.persist(new EventOrganizer());
        OffsetDateTime dateTime = Calendar.getInstance().toInstant().atOffset(ZoneOffset.of("+07:00"));
        Event event = new Event(0, organizer, "My first event", dateTime, null,
                new EventPlace("https://youtube.com"), null);

        assertThatThrownBy(() -> {
            em.persist(event);
            em.flush();
        })
                .as("")
                .isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("Если удаляем из БД событие, то организатор не удаляется")
    public void shouldRemainOrganizerWhenEventGetsDeleted() {
        EventOrganizer organizer = em.persist(new EventOrganizer());
        OffsetDateTime dateTime = Calendar.getInstance().toInstant().atOffset(ZoneOffset.of("+07:00"));
        Event event = em.persist(new Event(0, organizer, "My first event", dateTime, EventStatus.ARRANGED,
                new EventPlace(new GeoPoint(10f, 20f)), null));
        em.flush();
        long orgId = organizer.getId();
        em.remove(event);
        em.flush();
        em.clear();
        EventOrganizer eventOrganizer = em.find(EventOrganizer.class, orgId);
        assertThat(eventOrganizer).isNotNull();

    }

    @Test
    @DisplayName("Если из БД удаляется организатор, то удаляются и все его события")
    public void shouldDeleteEventWhenOrganizerGetsDeleted() {
        EventOrganizer organizer = em.persist(new EventOrganizer());
        OffsetDateTime dateTime = Calendar.getInstance().toInstant().atOffset(ZoneOffset.of("+07:00"));
        Event event = em.persist(new Event(0, organizer, "My first event", dateTime, EventStatus.ARRANGED,
                new EventPlace(new GeoPoint(10f, 20f)), null));
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
        EventOrganizer organizer1 = em.persist(new EventOrganizer());
        EventOrganizer organizer2 = em.persist(new EventOrganizer());

        OffsetDateTime dateTime = Calendar.getInstance().toInstant().atOffset(ZoneOffset.of("+07:00"));
        Event event1 = em.persist(new Event(0, organizer1, "My first event", dateTime, EventStatus.ARRANGED,
                new EventPlace(new GeoPoint(10f, 20f)), null));
        Event event2 = em.persist(new Event(0, organizer2, "My first event", dateTime, EventStatus.ARRANGED,
                new EventPlace(new GeoPoint(10f, 20f)), null));
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
