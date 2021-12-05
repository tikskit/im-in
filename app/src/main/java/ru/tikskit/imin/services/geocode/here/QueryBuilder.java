package ru.tikskit.imin.services.geocode.here;

interface QueryBuilder {
    QueryBuilder addPart(String part);
    String build();
}
