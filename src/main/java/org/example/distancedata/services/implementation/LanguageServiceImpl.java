package org.example.distancedata.services.implementation;

import lombok.AllArgsConstructor;
import org.example.distancedata.dto.LanguageDTO;
import org.example.distancedata.entity.Continent;
import org.example.distancedata.entity.Language;
import org.example.distancedata.repository.LanguageRepository;
import org.example.distancedata.services.DataService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class LanguageServiceImpl implements DataService<Language> {
    private final LanguageRepository repository;

    @Override
    public boolean create(Language language) {
        if (getByID(language.getId()) == null) {
            repository.save(language);
            return true;
        }
        return false;
    }

    @Override
    public List<Language> read() {
        return repository.findAll(Sort.by("id"));
    }

    @Override
    public Language getByName(String name) {
        return repository.getByName(name).orElse(null);
    }

    @Override
    public Language getByID(Long id) {
        return repository.getLanguageById(id).orElse(null);
    }

    @Override
    public boolean update(Language language) {
        if (getByID(language.getId()) != null) {
            repository.save(language);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        Language language;
        if ((language = getByID(id)) != null) {
            List<Continent> existingContinents = language.getContinents();
            for (Continent continent : existingContinents)
                continent.removeLanguage(language);
            repository.delete(language);
            return true;
        }
        return false;
    }

    public boolean update(LanguageDTO language) {
        if (language.getId() == null)
            return false;
        return update(Language.builder().name(language.getName()).continents(new ArrayList<>()).id(language.getId()).build());
    }

    public boolean create(LanguageDTO language) {
        if (repository.getByName(language.getName()).isEmpty()) {
            return create(Language.builder().name(language.getName()).continents(new ArrayList<>()).build());
        }
        return false;
    }

}