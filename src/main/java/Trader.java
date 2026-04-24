import java.util.ArrayList;
import java.util.List;

public interface Trader {
    double capital = 1000.00;

    public void setup();

    public void buy(Stock stock);
    public void sell(Stock stock);

    public void act();
}
