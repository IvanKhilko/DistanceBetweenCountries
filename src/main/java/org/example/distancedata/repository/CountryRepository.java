package org.example.distancedata.repository;

import org.example.distancedata.entity.Country;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    Optional<Country> getCountryInfoByName(@Param("name") String name);

    Optional<Country> getCountryInfoById(@Param("id") Long id);
}