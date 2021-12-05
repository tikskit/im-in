package ru.tikskit.imin.services.event;

import ru.tikskit.imin.model.Event;
import ru.tikskit.imin.model.EventPlace;
import ru.tikskit.imin.model.Tag;

import java.util.Set;

public interface SearchService {
    Set<Event> search(Set<Tag> tags, EventPlace place);

}
