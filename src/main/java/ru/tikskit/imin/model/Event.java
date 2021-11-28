package ru.tikskit.imin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.OffsetDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne(targetEntity = EventOrganizer.class)
    private EventOrganizer organizer;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "datetime", nullable = false)
    private OffsetDateTime dateTime;

    @Column(name = "status", nullable = false)
    private EventStatus status;

    @Embedded
    private EventPlace eventPlace;
}
