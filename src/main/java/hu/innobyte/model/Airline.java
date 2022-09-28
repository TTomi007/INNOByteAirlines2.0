package hu.innobyte.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Airline {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "startCity")
    private List<Flight> startFlights;

    @OneToMany(mappedBy = "arriveCity")
    private List<Flight> arriveFlights;

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

    public List<Flight> getStartFlights() {
        return startFlights;
    }

    public void setStartFlights(List<Flight> startFlights) {
        this.startFlights = startFlights;
    }

    public List<Flight> getArriveFlights() {
        return arriveFlights;
    }

    public void setArriveFlights(List<Flight> arriveFlights) {
        this.arriveFlights = arriveFlights;
    }

}
