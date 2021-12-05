package ru.tikskit.imin.services.security;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tikskit.imin.model.security.User;
import ru.tikskit.imin.repositories.security.UserRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceJpa implements UserService {

    private final UserRepository userRepository;

    @Override
    public Optional<User> getUser(String username) {
        return Optional.of(userRepository.findUserWithRolesByUsername(username));
    }
}
