import controller.CoordinateController;
import model.CoordinateBase;
import view.MainFrame;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        CoordinateBase coordinateBase = new CoordinateBase();
        CoordinateController coordinateController = new CoordinateController(coordinateBase);

        MainFrame mf = new MainFrame("Построение графика функции", new Dimension(800,800), coordinateController);
        mf.init();
    }
}
