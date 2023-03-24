package org.example.domain;

public class Location {
    private final double lat;
    private final double lon;

    public Location(final double lat, final double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    @Override
    public String toString() {
        return String.format("%s[lat=%s, lon=%s]", getClass().getSimpleName(), lat, lon);
    }
}
