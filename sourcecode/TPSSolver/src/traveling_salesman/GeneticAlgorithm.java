package traveling_salesman;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class GeneticAlgorithm {
    private final ArrayList<City> initialRoute;

    public GeneticAlgorithm(ArrayList<City> initialRoute) {
        this.initialRoute = initialRoute;
    }

    public ArrayList<City> getInitialRoute() {
        return initialRoute;
    }

    public Population evolve(Population population) {
        return mutatePopulation(crossoverPopulation(population));
    }

    private Population mutatePopulation(Population population) {
        population.getRoutes().stream().filter(x -> population.getRoutes().indexOf(x) >= Settings.NUM_OF_ELITE_ROUTES).forEach(x -> mutateRoute(x));
        return population;
    }

    private void mutateRoute(Route route) {
        route.getCityList().stream().filter(x -> Math.random() < Settings.MUTATION_RATE).forEach(cityX -> {
            int y = (int) (route.getCityList().size() * Math.random());
            City cityY = route.getCityList().get(y);
            route.getCityList().set(route.getCityList().indexOf(cityX), cityY);
            route.getCityList().set(y, cityX);
        });
    }

    private Population crossoverPopulation(Population population) {
        Population crossoverPopulation = new Population(population.getRoutes().size(), this);
        IntStream.range(0, Settings.NUM_OF_ELITE_ROUTES).forEach(x -> crossoverPopulation.getRoutes().set(x, population.getRoutes().get(x)));
        IntStream.range(Settings.NUM_OF_ELITE_ROUTES, crossoverPopulation.getRoutes().size()).forEach(x -> {
            Route route1 = tournamentSelection(population).getRoutes().get(0);
            Route route2 = tournamentSelection(population).getRoutes().get(0);
            crossoverPopulation.getRoutes().set(x, crossoverRoute(route1, route2));
        });
        return crossoverPopulation;
    }

    private Population tournamentSelection(Population population) {
        Population tournamentPopulation = new Population(Settings.TOURNAMENT_SELECTION_SIZE, this);
        IntStream.range(0, Settings.TOURNAMENT_SELECTION_SIZE).forEach(x -> {
            int randomIndex = (int) (Math.random() * population.getRoutes().size());
            Route selectedRoute = population.getRoutes().get(randomIndex);
            tournamentPopulation.getRoutes().set(x, selectedRoute);
        });
        tournamentPopulation.sortRoutesByFitness();
        return tournamentPopulation;
    }

    private Route crossoverRoute(Route route1, Route route2) {
        Route crossoverRoute = new Route(this);
        Route tempRoute1 = route1;
        Route tempRoute2 = route2;
        if (Math.random() < 0.5) {
            tempRoute1 = route2;
            tempRoute2 = route1;
        }
        for (int x = 0; x < crossoverRoute.getCityList().size() / 2; x++) {
            crossoverRoute.getCityList().set(x, tempRoute1.getCityList().get(x));
        }
        return fillNulls(crossoverRoute, tempRoute2);
    }

    private Route fillNulls(Route crossoverRoute, Route route) {
        route.getCityList().stream().filter(x -> !crossoverRoute.getCityList().contains(x)).forEach(cityX -> {
            for (int y = 0; y < route.getCityList().size(); y++) {
                if (crossoverRoute.getCityList().get(y) == null) {
                    crossoverRoute.getCityList().set(y, cityX);
                    break;
                }
            }
        });
        return crossoverRoute;
    }
}
