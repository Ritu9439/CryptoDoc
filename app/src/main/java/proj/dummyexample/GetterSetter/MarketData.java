package proj.dummyexample.GetterSetter;

/**
 * Created by sai on 29/8/17.
 */

public class MarketData {
    String market,buy,sell,url;

    public MarketData(String market, String buy, String sell, String url) {
        this.market = market;
        this.buy = buy;
        this.sell = sell;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public MarketData() {
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getBuy() {
        return buy;
    }

    public void setBuy(String buy) {
        this.buy = buy;
    }

    public String getSell() {
        return sell;
    }

    public void setSell(String sell) {
        this.sell = sell;
    }
}
