package org.example.distancedata.services.implementation;

import lombok.AllArgsConstructor;

import org.example.distancedata.dto.CountryDTO;
import org.example.distancedata.entity.Country;
import org.example.distancedata.entity.Continent;
import org.example.distancedata.repository.CountryRepository;
import org.example.distancedata.services.DataService;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CountryServiceImpl implements DataService<Country> {
    private final CountryRepository repository;

    private long findFreeID() {
        var list = read();
        long i = 1;
        for (Country countryInfo : list) {
            if (countryInfo.getId() != i) {
                return i;
            }
            i++;
        }
        return i + 1;
    }

    public boolean createWithContinent(CountryDTO country, Continent continent) {
        if (getByName(country.getName()) == null) {
            var newCountry = Country.builder().name(country.getName()).latitude(country.getLatitude())
                    .longitude(country.getLongitude()).continent(continent).id(findFreeID()).build();
            repository.save(newCountry);
            return true;
        }
        return false;
    }

    @Override
    public boolean create(Country country) {
        if (getByID(country.getId()) == null) {
            repository.save(country);
            return true;
        }
        return false;
    }

    public boolean updateWithContinent(Country country, Continent continent) {
        if (getByID(country.getId()) != null) {
            var newCountry = Country.builder().name(country.getName()).latitude(country.getLatitude())
                    .longitude(country.getLongitude()).id(country.getId()).continent(continent).build();
            repository.save(newCountry);
            return true;
        }
        return false;
    }

    @Override
    public List<Country> read() {
        return repository.findAll(Sort.by("id"));
    }

    @Override
    public Country getByName(String name) {
        var optionalCountry = repository.getCountryInfoByName(name);
        return optionalCountry.orElse(null);
    }

    @Override
    public Country getByID(Long id) {
        return repository.getCountryInfoById(id).orElse(null);
    }

    @Override
    public boolean update(Country country) {
        if (getByID(country.getId()) != null) {
            repository.save(country);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        if (this.getByID(id) != null) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

}