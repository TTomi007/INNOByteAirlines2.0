package hu.innobyte.mapper;

import hu.innobyte.dto.AirlineDto;
import hu.innobyte.model.Airline;
import org.mapstruct.Mapper;

@Mapper
public interface AirlineMapper {

    AirlineDto airlineToAirlineDto(Airline airline);

    Airline airlineDtoToAirline(AirlineDto airlineDto);

}
