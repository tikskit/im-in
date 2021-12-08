package ru.tikskit.imin.repositories.event;

import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.tikskit.imin.model.Event;
import ru.tikskit.imin.model.Tag;

import java.util.Collection;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query(
            value = "select e.id, e.organizer_id, e.description, e.datetime, e.status, e.country, e.state, e.county, e.city" +
                    ", e.street, e.building, e.flat, e.extra, e.geo, uri, e.placetype " +
                    ", (select count(*) from events.events_tags as et where et.event_id=e.id and et.tag_id in :tags) as mcount " +
                    "from events.events as e " +
                    "where " +
                    "e.placetype in (0, 1) " +
                    "and st_dwithin(e.geo, :myLocation, :distance) = true " +
                    "and (select count(*) from events.events_tags as et where et.event_id=e.id and et.tag_id in :tags) > 0 " +
                    "order by st_distance(e.geo, :myLocation) asc, mcount desc",
            countQuery = "select count(*) " +
                    "from from events.events as e " +
                    "e.placetype in (0, 1) " +
                    "and st_dwithin(e.geo, :myLocation, :distance) = true " +
                    "and (select count(*) from events.events_tags as et where et.event_id=e.id and et.tag_id in :tags) > 0 ",
            nativeQuery = true
    )
    List<Event> findByDistance(Point myLocation, double distance, Collection<Tag> tags);
}
