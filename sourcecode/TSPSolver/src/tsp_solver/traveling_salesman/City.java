package tsp_solver.traveling_salesman;

public class City {
    private final String name;
    private int x;
    private int y;

    public City(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public double measureDistance(City city) {
        double kcX = city.getX() - this.getX();
        double kcY = city.getY() - this.getY();
        return Math.sqrt(kcX * kcX + kcY * kcY);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
