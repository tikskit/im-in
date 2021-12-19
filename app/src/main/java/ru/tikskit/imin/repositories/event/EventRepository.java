package ru.tikskit.imin.repositories.event;

import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.tikskit.imin.model.Event;
import ru.tikskit.imin.model.Tag;

import java.util.Collection;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long>, EventAdvancedSearch {
    @Query("select e from Event e " +
            "where e.status=:#{T(ru.tikskit.imin.model.EventStatus).ARRANGED} " +
            "and e.eventPlace.placeType in (:#{T(ru.tikskit.imin.model.EventPlaceType).ADDRESS}, :#{T(ru.tikskit.imin.model.EventPlaceType).GEO}) " +
            "and e.eventPlace.geo is not null " +
            "and dwithin(e.eventPlace.geo, :myLocation, :distance) = true " +
            "and (select count (*) from Tag t where t member of e.tags and t in :tags) > 0"
    )
    List<Event> findByDistance(Point myLocation, double distance, Collection<Tag> tags);
}
