package ru.tikskit.imin.services.geocode;

import org.locationtech.jts.geom.Point;

public interface LanLngToPointConverter {

    Point convert2Point(LatLng latLng);

}
