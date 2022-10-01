package hu.innobyte.repository;

import hu.innobyte.model.City;
import org.springframework.data.repository.CrudRepository;

public interface CityRepository extends CrudRepository<City, Integer> {

    City findFirstByOrderByPopulationAsc();
    City findFirstByOrderByPopulationDesc();

}
