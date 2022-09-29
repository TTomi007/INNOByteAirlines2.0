package hu.innobyte.service;

import hu.innobyte.model.City;
import hu.innobyte.repository.CityRepository;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public City save(City city) {
        return cityRepository.save(city);
    }

    public void delete(City city) {
        cityRepository.delete(city);
    }

    public void deleteById(Integer id) {
        cityRepository.deleteById(id);
    }

    public List<City> findAll() {
        return IterableUtils.toList(cityRepository.findAll());
    }

    public Optional<City> findById(Integer id) {
        return cityRepository.findById(id);
    }

}
