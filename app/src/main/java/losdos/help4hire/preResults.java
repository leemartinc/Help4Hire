package losdos.help4hire;

public class preResults {
    private String serviceProvider;
    private String serviceRate;
    private String serviceDescription;
    private String serviceName;

    public preResults(){
        //empty constructor needed
    }

    public preResults(String serviceProvider, String serviceRate, String serviceDescription, String serviceName){
        this.serviceRate = serviceRate;
        this.serviceProvider = serviceProvider;
        this.serviceDescription = serviceDescription;
        this.serviceName = serviceName;


    }

    public String getServiceProvider() {
        return serviceProvider;
    }

    public String getServiceRate() {
        return serviceRate;
    }

    public String getServiceDescription(){ return serviceDescription;}

    public String getServiceName() {
        return serviceName;
    }
}
