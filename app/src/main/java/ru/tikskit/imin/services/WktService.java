package ru.tikskit.imin.services;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;

public interface WktService {
    Geometry wkt2Geometry(String wellKnownText);
    Point wkt2Point(String wellKnownText);
}
