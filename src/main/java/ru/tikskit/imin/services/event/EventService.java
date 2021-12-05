package ru.tikskit.imin.services.event;

import ru.tikskit.imin.model.Event;

public interface EventService {

    void arrange(Event event);

    void update(Event event);

    void delete(long eventId);
}
