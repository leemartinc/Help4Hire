package losdos.help4hire;

public class preResults {
    private String providerName;
    private String providerLoc;
    private int providerRating;
    private int abbrevCost;

    public preResults(){
        //empty constructor needed
    }

    public preResults(String providerName, String providerLoc, int providerRating, int abbrevCost){
        this.abbrevCost = abbrevCost;
        this.providerLoc = providerLoc;
        this.providerName = providerName;
        this.providerRating = providerRating;

    }

    public String getProviderName() {
        return providerName;
    }

    public String getProviderLoc() {
        return providerLoc;
    }

    public int getProviderRating() {
        return providerRating;
    }

    public int getAbbrevCost() {
        return abbrevCost;
    }
}
