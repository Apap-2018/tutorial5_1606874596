package com.apap.tutorial5.service;

import com.apap.tutorial5.model.FlightModel;
import com.apap.tutorial5.repository.FlightDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FlightServiceImpl implements FlightService {
    @Autowired
    private FlightDB flightDB;

    @Override
    public void addFlight(FlightModel flight){
        flightDB.save(flight);
    }

    @Override
    public FlightModel getFlightDetailByFlightNumber(String flightNumber) {
        return flightDB.findByFlightNumber(flightNumber);
    }

    @Override
    public List<FlightModel> getAllFlights(){
        return flightDB.findAll();
    }

    @Override
    public void deleteFlight(FlightModel flightId) {
        flightDB.delete(flightId);
    }

    @Override
    public void deleteFlightById(Long id) {
        flightDB.deleteById(id);
    }

    @Override
    public void updateFlight(FlightModel flight, String flightNumber) {
        FlightModel updatedFlight = flightDB.findByFlightNumber(flightNumber);
        updatedFlight.setDestination(flight.getDestination());
        updatedFlight.setFlightNumber(flight.getFlightNumber());
        updatedFlight.setTime(flight.getTime());
        updatedFlight.setOrigin(flight.getOrigin());
        flightDB.save(updatedFlight);
    }
}
