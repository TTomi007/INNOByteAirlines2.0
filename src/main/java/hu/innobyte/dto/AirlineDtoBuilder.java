package hu.innobyte.dto;

public final class AirlineDtoBuilder {
    private Integer id;
    private String name;

    private AirlineDtoBuilder() {
    }

    public static AirlineDtoBuilder anAirlineDto() {
        return new AirlineDtoBuilder();
    }

    public AirlineDtoBuilder withId(Integer id) {
        this.id = id;
        return this;
    }

    public AirlineDtoBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public AirlineDto build() {
        return new AirlineDto(id, name);
    }
}
