module TSPSolver {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.swing;
    requires java.naming;

    opens tsp_solver.drivers.graph;
}