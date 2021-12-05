package ru.tikskit.imin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GeoPoint {
    @Column(name = "lat")
    private float lat;
    @Column(name = "lng")
    private float lng;
}
