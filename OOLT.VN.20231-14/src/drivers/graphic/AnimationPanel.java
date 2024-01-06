package drivers.graphic;

import travelingSalesman.City;
import travelingSalesman.Population;
import travelingSalesman.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;

// AnimationPanel is the black field where the animation occurs
public class AnimationPanel extends JPanel {
    // Integer representing how many cities has been selected
    public int count;
    // Runnable thread that controls the genetic algorithm
    public GeneticAlgorithmThread geneticAlgorithmThread = new GeneticAlgorithmThread(this);
    // Format pattern for rounding doubles
    private DecimalFormat DF = new DecimalFormat("#.###");
    private Graphics2D g2D;
    private MouseAdapter mouseAdapter;
    // Boolean value indicating whether selection is in progress
    private boolean isSelecting;
    // Stroke for the blue line, representing the best available route
    private Stroke stroke = new BasicStroke(3);

    public AnimationPanel() {
                this.geneticAlgorithmThread.initialRoute = new ArrayList<City>();
        this.setBackground(Color.BLACK);
        this.setVisible(true);
    }

    // StartAnimation method is called whenever the "Start" button is pressed.
    public void startAnimation() {
        // Everytime the panel is resized, the cities coordinates with routes will
        // update.
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateCityPositions();
            }
        });
        // Check whether the initialRoute is selected, if so, the animation will start.
        if (!geneticAlgorithmThread.initialRoute.isEmpty()) {
            geneticAlgorithmThread.population = new Population(Settings.POPULATION_SIZE,
                    geneticAlgorithmThread.initialRoute);
            geneticAlgorithmThread.population.sortRoutesByFitness();
            drawPopulation();
            GraphicsDriver.fitnessLabel
                    .setText("Highest Fitness :"
                            + DF.format(geneticAlgorithmThread.population.getRoutes().get(0).getFitness()));
            GraphicsDriver.totalDistanceLabel
                    .setText(" Best total distance: "
                            + geneticAlgorithmThread.population.getRoutes().get(0).totalDistance());
        }
        // Starts thread for the rest of the animation
        geneticAlgorithmThread.start();
    }

    // Takes all routes from population to draw them.
    public void drawPopulation() {
        drawBestRoute();
        g2D.setColor(Color.DARK_GRAY);
        geneticAlgorithmThread.population.getRoutes().forEach(x -> {
            ArrayList<City> route = x.getCityList();
            drawRoute(route);
        });
        drawBestRoute();
    }

    // Draw the best route available - represented by the blue line.
    private void drawBestRoute() {
        g2D.setColor(Color.BLUE);
        g2D.setStroke(stroke);
        drawRoute(geneticAlgorithmThread.population.getRoutes().get(0).getCityList());
        g2D.setStroke(new BasicStroke(0));
        g2D.setColor(Color.WHITE);
    }

    // DrawRoute will draw single route passed as an argument by connecting two
    // cities in array.
    private void drawRoute(ArrayList<City> route) {
        City currentCity;
        City nextCity;
        for (int i = 0; i < route.size() - 1; i++) {
            currentCity = route.get(i);
            nextCity = route.get(i + 1);
            g2D.drawLine(currentCity.getX(), currentCity.getY(), nextCity.getX(), nextCity.getY());
        }
        currentCity = route.get(route.size() - 1);
        nextCity = route.get(0);
        g2D.drawLine(currentCity.getX(), currentCity.getY(), nextCity.getX(), nextCity.getY());
    }

    // SelectCities method is called whenever the "Select" button is pressed.
    // It allows the user to click at the AnimationPanel and select own cities.
    // And it's more fun
    public void selectCities() {
        geneticAlgorithmThread.population = null;
        Settings.POPULATION_SIZE = 0;
        GraphicsDriver.populationNumber.setText(Integer.toString(Settings.POPULATION_SIZE));
        GraphicsDriver.settingsPanel.repaint();
        count = 0;
        geneticAlgorithmThread.initialRoute.clear();
        repaint();
        // IsSelecting indicates if there is already another mouseListener.
        if (isSelecting) {
            // If yes, the mouseListener resets.
            if (mouseAdapter != null) {
                this.removeMouseListener(mouseAdapter);
            }
            mouseAdapter = new MouseAdapter() {
                // Record all clicks in the JPanel and save the coordinates to the initialRoute.
                @Override
                public void mousePressed(MouseEvent e) {
                    if (isSelecting) {
                        int x = e.getX();
                        int y = e.getY();
                        String name = Integer.toString(count);
                        // Set padding 5px, so all the dots are fully visible
                        // That means the user can't select city that would lay on the panel's border
                        int padding = 5;
                        int panelWidth = getWidth();
                        int panelHeight = getHeight();

                        if (x < padding) {
                            x = padding;
                        } else if (x > panelWidth - padding) {
                            x = panelWidth - padding;
                        }
                        if (y < padding) {
                            y = padding;
                        } else if (y > panelHeight - padding) {
                            y = panelHeight - padding;
                        }
                        // Updates the number of selected cities and adds the new city to initialRoute
                        geneticAlgorithmThread.initialRoute.add(new City(x, y, name));
                        count++;
                        Settings.POPULATION_SIZE = count;
                        GraphicsDriver.populationNumber.setText(Integer.toString(Settings.POPULATION_SIZE));
                        GraphicsDriver.settingsPanel.repaint();
                        repaint();
                    }
                }
            };
            this.addMouseListener(mouseAdapter);
        } else {
            isSelecting = true;
            this.removeMouseListener(mouseAdapter);
        }
    }

    // RandomCities method is called whenever the "Random" button is pressed.
    // It randomly generates cities coordinates in the JPanel with 20px padding.
    public void randomCities() {
        geneticAlgorithmThread.population = null;
        geneticAlgorithmThread.initialRoute.clear();
        repaint();
        int height = this.getHeight() - 40;
        int width = this.getWidth() - 40;
        for (int i = 0; i < Settings.NUM_OF_CITIES; i++) {
            String name = Integer.toString(i);
            int coordinateX = (int) (Math.random() * width) + 20;
            int coordinateY = (int) (Math.random() * height) + 20;
            geneticAlgorithmThread.initialRoute.add(new City(coordinateX, coordinateY, name));
        }
    }

    // After the JPanel is resized, this method updates the location of all cities.
    // (So the routes will also be updated)
    // Adds an offset of 20px to ensure all the dots are visible.
    private void updateCityPositions() {
        if (geneticAlgorithmThread.initialRoute.isEmpty()) {
            return;
        }
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (City city : geneticAlgorithmThread.initialRoute) {
            int x = city.getX();
            int y = city.getY();
            minX = Math.min(minX, x);
            minY = Math.min(minY, y);
            maxX = Math.max(maxX, x);
            maxY = Math.max(maxY, y);
        }
        int rangeX = maxX - minX;
        int rangeY = maxY - minY;

        double scaleX = (double) (panelWidth - 2 * 20) / rangeX;
        double scaleY = (double) (panelHeight - 2 * 20) / rangeY;

        for (City city : geneticAlgorithmThread.initialRoute) {
            int x = city.getX();
            int y = city.getY();
            city.setX((int) ((x - minX) * scaleX + 20));
            city.setY((int) ((y - minY) * scaleY + 20));
        }
        repaint();
    }

    // This method is responsible for drawing the animation elements.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        this.g2D = (Graphics2D) g;

        if (geneticAlgorithmThread.population != null) {
            if (geneticAlgorithmThread.running)
                drawPopulation();
            else {
                drawBestRoute();
            }
        }
        for (City city : geneticAlgorithmThread.initialRoute) {
            int x = city.getX();
            int y = city.getY();
            g.fillOval(x - 4, y - 4, 8, 8);
        }
    }

    // setter for isSelecting variable
    public void setIsSelecting(boolean b) {
        this.isSelecting = b;
    }
}
