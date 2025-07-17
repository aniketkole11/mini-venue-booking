package com.eazyvenue.venuebooking.controller;

import com.eazyvenue.venuebooking.entity.Venue;
import com.eazyvenue.venuebooking.repository.VenueRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/venues")
@CrossOrigin("*")
public class VenueController {

    @Autowired
    private VenueRepository venueRepo;

    // 1. Get all venues
    @GetMapping
    public List<Venue> getAllVenues() {
        return venueRepo.findAll();
    }

    // 2. Add a new venue
    @PostMapping
    public Venue addVenue(@RequestBody Venue venue) {
        return venueRepo.save(venue);
    }

    // 3. Block a date
    @PutMapping("/{id}/block")
    public Venue blockDate(@PathVariable Long id, @RequestParam String date) {
        Optional<Venue> optionalVenue = venueRepo.findById(id);
        if (optionalVenue.isPresent()) {
            Venue venue = optionalVenue.get();
            List<String> blocked = venue.getBlockedDates();
            if (!blocked.contains(date)) {
                blocked.add(date);
            }
            venue.setBlockedDates(blocked);
            return venueRepo.save(venue);
        }
        return null;
    }

    // 4. Unblock a date
    @PutMapping("/{id}/unblock")
    public Venue unblockDate(@PathVariable Long id, @RequestParam String date) {
        Optional<Venue> optionalVenue = venueRepo.findById(id);
        if (optionalVenue.isPresent()) {
            Venue venue = optionalVenue.get();
            venue.getBlockedDates().remove(date);
            return venueRepo.save(venue);
        }
        return null;
    }

    // 5. Book a venue (if not blocked)
    @PostMapping("/{id}/book")
    public String bookVenue(@PathVariable Long id, @RequestParam String date) {
        Optional<Venue> optionalVenue = venueRepo.findById(id);
        if (optionalVenue.isPresent()) {
            Venue venue = optionalVenue.get();
            if (venue.getBlockedDates().contains(date)) {
                return "Venue not available on this date.";
            }
            venue.getBlockedDates().add(date);
            venueRepo.save(venue);
            return "Venue booked for " + date;
        }
        return "Venue not found.";
    }
}

