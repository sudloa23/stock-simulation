import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class PaintArea extends JPanel{
    private Simulation simulation;

    public PaintArea(Simulation simulation){
        super();

        this.simulation = simulation;
        setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        try {
            simulation.draw(g2d);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
