
package org.example.distancedata.controllers;

import lombok.AllArgsConstructor;

import org.example.distancedata.dto.CountryDTO;
import org.example.distancedata.entity.Country;
import org.example.distancedata.services.DistanceService;

import org.example.distancedata.services.implementation.CountryServiceImpl;
import org.example.distancedata.services.implementation.ContinentServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/countries")
@AllArgsConstructor
public class CountryController {
    private final CountryServiceImpl dataService;
    private final DistanceService distanceService;
    private final ContinentServiceImpl continentService;

    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<List<Country>> getAllCountry() {
        return new ResponseEntity<>(dataService.read(), HttpStatus.OK);
    }

    @GetMapping(value = "/info", produces = "application/json")
    public ResponseEntity<Country> getCountryInfo(@RequestParam(name = "country") String countryName) {
        var countryInfo = dataService.getByName(countryName);
        if (countryInfo == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(countryInfo, HttpStatus.OK);
    }

    @GetMapping(value = "/find", produces = "application/json")
    public ResponseEntity<Country> getCountryInfoById(@RequestParam(name = "id") Long id) {
        var countryInfo = dataService.getByID(id);
        if (countryInfo == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(countryInfo, HttpStatus.OK);
    }

    @GetMapping(value = "/distance/{firstCountry}+{secondCountry}", produces = "application/json")
    public ResponseEntity<?> getDistance(@PathVariable(name = "firstCountry") String firstCountry, @PathVariable(name = "secondCountry") String secondCountry) {
        var firstCountryInfo = dataService.getByName(firstCountry);
        var secondCountryInfo = dataService.getByName(secondCountry);
        double distance = distanceService.getDistanceInKilometres(firstCountryInfo, secondCountryInfo);
        if (distance != -1) {
            var objects = new HashMap<String, String>();
            objects.put("First country info", firstCountryInfo.toString());
            objects.put("Second country info", secondCountryInfo.toString());
            objects.put("Distance", Double.toString(distance));
            return new ResponseEntity<>(objects, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{continentName}")
    private HttpStatus update(@RequestBody Country country, @PathVariable(name = "continentName") String continentName) {
        var continent = continentService.getByName(continentName);
        if (continent == null)
            return HttpStatus.NOT_FOUND;
        if (Boolean.TRUE.equals(dataService.updateWithContinent(country, continent)))
            return HttpStatus.OK;
        return HttpStatus.BAD_REQUEST;
    }

    @PostMapping("/create/{continentName}")
    private HttpStatus create(@RequestBody CountryDTO country, @PathVariable(name = "continentName") String continentName) {
        var continent = continentService.getByName(continentName);
        if (continent == null)
            return HttpStatus.NOT_FOUND;
        if (Boolean.TRUE.equals(dataService.createWithContinent(country, continent)))
            return HttpStatus.OK;
        return HttpStatus.BAD_REQUEST;
    }

    @DeleteMapping("/delete")
    private HttpStatus delete(@RequestParam(name = "id") Long id) {
        if (Boolean.TRUE.equals(dataService.delete(id)))
            return HttpStatus.OK;
        return HttpStatus.NOT_FOUND;
    }
}
