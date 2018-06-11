package model;

public class Coordinate {

    private double xValue;
    private double yValue;

    public Coordinate(double xValue, double yValue) {
        this.xValue = xValue;
        this.yValue = yValue;
    }

    //public Coordinate() {}

    public void setXValue(double xValue) { this.xValue = xValue; }
    public void setYValue(double yValue) { this.yValue = yValue; }

    public double getXValue() { return xValue; }
    public double getYValue() { return yValue; }

    public Object getValue(int columnIndex) {
        switch (columnIndex)
        {
            case 0: return xValue;
            case 1: return yValue;
        }
        return null;
    }
}
