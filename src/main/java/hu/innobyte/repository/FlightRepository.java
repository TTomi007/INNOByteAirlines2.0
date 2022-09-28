package hu.innobyte.repository;

import hu.innobyte.model.Flight;
import org.springframework.data.repository.CrudRepository;

public interface FlightRepository extends CrudRepository<Flight, Integer> {
}
