package hu.innobyte.controller;

import hu.innobyte.dto.FlightDto;
import hu.innobyte.service.FlightService;
import hu.innobyte.service.UtilService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/result")
public class ResultController {

    private final FlightService flightService;

    private final UtilService utilService;

    public ResultController(FlightService flightService, UtilService utilService) {
        this.flightService = flightService;
        this.utilService = utilService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<FlightDto> getFlightsFromSmallestToLargestCity() {
        return utilService.mapperFlightListToFlightDtoList(
                utilService.getAirlineFlightsFromSmallestToLargestCity(flightService.findAll()));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<FlightDto> getAirlineFlightsFromSmallestToLargestCity(@PathVariable("id") Integer id) {
        return utilService.mapperFlightListToFlightDtoList(
                utilService.getAirlineFlightsFromSmallestToLargestCity(utilService.findAllFlightByAirlineId(id)));
    }

}
