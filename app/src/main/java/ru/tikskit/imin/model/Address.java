package ru.tikskit.imin.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Address {
    @Column(name = "country")
    private String country;

    @Column(name = "region")
    private String reqion;

    @Column(name = "county")
    private String county;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "building")
    private String building;

    @Column(name = "flat")
    private String flat;

    @Column(name = "extra")
    private String extra;
}
