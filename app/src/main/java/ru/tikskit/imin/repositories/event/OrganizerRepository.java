package ru.tikskit.imin.repositories.event;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tikskit.imin.model.Organizer;

public interface OrganizerRepository extends JpaRepository<Organizer, Long> {
}
