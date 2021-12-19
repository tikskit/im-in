package ru.tikskit.imin.kladrimport.model.tar;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "counties", schema = "places")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
public class County {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @EqualsAndHashCode.Include
    private String name;

    @Column
    @ManyToOne(targetEntity = State.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id", nullable = false)
    private State state;

}
