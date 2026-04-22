import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Random;

public class Stock{
    private float startPrice, expectedDrift, volatility, randomShock, currentPrice, time = 0.0f, lastPrice, exponent, expression1, expression2, dT = 0.00369f;
    private String name;
    private Random random = new Random();
    private Color color;
    private int height;
    Path path;
    private FileWriter fw;



    public Stock(String name, float startVal, float expectedDrift, float volatility, Color color) throws IOException {
        this.name = name;
        this.startPrice = startVal;
        this.expectedDrift = expectedDrift;
        this.volatility = volatility;

        currentPrice = startPrice;
        lastPrice = startPrice;

        this.color = color;

        path = Paths.get("data", name + ".csv");
        Files.createDirectories(path.getParent());
        fw = new FileWriter(path.toFile(), false);
        fw.write("2026-01-01" + "," + currentPrice + "\n");
        fw.close();
    }

    public void calculatePrice(LocalDate date) throws IOException {
        fw = new FileWriter(path.toFile(), true);
        time += dT;

        lastPrice = currentPrice;

        expression1 = (float) (expectedDrift - (Math.pow(volatility, 2) / 2)) * dT;


        expression2 = (float) (volatility * Math.sqrt(dT) * random.nextGaussian());

        exponent = expression1 + expression2;

        currentPrice = (float) (lastPrice * Math.pow(2.718281828459, exponent));

        fw.write(String.valueOf(date) + "," + currentPrice + "\n");
        fw.close();
    }

    public void draw(Graphics2D g2d, int x){
        g2d.setColor(color);
        height = (int) (currentPrice / 6);
        g2d.fillRect(x, 720 - height, 80, height);
        g2d.setColor(Color.BLACK);
        g2d.drawString(String.valueOf(currentPrice), x, 730);
        g2d.drawString(name, x, 750);
    }
}
