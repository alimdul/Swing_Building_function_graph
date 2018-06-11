package model;

import java.util.ArrayList;

public class CoordinateBase {

    public ArrayList<Coordinate> coordinates = new ArrayList<>();

    public void add(Coordinate coordinate) {
        coordinates.add(coordinate);
    }

    public void delete(int index) {
        coordinates.remove(index);
    }

    public int size() {
        return coordinates.size();
    }

    public Coordinate get(int index) {
        return coordinates.get(index);
    }

    public void deleteAll()
    {
        coordinates.clear();
    }
}
