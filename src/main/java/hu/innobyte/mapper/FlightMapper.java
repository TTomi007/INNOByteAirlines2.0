package hu.innobyte.mapper;

import hu.innobyte.dto.FlightDto;
import hu.innobyte.model.Flight;
import org.mapstruct.Mapper;

@Mapper
public interface FlightMapper {

    FlightDto fligthToFlightDto(Flight flight);

    Flight flightDtoToFlight(FlightDto flightDto);

}
