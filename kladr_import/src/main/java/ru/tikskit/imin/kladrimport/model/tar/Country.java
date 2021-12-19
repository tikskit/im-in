package ru.tikskit.imin.kladrimport.model.tar;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "countries", schema = "places")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @EqualsAndHashCode.Include
    private String name;

    public Country(String name) {
        this.name = name;
    }
}
