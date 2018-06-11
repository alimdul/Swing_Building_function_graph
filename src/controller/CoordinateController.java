package controller;

import model.Coordinate;
import model.CoordinateBase;

public class CoordinateController {

    private CoordinateBase coordinateBase;

    public CoordinateController (CoordinateBase coordinateBase) {
        this.coordinateBase = coordinateBase;
    }

    public CoordinateBase getCoordinateBase() {
        return coordinateBase;
    }

    public void addCoordinateInCoordinateBase (Coordinate coordinate) {
        coordinateBase.add(coordinate);
    }

    public Coordinate getCoordinate(int i) {
        return coordinateBase.get(i);
    }

    public void deleteCoordinate (int i) {
        coordinateBase.delete(i);
    }

    public int size()
    {
        return coordinateBase.size();
    }

    public void deleteAllCoordinates() {
        coordinateBase.deleteAll();
    }
}
