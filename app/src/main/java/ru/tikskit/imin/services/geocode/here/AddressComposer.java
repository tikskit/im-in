package ru.tikskit.imin.services.geocode.here;

interface AddressComposer {
    AddressComposer addPart(String part);
    String build();
}
