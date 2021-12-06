package ru.tikskit.imin.repositories.event;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tikskit.imin.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

}
