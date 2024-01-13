/**
 * @project tsp_solver.TSPSolver
 * @author Mitchell Vu
 * @since December 2023
 */

package tsp_solver.drivers.graph;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import tsp_solver.traveling_salesman.Settings;

import java.net.URL;
import java.util.ResourceBundle;

public class GraphController implements Initializable {
    private final Number[] cityChoices = {10, 15, 20, 25, 30, 40, 50};
    private final Number[] populationChoices = {5, 10, 15, 20, 30, 40, 50};
    private final Number[] generationChoices = {30, 50, 100, 200, 300, 500, 800, 100};
    private final Number[] tournamentSelectionChoices = {2, 3, 4, 5, 6, 7, 8, 9, 10};
    private final Number[] eliteRouteChoices = {0, 1, 2, 3, 4, 5, 6, 7, 8};

    @FXML
    private ChoiceBox<Number> cityQuantityChoiceBox;

    @FXML
    private ChoiceBox<Number> populationChoiceBox;

    @FXML
    private ChoiceBox<Number> eliteRouteChoiceBox;

    @FXML
    private ChoiceBox<Number> generationChoiceBox;

    @FXML
    private Slider mutationRateSlider;

    @FXML
    private ChoiceBox<Number> tournamentSelectionChoiceBox;

    @FXML
    private VBox centerVBox;

    @FXML
    private Label bestDistanceTotalLabel;

    @FXML
    private Label generationLabel;

    @FXML
    private Label highestFitnessLabel;

    private AnimationPane animationPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set default value according to Settings
        cityQuantityChoiceBox.getItems().addAll(cityChoices);
        for (Number cityChoice : cityChoices) {
            if (cityChoice.intValue() == Settings.NUM_OF_CITIES) {
                cityQuantityChoiceBox.setValue(cityChoice);
                break;
            }
        }
        cityQuantityChoiceBox.getSelectionModel().selectedItemProperty()
                .addListener((observableValue, prevValue, newValue) -> {
                    Settings.NUM_OF_CITIES = (int) newValue;
                });

        populationChoiceBox.getItems().addAll(populationChoices);
        for (Number populationChoice : populationChoices) {
            if (populationChoice.intValue() == Settings.POPULATION_SIZE) {
                populationChoiceBox.setValue(populationChoice);
                break;
            }
        }
        populationChoiceBox.getSelectionModel().selectedItemProperty()
                .addListener((observableValue, prevValue, newValue) -> {
                    Settings.POPULATION_SIZE = (int) newValue;
                });

        generationChoiceBox.getItems().addAll(generationChoices);
        for (Number generationChoice : generationChoices) {
            if (generationChoice.intValue() == Settings.GENERATION_LIMIT) {
                generationChoiceBox.setValue(generationChoice);
                break;
            }
        }
        generationChoiceBox.getSelectionModel().selectedItemProperty()
                .addListener((observableValue, prevValue, newValue) -> {
                    Settings.GENERATION_LIMIT = (int) newValue;
                });

        eliteRouteChoiceBox.getItems().addAll(eliteRouteChoices);
        for (Number eliteRouteChoice : eliteRouteChoices) {
            if (eliteRouteChoice.intValue() == Settings.NUM_OF_ELITE_ROUTES) {
                eliteRouteChoiceBox.setValue(eliteRouteChoice);
                break;
            }
        }
        eliteRouteChoiceBox.getSelectionModel().selectedItemProperty()
                .addListener((observableValue, prevValue, newValue) -> {
                    Settings.NUM_OF_ELITE_ROUTES = (int) newValue;
                });

        tournamentSelectionChoiceBox.getItems().addAll(tournamentSelectionChoices);
        for (Number tournamentSelectionChoice : tournamentSelectionChoices) {
            if (tournamentSelectionChoice.intValue() == Settings.TOURNAMENT_SELECTION_SIZE) {
                tournamentSelectionChoiceBox.setValue(tournamentSelectionChoice);
                break;
            }
        }
        tournamentSelectionChoiceBox.getSelectionModel().selectedItemProperty()
                .addListener((observableValue, prevValue, newValue) -> {
                    Settings.TOURNAMENT_SELECTION_SIZE = (int) newValue;
                });

        mutationRateSlider.setValue(Settings.MUTATION_RATE * 100);
        mutationRateSlider.valueProperty().addListener((observableValue, prevValue, newValue) -> {
            Settings.MUTATION_RATE = newValue.doubleValue() / 100;
        });

        // Add Pane to VBox
        this.animationPane = new AnimationPane(this);
        centerVBox.getChildren().add(this.animationPane);
    }

    @FXML
    public void handleStart(ActionEvent actionEvent) {
        this.animationPane.thread.stop();
        this.animationPane.startAnimation();
    }

    @FXML
    public void handleRandom(ActionEvent actionEvent) {
        this.animationPane.thread.stop();
        this.animationPane.randomCities();
    }

    public void setBestDistanceTotalLabel(String label) {
        bestDistanceTotalLabel.setText(label);
    }

    public void setGenerationLabel(String label) {
        generationLabel.setText(label);
    }

    public void setHighestFitnessLabel(String label) {
        highestFitnessLabel.setText(label);
    }
}
