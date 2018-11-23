package losdos.help4hire;

public class PreActiveServices {
    private String serviceProvider;
    private String serviceRate;

    public PreActiveServices(){
        //empty constructor needed
    }

    public PreActiveServices(String serviceProvider, String serviceRate){
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
