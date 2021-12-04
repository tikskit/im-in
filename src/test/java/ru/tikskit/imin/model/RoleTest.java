package ru.tikskit.imin.model;

import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.tikskit.imin.model.security.Role;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
public class RoleTest {
    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("При нарушении уникальности ролей должно выбрасываться исключение")
    public void shouldNotAllowDoubleRoles() {
        final String sameRoleName = "organizer";
        Role role1 = new Role(0, sameRoleName);
        Role role2 = new Role(0, sameRoleName);

        assertThatThrownBy(() -> {
            em.persist(role1);
            em.persist(role2);
            em.flush();
        })
                .getCause()
                .getCause()
                .isInstanceOf(JdbcSQLIntegrityConstraintViolationException.class)
                .hasMessageContaining("c_role_unique".toUpperCase());
    }

    @Test
    @DisplayName("Должно выбрасывать exception, если не задано название роли")
    public void shouldThrowWhenRoleNameIsEmpty() {
        assertThatThrownBy(() -> em.persist(new Role(0, null))).isInstanceOf(Exception.class);
    }
}
