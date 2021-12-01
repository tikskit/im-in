package ru.tikskit.imin.services.geocode.here.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Position {
    private double lat;
    private double lng;
}
