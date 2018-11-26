package losdos.help4hire;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

public class ActiveServicesAdapter extends FirestoreRecyclerAdapter<PreActiveServices, ActiveServicesAdapter.ActiveServicesHolder> {
public static  String providerFullName;
public static String serviceTitle;
public static double lat;
public static double lng;
//public static GeoPoint latlng;

    public ActiveServicesAdapter(@NonNull FirestoreRecyclerOptions<PreActiveServices> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ActiveServicesHolder holder, int position, @NonNull PreActiveServices model) {

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference UserReference = firestore.collection("users");
        if (model.getRequestProvider() != null) {
            UserReference.document(model.getRequestProvider()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    if (task.isSuccessful()) {
//                        GeoPoint latlng;
                        DocumentSnapshot documentSnapshot = task.getResult();


                        String providerFirstName = documentSnapshot.getString("firstName");
                        String providerLastName = documentSnapshot.getString("lastName");
                        providerFullName = providerFirstName + " " + providerLastName;

                        holder.textViewRequestProvider.setText(providerFullName);

                    } else {
                        holder.textViewRequestProvider.setText("ERROR");
                    }
                }
            });
        } else {
            holder.textViewRequestProvider.setText("ERROR");
        }

        CollectionReference serviceReference = firestore.collection("services");

        if(model.getRequestService() != null){
        serviceReference.document(model.getRequestService()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();

                    serviceTitle = documentSnapshot.getString("serviceName");

                    holder.textViewRequestServiceTitle.setText(serviceTitle);

                } else {
                    holder.textViewRequestServiceTitle.setText("ERROR");
                }

            }
        });

    }else {
            holder.textViewRequestServiceTitle.setText("ERROR");
        }
                if(model.getServiceStatus() != null) {
                    holder.textViewRequestStatus.setText(model.getServiceStatus());

                    switch (model.getServiceStatus().toUpperCase()) {
                        case "ACTIVE":
                            holder.locationBtn.setVisibility(View.VISIBLE);
                            holder.messagesBtn.setVisibility(View.VISIBLE);
                            break;
                        case "PENDING":
                            holder.cancelBtn.setVisibility(View.VISIBLE);
                            break;
                        case "CLOSED":
                            holder.reviewBtn.setVisibility(View.VISIBLE);
                            holder.hireBtn.setVisibility(View.VISIBLE);
                            break;
                    }

                }else{holder.textViewRequestStatus.setText("ERROR");}

                if(model.getServiceStatus() != null) {
                        holder.textViewTotalCost.setText("Cost: $" + model.getTotalCost());

                    }else{ holder.textViewTotalCost.setText("ERROR");}

                    if(model.getProviderLocation() != null){
                    CollectionReference requestsReference = firestore.collection("requests");
//        requestsReference.document(model.getProviderLocation()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        requestsReference.document(String.valueOf(model.getProviderLocation())).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                GeoPoint latlng;

                                if (task.isSuccessful()) {
                                    DocumentSnapshot documentSnapshot = task.getResult();
                                    System.out.println(documentSnapshot.getString("providerLocation"));
//                                    Log.d("help4hire5", String.valueOf(documentSnapshot));
//                                    latlng = documentSnapshot.getGeoPoint("providerLoation");
//                                    Log.d("help4hire5", String.valueOf(documentSnapshot.getData()));
//                                    if (latlng != null){
//                                        Log.d("help4hire5", "LATLNG IS NOT NULL");
//                                    } else {
//                                        Log.d("help4hire5", "LATLNG IS NULLLLLLLL");
//
//                                    }
                                } else{
                                Log.d("help4hire", "TASK WAS NOT SUCCESSFUL");}
                            }

                        });




                } else {
            Log.d("help4hire", "GETLOCATION WAS NULLLLLLL");
                    }
                }

    @NonNull
    @Override
    public ActiveServicesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final Bundle bundle = new Bundle();


        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.active_services_item,
                viewGroup, false);

        Button reviewButton = v.findViewById(R.id.btnReview);
        reviewButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                bundle.putString("reviewee", providerFullName);
                bundle.putString("title", serviceTitle);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                ReviewActivity reviewFrag = new ReviewActivity();
                reviewFrag.setArguments(bundle);
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, reviewFrag)
                        .addToBackStack("nextFragment")
                        .commit();
            }
        });

        Button locationButton = v.findViewById(R.id.btnLocation);
        locationButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

//                bundle.putAll("loc", loc);
                bundle.putDouble("lat", lat);
                bundle.putDouble("lng", lng);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                MapsActivity mapsFrag = new MapsActivity();
                mapsFrag.setArguments(bundle);
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, mapsFrag)
                        .addToBackStack("nextFragment")
                        .commit();
            }
        });

        return new ActiveServicesHolder(v);
    }

    class ActiveServicesHolder extends RecyclerView.ViewHolder{

        TextView textViewRequestProvider;
        TextView textViewRequestServiceTitle;
        TextView textViewRequestStatus;
        TextView textViewTotalCost;

        Button messagesBtn;
        Button locationBtn;

        Button cancelBtn;

        Button reviewBtn;
        Button hireBtn;

        public ActiveServicesHolder(@NonNull View itemView) {
            super(itemView);

            textViewRequestProvider = itemView.findViewById(R.id.provider_name);
            textViewRequestServiceTitle = itemView.findViewById(R.id.provider_service_title);
            textViewRequestStatus = itemView.findViewById(R.id.provider_service_status);
            textViewTotalCost = itemView.findViewById(R.id.provider_service_cost);


            messagesBtn = itemView.findViewById(R.id.btnMessage);
            locationBtn = itemView.findViewById(R.id.btnLocation);

            cancelBtn = itemView.findViewById(R.id.btnCancel);

            reviewBtn = itemView.findViewById(R.id.btnReview);
            hireBtn = itemView.findViewById(R.id.btnHireAgain);
            
        }
    }
}
