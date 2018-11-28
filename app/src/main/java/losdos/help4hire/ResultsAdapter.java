package losdos.help4hire;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
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

public class ResultsAdapter extends FirestoreRecyclerAdapter<preResults, ResultsAdapter.ResultHolder> {
    public static String fullName;
    public static int rating;
    public static String serviceDescription;
    public String providerUID;
    public String serviceDocID;

    public ResultsAdapter(@NonNull FirestoreRecyclerOptions<preResults> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ResultHolder holder, int position, @NonNull final preResults model) {
        //Bundle bundle = new Bundle();

        serviceDocID = getSnapshots().getSnapshot(position).getId();
        //System.out.println(serviceDocID);

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference reference = firestore.collection("users");

        providerUID = model.getServiceProvider();

        reference.document(model.getServiceProvider()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();

                    String firstName = documentSnapshot.getString("firstName");
                    String lastName = documentSnapshot.getString("lastName");

                    fullName = firstName + " " + lastName;

                    holder.textViewProviderName.setText(fullName);

                    String zip = documentSnapshot.getString("zip");

                    holder.textViewProviderLoc.setText(zip);

                    rating = documentSnapshot.getLong("rating").intValue();

                    holder.textViewProviderRating.setText(String.valueOf(rating));





                }
                if(model.getServiceDescription() != null){
                    serviceDescription = model.getServiceDescription();

                }

            }
        });





        /*
        //holder.textViewProviderName.setText(model.getServiceName());
        holder.textViewProviderLoc.setText(model.getProviderLoc());
        holder.textViewProviderRating.setText(String.valueOf(model.getProviderRating()));
        */



        holder.textViewAbbrevCost.setText(String.valueOf(model.getServiceRate()));




    }

    @NonNull
    @Override
    public ResultHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final Bundle bundle = new Bundle();

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_item,
                viewGroup, false);
        Button resultButton = v.findViewById(R.id.resultButton);
        resultButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                bundle.putString("fullName", fullName);
                bundle.putInt("rating", rating);
                bundle.putString("providerUID", providerUID);
                bundle.putString("serviceDocID", serviceDocID);
//                bundle.putDouble("lat", lat);
//                bundle.putDouble("lng", lng);
//                bundle.putString("providerFullName", providerFullName);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                ProviderProfile profileFrag = new ProviderProfile();
                profileFrag.setArguments(bundle);
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, profileFrag)
                        .addToBackStack("nextFragment")
                        .commit();
            }
        });


        return new ResultHolder(v);
    }

    class ResultHolder extends RecyclerView.ViewHolder{

        TextView textViewProviderName;
        TextView textViewProviderLoc;
        TextView textViewProviderRating;
        TextView textViewAbbrevCost;

        public ResultHolder(@NonNull View itemView) {
            super(itemView);

            textViewProviderName = itemView.findViewById(R.id.resultItem1);
            textViewProviderLoc = itemView.findViewById(R.id.resultItem2);
            textViewProviderRating = itemView.findViewById(R.id.resultItem3);
            textViewAbbrevCost = itemView.findViewById(R.id.resultItem4);


        }
    }
}
