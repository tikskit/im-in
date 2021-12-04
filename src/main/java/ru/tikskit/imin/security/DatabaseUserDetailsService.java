package ru.tikskit.imin.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.tikskit.imin.model.security.User;
import ru.tikskit.imin.services.security.UserService;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class DatabaseUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userService.getUser(username).orElseThrow(
                () -> new UsernameNotFoundException(String.format("User with name %s not found", username)));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                Stream.ofNullable(user.getRoles())
                        .flatMap(Collection::stream)
                        .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getRole()))
                        .collect(Collectors.toList()));
    }
}