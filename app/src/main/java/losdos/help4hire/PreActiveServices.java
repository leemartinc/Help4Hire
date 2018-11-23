package losdos.help4hire;

public class PreActiveServices {
    private String requestProvider;
    private String requestService;
    private String serviceStatus;
    private String totalCost;



    public PreActiveServices(){
        //empty constructor needed
    }

    public PreActiveServices(String requestProvider, String requestService, String serviceStatus, String totalCost){
        this.requestProvider = requestProvider;
        this.requestService = requestService;
        this.serviceStatus = serviceStatus;
        this.totalCost = totalCost;
    }

    public String getRequestProvider() {
        return requestProvider;
    }

    public String getRequestService() {
        return requestService;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public String getTotalCost() {
        return totalCost;
    }
}
