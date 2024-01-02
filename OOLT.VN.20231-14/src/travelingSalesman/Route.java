package travelingSalesman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class Route {
    private boolean isFitnessChanged = true;
    private double fitness = 0;
    private ArrayList<City> cityList = new ArrayList<City>();
    public Route(GeneticAlgorithms geneticAlgorithms){
        geneticAlgorithms.getInitialRoute().forEach(x -> cityList.add(null));
    }
    public Route(ArrayList<City> cityList){
        this.cityList.addAll(cityList);
        Collections.shuffle(this.cityList);
    }
    public double getFitness(){
        if(isFitnessChanged){
            fitness = (1 / totalDistance()) * 1000;
            return fitness;
        }
        return fitness;
    }
    public double totalDistance(){
        int numsOfCities = this.cityList.size();
        int count = 0;
        for(int i = 0; i < numsOfCities; i++){
            double returnValue = 0;
            City currentCity = this.cityList.get(i);
            if(i < numsOfCities - 1){
                City nextCity = this.cityList.get(i + 1);
                returnValue = currentCity.measureDistance(nextCity);
            }
            count += returnValue;
        }
        City firstCity = this.cityList.get(0);
        City lastCity = this.cityList.get(numsOfCities - 1);
        count += firstCity.measureDistance(lastCity);
        return count;
    }
    public String toString(){
        return Arrays.toString(cityList.toArray());
    }
    public ArrayList<City> getCityList(){
        isFitnessChanged = true;
        return cityList;
    }
}
