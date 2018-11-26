package losdos.help4hire;

import com.google.firebase.firestore.GeoPoint;

public class PreActiveServices {
    private String requestProvider;
    private String requestService;
    private String serviceStatus;
    private String totalCost;
    private GeoPoint location;



    public PreActiveServices(){
        //empty constructor needed
    }

    public PreActiveServices(String requestProvider, String requestService, String serviceStatus, String totalCost, GeoPoint location){
        this.requestProvider = requestProvider;
        this.requestService = requestService;
        this.serviceStatus = serviceStatus;
        this.totalCost = totalCost;
        this.location = location;
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

    public GeoPoint getLocation() {
        return location;
    }
}
