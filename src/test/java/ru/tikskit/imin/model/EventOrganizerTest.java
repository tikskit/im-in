package ru.tikskit.imin.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThatThrownBy;


@DataJpaTest
public class EventOrganizerTest {
    @Autowired
    TestEntityManager em;

    @Test
    @DisplayName("Должно сохраняться нормальное, если EventPlaceType.ADDRESS и все необходимые поля заданы")
    public void shouldSaveWhenAddressSetForPlaceTypeADDRESS() {
        EventOrganizer persist = em.persist(new EventOrganizer());
        OffsetDateTime dateTime = Calendar.getInstance().toInstant().atOffset(ZoneOffset.of("+07:00"));

        Event event = new Event(0, persist, "My first event", dateTime, EventStatus.ARRANGED,
                new EventPlace(EventPlaceType.ADDRESS, new Address("Russia", "Novosibirskaya oblast", "Novosibirsk",
                        "Tulenina", "404", null, null), null, null));

        em.persist(event);
        em.flush();
    }

    @Test
    @DisplayName("Если тип места проведения EventPlaceType.ADDRESS и некоторые из обязательных полей не заданы, то вываливается ошибка")
    public void shouldThrowWhenAddressNotSetForPlaceTypeADDRESS() {
        EventOrganizer persist = em.persist(new EventOrganizer());
        OffsetDateTime dateTime = Calendar.getInstance().toInstant().atOffset(ZoneOffset.of("+07:00"));

        Event event = new Event(0, persist, "My first event", dateTime, EventStatus.ARRANGED,
                new EventPlace(EventPlaceType.ADDRESS, new Address(null, null, null,
                        null, null, "flat", "extra"), "uri", new GeoPoint()));

        assertThatThrownBy(() -> {
            em.persist(event);
            em.flush();
        })
                .as("")
                .isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("Должно сохраняться нормальное, если EventPlaceType.URI и все необходимые поля заданы")
    public void shouldSaveWhenUriSetForPlaceTypeURI() {
        EventOrganizer persist = em.persist(new EventOrganizer());
        OffsetDateTime dateTime = Calendar.getInstance().toInstant().atOffset(ZoneOffset.of("+07:00"));

        Event event = new Event(0, persist, "My first event", dateTime, EventStatus.ARRANGED,
                new EventPlace(EventPlaceType.URI, null, "http://youtube.com", null));

        em.persist(event);
        em.flush();
    }

    @Test
    @DisplayName("Если тип места проведения EventPlaceType.URI и некоторые из обязательных полей не заданы, то вываливается ошибка")
    public void shouldThrowWhenUriNotSetForPlaceTypeURI() {
        EventOrganizer persist = em.persist(new EventOrganizer());
        OffsetDateTime dateTime = Calendar.getInstance().toInstant().atOffset(ZoneOffset.of("+07:00"));

        Event event = new Event(0, persist, "My first event", dateTime, EventStatus.ARRANGED,
                new EventPlace(EventPlaceType.URI, new Address(null, null, null,
                        null, null, "flat", "extra"), null, new GeoPoint()));

        assertThatThrownBy(() -> {
            em.persist(event);
            em.flush();
        })
                .as("")
                .isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("Должно сохраняться нормальное, если EventPlaceType.GEO и все необходимые поля заданы")
    public void shouldSaveWhenGeoSetForPlaceTypeGEO() {
        EventOrganizer persist = em.persist(new EventOrganizer());
        OffsetDateTime dateTime = Calendar.getInstance().toInstant().atOffset(ZoneOffset.of("+07:00"));

        Event event = new Event(0, persist, "My first event", dateTime, EventStatus.ARRANGED,
                new EventPlace(new GeoPoint(10f, 20f)));

        em.persist(event);
        em.flush();
    }

    @Test
    @DisplayName("Если тип места проведения EventPlaceType.URI и некоторые из обязательных полей не заданы, то вываливается ошибка")
    public void shouldThrowWhenGeoNotSetForPlaceTypeGEO() {
        EventOrganizer persist = em.persist(new EventOrganizer());
        OffsetDateTime dateTime = Calendar.getInstance().toInstant().atOffset(ZoneOffset.of("+07:00"));

        Event event = new Event(0, persist, "My first event", dateTime, EventStatus.ARRANGED,
                new EventPlace(EventPlaceType.GEO, new Address(null, null, null,
                        null, null, "flat", "extra"), "https://youtube.com", null));

        assertThatThrownBy(() -> {
            em.persist(event);
            em.flush();
        })
                .as("")
                .isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("Должна вываливаться ошибка, если не задан статус")
    public void shouldThrowWhenStatusNotSet() {
        EventOrganizer persist = em.persist(new EventOrganizer());
        OffsetDateTime dateTime = Calendar.getInstance().toInstant().atOffset(ZoneOffset.of("+07:00"));
        Event event = new Event(0, persist, "My first event", dateTime, null,
                new EventPlace("https://youtube.com"));


        assertThatThrownBy(() -> {
            em.persist(event);
            em.flush();
        })
                .as("")
                .isInstanceOf(Exception.class);
    }
}
