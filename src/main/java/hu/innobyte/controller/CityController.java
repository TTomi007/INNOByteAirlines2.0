package hu.innobyte.controller;

import hu.innobyte.dto.CityDto;
import hu.innobyte.mapper.CityMapper;
import hu.innobyte.model.City;
import hu.innobyte.service.CityService;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/city")
public class CityController {

    private final CityService cityService;

    private final CityMapper mapper = Mappers.getMapper(CityMapper.class);

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<CityDto> findAll() {
        return cityService.findAll().stream().map(mapper::cityToCityDto).toList();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CityDto findById(@PathVariable("id") Integer id) {
        return mapper.cityToCityDto(cityService.findById(id).orElse(null));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CityDto save(@RequestBody CityDto cityDto) {
        City city = mapper.cityDtoToCity(cityDto);
        return mapper.cityToCityDto(cityService.save(city));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable("id") Integer id) {
        try {
            cityService.deleteById(id);
            return ResponseEntity.ok().body("Success delete");
        } catch (DataIntegrityViolationException de) {
            return ResponseEntity.badRequest().body("Unsuccessful delete, because other data reference this");
        } catch (RuntimeException re) {
            return ResponseEntity.badRequest().body("Unsuccessful delete, because: " + re.getMessage());
        }
    }

}
