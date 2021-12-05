package ru.tikskit.imin.services.security;

import ru.tikskit.imin.model.security.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getUser(String username);
}
