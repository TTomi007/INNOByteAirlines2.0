package hu.innobyte.dto;

public final class FlightDtoBuilder {
    private Integer id;
    private AirlineDto airline;
    private CityDto startCity;
    private CityDto arriveCity;
    private int distance;
    private int journeyTime;

    private FlightDtoBuilder() {
    }

    public static FlightDtoBuilder aFlightDto() {
        return new FlightDtoBuilder();
    }

    public FlightDtoBuilder withId(Integer id) {
        this.id = id;
        return this;
    }

    public FlightDtoBuilder withAirline(AirlineDto airline) {
        this.airline = airline;
        return this;
    }

    public FlightDtoBuilder withStartCity(CityDto startCity) {
        this.startCity = startCity;
        return this;
    }

    public FlightDtoBuilder withArriveCity(CityDto arriveCity) {
        this.arriveCity = arriveCity;
        return this;
    }

    public FlightDtoBuilder withDistance(int distance) {
        this.distance = distance;
        return this;
    }

    public FlightDtoBuilder withJourneyTime(int journeyTime) {
        this.journeyTime = journeyTime;
        return this;
    }

    public FlightDto build() {
        return new FlightDto(id, airline, startCity, arriveCity, distance, journeyTime);
    }
}
