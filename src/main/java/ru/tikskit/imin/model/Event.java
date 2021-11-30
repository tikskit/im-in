package ru.tikskit.imin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne(targetEntity = EventOrganizer.class)
    @EqualsAndHashCode.Include
    private EventOrganizer organizer;

    @Column(name = "description", nullable = false)
    @EqualsAndHashCode.Include
    private String description;

    @Column(name = "datetime", nullable = false)
    @EqualsAndHashCode.Include
    private OffsetDateTime dateTime;

    @Column(name = "status", nullable = false)
    @EqualsAndHashCode.Include
    private EventStatus status;

    @Embedded
    @EqualsAndHashCode.Include
    private EventPlace eventPlace;
}
