package hu.innobyte.repository;

import hu.innobyte.model.Airline;
import org.springframework.data.repository.CrudRepository;

public interface AirlineRepository extends CrudRepository<Airline, Integer> {
}
