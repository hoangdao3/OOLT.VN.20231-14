/**
 * @project tsp_solver.TSPSolver
 * @author Mitchell Vu
 * @since December 2023
 */

package tsp_solver.drivers.graph;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Objects;

public class Graph extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("Graph.fxml")));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setTitle("Traveling Salesman Solver");
        stage.setScene(scene);
        stage.show();
    }
}
