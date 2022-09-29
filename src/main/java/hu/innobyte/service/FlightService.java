package hu.innobyte.service;

import hu.innobyte.model.Flight;
import hu.innobyte.repository.FlightRepository;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightService {

    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public Flight save(Flight flight) {
        return flightRepository.save(flight);
    }

    public void delete(Flight flight) {
        flightRepository.delete(flight);
    }

    public void deleteById(Integer id) {
        flightRepository.deleteById(id);
    }

    public List<Flight> findAll() {
        return IterableUtils.toList(flightRepository.findAll());
    }

    public Optional<Flight> findById(Integer id) {
        return flightRepository.findById(id);
    }

}
