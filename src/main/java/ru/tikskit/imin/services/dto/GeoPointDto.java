package ru.tikskit.imin.services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class GeoPointDto {
    private final double lat;
    private final double lon;
}
