import drivers.console.ConsoleDriver;
import drivers.graphic.GraphicsDriver;

import java.io.IOException;

public class TPSSolver {
    public static void main(String[] args) throws IOException {
        // GUI with controllers and animated panel
        new GraphicsDriver();

        // Console Driver
        ConsoleDriver consoleDriver = new ConsoleDriver("src/data/cities.txt");
        consoleDriver.print();
    }
}