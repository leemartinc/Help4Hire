package losdos.help4hire;

public class ProviderOptionsDB {
    private String requestUser;
    private String requestService;
    private String requestStatus;
    private String totalCost;



    public ProviderOptionsDB (){
        //empty constructor needed
    }

    public ProviderOptionsDB (String requestUser, String requestService, String requestStatus, String totalCost){
        this.requestUser = requestUser;
        this.requestService = requestService;
        this.requestStatus = requestStatus;
        this.totalCost = totalCost;
    }

    public String getRequestUser() {
        return requestUser;
    }

    public String getRequestService() {
        return requestService;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public String getTotalCost() {
        return totalCost;
    }
}
