package ru.tikskit.imin.services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AddressDto {
    private final String country;
    private final String state;
    private final String county;
    private final String city;
    private final String street;
    private final String building;
}
