import java.util.*;

class StockMarket {
    private Map<String, Double> stocks = new HashMap<>();
    private Random random = new Random();

    public StockMarket() {
        stocks.put("AAPL", 150.0);
        stocks.put("GOOG", 2800.0);
        stocks.put("TSLA", 700.0);
        stocks.put("AMZN", 3300.0);
    }

    public void updatePrices() {
        for (String stock : stocks.keySet()) {
            double change = -5 + (10 * random.nextDouble());
            double newPrice = stocks.get(stock) + change;
            stocks.put(stock, Math.round(newPrice * 100.0) / 100.0);
        }
    }

    public void displayMarketData() {
        System.out.println("\n📊 MARKET DATA");
        for (String stock : stocks.keySet()) {
            System.out.println(stock + " : $" + stocks.get(stock));
        }
    }

    public double getPrice(String stock) {
        return stocks.getOrDefault(stock, 0.0);
    }

    public boolean isValidStock(String stock) {
        return stocks.containsKey(stock);
    }

    public Map<String, Double> getStocks() {
        return stocks;
    }
}

class Trader {
    private double balance;
    private Map<String, Integer> portfolio = new HashMap<>();
    private List<Double> portfolioHistory = new ArrayList<>();

    public Trader(double balance) {
        this.balance = balance;
    }

    public void buy(String stock, double price, int qty) {
        double cost = price * qty;
        if (cost > balance) {
            System.out.println("❌ Not enough balance!");
            return;
        }
        balance -= cost;
        portfolio.put(stock, portfolio.getOrDefault(stock, 0) + qty);
        System.out.println("✅ Bought " + qty + " shares of " + stock);
    }

    public void sell(String stock, double price, int qty) {
        if (!portfolio.containsKey(stock) || portfolio.get(stock) < qty) {
            System.out.println("❌ Not enough shares to sell!");
            return;
        }
        portfolio.put(stock, portfolio.get(stock) - qty);
        balance += price * qty;
        System.out.println("✅ Sold " + qty + " shares of " + stock);
    }

    public void displayPortfolio(StockMarket market) {
        System.out.println("\n📁 PORTFOLIO");
        double totalValue = 0;

        for (String stock : portfolio.keySet()) {
            int qty = portfolio.get(stock);
            double value = qty * market.getPrice(stock);
            totalValue += value;
            System.out.println(stock + " : " + qty + " shares | Value: $" + value);
        }

        double netWorth = balance + totalValue;
        portfolioHistory.add(netWorth);

        System.out.println("💵 Cash Balance: $" + balance);
        System.out.println("📈 Total Stock Value: $" + totalValue);
        System.out.println("💰 Net Worth: $" + netWorth);
    }

    public void showPerformanceHistory() {
        System.out.println("\n📉 PORTFOLIO PERFORMANCE OVER TIME");
        for (int i = 0; i < portfolioHistory.size(); i++) {
            System.out.println("Step " + (i + 1) + " : $" + portfolioHistory.get(i));
        }
    }
}

public class StockTradingSimulator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StockMarket market = new StockMarket();
        Trader trader = new Trader(10000);

        while (true) {
            market.updatePrices();
            market.displayMarketData();

            System.out.println("\nMENU");
            System.out.println("1. Buy Stock");
            System.out.println("2. Sell Stock");
            System.out.println("3. View Portfolio");
            System.out.println("4. View Portfolio Performance");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();

            if (choice == 1) {
                System.out.print("Enter stock symbol: ");
                String stock = sc.next().toUpperCase();
                System.out.print("Enter quantity: ");
                int qty = sc.nextInt();

                if (market.isValidStock(stock)) {
                    trader.buy(stock, market.getPrice(stock), qty);
                } else {
                    System.out.println("❌ Invalid stock!");
                }

            } else if (choice == 2) {
                System.out.print("Enter stock symbol: ");
                String stock = sc.next().toUpperCase();
                System.out.print("Enter quantity: ");
                int qty = sc.nextInt();

                if (market.isValidStock(stock)) {
                    trader.sell(stock, market.getPrice(stock), qty);
                } else {
                    System.out.println("❌ Invalid stock!");
                }

            } else if (choice == 3) {
                trader.displayPortfolio(market);

            } else if (choice == 4) {
                trader.showPerformanceHistory();

            } else if (choice == 5) {
                System.out.println("👋 Exiting trading platform.");
                break;

            } else {
                System.out.println("❌ Invalid option!");
            }
        }
        sc.close();
    }
}
