package com.apap.tutorial5.controller;

import com.apap.tutorial5.model.FlightModel;
import com.apap.tutorial5.model.PilotModel;
import com.apap.tutorial5.service.FlightService;
import com.apap.tutorial5.service.PilotService;
import com.sun.rowset.internal.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FlightController {
    @Autowired
    private FlightService flightService;

    @Autowired
    private PilotService pilotService;

    @RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.GET)
    private String add(@PathVariable("licenseNumber") String licenseNumber, Model model) {
        PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
        ArrayList<FlightModel> flightList = new ArrayList<>();
        pilot.setPilotFlight(flightList);
        flightList.add(new FlightModel());
        model.addAttribute("pilot", pilot);
        return "addFlight";
    }

    @RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.POST, params={"save"})
    private String addFlightSubmit(@ModelAttribute PilotModel pilot) {
        PilotModel archive = pilotService.getPilotDetailByLicenseNumber(pilot.getLicenseNumber());
        for(FlightModel flight : pilot.getPilotFlight()) {
            flight.setPilot(archive);
            flightService.addFlight(flight);
        }
        return "add";
    }

    @RequestMapping(value = "flight/delete", method = RequestMethod.POST)
    private String deleteFlight(@ModelAttribute PilotModel pilot) {
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

    @RequestMapping(value="/flight/add/{licenseNumber}", params={"addRow"}, method = RequestMethod.POST)
    public String addRow(@ModelAttribute PilotModel pilot, BindingResult bindingResult, Model model) {
        pilot.getPilotFlight().add(new FlightModel());
        model.addAttribute("pilot", pilot);
        return "addFlight";
    }

    @RequestMapping(value="/flight/add/{licenseNumber}", method = RequestMethod.POST, params={"removeRow"})
    public String removeRow(@ModelAttribute PilotModel pilot, final BindingResult bindingResult, final HttpServletRequest req, Model model) {
        final Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
        pilot.getPilotFlight().remove(rowId.intValue());

        model.addAttribute("pilot", pilot);
        return "addFlight";
    }



}
