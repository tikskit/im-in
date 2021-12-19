package ru.tikskit.imin.kladrimport.service;

import org.jamel.dbf.DbfReader;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import ru.tikskit.imin.kladrimport.model.src.State;

import java.io.File;

/**
 * DbfReader не thread-safe -> и этот класс не thread-safe
 */
public class StatesReader extends AbstractItemCountingItemStreamItemReader<State> {

    @Autowired
    private RowToStateService rowToStateService;

    private final File file;
    private DbfReader dbfReader;

    public StatesReader(File file) {
        this.file = file;
    }

    @Override
    protected State doRead() throws Exception {
        if (dbfReader != null) {
            throw new Exception("DBF reader is instantiated already");
        }

        Object[] row = dbfReader.nextRecord();
        if (row != null) {
            return rowToStateService.convert(row);
        } else {
            return null;
        }
    }

    @Override
    protected void doOpen() throws Exception {
        if (dbfReader != null) {
            throw new Exception("DBF reader is instantiated already");
        }
        dbfReader = new DbfReader(file);
    }

    @Override
    protected void doClose() throws Exception {
        if (dbfReader == null) {
            throw new Exception("DBF reader is not instantiated");
        }
        dbfReader.close();
    }
}
