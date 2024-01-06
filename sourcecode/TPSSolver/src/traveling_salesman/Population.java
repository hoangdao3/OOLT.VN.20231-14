package traveling_salesman;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class Population {
    private ArrayList<Route> routes = new ArrayList<>(Settings.POPULATION_SIZE);// 1

    public Population(int populationSize, GeneticAlgorithm geneticAlgorithm) {
        IntStream.range(0, populationSize).forEach(x -> routes.add(new Route(geneticAlgorithm.getInitialRoute())));
    }

    public Population(int populationSize, ArrayList<City> cities) {
        IntStream.range(0, populationSize).forEach(x -> routes.add(new Route(cities)));
    }

    public void sortRoutesByFitness() {
        routes.sort((route1, route2) -> {
            int flag = 0;
            if (route1.getFitness() > route2.getFitness())
                flag = -1;
            else if (route1.getFitness() < route2.getFitness())
                flag = 1;
            return flag;
        });
    }

    public ArrayList<Route> getRoutes() {
        return routes;
    }
}
