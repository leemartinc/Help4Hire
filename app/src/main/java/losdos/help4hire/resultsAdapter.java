package losdos.help4hire;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ResultsAdapter extends FirestoreRecyclerAdapter<preResults, ResultsAdapter.ResultHolder> {


    public ResultsAdapter(@NonNull FirestoreRecyclerOptions<preResults> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ResultHolder holder, int position, @NonNull preResults model) {
        //Bundle bundle = new Bundle();

         FirebaseFirestore firestore = FirebaseFirestore.getInstance();
         CollectionReference reference = firestore.collection("users");

         reference.document(model.getServiceProvider()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
             @Override
             public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                 if(task.isSuccessful()){
                     DocumentSnapshot documentSnapshot = task.getResult();

                     String firstName = documentSnapshot.getString("firstName");
                     String lastName = documentSnapshot.getString("lastName");

                     String fullName = firstName + " " + lastName;

                     holder.textViewProviderName.setText(fullName);

                     String zip = documentSnapshot.getString("zip");

                     holder.textViewProviderLoc.setText(zip);

                     int rating = documentSnapshot.getLong("rating").intValue();

                     holder.textViewProviderRating.setText(String.valueOf(rating));

                 }else{

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

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_item,
                viewGroup, false);


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
