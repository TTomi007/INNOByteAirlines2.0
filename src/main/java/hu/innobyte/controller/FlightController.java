package hu.innobyte.controller;

import hu.innobyte.dto.FlightDto;
import hu.innobyte.mapper.FlightMapper;
import hu.innobyte.model.Flight;
import hu.innobyte.service.FlightService;
import org.mapstruct.factory.Mappers;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/flight")
public class FlightController {

    private final FlightService flightService;

    private final FlightMapper mapper = Mappers.getMapper(FlightMapper.class);

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<FlightDto> findAll() {
        return flightService.findAll().stream().map(mapper::fligthToFlightDto).toList();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public FlightDto findById(@PathVariable("id") Integer id) {
        return mapper.fligthToFlightDto(flightService.findById(id).orElse(null));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public FlightDto save(@RequestBody FlightDto flightDto) {
        Flight flight = mapper.flightDtoToFlight(flightDto);
        return mapper.fligthToFlightDto(flightService.save(flight));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable("id") Integer id) {
        try {
            flightService.deleteById(id);
            return ResponseEntity.ok().body("Success delete");
        } catch (DataIntegrityViolationException de) {
            return ResponseEntity.badRequest().body("Unsuccessful delete, because other data reference this");
        } catch (RuntimeException re) {
            return ResponseEntity.badRequest().body("Unsuccessful delete, because: " + re.getMessage());
        }
    }

    @GetMapping(value = "/way", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<FlightDto> findAllByBetweenTwoCities(
            @RequestParam Integer startCityId, @RequestParam Integer arriveCityId) {
        return flightService.findAllByBetweenTwoCities(startCityId, arriveCityId)
                .stream().map(mapper::fligthToFlightDto).toList();
    }

}
