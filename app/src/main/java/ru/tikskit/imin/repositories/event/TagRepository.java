package ru.tikskit.imin.repositories.event;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tikskit.imin.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByTag(String tag);
}
