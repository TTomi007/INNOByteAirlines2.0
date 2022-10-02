package hu.innobyte.dto;

public record CityDto(
        Integer id,
        String name,
        int population
) {
    private CityDto(CityDto.CityDtoBuilder cityDtoBuilder) {
        this(cityDtoBuilder.id, cityDtoBuilder.name, cityDtoBuilder.population);
    }

    public static CityDto.CityDtoBuilder builder() {
        return new CityDto.CityDtoBuilder();
    }

    public static class CityDtoBuilder {
        private Integer id;
        private String name;
        private int population;

        public CityDtoBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public CityDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CityDtoBuilder population(int population) {
            this.population = population;
            return this;
        }

        public CityDto create() {
            return new CityDto(this);
        }

    }

}
