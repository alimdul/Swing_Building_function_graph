package view;

import controller.CoordinateController;
import model.Coordinate;

import javax.swing.*;
import java.util.ArrayList;

public class Table {

    private CoordinateController coordinateController;

    private TableModel model = new TableModel();
    private JTable table = new JTable(model);
    private JScrollPane jsp = new JScrollPane(table);

    public Table(CoordinateController coordinateController) {
        this.coordinateController = coordinateController;
    }

    public JScrollPane getJsp()
    {
        return jsp;
    }

    public void createTable() {

        model.deleteCoordinates();

        for (int i = 0; i < coordinateController.size(); i++) {
            model.addCoordinate(coordinateController.getCoordinate(i));
        }
    }




}
