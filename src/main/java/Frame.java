import javax.swing.*;

public class Frame extends JFrame{
    private PaintArea paintArea;
    private int width = 800, height = 800;
    private String title = "Simulation";

    public Frame(PaintArea paintArea){
        this.paintArea = paintArea;

        setSize(width, height);
        setTitle(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(paintArea);

        Timer timer = new Timer(20, e -> paintArea.repaint());
        timer.start();
    }
}
