package org.example.domain;

import java.util.HashMap;
import java.util.Map;

public class Pojo {

    @MapWithKeys({"foo", "bar"})
    private Map<String, @Hex(length = 10) String> map = new HashMap<>();

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(final Map<String, String> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return String.format("%s[map=%s]", getClass().getSimpleName(), map);
    }
}
