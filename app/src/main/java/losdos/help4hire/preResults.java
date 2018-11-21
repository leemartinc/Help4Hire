package losdos.help4hire;

public class preResults {
    private String serviceName;
    private String providerLoc;
    private int providerRating;
    private int abbrevCost;

    public preResults(){
        //empty constructor needed
    }

    public preResults(String serviceName, String providerLoc, int providerRating, int abbrevCost){
        this.abbrevCost = abbrevCost;
        this.providerLoc = providerLoc;
        this.serviceName = serviceName;
        this.providerRating = providerRating;

    }

    public String getServiceName() {
        return serviceName;
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
