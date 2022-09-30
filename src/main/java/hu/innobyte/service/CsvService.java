package hu.innobyte.service;

import hu.innobyte.dto.AirlineDtoBuilder;
import hu.innobyte.dto.CityDto;
import hu.innobyte.dto.CityDtoBuilder;
import hu.innobyte.dto.FlightDto;
import hu.innobyte.dto.FlightDtoBuilder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Stream;

@Service
public class CsvService {

    public List<CityDto> readCity(InputStream inputStream, String splitRegexp) {
        return readStringArrayListFromInputStream(inputStream, splitRegexp)
                .map(this::mapToCityDto)
                .toList();
    }

    public List<FlightDto> readFlight(InputStream inputStream, String splitRegexp) {
        return readStringArrayListFromInputStream(inputStream, splitRegexp)
                .map(this::mapToFlightDto)
                .toList();
    }

    private Stream<String[]> readStringArrayListFromInputStream(InputStream inputStream, String splitRegexp) {
        return new BufferedReader(new InputStreamReader(inputStream)).lines()
                .map(line ->line.split(splitRegexp, -1));
    }

    private CityDto mapToCityDto(String[] values) {
        return CityDtoBuilder.aCityDto()
                .withName(values[0])
                .withPopulation(Integer.parseInt(values[1]))
                .build();
    }

    private FlightDto mapToFlightDto(String[] values) {
        return FlightDtoBuilder.aFlightDto()
                .withAirline(AirlineDtoBuilder.anAirlineDto().withName(values[0]).build())
                .withStartCity(CityDtoBuilder.aCityDto().withName(values[1]).build())
                .withArriveCity(CityDtoBuilder.aCityDto().withName(values[2]).build())
                .withDistance(Integer.parseInt(values[3]))
                .withJourneyTime(Integer.parseInt(values[4]))
                .build();
    }

}
