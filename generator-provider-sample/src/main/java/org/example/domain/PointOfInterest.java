package org.example.domain;

public class PointOfInterest {

    private String description;
    private Location location;

    public String getDescription() {
        return description;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return String.format("%s[description=%s, location=%s]",
                getClass().getSimpleName(), description, location);
    }
}
