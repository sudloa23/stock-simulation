import java.util.HashMap;

public class Stein implements Trader{
    private double capital = 1000.00;
    private String[] names = {"APPLE", "DATADOG", "MICROSOFT", "NVIDIA", "PALANTIR", "TESLA"};
    private HashMap<String, Integer> wallet = new HashMap<>();

    public Stein(){
        for(int i = 0; i < names.length; i++){
            wallet.put(names[i], 0);
        }
    }

    @Override
    public void setup(){

    }

    @Override
    public void buy(Stock stock){
        if(capital >= stock.getCurrentPrice()){
            capital -= stock.getCurrentPrice();
            wallet.merge(stock.getName(), 1, Integer::sum);
        }else{
            System.out.println("Stein tried to buy " + stock.getName() + " with insufficient capital");
        }
    }

    @Override
    public void sell(Stock stock){
        if(wallet.get(stock.getName()) > 0){
            capital += stock.getCurrentPrice();
            wallet.merge(stock.getName(), -1, Integer::sum);
        }else{
            System.out.println("Stein tried to sell " + stock.getName() + " without owning any");
        }
    }

    @Override
    public void simulate(){

    }
}
