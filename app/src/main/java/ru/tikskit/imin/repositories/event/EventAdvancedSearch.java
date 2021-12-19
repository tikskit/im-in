package ru.tikskit.imin.repositories.event;

import ru.tikskit.imin.model.Address;
import ru.tikskit.imin.model.Event;
import ru.tikskit.imin.model.Tag;

import java.util.Collection;
import java.util.List;

public interface EventAdvancedSearch {
    List<Event> findByAddress(Address address, Collection<Tag> tags);
}
