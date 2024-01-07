package tsp_solver.traveling_salesman;

import tsp_solver.genetic.GeneticAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Route {
    private boolean isFitnessChanged = true;
    private double fitness = 0;
    private final ArrayList<City> cities = new ArrayList<City>();

    public Route(GeneticAlgorithm geneticAlgorithm) {
        geneticAlgorithm.getInitialRoute().forEach(x -> cities.add(null));
    }

    public Route(ArrayList<City> cities) {
        this.cities.addAll(cities);
        Collections.shuffle(this.cities);
    }

    public double getFitness() {
        if (isFitnessChanged) {
            fitness = (1 / totalDistance()) * 1000;
            return fitness;
        }
        return fitness;
    }

    public double totalDistance() {
        int numsOfCities = this.cities.size();
        int count = 0;
        for (int i = 0; i < numsOfCities; i++) {
            double returnValue = 0;
            City currentCity = this.cities.get(i);
            if (i < numsOfCities - 1) {
                City nextCity = this.cities.get(i + 1);
                returnValue = currentCity.measureDistance(nextCity);
            }
            count += (int) returnValue;
        }
//        System.out.println(this.cities);
        City firstCity = this.cities.get(0);
        City lastCity = this.cities.get(numsOfCities - 1);
        count += (int) firstCity.measureDistance(lastCity);
        return count;
    }

    public String toString() {
        return Arrays.toString(cities.toArray());
    }

    public ArrayList<City> getCities() {
        isFitnessChanged = true;
        return cities;
    }
}
