package ru.tikskit.imin.kladrimport.service;

import ru.tikskit.imin.kladrimport.model.src.State;

public interface RowToStateService {
    State convert(Object[] row);
}
