package hu.innobyte.controller;

import hu.innobyte.mapper.CityMapper;
import hu.innobyte.mapper.FlightMapper;
import hu.innobyte.model.Airline;
import hu.innobyte.model.City;
import hu.innobyte.model.Flight;
import hu.innobyte.service.AirlineService;
import hu.innobyte.service.CityService;
import hu.innobyte.service.CsvService;
import hu.innobyte.service.FlightService;
import org.mapstruct.factory.Mappers;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/upload")
public class UploadController {

    private final CsvService csvService;

    private final CityService cityService;

    private final FlightService flightService;

    private final AirlineService airlineService;

    private final CityMapper cityMapper = Mappers.getMapper(CityMapper.class);

    private final FlightMapper flightMapper = Mappers.getMapper(FlightMapper.class);

    public UploadController(CsvService csvService, CityService cityService, FlightService flightService, AirlineService airlineService) {
        this.csvService = csvService;
        this.cityService = cityService;
        this.flightService = flightService;
        this.airlineService = airlineService;
    }

    @PostMapping(value = "/city")
    public void uploadCity(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        List<City> cities = csvService.readCity(multipartFile.getInputStream(), ";").stream()
                .map(cityMapper::cityDtoToCity)
                .toList();
        cityService.saveAll(cities);
    }

    @PostMapping(value = "/flight")
    public void uploadFlight(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        List<Flight> flights = csvService.readFlight(multipartFile.getInputStream(), ";").stream()
                .map(flightMapper::flightDtoToFlight)
                .toList();
        
        List<Airline> airlines = flights.stream().map(Flight::getAirline).distinct().toList();
        airlineService.saveAll(airlines);

        List<City> cities = cityService.findAll();
        flights.forEach(flight -> {
            flight.setAirline(getCurrentAirline(airlines, flight));
            flight.setStartCity(getCurrentStartCity(cities, flight));
            flight.setArriveCity(getCurrentArriveCity(cities, flight));
        });
        flightService.saveAll(flights);
    }

    private Airline getCurrentAirline(List<Airline> airlines, Flight flight) {
        return airlines.stream().filter(airline -> flight.getAirline().equals(airline)).findFirst().orElse(null);
    }

    private City getCurrentStartCity(List<City> cities, Flight flight) {
        return cities.stream().filter(city -> flight.getStartCity().equals(city)).findFirst().orElse(null);
    }

    private City getCurrentArriveCity(List<City> cities, Flight flight) {
        return cities.stream().filter(city -> flight.getArriveCity().equals(city)).findFirst().orElse(null);
    }

}
