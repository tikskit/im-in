package ru.tikskit.imin.repositories.event;

import ru.tikskit.imin.model.Address;
import ru.tikskit.imin.model.Event;
import ru.tikskit.imin.model.EventPlaceType;
import ru.tikskit.imin.model.EventStatus;
import ru.tikskit.imin.model.Tag;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;

public class EventAdvancedSearchImpl implements EventAdvancedSearch {

    @PersistenceContext
    EntityManager em;

    @Override
    @SuppressWarnings("unchecked")
    public List<Event> findByAddress(Address address, Collection<Tag> tags) {
        if (address == null) {
            throw new IllegalArgumentException("Address must not be null");
        }

        if (tags == null) {
            throw new IllegalArgumentException("Tags must not be null");
        }

        String sql = "select e " +
                "from Event e " +
                "left join fetch e.tags " +
                "where e.eventPlace.placeType = :placetype " +
                "and e.status = :status " +
                "and (select count (*) from Tag t where t member of e.tags and t in :tags) > 0 ";

        if (address.getCountry() != null) {
            sql = sql + "and e.eventPlace.address.country like :country ";
        }
        if (address.getState() != null) {
            sql = sql + "and e.eventPlace.address.state like :state ";
        }
        if (address.getCity() != null) {
            sql = sql + "and e.eventPlace.address.city like :city ";
        }
        if (address.getStreet() != null) {
            sql = sql + "and e.eventPlace.address.street like :street ";
        }

        EntityGraph<?> entityGraph = em.getEntityGraph("events-eager-entity-graph");
        Query query = em.createQuery(sql, Event.class);
        query.setParameter("placetype", EventPlaceType.ADDRESS);
        query.setParameter("status", EventStatus.ARRANGED);
        query.setParameter("tags", tags);
        query.setHint("javax.persistence.fetchgraph", entityGraph);

        if (address.getCountry() != null) {
            query.setParameter("country", address.getCountry());
        }
        if (address.getState() != null) {
            query.setParameter("state", address.getState());
        }
        if (address.getCity() != null) {
            query.setParameter("city", address.getCity());
        }
        if (address.getStreet() != null) {
            query.setParameter("street", address.getStreet());
        }

        return (List<Event>)query.getResultList();
    }
}
