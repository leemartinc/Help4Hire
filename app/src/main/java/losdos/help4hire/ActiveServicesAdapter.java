package losdos.help4hire;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.google.firebase.firestore.Query;

public class ActiveServicesAdapter extends FirestoreRecyclerAdapter<preResults, ActiveServicesAdapter.ActiveServicesHolder> {

    public ActiveServicesAdapter(@NonNull FirestoreRecyclerOptions<preResults> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ActiveServicesHolder holder, int position, @NonNull preResults model) {
        //Bundle bundle = new Bundle();

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference reference = firestore.collection("requests");

        reference.document(model.getServiceProvider()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();

                    String requestProvider = documentSnapshot.getString("requestProvider");
                    String requestServiceTitle = documentSnapshot.getString("requestService");
                    String requestStatus = documentSnapshot.getString("requestStatus");
                    String totalCost = documentSnapshot.getString("totalCost");


                    holder.textViewRequestProvider.setText(requestProvider);

                    holder.textViewRequestServiceTitle.setText(requestServiceTitle);

                    holder.textViewRequestStatus.setText(requestStatus);

                    //int cost = documentSnapshot.getLong("total cost").intValue();

                    holder.textViewTotalCost.setText(String.valueOf(totalCost));

                    //button Magic



                }else{

                }

            }
        });





        /*
        //holder.textViewProviderName.setText(model.getServiceName());
        holder.textViewProviderLoc.setText(model.getProviderLoc());
        holder.textViewProviderRating.setText(String.valueOf(model.getProviderRating()));
        */



//        holder.textViewAbbrevCost.setText(String.valueOf(model.getServiceRate()));




    }

    @NonNull
    @Override
    public ActiveServicesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_item,
                viewGroup, false);


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
