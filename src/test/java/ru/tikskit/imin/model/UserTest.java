package ru.tikskit.imin.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.tikskit.imin.model.security.User;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
public class UserTest {

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Должно выбрасываться исключение, если нарушается уникальность логинов")
    public void shouldThrowWhenUsernamesNotUnique() {
        final String sameUsername = "theuser";
        User user1 = new User(0, sameUsername, "123", null);
        User user2 = new User(0, sameUsername, "232", null);
        assertThatThrownBy(() -> {
            em.persist(user1);
            em.persist(user2);
            em.flush();
        })
                .getCause()
                .getCause()
                .hasMessageContaining("ix_username".toUpperCase());
    }

    @Test
    @DisplayName("Должно выбрасывать исключение, если не задан username")
    public void shouldThrowWhenEmptyUsername() {
        assertThatThrownBy(() -> {
            em.persist(new User(0, null, "pass", null));
            em.flush();
        }).isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("Должно выбрасывать исключение, если не задан password")
    public void shouldThrowWhenEmptyPassword() {
        assertThatThrownBy(() -> {
            em.persist(new User(0, "user", null, null));
            em.flush();
        }).isInstanceOf(Exception.class);
    }
}
