package org.example.Distance.services;

import org.example.Distance.entity.Country;

import org.springframework.stereotype.Service;

@Service
public interface DistanceService {
    double getDistanceInKilometres(Country to, Country from);
}