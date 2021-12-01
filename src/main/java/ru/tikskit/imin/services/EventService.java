package ru.tikskit.imin.services;

import ru.tikskit.imin.model.Event;
import ru.tikskit.imin.model.EventPlace;
import ru.tikskit.imin.model.Tag;

import java.util.List;
import java.util.Set;

public interface EventService {
    List<Event> findEventVariants(Set<Tag> tags, EventPlace place);
}
