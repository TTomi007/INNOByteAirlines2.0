package hu.innobyte.dto;

public record AirlineDto(
        Integer id,
        String name
) {

    private AirlineDto(AirlineDto.AirlineDtoBuilder airlineDtoBuilder)
    {
        this(airlineDtoBuilder.id, airlineDtoBuilder.name);
    }

    public static AirlineDto.AirlineDtoBuilder builder() {
        return new AirlineDto.AirlineDtoBuilder();
    }

    public static final class AirlineDtoBuilder {
        private Integer id;
        private String name;

        public AirlineDtoBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public AirlineDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        public AirlineDto create() {
            return new AirlineDto(id, name);
        }

    }

}
