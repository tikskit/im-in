package ru.tikskit.imin.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.tikskit.imin.model.security.Role;
import ru.tikskit.imin.model.security.User;
import ru.tikskit.imin.repositories.security.UserRepository;

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.when;

@WebMvcTest
@TestPropertySource
class ActuatorSecurityTest {

    // todo разобраться, почему не работает тестирование актуатора
/*
    @Configuration
    @ComponentScan(basePackages = {"ru.tikskit.imin.security", "ru.tikskit.imin.services.security"})
    @Import({UserRepository.class})
    static class Config {

    }

    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserRepository userRepository;

    @Test
    @WithMockUser(
            username = "maintainer",
            roles = {"MAINTAINER"}
    )
    @DisplayName("Пользователь с ролью MAINTAINER может выполнять запросы GET в /actuator")
    public void shouldGetActuator() throws Exception {
        mvc.perform(
                get("/actuator")
        )
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(
            username = "maintainer",
            roles = {"MAINTAINER"}
    )
    @DisplayName("Пользователь с ролью MAINTAINER может выполнять запросы GET в /actuator/health")
    public void shouldCanGetActuatorHealth() throws Exception {
        mvc.perform(
                get("/actuator/health")
        )
                .andExpect(status().isOk());
    }
    */
}