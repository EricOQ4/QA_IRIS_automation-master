package pageobjects.admin.morningCoffeePage;

/**
 * Created by noelc on 2017-01-17.
 */
public enum Sector {
    FINANCIALS("Financials"),UTILITIES("Utilities"),TECHNOLOGY("Technology"),CONSUMER_STAPLES("Consumer Staples"),INDUSTRIALS("Industrials"),MATERIALS("Materials"),CONSUMER_DISCRETIONARY("Consumer Discretionary"),ENERGY
            ("Energy"),REAL_ESTATE("Real Estate"),HEALTH_CARE("Health Care"),OTHER("Other");

    private final String name;

   Sector(String name){
       this.name=name;
   }
    public String getSector(){
        return this.name;
    }
}
