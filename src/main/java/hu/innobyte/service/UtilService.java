package hu.innobyte.service;

import hu.innobyte.dto.FlightDto;
import hu.innobyte.mapper.FlightMapper;
import hu.innobyte.model.Airline;
import hu.innobyte.model.City;
import hu.innobyte.model.Flight;
import hu.innobyte.util.Node;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class UtilService {

    private final AirlineService airlineService;

    private final CityService cityService;

    private final FlightMapper flightMapper = Mappers.getMapper(FlightMapper.class);

    public UtilService(AirlineService airlineService, CityService cityService) {
        this.airlineService = airlineService;
        this.cityService = cityService;
    }

    public List<FlightDto> mapperFlightListToFlightDtoList(List<Flight> flights) {
        if (flights == null) {
            return null;
        }
        return flights.stream().map(flightMapper::fligthToFlightDto).toList();
    }

    public List<Flight> findAllFlightByAirlineId(Integer airlineId) {
        return airlineService.findById(airlineId).map(Airline::getFlights).orElse(null);
    }

    public List<Flight> getAirlineFlightsFromSmallestToLargestCity(List<Flight> flights) {
        List<Flight> result = null;
        List<Integer> visitedCityIds = new ArrayList<>();
        City startCity = cityService.findMinPopulationCity();
        City arrCity = cityService.findMaxPopulationCity();
        Node[] nodes = new Node[(int)cityService.countCity()];
        Arrays.setAll(nodes, node -> new Node());
        Integer actualCityId = null;
        nodes[startCity.getId()-1000].setDist(0); //city id started 1000
        List<Flight> originalFlight = new ArrayList<>(flights);
        while (!flights.isEmpty()) {
            if (actualCityId != null) {
                List<Flight> neighbourCityFlightList = getNeighbourCityFlightStream(flights, actualCityId).toList();
                flights.removeAll(neighbourCityFlightList);
            }
            actualCityId = getActualCityId(originalFlight, visitedCityIds, actualCityId, startCity.getId());
            if (actualCityId == null) { // not flight from visitedCities to neighbours
                flights.clear();
            } else {
                if (!Objects.equals(actualCityId, arrCity.getId())) {
                    setNeighbourNodesDistance(flights, nodes, actualCityId);
                } else {
                    result = getResultFlights(flights, originalFlight, nodes, actualCityId);
                }
                visitedCityIds.add(actualCityId);
            }
        }

        return result;
    }

    private Integer getActualCityId(List<Flight> originalFlight, List<Integer> visitedCityIds,
                                    Integer actualCityId, Integer startCityId) {
        if (actualCityId == null) {
            return startCityId;
        }
        return originalFlight.stream()
                .filter(flight -> visitedCityIds.contains(flight.getStartCity().getId()) // flight from vistedCity
                        && !visitedCityIds.contains(flight.getArriveCity().getId()))  // not flight to vistedCity
                .min(Comparator.comparing(Flight::getDistance))
                .map(flight -> flight.getArriveCity().getId()).orElse(null);
    }

    private Stream<Flight> getNeighbourCityFlightStream(List<Flight> flights, Integer actualCityId) {
        return flights.stream().filter(flight -> flight.getStartCity().getId().equals(actualCityId));
    }

    private void setNeighbourNodesDistance(List<Flight> flights, Node[] nodes, Integer actualCityId) {
        Stream<Flight> neighbourCityFlightStream = getNeighbourCityFlightStream(flights, actualCityId); //neighbour of actualCityId
        neighbourCityFlightStream.forEach(flight -> {
            int alt = nodes[actualCityId - 1000].getDist() + flight.getDistance();
            Integer nextCityId = flight.getArriveCity().getId();
            Node actualNode = nodes[nextCityId - 1000];
            if (alt < actualNode.getDist()) {
                actualNode.setValues(alt, actualCityId, flight.getId());
            }
        });
    }

    private List<Flight> getResultFlights(List<Flight> flights, List<Flight> originalFlight, Node[] nodes, Integer actualCityId) {
        List<Flight> result = new ArrayList<>();
        while (nodes[actualCityId-1000].getPrevious() != null) { // check is startCity
            result.add(getFlight(originalFlight, nodes, actualCityId));
            actualCityId = nodes[actualCityId-1000].getPrevious();
        }
        flights.clear();
        Collections.reverse(result);
        return result;
    }

    private Flight getFlight(List<Flight> originalFlight, Node[] nodes, int actualCityId) {
        return originalFlight.stream()
                .filter(flight -> Objects.equals(flight.getId(), nodes[actualCityId - 1000].getToFlightId()))
                .findFirst().orElse(null);
    }

}
