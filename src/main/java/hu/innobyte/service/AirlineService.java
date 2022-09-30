package hu.innobyte.service;

import hu.innobyte.model.Airline;
import hu.innobyte.repository.AirlineRepository;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AirlineService {

    private final AirlineRepository airlineRepository;

    public AirlineService(AirlineRepository airlineRepository) {
        this.airlineRepository = airlineRepository;
    }

    public Airline save(Airline airline) {
        return airlineRepository.save(airline);
    }

    public List<Airline> saveAll(List<Airline> airlines) {
        return IterableUtils.toList(airlineRepository.saveAll(airlines));
    }

    public void delete(Airline airline) {
        airlineRepository.delete(airline);
    }

    public void deleteById(Integer id) {
        airlineRepository.deleteById(id);
    }

    public List<Airline> findAll() {
        return IterableUtils.toList(airlineRepository.findAll());
    }

    public Optional<Airline> findById(Integer id) {
        return airlineRepository.findById(id);
    }

}
