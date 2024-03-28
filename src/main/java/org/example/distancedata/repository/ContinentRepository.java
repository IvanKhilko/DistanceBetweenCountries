package org.example.distancedata.repository;

import org.example.distancedata.entity.Continent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContinentRepository extends JpaRepository<Continent, Long> {
    Optional<Continent> getByName(@Param("name") String name);

    Optional<Continent> getContinentById(@Param("id") Long id);
}