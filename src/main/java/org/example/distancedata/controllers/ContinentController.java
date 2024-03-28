package org.example.distancedata.controllers;

import lombok.AllArgsConstructor;
import org.example.distancedata.dto.ContinentDTO;
import org.example.distancedata.entity.Continent;
import org.example.distancedata.services.implementation.ContinentServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/continents")
@AllArgsConstructor
public class ContinentController {
    private final ContinentServiceImpl continentService;

    @GetMapping(value = "/all", produces = "application/json")
    private ResponseEntity<List<Continent>> getAll() {
        return new ResponseEntity<>(continentService.read(), HttpStatus.OK);
    }

    @GetMapping(value = "/info", produces = "application/json")
    public ResponseEntity<Continent> getContinent(@RequestParam(name = "continent") String name) {
        var continent = continentService.getByName(name);
        if (continent == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(continent, HttpStatus.OK);
    }

    @GetMapping(value = "/find", produces = "application/json")
    public ResponseEntity<Continent> getCountryInfoById(@RequestParam(name = "id") Long id) {
        var continent = continentService.getByID(id);
        if (continent == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(continent, HttpStatus.OK);
    }

    @PutMapping("/update")
    private HttpStatus update(@RequestBody ContinentDTO continentDTO) {
        if (Boolean.TRUE.equals(continentService.updateWithExist(continentDTO)))
            return HttpStatus.OK;
        return HttpStatus.BAD_REQUEST;

    }

    @PostMapping("/create")
    private HttpStatus create(@RequestBody ContinentDTO continentDTO) {

        if (Boolean.TRUE.equals(continentService.create(continentDTO)))
            return HttpStatus.OK;
        return HttpStatus.BAD_REQUEST;
    }

    @DeleteMapping("/delete")
    private HttpStatus delete(@RequestParam(name = "id") Long id) {
        if (Boolean.TRUE.equals(continentService.delete(id)))
            return HttpStatus.OK;
        return HttpStatus.NOT_FOUND;
    }

    @PutMapping("/add_language")
    public HttpStatus addLanguages(@RequestBody ContinentDTO continentDTO) {
        if (Boolean.TRUE.equals(continentService.modifyLanguage(continentDTO, false)))
            return HttpStatus.OK;
        return HttpStatus.BAD_REQUEST;
    }

    @PutMapping("/delete_language")
    public HttpStatus deleteLanguages(@RequestBody ContinentDTO continentDTO) {
        if (Boolean.TRUE.equals(continentService.modifyLanguage(continentDTO, true)))
            return HttpStatus.OK;
        return HttpStatus.BAD_REQUEST;
    }
}