package hu.innobyte.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Flight {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "airline_id")
    private Airline airline;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "start_city_id")
    private City startCity;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "arrive_city_id")
    private City arriveCity;

    @Column(nullable = false)
    private int distance;

    @Column(nullable = false)
    private int journeyTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public City getStartCity() {
        return startCity;
    }

    public void setStartCity(City startCity) {
        this.startCity = startCity;
    }

    public City getArriveCity() {
        return arriveCity;
    }

    public void setArriveCity(City arriveCity) {
        this.arriveCity = arriveCity;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getJourneyTime() {
        return journeyTime;
    }

    public void setJourneyTime(int journeyTime) {
        this.journeyTime = journeyTime;
    }

}
