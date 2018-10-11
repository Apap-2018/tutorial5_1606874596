package com.apap.tutorial5.controller;

import com.apap.tutorial5.model.FlightModel;
import com.apap.tutorial5.model.PilotModel;
import com.apap.tutorial5.service.FlightService;
import com.apap.tutorial5.service.PilotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class FlightController {
    @Autowired
    private FlightService flightService;

    @Autowired
    private PilotService pilotService;

    @RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.GET)
    private String add(@PathVariable(value = "licenseNumber") String licenseNumber, Model model) {
        FlightModel flight = new FlightModel();
        PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
        flight.setPilot(pilot);

        model.addAttribute("flight", flight);
        return "addFlight";
    }

    @RequestMapping(value = "/flight/add", method = RequestMethod.POST)
    private String addFlightSubmit(@ModelAttribute FlightModel flight) {
        flightService.addFlight(flight);
        return "add";
    }

    @RequestMapping(value = "flight/delete", method = RequestMethod.POST)
    private String deleteFlight(@ModelAttribute PilotModel pilot, Model model) {
        for(FlightModel flight :  pilot.getPilotFlight()){
            flightService.deleteFlightById(flight.getId());
        }
        return "delete";
    }

    @RequestMapping("/flight/view")
    public String view(Model model){
        List<FlightModel> allFlights = flightService.getAllFlights();

        model.addAttribute("flights", allFlights);
        return"view-flight";
    }

    @RequestMapping (value = "/flight/update/{flightNumber}", method = RequestMethod.GET)
    private String updateFlight (@PathVariable ("flightNumber") String flightNumber, Model model) {
        FlightModel flightNow = flightService.getFlightDetailByFlightNumber(flightNumber);
        model.addAttribute("flightNow", flightNow);
        return "update-flight";
    }

    @RequestMapping (value = "/flight/update", method = RequestMethod.POST)
    private String updateFlightSubmit (@ModelAttribute FlightModel flight) {
        flightService.updateFlight(flight, flight.getFlightNumber());
        return "update";
    }
}
