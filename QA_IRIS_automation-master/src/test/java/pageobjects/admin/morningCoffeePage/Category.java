package pageobjects.admin.morningCoffeePage;

/**
 * Created by noelc on 2017-01-17.
 */
public enum Category {
    MARKET("Market"),SECTOR("Sector");
    private final String name;


    Category(String name){
        this.name=name;
    }

    public String getCategory(){
        return this.name;
    }

}
