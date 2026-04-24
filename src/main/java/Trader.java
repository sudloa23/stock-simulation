public interface Trader{

    public void setup();

    public void buy(Stock stock);
    public void sell(Stock stock);

    public void simulate();
}
