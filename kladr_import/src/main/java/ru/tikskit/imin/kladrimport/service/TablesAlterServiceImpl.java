package ru.tikskit.imin.kladrimport.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Component;

import java.sql.Connection;

@Component
@RequiredArgsConstructor
public class TablesAlterServiceImpl implements TablesAlterService {
    private final JdbcOperations jdbc;

    @Override
    public void addOuterCodeColumnToStatesTable() {
        jdbc.execute("ALTER TABLE places.states ADD COLUMN kladrCode SMALLINT NOT NULL;");
    }

    @Override
    public void removeOuterCodeColumnFromStatesTable() {
        jdbc.execute("ALTER TABLE places.states DROP COLUMN kladrCode;");
    }
}
