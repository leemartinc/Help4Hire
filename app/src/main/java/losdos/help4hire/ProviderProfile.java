package losdos.help4hire;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class ProviderProfile extends Fragment {

        View myView;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    CollectionReference userRef = firestore.collection("users");
    CollectionReference serviceRef = firestore.collection("services");
    String serviceDocID;
    String currentUserID;
    String providerUID;
    String name;
    String serviceName;
    String serviceRate;
    String serviceDesc;



    @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                Bundle bundle = this.getArguments();
                String fullName = bundle.getString("fullName");
                int rating = bundle.getInt("rating");
                 String serviceDescription = bundle.getString("serviceDescription");
                 providerUID = bundle.getString("providerUID");
                 serviceDocID = bundle.getString("serviceDocID");
                 serviceName = bundle.getString("serviceName");
                serviceRate = bundle.getString("serviceRate");
                serviceDesc = bundle.getString("serviceDescription");




        Log.d("help4hire5", "STRING FROM BUNDLE: " + fullName);
            Log.d("help4hire5", "STRING FROM BUNDLE: " + rating);
            Log.d("help4hire5", "STRING FROM BUNDLE: " + serviceDescription);
            System.out.println(providerUID);
        myView = inflater.inflate(R.layout.provider_profile, container, false);

        final android.support.v4.app.FragmentManager fm = getFragmentManager();



        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        currentUserID = currentUser.getUid();

            final TextView nameField = myView.findViewById(R.id.ProviderName);
            TextView serviceField = myView.findViewById(R.id.ServiceTitle);
            final TextView rateField = myView.findViewById(R.id.HourlyRate);
            final RatingBar ratingBar = myView.findViewById(R.id.ProviderRatingBar);
            TextView serviceDescriptionField = myView.findViewById(R.id.ProviderServiceDescription);
            Button hireBtn = myView.findViewById(R.id.HireMeBtn);

            serviceField.setText(serviceName);
            rateField.setText(serviceRate);
            serviceDescriptionField.setText(serviceDesc);

            /*

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference reference = firestore.collection("services");


        reference.document(serviceDocID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                Bundle bundle = new Bundle();
                bundle.putString("serviceDescription",documentSnapshot.getString("serviceDescription"));
                bundle.putString("serviceRate",documentSnapshot.getString("serviceRate"));
                bundle.putString("serviceName",documentSnapshot.getString("serviceName"));
                ProviderProfile profileFrag = new ProviderProfile();
                profileFrag.setArguments(bundle);

            }
        });
        */



        userRef.document(providerUID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    if(task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();

                        name = documentSnapshot.getString("firstName") +" "+ documentSnapshot.getString("lastName");
                        nameField.setText(name);
                        ratingBar.setRating(Float.parseFloat(documentSnapshot.getLong("rating").toString()));
                    }
                }
            });


                    final Map<String,Object> myMap = new HashMap<String,Object>();
                    //providerlcation
                    myMap.put("requestDate",new Date());
                    myMap.put("requestProvider",providerUID);
                    myMap.put("requestService",serviceDocID);
                    myMap.put("requestStatus","Pending");
                    myMap.put("requestUser",currentUserID);
                    myMap.put("serviceStatus","Pending");
                    myMap.put("totalCost","200");
                    myMap.put("totalHours","5");




            hireBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //create request

                    firestore.collection("requests").document()
                            .set(myMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getContext(), name + " has been hired. Awaiting Acceptance!", Toast.LENGTH_SHORT).show();
                            // This should go to the home screen if the user is succesfully entered
                            fm.beginTransaction().replace(R.id.content_frame
                                    ,new ActiveServicesActivity()).addToBackStack("Search")
                                    .commit();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Error!", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, e.toString());
                        }
                    });

                }
            });



        return myView;
    }

}
