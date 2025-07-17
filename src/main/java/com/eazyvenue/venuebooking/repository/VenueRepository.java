package com.eazyvenue.venuebooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eazyvenue.venuebooking.entity.Venue;

@Repository
public interface VenueRepository  extends JpaRepository<Venue, Long>{

}
