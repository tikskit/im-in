package ru.tikskit.imin.services;

import lombok.AllArgsConstructor;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

@AllArgsConstructor
public class WktServiceImpl implements WktService {

    private final WKTReader wktReader;

    @Override
    public Geometry wkt2Geometry(String wellKnownText) {
        try {
            return wktReader.read(wellKnownText);
        } catch (ParseException e) {
            throw new WktParseException(e);
        }
    }

    @Override
    public Point wkt2Point(String wellKnownText) {
        return (Point) wkt2Geometry(wellKnownText);
    }
}
