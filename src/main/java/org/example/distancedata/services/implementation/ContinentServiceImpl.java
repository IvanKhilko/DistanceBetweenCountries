package org.example.distancedata.services.implementation;

import lombok.AllArgsConstructor;
import org.example.distancedata.dto.ContinentDTO;
import org.example.distancedata.entity.Continent;
import org.example.distancedata.entity.Language;
import org.example.distancedata.repository.ContinentRepository;
import org.example.distancedata.repository.LanguageRepository;
import org.example.distancedata.services.DataService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@AllArgsConstructor
public class ContinentServiceImpl implements DataService<Continent> {
    private final ContinentRepository continentRepository;
    private final LanguageRepository languageRepository;

    private long findFreeID() {
        var list = read();
        long i = 1;
        for (Continent continent : list) {
            if (continent.getId() != i) {
                return i;
            }
            i++;
        }
        return i + 1;
    }

    @Override
    public boolean create(Continent continent) {
        if (getByName(continent.getName()) == null) {
            continentRepository.save(continent);
            return true;
        }
        return false;
    }

    @Override
    public List<Continent> read() {
        return continentRepository.findAll(Sort.by("id"));
    }

    @Override
    public Continent getByName(String name) {
        return continentRepository.getByName(name).orElse(null);
    }


    @Override
    public Continent getByID(Long id) {
        return continentRepository.getContinentById(id).orElse(null);
    }

    @Override
    public boolean update(Continent continent) {
        if (getByID(continent.getId()) != null) {
            continentRepository.save(continent);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        if (this.getByID(id) != null) {
            continentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean create(ContinentDTO continentDTO) {
        var listLanguage = new HashSet<Language>();
        for (String ptrLanguage : continentDTO.getLanguages()) {
            var language = languageRepository.getByName(ptrLanguage);
            language.ifPresent(listLanguage::add);
        }
        var newContinent = Continent.builder().name(continentDTO.getName()).languages(new HashSet<>()).id(findFreeID()).build();
        for (Language language : listLanguage) {
            newContinent.addLanguage(language);
        }
        return create(newContinent);
    }

    public boolean updateWithExist(ContinentDTO continent) {
        if (continentRepository.getContinentById(continent.getId()).isEmpty())
            return false;
        var newLanguages = new HashSet<Language>();
        for (String language : continent.getLanguages()) {
            var languageTemp = languageRepository.getByName(language);
            languageTemp.ifPresent(newLanguages::add);
        }
        var updatedContinent = Continent.builder().name(continent.getName()).languages(new HashSet<>())
                .id(continent.getId()).build();
        for (Language language : newLanguages) {
            updatedContinent.addLanguage(language);
        }
        return update(updatedContinent);
    }

    public boolean modifyLanguage(ContinentDTO continentDTO, boolean deleteFlag) {
        var continent = this.getByID(continentDTO.getId());
        if (continent != null) {
            for (String language : continentDTO.getLanguages()) {
                var tempLanguage = languageRepository.getByName(language);
                tempLanguage.ifPresent(!deleteFlag ? continent::addLanguage : continent::removeLanguage);
            }
            return update(continent);
        }
        return false;
    }
}