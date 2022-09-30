package hu.innobyte.controller;

import hu.innobyte.dto.AirlineDto;
import hu.innobyte.dto.FlightDto;
import hu.innobyte.mapper.AirlineMapper;
import hu.innobyte.mapper.FlightMapper;
import hu.innobyte.model.Airline;
import hu.innobyte.service.AirlineService;
import org.mapstruct.factory.Mappers;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@RestController
@RequestMapping("/airline")
public class AirlineController {

    private final AirlineService airlineService;

    private final AirlineMapper airlineMapper = Mappers.getMapper(AirlineMapper.class);

    private final FlightMapper flightMapper = Mappers.getMapper(FlightMapper.class);

    public AirlineController(AirlineService airlineService) {
        this.airlineService = airlineService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<AirlineDto> findAll() {
        return airlineService.findAll().stream().map(airlineMapper::airlineToAirlineDto).toList();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AirlineDto findById(@PathVariable("id") Integer id) {
        return airlineMapper.airlineToAirlineDto(airlineService.findById(id).orElse(null));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AirlineDto save(@RequestBody AirlineDto airlineDto) {
        Airline airline = airlineMapper.airlineDtoToAirline(airlineDto);
        return airlineMapper.airlineToAirlineDto(airlineService.save(airline));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void delete(@PathVariable("id") Integer id) {
        airlineService.deleteById(id);
    }

    @GetMapping(value = "/{id}/flight", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<FlightDto> findAllFlightById(@PathVariable("id") Integer id) {
        Optional<Airline> airline = airlineService.findById(id);
        return airline.map(getFlightDtoListByAirline()).orElse(null);
    }

    private Function<Airline, List<FlightDto>> getFlightDtoListByAirline() {
        return airline -> airline.getFlights().stream().map(flightMapper::fligthToFlightDto).toList();
    }

}
