package view;

import controller.CoordinateController;
import controller.Function;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainFrame {

    private CoordinateController coordinateController;

    private JFrame mainFrame = new JFrame();
    private String title;
    private Dimension d;

    private Table table;

    private JLabel parametrALabel = new JLabel("Введите параметр a");
    private JTextField parametrA = new JTextField(15);
    private JLabel diapasonXLabel = new JLabel("Введите диапазон x");
    private JTextField lowerValueX = new JTextField(15);
    private JLabel hyphen = new JLabel("-----");
    private JTextField upperValueX = new JTextField(15);
    private JButton createGraph = new JButton("Построить график");
    private JPanel panel = new JPanel();

    private GraphPanel graphPanel;
    private JScrollPane jspGraph;

    public JLabel label = new JLabel("0"+"%");

    public MainFrame (String title, Dimension d, CoordinateController coordinateController) {

        this.title = title;
        this.d = d;
        this.coordinateController = coordinateController;
        table = new Table(coordinateController);

        graphPanel = new GraphPanel(1.0, coordinateController, MainFrame.this);
        jspGraph = new JScrollPane(graphPanel);
        graphPanel.setPreferredSize(graphPanel.getInitialSize());

        jspGraph.getHorizontalScrollBar().addMouseListener(new MouseGraphMouseListener());
        jspGraph.getVerticalScrollBar().addMouseListener(new MouseGraphMouseListener());

    }

    public void init () {

        mainFrame.setTitle(title);
        mainFrame.setSize(d);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);

        createGraph.addActionListener(new CreateGraphActionListener());

        table.getJsp().setPreferredSize(new Dimension(300,300));

        panel.setLayout(new GridBagLayout());
        panel.add(parametrALabel, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.CENTER, new Insets(2, 2, 2, 2), 0, 0));
        panel.add(parametrA, new GridBagConstraints(1, 0, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        panel.add(diapasonXLabel, new GridBagConstraints(0, 1, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.CENTER, new Insets(2, 2, 2, 2), 0, 0));
        panel.add(lowerValueX, new GridBagConstraints(1, 1, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        panel.add(hyphen, new GridBagConstraints(2, 1, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.CENTER, new Insets(2, 2, 2, 2), 0, 0));
        panel.add(upperValueX, new GridBagConstraints(3, 1, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
        panel.add(createGraph, new GridBagConstraints(0, 2, 4, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));

        jspGraph.setPreferredSize(new Dimension(600,600));
        mainFrame.add(jspGraph, BorderLayout.EAST);

        mainFrame.add(table.getJsp(),BorderLayout.WEST);
        mainFrame.add(panel,BorderLayout.SOUTH);

        mainFrame.add(label, BorderLayout.NORTH);

        mainFrame.setVisible(true);
        mainFrame.pack();
    }

    public String getParametrA () {
        return parametrA.getText();
    }

    public String getUpperValueX () {
        return upperValueX.getText();
    }

    public String getLowerValueX () {
        return lowerValueX.getText();
    }

    public void update() {
        table.createTable();
        mainFrame.repaint();
    }

    public class CreateGraphActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            Function function = new Function(MainFrame.this,coordinateController);
            new Thread(function).start();
        }
    }

    public class MouseGraphMouseListener implements MouseListener{
        public void actionPerformed(MouseEvent e) {

        }

        @Override
        public void mouseClicked(MouseEvent e) {

            if(jspGraph.getHorizontalScrollBar().getValue() == 0) {
                graphPanel.DimMinusX();
            }

            if(jspGraph.getHorizontalScrollBar().getValue() + jspGraph.getHorizontalScrollBar().getSize().getWidth() == jspGraph.getHorizontalScrollBar().getMaximum()) {
                graphPanel.DimPlusX();
            }

            if(jspGraph.getVerticalScrollBar().getValue() == 0) {
                graphPanel.DimPlusY();
            }

            if(jspGraph.getVerticalScrollBar().getValue() + jspGraph.getVerticalScrollBar().getSize().getHeight() == jspGraph.getVerticalScrollBar().getMaximum()) {
                graphPanel.DimMinusY();
            }

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }


}

