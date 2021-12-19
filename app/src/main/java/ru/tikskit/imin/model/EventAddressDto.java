package ru.tikskit.imin.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.OffsetDateTime;
import java.util.Set;

@RequiredArgsConstructor
@Data
public class EventAddressDto {
    private final long id;
    private final String description;
    private final OffsetDateTime dateTime;

    private Organizer organizer;
    private Address address;
    private Set<Tag> tags;


}
