package hu.innobyte.service;

import hu.innobyte.dto.FlightDto;
import hu.innobyte.mapper.FlightMapper;
import hu.innobyte.model.Airline;
import hu.innobyte.model.City;
import hu.innobyte.model.Flight;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;
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
        int cityCount = (int)cityService.countCity();
        int[] dist = new int[cityCount];
        int[] previous = new int[cityCount];
        int[] toFlightId = new int[cityCount];
        IntStream.range(0, cityCount).forEach(i -> {
            dist[i] = Integer.MAX_VALUE;
            previous[i] = -1; //undefinied
            toFlightId[i] = -1; //undefinied
        });
        int actualCityId = -1;
        dist[startCity.getId()-1000] = 0; //city id started 1000
        List<Flight> originalFlight = new ArrayList<>(flights);
        while (!flights.isEmpty()) {
            actualCityId = extractMinDistance(flights, visitedCityIds, actualCityId, startCity.getId());
            result = setNeighbourCityDistance(flights, originalFlight, dist, previous, toFlightId, actualCityId, arrCity.getId());
            visitedCityIds.add(actualCityId);
        }

        return result;
    }

    private List<Flight> setNeighbourCityDistance(List<Flight> flights,
                                                  List<Flight> originalFlight,
                                                  int[] dist, int[] previous, int[] toFlightId,
                                                  int actualCityId,
                                                  int arriveCityId) {
        if (actualCityId != arriveCityId) {
            Stream<Flight> neighbourCityFlightStream = getNeighbourCityFlightStream(flights, actualCityId); //neighbour of actualCityId
            neighbourCityFlightStream
                    .forEach(flight -> {
                        int alt = dist[actualCityId - 1000] + flight.getDistance();
                        Integer nextCityId = flight.getArriveCity().getId();
                        if (alt < dist[nextCityId - 1000]) {
                            dist[nextCityId - 1000] = alt;
                            previous[nextCityId - 1000] = actualCityId;
                            toFlightId[nextCityId - 1000] = flight.getId();
                        }
                    });
            return null;
        } else {
            List<Flight> result = new ArrayList<>();
            int resultActualCityId = actualCityId;
            while (previous[resultActualCityId-1000] != -1) {
                result.add(
                        getFlight(originalFlight, toFlightId, resultActualCityId));
                resultActualCityId = previous[resultActualCityId-1000];
            }
            flights.clear();
            Collections.reverse(result);
            return result;
        }
    }

    private Flight getFlight(List<Flight> originalFlight, int[] toFlightId, int actualCityId) {
        return originalFlight.stream()
                .filter(flight -> flight.getId() == toFlightId[actualCityId - 1000])
                .findFirst().orElse(null);
    }

    private int extractMinDistance(List<Flight> flights, List<Integer> visitedCityIds,
                                   int actualCityId, int startCityId) {
        if (actualCityId == - 1) {
            return startCityId;
        }
        List<Flight> neighbourCityFlightList = getNeighbourCityFlightStream(flights, actualCityId).toList();
        flights.removeAll(neighbourCityFlightList);
        return neighbourCityFlightList.stream()
                .filter(flight -> !visitedCityIds.contains(flight.getArriveCity().getId()))
                .min(Comparator.comparing(Flight::getDistance))
                .map(flight -> flight.getArriveCity().getId()).orElse(-1);
    }

    private static Stream<Flight> getNeighbourCityFlightStream(List<Flight> flights, int actualCityId) {
        return flights.stream()
                .filter(flight -> flight.getStartCity().getId().equals(actualCityId));
    }

}
