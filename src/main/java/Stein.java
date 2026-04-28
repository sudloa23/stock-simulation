import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Stein implements Trader{
    private double capital = 1000.00;
    private String[] names = {"APPLE", "DATADOG", "MICROSOFT", "NVIDIA", "PALANTIR", "TESLA"};
    private HashMap<Stock, Integer> wallet = new HashMap<>();
    private List<Stock> stocks = new ArrayList<>();
    private float value = 0.0f;
    private int x, y;

    public Stein(List<Stock> stocks, int x, int y){
        this.x = x;
        this.y = y;
        this.stocks = stocks;

        for(int i = 0; i < names.length; i++){
            wallet.put(this.stocks.get(i), 0);
        }
    }

    @Override
    public void setup(){
        float min = 1000.00f;
        for(int i = 0; i < stocks.size(); i++){
            if(stocks.get(i).getCurrentPrice() < min){
                min = stocks.get(i).getCurrentPrice();
            }
        }

        while(capital >= min){
            Stock random = stocks.get((int) (Math.random()*6));
            if(random.getCurrentPrice() <= capital){
                buy(random);
                wallet.merge(random, 1, Integer::sum);
                System.out.println("bought " + random.getName().toUpperCase());
            }
        }

    }

    @Override
    public void buy(Stock stock){
        if(capital >= stock.getCurrentPrice()){
            capital -= stock.getCurrentPrice();
//            wallet.merge(stock.getName(), 1, Integer::sum);
        }else{
            System.out.println("Stein tried to buy " + stock.getName() + " with insufficient capital");
        }
    }

    @Override
    public void sell(Stock stock){
        if(wallet.get(stock.getName()) > 0){
            capital += stock.getCurrentPrice();
            //wallet.merge(stock.getName(), -1, Integer::sum);
        }else{
            System.out.println("Stein tried to sell " + stock.getName() + " without owning any");
        }
    }

    @Override
    public void simulate(){

    }

    public void updateValue(){
        Collection<Integer> tempValues = wallet.values();
        Collection<Stock> tempStocks = wallet.keySet();
        List<Integer> amount = new ArrayList<>();
        value = 0;

        for(Integer i : tempValues){
            amount.add(i.intValue());
        }

        int i = 0;
        for(Stock s : tempStocks){
            value += s.getCurrentPrice() * amount.get(i);
            i++;
        }
    }

    public void draw(Graphics2D g2d){
        updateValue();
        g2d.drawString("stein stock values: " + value, x, y);
    }
}
