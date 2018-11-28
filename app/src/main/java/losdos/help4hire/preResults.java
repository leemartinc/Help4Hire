package losdos.help4hire;

public class preResults {
    private String serviceProvider;
    private String serviceRate;
    private String serviceDescription;

    public preResults(){
        //empty constructor needed
    }

    public preResults(String serviceProvider, String serviceRate, String serviceDescription){
        this.serviceRate = serviceRate;
        this.serviceProvider = serviceProvider;
        this.serviceDescription = serviceDescription;

    }

    public String getServiceProvider() {
        return serviceProvider;
    }

    public String getServiceRate() {
        return serviceRate;
    }

    public String getServiceDescription(){ return serviceDescription;}
}
