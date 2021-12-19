package ru.tikskit.imin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FetchType;
import javax.persistence.FieldResult;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "events", schema = "events")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SqlResultSetMapping(
        name = "EventFoundByAddressMapping",
        entities = @EntityResult(
                entityClass = Event.class,
                fields = {
                        @FieldResult(name = "id", column = "id"),
                        @FieldResult(name = "description", column = "description"),
                        @FieldResult(name = "dateTime", column = "dateTime"),
                        @FieldResult(name = "placetype", column = "placetype")
                }
        )
)
@SqlResultSetMapping(
        name = "EventShortFoundByAddressMapping",
        classes = @ConstructorResult(
                targetClass = EventAddressDto.class,
                columns = {
                        @ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name = "description", type = String.class),
                        @ColumnResult(name = "dateTime", type = OffsetDateTime.class)
                }
        )
)
@SqlResultSetMapping(
        name = "EventAddressDtoFoundByAddress",
        classes = @ConstructorResult(
                targetClass = EventAddressDto.class,
                columns = {
                        @ColumnResult(name = "event_id", type = Long.class),
                        @ColumnResult(name = "description", type = String.class),
                        @ColumnResult(name = "dateTime", type = OffsetDateTime.class)
                }
        ),
        entities = {
                @EntityResult(
                        entityClass = Tag.class,
                        fields = {
                                @FieldResult(name = "id", column = "tag_id"),
                                @FieldResult(name = "tag", column = "tag"),
                        }
                )
        }
)
@NamedEntityGraph(name = "events-eager-entity-graph",
        attributeNodes = {@NamedAttributeNode("tags"), @NamedAttributeNode("organizer")})
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne(targetEntity = Organizer.class)
    @EqualsAndHashCode.Include
    private Organizer organizer;

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

    @OneToMany(targetEntity = Tag.class, fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinTable(name = "events_tags", schema = "events",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags;
}
