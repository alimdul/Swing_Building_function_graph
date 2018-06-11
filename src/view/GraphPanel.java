package view;

import controller.CoordinateController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class GraphPanel extends JComponent implements MouseWheelListener, MouseMotionListener, MouseListener {

    public static final double SCALE_STEP = 0.1d;
    public static final double PERCENT = 100;
    public static final double NULL = 0;

    CoordinateController coordinateController;
    MainFrame mainFrame;

    private Dimension initialSize = new Dimension(600,600);
    private double zoom = 1.0;
    int valueOfDivision = 65;

    double x = initialSize.getWidth();
    double y = initialSize.getHeight();

    private Point origin;
    private double previousZoom = zoom;
    AffineTransform tx = new AffineTransform();
    private double scrollX = 0d;
    private double scrollY = 0d;

    public GraphPanel(double zoom, CoordinateController coordinateController, MainFrame mainFrame) {

        this.coordinateController = coordinateController;
        this.zoom = zoom;
        this.mainFrame = mainFrame;

        addMouseWheelListener(this);
        addMouseMotionListener(this);
        addMouseListener(this);
        setAutoscrolls(true);
    }

    public Dimension getInitialSize() {
        return initialSize;
    }

    @Override
    public void paintComponent(Graphics g) {

        Graphics2D segment = (Graphics2D) g.create();
        segment.transform(tx);
        segment.setColor(Color.BLACK);
        drawAxis(segment);
        drawValueOfDivision(segment);

        segment.setColor(Color.GREEN);

        for (int i = 1; i < coordinateController.size(); i++) {

            double x1 = (double) coordinateController.getCoordinate(i).getValue(0);
            double x2 = (double) coordinateController.getCoordinate(i-1).getValue(0);
            double y1 = (double) coordinateController.getCoordinate(i).getValue(1);
            double y2 = (double) coordinateController.getCoordinate(i-1).getValue(1);

            segment.draw(new Line2D.Double((x1+x/2)* zoom,(y/2 - y1)* zoom,(x2+x/2)* zoom,(y/2 - y2)* zoom));
        }
        segment.dispose();
    }

    private void drawValueOfDivision(Graphics2D segment) {
        int temporaryValue = valueOfDivision;
        for (double i = x/2; i<= x; i+=valueOfDivision) {
            segment.draw(new Line2D.Double(i* zoom, (y/2-6)* zoom, i* zoom, (y/2+6)* zoom));
            if (i!=x/2) {
                segment.drawString(String.valueOf(temporaryValue), (int) (i* zoom), (int) ((y/2+20)* zoom));
                temporaryValue+=valueOfDivision;
            }
        }

        temporaryValue = valueOfDivision;
        for (double i = x/2; i>= 0; i-=valueOfDivision) {
            segment.draw(new Line2D.Double(i* zoom, (y/2-6)* zoom, i* zoom, (y/2+6)* zoom));
            if (i!=x/2) {
                segment.drawString(String.valueOf(-temporaryValue), (int) (i* zoom), (int) ((y/2+20)* zoom));
                temporaryValue+=valueOfDivision;
            }
        }

        temporaryValue = valueOfDivision;
        for (double i = y/2; i>= 0; i-=valueOfDivision) {
            segment.draw(new Line2D.Double((x/2-6)* zoom, i* zoom, (x/2+6)* zoom, i* zoom));
            if (i!=y/2) {
                segment.drawString(String.valueOf(temporaryValue), (int) ((x/2-40)* zoom), (int) (i* zoom));
                temporaryValue+=valueOfDivision;
            }
        }

        temporaryValue = valueOfDivision;
        for (double i = y/2; i<= y; i+=valueOfDivision) {
            segment.draw(new Line2D.Double((x/2-6)* zoom, i* zoom, (x/2+6)* zoom, i* zoom));
            if (i!=y/2) {
                segment.drawString(String.valueOf(-temporaryValue), (int) ((x/2-40)* zoom), (int) (i* zoom));
                temporaryValue+=valueOfDivision;
            }
        }
    }

    private void drawAxis(Graphics2D segment) {
        segment.draw(new Line2D.Double(x/2* zoom,0,x/2* zoom,y* zoom));
        segment.draw(new Line2D.Double((x/2-6)* zoom,14* zoom,x/2* zoom,0));
        segment.draw(new Line2D.Double((x/2+6)* zoom,14* zoom,x/2* zoom,0));

        segment.draw(new Line2D.Double(0,y/2* zoom,x* zoom,y/2* zoom));
        segment.draw(new Line2D.Double((x-14)* zoom,(y/2-6)* zoom,x* zoom,y/2* zoom));
        segment.draw(new Line2D.Double((x-14)* zoom,(y/2+6)* zoom,x* zoom,y/2* zoom));

        segment.drawString("X", (int) ((x-15)* zoom),(int) ((y/2+20)* zoom));
        segment.drawString("Y", (int) ((x/2-20)* zoom), (int)(15* zoom));
        segment.drawString("0", (int) ((x/2-20)* zoom),(int) ((y/2+20)* zoom));
    }

    @Override
    public void setSize(Dimension size) {
        super.setSize(size);
        if (initialSize == null) {
            this.initialSize = size;
        }
    }

    @Override
    public void setPreferredSize(Dimension preferredSize) {
        super.setPreferredSize(preferredSize);
        if (initialSize == null) {
            this.initialSize = preferredSize;
        }
    }

    public void mouseWheelMoved(MouseWheelEvent e) {

        int temp = 0;

        if ((e.getModifiers() & KeyEvent.CTRL_MASK) != NULL) {

            if ((initialSize.width) * zoom >= x && (initialSize.height) * zoom >= y) {

                double zoomFactor = -SCALE_STEP * e.getPreciseWheelRotation() * zoom;
                zoom = Math.abs(zoom + zoomFactor);

                Rectangle realView = getVisibleRect();
                Dimension d = new Dimension((int) (initialSize.width * zoom), (int) (initialSize.height * zoom));
                setPreferredSize(d);
                setSize(d);
                validate();
                followMouseOrCenter(e);

                temp = (int) ((initialSize.getHeight() * zoom) / ((initialSize.getHeight())) * 10 * zoom);
                mainFrame.label.setText(String.valueOf(temp) + "%");

                previousZoom = zoom;

            }
            else {

                followMouseOrCenter(e);
                zoom = 1.0;
            }
        }
    }

    public void followMouseOrCenter(MouseWheelEvent e) {

        Point2D point = e.getPoint();
        Rectangle visibleRect = getVisibleRect();

        scrollX = point.getX()/previousZoom*zoom - (point.getX()-visibleRect.getX());
        scrollY = point.getY()/previousZoom*zoom - (point.getY()-visibleRect.getY());

        visibleRect.setRect(scrollX, scrollY, visibleRect.getWidth(), visibleRect.getHeight());
        scrollRectToVisible(visibleRect);
    }

    public void mouseDragged(MouseEvent e) {

        if (origin != null) {

            int deltaX = origin.x - e.getX();
            int deltaY = origin.y - e.getY();
            Rectangle view = getVisibleRect();
            Dimension size = getSize();
            view.x += deltaX;
            view.y += deltaY;
            scrollRectToVisible(view);
        }
    }

    public void newDim() {
        Dimension d = new Dimension(
                (int) (initialSize.width * zoom),
                (int) (initialSize.height * zoom));
        setPreferredSize(d);
        setSize(d);
    }

    public void DimPlusY() {

        initialSize.height = initialSize.height + valueOfDivision;
        y = initialSize.height;
        newDim();
    }

    public void DimMinusY() {

        initialSize.height = initialSize.height + valueOfDivision;
        y = initialSize.height;
        newDim();
    }

    public void DimMinusX() {

        initialSize.width = initialSize.width + valueOfDivision;
        x = initialSize.width;
        newDim();
    }

    public void DimPlusX() {

        initialSize.width = initialSize.width + valueOfDivision;
        x = initialSize.width;
        newDim();
    }

    public int percent(double zoom) {
        return (int) ((initialSize.getHeight() / (initialSize.getHeight() * zoom)) * PERCENT);
    }

    public void mousePressed(MouseEvent e) {
        origin = new Point(e.getPoint());
    }

    public void mouseMoved(MouseEvent e) {

    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {
    }


}
