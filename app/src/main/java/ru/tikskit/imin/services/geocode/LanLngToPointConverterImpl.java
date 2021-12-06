package ru.tikskit.imin.services.geocode;

import lombok.AllArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LanLngToPointConverterImpl implements LanLngToPointConverter {

    @Override
    public Point convert2Point(LatLng latLng) {
        GeometryFactory geometryFactory = new GeometryFactory();
        return geometryFactory.createPoint(new Coordinate(latLng.getLng(), latLng.getLat()));
    }
}
