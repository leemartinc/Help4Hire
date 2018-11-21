package losdos.help4hire;

public class preResults {
    private String serviceProvider;
    private String serviceRate;

    public preResults(){
        //empty constructor needed
    }

    public preResults(String serviceProvider, String serviceRate){
        this.serviceRate = serviceRate;
        this.serviceProvider = serviceProvider;

    }

    public String getServiceProvider() {
        return serviceProvider;
    }

    public String getServiceRate() {
        return serviceRate;
    }
}
