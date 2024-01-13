package tsp_solver;

import javafx.application.Application;
import tsp_solver.drivers.console.ConsoleDriver;
import tsp_solver.drivers.graph.Graph;

import java.io.IOException;

public class TSPSolver {
    public static void main(String[] args) throws IOException {
        // GUI with controllers and animated panel
        Application.launch(Graph.class, args);

        // Console Driver
        ConsoleDriver consoleDriver = new ConsoleDriver("src/tsp_solver/data/cities.txt");
        consoleDriver.print();
    }
}