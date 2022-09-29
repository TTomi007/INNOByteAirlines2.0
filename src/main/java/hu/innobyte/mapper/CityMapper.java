package hu.innobyte.mapper;

import hu.innobyte.dto.CityDto;
import hu.innobyte.model.City;
import org.mapstruct.Mapper;

@Mapper
public interface CityMapper {

    CityDto cityToCityDto(City city);

    City cityDtoToCity (CityDto cityDto);

}
