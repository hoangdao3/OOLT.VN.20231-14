/**
 * @project TSPSolver
 * @author Mitchell Vu
 * @since January 2024
 */

package tsp_solver.drivers.graph;

import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import tsp_solver.genetic.Population;
import tsp_solver.traveling_salesman.City;
import tsp_solver.traveling_salesman.Settings;

import java.util.ArrayList;

public class AnimationPane extends Pane {
    private final GraphController graphController;
    public GAThread thread = new GAThread(this);

    public AnimationPane(GraphController graphController) {
        super();
        this.graphController = graphController;
        this.thread.initialRoute = new ArrayList<City>();

        VBox.setVgrow(this, Priority.ALWAYS);
        this.setStyle("-fx-background-color: black;");
        this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    }

    public void startAnimation() {
        if (!thread.initialRoute.isEmpty()) {
            setGenerationLabel("0");
            setBestDistanceTotalLabel("0.00");
            setHighestFitnessLabel("0.00");

            thread.population = new Population(Settings.POPULATION_SIZE, thread.initialRoute);
            thread.population.sortRoutesByFitness();
            drawPopulation();
        }
        // Starts thread for the rest of the animation
        thread.start();
    }

    public void drawPopulation() {
        drawBestRoute();
        thread.population.getRoutes().forEach(x -> {
            ArrayList<City> route = x.getCities();
            drawRoute(route, Color.rgb(51, 56, 63), 1);
        });
        drawBestRoute();
    }

    private void drawBestRoute() {
        drawRoute(thread.population.getRoutes().get(0).getCities(), Color.BLUE, 3);
    }

    private void drawRoute(ArrayList<City> route, Color color, int strokeWidth) {
        City currentCity;
        City nextCity;

        for (int i = 0; i < route.size() - 1; i++) {
            currentCity = route.get(i);
            nextCity = route.get(i + 1);

            Line line = new Line(currentCity.getX(), currentCity.getY(), nextCity.getX(), nextCity.getY());
            line.setStroke(color);
            line.setStrokeWidth(strokeWidth);
            this.getChildren().add(line);
            line.toBack();
        }

        currentCity = route.get(route.size() - 1);
        nextCity = route.get(0);

        Line line = new Line(currentCity.getX(), currentCity.getY(), nextCity.getX(), nextCity.getY());
        this.getChildren().add(line);
        line.toBack();
    }

    public void randomCities() {
        thread.population = null;
        thread.initialRoute.clear();

        double height = this.getHeight() - 40;
        double width = this.getWidth() - 40;

        for (int i = 0; i < Settings.NUM_OF_CITIES; i++) {
            String name = Integer.toString(i);
            int x = (int) (Math.random() * width) + 20;
            int y = (int) (Math.random() * height) + 20;
            thread.initialRoute.add(new City(x, y, name));
        }

        drawRouteAndCity();
    }

    protected void drawRouteAndCity() {
        this.getChildren().clear();

        if (this.thread.population != null) {
            if (this.thread.running) drawPopulation();
            else drawBestRoute();
        }

        for (City city : thread.initialRoute) {
            int x = city.getX();
            int y = city.getY();

            Circle circle = new Circle(x - 2, y - 2, 4, Color.WHITE);
            this.getChildren().add(circle);
        }
    }

    public void setBestDistanceTotalLabel(String label) {
        graphController.setBestDistanceTotalLabel(label);
    }

    public void setGenerationLabel(String label) {
        graphController.setGenerationLabel(label);
    }

    public void setHighestFitnessLabel(String label) {
        graphController.setHighestFitnessLabel(label);
    }
}
