package tsp_solver;

import tsp_solver.drivers.console.ConsoleDriver;
import tsp_solver.drivers.graphic.GraphicsDriver;

import java.io.IOException;

public class TSPSolver {
    public static void main(String[] args) throws IOException {
        // GUI with controllers and animated panel
        new GraphicsDriver();

        // Console Driver
        ConsoleDriver consoleDriver = new ConsoleDriver("src/tsp_solver/data/cities.txt");
        consoleDriver.print();
    }
}