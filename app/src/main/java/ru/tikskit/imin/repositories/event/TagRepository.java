package ru.tikskit.imin.repositories.event;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tikskit.imin.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
    // @todo проверить, что работает с ignoring case
    Tag findByTagTagIgnoringCase(String tag);
}
