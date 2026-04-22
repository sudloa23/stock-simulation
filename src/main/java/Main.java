import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Simulation simulation = new Simulation();
        PaintArea paintArea = new PaintArea(simulation);
        Frame frame = new Frame(paintArea);
        frame.setVisible(true);

    }
}