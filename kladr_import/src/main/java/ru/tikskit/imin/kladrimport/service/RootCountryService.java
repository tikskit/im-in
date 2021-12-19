package ru.tikskit.imin.kladrimport.service;

import ru.tikskit.imin.kladrimport.model.tar.Country;

public interface RootCountryService {
    void insertRootCountry();

    Country getCountry();
}
