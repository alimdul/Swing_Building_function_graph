package controller;

import model.Coordinate;
import view.MainFrame;

import javax.swing.*;

public class Function implements Runnable{

    public static final double EPS = 0.0001;

    private MainFrame mainFrame;
    private CoordinateController coordinateController;

    public Function (MainFrame mainFrame,CoordinateController coordinateController) {
        this.coordinateController=coordinateController;
        this.mainFrame = mainFrame;
    }

    public void run() {

        coordinateController.deleteAllCoordinates();

        if (mainFrame.getParametrA().equals("") || mainFrame.getLowerValueX().equals("") || mainFrame.getUpperValueX().equals(""))
            JOptionPane.showMessageDialog(new JFrame(), "Ошибка! Не все данные введены" );

        if (Double.parseDouble(mainFrame.getLowerValueX()) > Double.parseDouble(mainFrame.getUpperValueX())) {
            JOptionPane.showMessageDialog(new JFrame(), "Ошибка! Некоррекный диапазон данных" );
        }

        else {

            for (double i = Double.parseDouble(mainFrame.getLowerValueX()); i <= Double.parseDouble(mainFrame.getUpperValueX()); i += 0.1) {

                Coordinate coordinate = new Coordinate(i, sum(i));
                coordinateController.addCoordinateInCoordinateBase(coordinate);
                mainFrame.update();
                try {
                    Thread.sleep((long) 100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
         //   mainFrame.update();
            JOptionPane.showMessageDialog(new JFrame(), "Данные успешно добавлены");
        }
    }

    public double memberValue(long i, double a, double x) {
        return Math.pow(-1, i+1)*((Math.pow(2, 2*i+1)*Math.pow(a*x, 2*i))/fact(2*i));
    }

    public double fact(long number) {
        return (number == 0) ? 1 : number * fact(number - 1);
    }

    public double sum(double x) {

        double previous, current;
        double sum = 0;
        long i = 0;
        double a = Double.parseDouble(mainFrame.getParametrA());
        double tempValue;

        current = memberValue(i, a, x);
        sum += current;
        i++;
        do {
            previous = current;
            current = memberValue(i, a, x);
            sum += current;
            i++;
            tempValue = Math.abs(current - previous);
        } while (tempValue > EPS);

        return sum;
    }



}
