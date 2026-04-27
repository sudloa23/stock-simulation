import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Random;

public class Stock{
    private float startPrice, expectedDrift, volatility, randomShock, currentPrice, time = 0.0f, lastPrice, exponent, expression1, expression2, deltaT = 0.00369f;
    private String name;
    private Random random = new Random();
    private Color color;
    private int height;
    Path path;
    private FileWriter fw;

    double dT = deltaT;
    double sigma = volatility;
    double mu = expectedDrift;
    double marketRegime = 1.0, volatilityState = 1.0, shock = 0.0;


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
    }

    public void calculatePrice(LocalDate date) throws IOException {
        fw = new FileWriter(path.toFile(), true);
        double mu = expectedDrift;
        double sigma = volatility;
        double dt = deltaT;

        marketRegime += random.nextGaussian() * 0.01;
        marketRegime = Math.max(0.5, Math.min(2.0, marketRegime));

        volatilityState += (1.0 - volatilityState) * 0.05
                + random.nextGaussian() * 0.02;
        volatilityState = Math.max(0.5, Math.min(2.0, volatilityState));

        double shock = 0.0;
        if (random.nextDouble() < 0.02) {
            shock = random.nextGaussian() * sigma * 0.5;
        }

        double momentum = (lastPrice - lastPrice) / lastPrice;

        double trueValue = 100;
        double meanReversion = (trueValue - lastPrice) * 0.001;

        double driftTerm = (mu * marketRegime
                + momentum * 0.5
                + meanReversion
                - 0.5 * sigma * sigma) * dt;

        double diffusionTerm = sigma * volatilityState
                * Math.sqrt(dt)
                * random.nextGaussian();

        double exponent = driftTerm + diffusionTerm + shock;

        currentPrice = (float) (lastPrice * Math.exp(exponent));
        fw.write(String.valueOf(date) + "," + String.valueOf(currentPrice) + "\n");
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

    public float getCurrentPrice(){
        return currentPrice;
    }

    public String getName(){
        return name.toUpperCase();
    }


}
