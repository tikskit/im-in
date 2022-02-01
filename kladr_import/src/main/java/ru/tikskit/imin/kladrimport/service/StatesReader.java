package ru.tikskit.imin.kladrimport.service;

import org.jamel.dbf.DbfReader;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import ru.tikskit.imin.kladrimport.model.src.State;

import java.util.Objects;

/**
 * DbfReader не thread-safe -> и этот класс не thread-safe
 */
public class StatesReader extends AbstractItemCountingItemStreamItemReader<State> {

    @Autowired
    private RowToStateService rowToStateService;
    private final DbfReader dbfReader;

    public StatesReader(DbfReader dbfReader) {
        Objects.requireNonNull(dbfReader);
        this.dbfReader = dbfReader;
    }

    @Override
    protected State doRead() throws Exception {
        State state = null;
        Object[] row;
        while (state == null) {
            row = dbfReader.nextRecord();
            if (row == null) {
                return null;
            }
            state = rowToStateService.convert(row); // вернет null, если текущая строка не state
        }
        return state;
    }

    @Override
    protected void doOpen() throws Exception {

    }

    @Override
    protected void doClose() throws Exception {
        dbfReader.close();
    }
}
