package org.example.distance.services;

import org.example.distance.entity.Country;

import org.springframework.stereotype.Service;

@Service
public interface DistanceService {
    double getDistanceInKilometres(Country to, Country from);
}