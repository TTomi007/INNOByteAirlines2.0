package hu.innobyte.dto;

public record FlightDto(
        Integer id,
        AirlineDto airline,
        CityDto startCity,
        CityDto arriveCity,
        int distance,
        int journeyTime
) {
}
