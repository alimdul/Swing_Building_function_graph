package view;

import model.Coordinate;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class TableModel extends AbstractTableModel {

    public static final int XVALUE = 0;
    public static final int YVALUE = 1;

    private int columnCount = 2;

    private List<Coordinate> listData;

    public TableModel () { this.listData = new ArrayList<Coordinate>(); }

    public String getColumnName (int columnIndex) {
        switch (columnIndex) {
            case XVALUE: return "Значение x";
            case YVALUE: return "Значение y";
        }
        return "";
    }

    public void addCoordinate(Coordinate coordinate) {
        listData.add(coordinate);
        fireTableDataChanged();
    }

    public int getRowCount () {
        return listData.size();
    }

    public int getColumnCount () {
        return columnCount;
    }

    public Object getValueAt (int rowIndex, int columnIndex) {
        Coordinate rows = listData.get(rowIndex);
        return rows.getValue(columnIndex);
    }

    public void deleteCoordinates() {
        listData.clear();
        fireTableDataChanged();
    }


}