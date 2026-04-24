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
        fw = new FileWriter(path.toFile(), false);
        fw.write("2026-01-01" + "," + currentPrice + "\n");
        fw.close();
    }

    public void calculatePrice(LocalDate date) throws IOException {
        fw = new FileWriter(path.toFile(), true);
        time += deltaT;

        double mu = expectedDrift;
        double sigma = volatility;
        double dt = deltaT;

        marketRegime += (random.nextGaussian() * 0.01);
        marketRegime = Math.max(0.5, Math.min(2.0, volatilityState));

        volatilityState += (random.nextGaussian() * 0.05);
        volatilityState = Math.max(0.5, Math.min(2.0, volatilityState));

        shock = 0.0;
        if(random.nextDouble() < 0.02){
            shock = random.nextGaussian() * 0.1;
        }

        lastPrice = currentPrice;

        double driftTerm = (mu * marketRegime - 0.5 * sigma * sigma * volatilityState) * dt;
        double diffusionTerm = sigma * volatilityState * Math.sqrt(dt) * random.nextGaussian();

        double exponent = driftTerm + diffusionTerm + shock;

        currentPrice = (float) (lastPrice * Math.exp(exponent));

//        expression1 = (float) (expectedDrift - (Math.pow(volatility, 2) / 2)) * deltaT;
//
//        expression2 = (float) (volatility * Math.sqrt(deltaT) * random.nextGaussian());
//
//        exponent = expression1 + expression2;
//
//        currentPrice = (float) (lastPrice * Math.pow(2.718281828459, exponent));

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

    public float getCurrentPrice(){
        return currentPrice;
    }

    public String getName(){
        return name.toUpperCase();
    }


}
