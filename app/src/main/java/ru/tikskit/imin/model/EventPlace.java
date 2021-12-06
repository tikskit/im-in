package ru.tikskit.imin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

/**
 * Местом события могут быть:
 * 1. Адрес
 * 2. Гео-координаты
 * 3. Ссылка (на вебинар)
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EventPlace {
    @Column(name = "placetype", nullable = false)
    private EventPlaceType placeType;

    @Embedded
    private Address address;

    @Column(name = "uri")
    private String uri;

//    @Column(name = "geo", columnDefinition = "GEOGRAPHY(POINT)")
    @Column(name = "geo")
    private Point geo;

    public EventPlace(Address address) {
        this(EventPlaceType.ADDRESS, address, null, null);
    }

    public EventPlace(String uri) {
        this(EventPlaceType.URI, null, uri, null);
    }

    public EventPlace(Point geo) {
        this(EventPlaceType.GEO, null, null, geo);
    }
}
