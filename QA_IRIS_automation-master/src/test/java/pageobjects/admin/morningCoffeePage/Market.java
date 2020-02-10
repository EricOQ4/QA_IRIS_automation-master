package pageobjects.admin.morningCoffeePage;

/**
 * Created by noelc on 2017-01-17.
 */
public enum Market {
    US("United States"),CANADA("Canada"),UK("United Kingdom");

    private final String name;

    Market(String name){
        this.name=name;
    }

    public String getMarket(){
        return this.name;
    }
}
