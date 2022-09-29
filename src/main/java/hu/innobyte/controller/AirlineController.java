package hu.innobyte.controller;

import hu.innobyte.dto.AirlineDto;
import hu.innobyte.mapper.AirlineMapper;
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

@RestController
@RequestMapping("/airline")
public class AirlineController {

    private final AirlineService airlineService;

    private final AirlineMapper mapper = Mappers.getMapper(AirlineMapper.class);

    public AirlineController(AirlineService airlineService) {
        this.airlineService = airlineService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<AirlineDto> findAll() {
        return airlineService.findAll().stream().map(mapper::airlineToAirlineDto).toList();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AirlineDto findById(@PathVariable("id") Integer id) {
        return mapper.airlineToAirlineDto(airlineService.findById(id).orElse(null));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AirlineDto save(@RequestBody AirlineDto airlineDto) {
        Airline airline = mapper.airlineDtoToAirline(airlineDto);
        return mapper.airlineToAirlineDto(airlineService.save(airline));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void delete(@PathVariable("id") Integer id) {
        airlineService.deleteById(id);
    }

}
