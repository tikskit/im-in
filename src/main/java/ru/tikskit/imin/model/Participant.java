package ru.tikskit.imin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

//@Entity
//@Table(name = "participants")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Participant {
    private long id;
}
