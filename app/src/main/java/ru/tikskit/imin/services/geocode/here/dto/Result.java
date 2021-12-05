package ru.tikskit.imin.services.geocode.here.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Result {
    private List<GeoData> items;
}
