package hu.innobyte.service;

import hu.innobyte.dto.AirlineDto;
import hu.innobyte.dto.CityDto;
import hu.innobyte.dto.FlightDto;
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
        return CityDto.builder()
                .name(values[0])
                .population(Integer.parseInt(values[1]))
                .create();
    }

    private FlightDto mapToFlightDto(String[] values) {
        return FlightDto.builder()
                .airline(AirlineDto.builder().name(values[0]).create())
                .startCity(CityDto.builder().name(values[1]).create())
                .arriveCity(CityDto.builder().name(values[2]).create())
                .distance(Integer.parseInt(values[3]))
                .journeyTime(Integer.parseInt(values[4]))
                .create();
    }

}
