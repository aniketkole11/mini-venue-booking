package com.eazyvenue.venuebooking.controller;

import com.eazyvenue.venuebooking.entity.Venue;
import com.eazyvenue.venuebooking.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class HomePageController {

    @Autowired
    private VenueRepository venueRepo;
    @GetMapping("/admin")
    public String showDashboard(Model model) {
        model.addAttribute("venues", venueRepo.findAll());
        return "admin_dashboard";
    }


    @GetMapping("/")
    public String loadDashboard(Model model) {
        model.addAttribute("venues", venueRepo.findAll());
        return "index"; // loads your updated UI
    }

    @PostMapping("/book/{id}")
    public String bookVenue(@PathVariable Long id, @RequestParam String date, Model model) {
        Optional<Venue> optionalVenue = venueRepo.findById(id);
        String message;
        if (optionalVenue.isPresent()) {
            Venue venue = optionalVenue.get();
            if (venue.getBlockedDates().contains(date)) {
                message = "❌ Venue is already booked/unavailable on " + date;
            } else {
                venue.getBlockedDates().add(date);
                venueRepo.save(venue);
                message = "✅ Successfully booked for " + date;
            }
        } else {
            message = "❌ Venue not found.";
        }

        model.addAttribute("message", message);
        model.addAttribute("venues", venueRepo.findAll());
        return "index";
    }

    @GetMapping("/block/{id}")
    public String showBlockPage(@PathVariable Long id, Model model) {
        Optional<Venue> optionalVenue = venueRepo.findById(id);
        if (optionalVenue.isPresent()) {
            model.addAttribute("venue", optionalVenue.get());
            return "block_dates"; // ✅ must match the template filename!
        }
        return "redirect:/admin";
    }

    @PostMapping("/block-date/{id}")
    public String blockDate(@PathVariable Long id, @RequestParam String date) {
        Optional<Venue> optionalVenue = venueRepo.findById(id);
        if (optionalVenue.isPresent()) {
            Venue venue = optionalVenue.get();
            List<String> blocked = venue.getBlockedDates();
            if (!blocked.contains(date)) {
                blocked.add(date);
                venue.setBlockedDates(blocked);
                venueRepo.save(venue);
            }
        }
        return "redirect:/block/" + id;
    }
    @PostMapping("/unblock-date/{id}")
    public String unblockDates(@PathVariable Long id, @RequestParam(name = "unblockDates", required = false) List<String> unblockDates) {
        Optional<Venue> optionalVenue = venueRepo.findById(id);
        if (optionalVenue.isPresent() && unblockDates != null) {
            Venue venue = optionalVenue.get();
            List<String> currentDates = venue.getBlockedDates();
            currentDates.removeAll(unblockDates);
            venue.setBlockedDates(currentDates);
            venueRepo.save(venue);
        }
        return "redirect:/block/" + id;
    }


    @PostMapping("/add")
    public String addVenue(@ModelAttribute Venue venue) {
        venueRepo.save(venue);
        return "redirect:/";
    }
}
