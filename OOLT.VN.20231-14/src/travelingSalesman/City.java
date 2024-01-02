package travelingSalesman;

public class City {
    private int x;
    private int y;
    private final String name;

    public City(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }
    public double measureDistance(City city){
        double kcX = city.getX() - this.getX();
        double kcY = city.getY() - this.getY();
        return Math.sqrt(kcX * kcX + kcY * kcY );
    }
    @Override
    public String toString(){
        return this.getName();
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
}
