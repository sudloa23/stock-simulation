import java.awt.*;
import java.util.*;
import java.util.List;

public class Stein implements Trader{
    private double capital = 1000.00;
    private String[] names = {"APPLE", "DATADOG", "MICROSOFT", "NVIDIA", "PALANTIR", "TESLA"};
    private HashMap<Stock, Integer> wallet = new HashMap<>();
    private List<Stock> stocks = new ArrayList<>();
    private float value = 0.0f;
    private int x, y;
    public float initialPrice = 0.0f;

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
                initialPrice += random.getCurrentPrice();
            }
        }

    }

    @Override
    public void buy(Stock stock){
        if(capital >= stock.getCurrentPrice()){
            System.out.println("Stein bought " + stock.getName() + " capital before " + capital + " capital after " + (capital - stock.getCurrentPrice()));
            capital -= stock.getCurrentPrice();
            wallet.merge(stock, 1, Integer::sum);
        }else{
            System.out.println("Stein tried to buy " + stock.getName() + " with insufficient capital: " + capital);
        }
    }

    @Override
    public void sell(Stock stock){
        if(wallet.get(stock.getName()) > 0){
            capital += stock.getCurrentPrice();
            wallet.merge(stock, -1, Integer::sum);
            System.out.println("Stein sold " + stock.getName() + " and made " + stock.getCurrentPrice());
        }else{
            System.out.println("Stein tried to sell " + stock.getName() + " without owning any");
        }
    }

    @Override
    public void simulate(){
        Collection<Integer> tempValues = wallet.values();
        List<Stock> tempStock = new ArrayList<>(wallet.keySet());
        List<Integer> amount = new ArrayList<>();

        for(int i = 0; i < tempStock.size(); i++){
            if(tempStock.get(i).getPercentage() > 0.25){
                sell(tempStock.get(i));
            }else if(tempStock.get(i).getPercentage() < -0.25){
                buy(tempStock.get(i));
            }
        }
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
        g2d.drawString("stein: ", x, y);
        g2d.drawString("stock value: " + value, x, y + 30);
        g2d.drawString("stocks: ", x, y+60);
        List<Stock> tempList = new ArrayList<>(wallet.keySet());
        List<Integer> tempNums = new ArrayList<>(wallet.values());
        for(int i = 0; i < wallet.size(); i++){
            g2d.drawString(tempList.get(i).getName() + ": " + tempNums.get(i),x +40, y+60 + (30*i));
        }
    }
}
