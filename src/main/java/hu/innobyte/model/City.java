package hu.innobyte.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class City {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int population;

    @OneToMany(mappedBy = "startCity")
    private List<Flight> startCityFlights;

    @OneToMany(mappedBy = "arriveCity")
    private List<Flight> arriveCityFlights;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public List<Flight> getStartCityFlights() {
        return startCityFlights;
    }

    public void setStartCityFlights(List<Flight> startCityFlights) {
        this.startCityFlights = startCityFlights;
    }

    public List<Flight> getArriveCityFlights() {
        return arriveCityFlights;
    }

    public void setArriveCityFlights(List<Flight> arriveCityFlights) {
        this.arriveCityFlights = arriveCityFlights;
    }

}
