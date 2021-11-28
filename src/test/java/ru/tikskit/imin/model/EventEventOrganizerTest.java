package ru.tikskit.imin.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class EventEventOrganizerTest {
    @Autowired
    TestEntityManager em;

    @Test
    @DisplayName("Когда удаляется событие, не должен удаляться организатор")
    public void xxx() {

    }
}
