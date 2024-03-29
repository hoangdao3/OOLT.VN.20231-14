/**
 * @project TSPSolver
 * @author Mitchell Vu
 * @since January 2024
 */

package tsp_solver.drivers.graph;

import javafx.application.Platform;
import tsp_solver.genetic.GeneticAlgorithm;
import tsp_solver.genetic.Population;
import tsp_solver.traveling_salesman.City;
import tsp_solver.traveling_salesman.Settings;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GAThread implements Runnable {
    private final AnimationPane pane;
    private final DecimalFormat DF = new DecimalFormat("#.##");

    // Arraylist of cities, storing the first route.
    // The initial route contains cities with the same name as their index.
    public ArrayList<City> initialRoute;
    public Population population;
    public boolean running = false;
    private Thread thread;
    private int generationNumber = 1;

    public GAThread(AnimationPane pane) {
        this.pane = pane;
    }

    public void start() {
        if (population == null) return;
        generationNumber = 0;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        if (thread == null) return;
        running = false;
        thread.interrupt();
        thread = null;
    }

    @Override
    public void run() {
        if (!running) return;

        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(initialRoute);

        while (running && generationNumber < Settings.GENERATION_LIMIT + 1) {
            generationNumber++;
            population = geneticAlgorithm.evolve(population);
            population.sortRoutesByFitness();

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    pane.setGenerationLabel(String.valueOf(generationNumber));
                    pane.setBestDistanceTotalLabel(DF.format(population.getRoutes().get(0).totalDistance()));
                    pane.setHighestFitnessLabel(DF.format(population.getRoutes().get(0).getFitness()));

                    // Remove everything from the pane
                    pane.drawRouteAndCity();
                }
            });

            // Pause the thread for the specified delay
            try {
                Thread.sleep(Settings.DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        running = false;
    }
}