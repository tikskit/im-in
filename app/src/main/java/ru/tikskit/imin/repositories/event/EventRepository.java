package ru.tikskit.imin.repositories.event;

import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.tikskit.imin.model.Event;

import java.util.Set;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("select e from Event e where e.eventPlace.placeType = ?#{T(ru.tikskit.imin.model.EventPlaceType).GEO} and dwithin(e.eventPlace.geo, :myLocation, :distance) = true")
    Set<Event> findByDistance(Point myLocation, double distance);
}
