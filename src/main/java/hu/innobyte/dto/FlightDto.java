package hu.innobyte.dto;

public record FlightDto(
        Integer id,
        AirlineDto airline,
        CityDto startCity,
        CityDto arriveCity,
        int distance,
        int journeyTime
) {
    private FlightDto(FlightDto.FlightDtoBuilder flightDtoBuilder) {
        this(flightDtoBuilder.id,
                flightDtoBuilder.airline,
                flightDtoBuilder.startCity,
                flightDtoBuilder.arriveCity,
                flightDtoBuilder.distance,
                flightDtoBuilder.journeyTime);
    }

    public static FlightDto.FlightDtoBuilder builder() {
        return new FlightDto.FlightDtoBuilder();
    }

    public static class FlightDtoBuilder {
        private Integer id;
        private AirlineDto airline;
        private CityDto startCity;
        private CityDto arriveCity;
        private int distance;
        private int journeyTime;

        public FlightDtoBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public FlightDtoBuilder airline(AirlineDto airline) {
            this.airline = airline;
            return this;
        }

        public FlightDtoBuilder startCity(CityDto startCity) {
            this.startCity = startCity;
            return this;
        }

        public FlightDtoBuilder arriveCity(CityDto arriveCity) {
            this.arriveCity = arriveCity;
            return this;
        }

        public FlightDtoBuilder distance(int distance) {
            this.distance = distance;
            return this;
        }

        public FlightDtoBuilder journeyTime(int journeyTime) {
            this.journeyTime = journeyTime;
            return this;
        }

        public FlightDto create() {
            return new FlightDto(this);
        }

    }

}
