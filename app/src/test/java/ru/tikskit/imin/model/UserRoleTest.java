package ru.tikskit.imin.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.tikskit.imin.model.security.Role;
import ru.tikskit.imin.model.security.User;

import java.util.Set;

@DataJpaTest
class UserRoleTest {
    @Autowired
    TestEntityManager em;

    @Test
    @DisplayName("Должен сохраняться пользователь с набором ролей")
    public void shouldPersistUserWithRoles() {
        User user = new User(0, "tikskit", "123",
                Set.of(new Role(0, "admin"), new Role(0, "participant")));
        em.persist(user);
        em.flush();
    }
}