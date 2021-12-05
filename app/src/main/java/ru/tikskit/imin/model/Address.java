package ru.tikskit.imin.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Структуру федерального деления возмем из той, которая используется в Here:
 * Страны, country (напр., Россия)
 *   Регионы, state (Напр., Новосибирская область)
 *     Районы, county (напр., Тогучинский район)
 *       Города, city (Напр., село Буготак)
 * В приложении важно, чтобы адреса однозначно идентифицировались, воспроизводить структуру федерального деления не
 * обязательно
 */

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Address {
    @Column(name = "country")
    private String country;

    @Column(name = "state")
    private String state;

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
