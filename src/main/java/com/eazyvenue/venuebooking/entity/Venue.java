package com.eazyvenue.venuebooking.entity;
import jakarta.persistence.*;
import java.util.List;
@Entity
public class Venue 
{ 	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String location;

    private int capacity;

    private String imageUrl;

    @ElementCollection
    private List<String> blockedDates;
    

    public Venue() {}

    public Venue(String name, String location, int capacity, String imageUrl) {
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.imageUrl = imageUrl;
    }
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<String> getBlockedDates() {
		return blockedDates;
	}

	public void setBlockedDates(List<String> blockedDates) {
		this.blockedDates = blockedDates;
	}
	

	

}
