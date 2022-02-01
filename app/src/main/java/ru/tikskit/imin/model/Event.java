package ru.tikskit.imin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.OffsetDateTime;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "events", schema = "events")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NamedEntityGraph(name = "events-eager-entity-graph",
        attributeNodes = {@NamedAttributeNode("tags"), @NamedAttributeNode("organizer")})
@ToString(onlyExplicitlyIncluded = true)
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ToString.Include
    private long id;

    @ManyToOne(targetEntity = Organizer.class)
    private Organizer organizer;

    @Column(name = "description", nullable = false)
    @EqualsAndHashCode.Include
    private String description;

    @Column(name = "datetime", nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private OffsetDateTime dateTime;

    @Column(name = "status", nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private EventStatus status;

    @Embedded
    @EqualsAndHashCode.Include
    @ToString.Include
    private EventPlace eventPlace;

    @OneToMany(targetEntity = Tag.class, fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinTable(name = "events_tags", schema = "events",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags;
}
