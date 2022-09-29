package hu.innobyte.dto;

public final class CityDtoBuilder {
    private Integer id;
    private String name;
    private int population;

    private CityDtoBuilder() {
    }

    public static CityDtoBuilder aCityDto() {
        return new CityDtoBuilder();
    }

    public CityDtoBuilder withId(Integer id) {
        this.id = id;
        return this;
    }

    public CityDtoBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public CityDtoBuilder withPopulation(int population) {
        this.population = population;
        return this;
    }

    public CityDto build() {
        return new CityDto(id, name, population);
    }
}
