import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Simulation{
    private List<Stock> stocks = new ArrayList<>();
    private String[] names = {"Palantir", "NVIDIA", "Tesla", "Apple", "Microsoft", "Datadog"};
    private float[] prices = {
            145.89f,
            201.80f,
            392.50f,
            271.48f,
            418.07f,
            130.12f
    };

    private float[] drifts = {
            0.12f,
            0.18f,
            0.15f,
            0.08f,
            0.10f,
            0.14f
    };

    private float[] volatilities = {
            0.45f,
            0.50f,
            0.60f,
            0.22f,
            0.25f,
            0.40f
    };
    private Color[] colors = {
            new Color(205,	170,	125),
            new Color(118,	185,	0),
            new Color(232,30,37),
            new Color(162,	170,	173),
            new Color(0, 164,239),
            new Color(	99,	44,	166)
    };
    private int day = 1;
    private LocalDate start = LocalDate.of(2026, 1, 1), date;

    public Simulation() throws IOException {
         for(int i = 0; i < names.length; i++){
             stocks.add(new Stock(names[i], prices[i], drifts[i], volatilities[i], colors[i]));
             //System.out.println("added Stock: " + names[i] + " price: " + prices[i] + " drift: " + drifts[i] + " volatility: " + volatilities[i]);
         }
    }

    public void draw(Graphics2D g2d) throws IOException {
        for(int i = 0; i < stocks.size(); i++){
            stocks.get(i).draw(g2d, i*100 + 100);
            stocks.get(i).calculatePrice(date);
        }
        date = start.plusDays(day - 1);
        g2d.drawString(String.valueOf(date),50,50);
        day++;
    }
}
