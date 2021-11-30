package ru.tikskit.imin.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
public class TagTest {
    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Должен создавать 3 тэга для события")
    public void shouldAddTag4Event() {
        EventOrganizer organizer = em.persist(new EventOrganizer());
        OffsetDateTime dateTime = Calendar.getInstance().toInstant().atOffset(ZoneOffset.of("+07:00"));

        Event event = new Event(0, organizer, "My first event", dateTime, EventStatus.ARRANGED,
                new EventPlace("http://youtube.com"), null);

        event.setTags(Set.of(new Tag(0, "Занятие йогой"), new Tag(0, "Собрание курильщиков трубки"), new Tag(0, "BDSM-вечеринка")));
        long eventId = em.persist(event).getId();
        em.flush();
        em.clear();

        Event event1 = em.find(Event.class, eventId);
        assertThat(event1.getTags()).isNotNull().size().isEqualTo(3);
    }
}
