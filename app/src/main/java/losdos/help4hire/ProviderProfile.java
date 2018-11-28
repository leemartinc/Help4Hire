package losdos.help4hire;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class ProviderProfile extends Fragment {

        View myView;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    CollectionReference userRef = firestore.collection("users");
    CollectionReference serviceRef = firestore.collection("services");


    @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                Bundle bundle = this.getArguments();
                String fullName = bundle.getString("fullName");
                int rating = bundle.getInt("rating");
                 String serviceDescription = bundle.getString("serviceDescription");
                 String providerUID = bundle.getString("providerUID");
                Log.d("help4hire5", "STRING FROM BUNDLE: " + fullName);
            Log.d("help4hire5", "STRING FROM BUNDLE: " + rating);
            Log.d("help4hire5", "STRING FROM BUNDLE: " + serviceDescription);
            System.out.println(providerUID);
        myView = inflater.inflate(R.layout.provider_profile, container, false);

            final TextView nameField = myView.findViewById(R.id.ProviderName);
            TextView serviceField = myView.findViewById(R.id.ServiceTitle);
            final TextView rateField = myView.findViewById(R.id.HourlyRate);
            final RatingBar ratingBar = myView.findViewById(R.id.ProviderRatingBar);
            TextView serviceDescriptionField = myView.findViewById(R.id.ProviderServiceDescription);


            userRef.document(providerUID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    if(task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();

                        String name = documentSnapshot.getString("firstName") +" "+ documentSnapshot.getString("lastName");
                        nameField.setText(name);
                        ratingBar.setRating(Float.parseFloat(documentSnapshot.getLong("rating").toString()));
                    }
                }
            });


            /*

            serviceRef.whereEqualTo("serviceProvider", providerUID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                    }

                }
            });


            */








        return myView;
    }

}
