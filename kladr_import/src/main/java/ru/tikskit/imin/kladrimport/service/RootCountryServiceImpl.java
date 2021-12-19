package ru.tikskit.imin.kladrimport.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tikskit.imin.kladrimport.model.tar.Country;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
@AllArgsConstructor
public class RootCountryServiceImpl implements RootCountryService {
    @PersistenceContext
    private final EntityManager em;
    private final Country country = new Country("Россия");

    public void insertRootCountry() {
        em.persist(country);
    }

    @Override
    public Country getCountry() {
        return country;
    }
}
