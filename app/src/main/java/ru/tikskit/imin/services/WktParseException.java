package ru.tikskit.imin.services;

import org.locationtech.jts.io.ParseException;

public class WktParseException extends RuntimeException {
    public WktParseException(ParseException e) {
        super(e);
    }
}
