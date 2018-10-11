package com.apap.tutorial5.service;

import com.apap.tutorial5.model.FlightModel;

import java.util.List;

public interface FlightService {
    void addFlight(FlightModel flight);
    FlightModel getFlightDetailByFlightNumber(String flightNumber);
    List<FlightModel> getAllFlights();
    void deleteFlight(FlightModel flight);
    void deleteFlightById(Long id);
    void updateFlight (FlightModel flight, String flightNumber);

}
