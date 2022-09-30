package hu.innobyte.repository;

import hu.innobyte.model.Flight;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

public interface FlightRepository extends CrudRepository<Flight, Integer> {

    List<Flight> findByStartCity_IdInAndArriveCity_IdIn(Collection<Integer> startCityIds, Collection<Integer> arriveCityIds);

}
