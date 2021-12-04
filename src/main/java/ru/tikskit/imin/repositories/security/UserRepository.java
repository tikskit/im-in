package ru.tikskit.imin.repositories.security;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.tikskit.imin.model.security.User;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(value = "user-role")
    User findUserWithRolesByUsername(String username);
}
