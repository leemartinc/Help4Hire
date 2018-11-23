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

public class ActiveServicesAdapter extends FirestoreRecyclerAdapter<PreActiveServices, ActiveServicesAdapter.ActiveServicesHolder> {

    public ActiveServicesAdapter(@NonNull FirestoreRecyclerOptions<PreActiveServices> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ActiveServicesHolder holder, int position, @NonNull PreActiveServices model) {
        //Bundle bundle = new Bundle();

                    holder.textViewRequestProvider.setText(model.getRequestProvider());

                    holder.textViewRequestServiceTitle.setText(model.getRequestService());

                    holder.textViewRequestStatus.setText(model.getServiceStatus());

                    holder.textViewTotalCost.setText(model.getTotalCost());

                    //button Magic

    }

    @NonNull
    @Override
    public ActiveServicesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.active_services_item,
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

            /*
            messagesBtn = itemView.findViewById(R.id.btnMessage);
            locationBtn = itemView.findViewById(R.id.btnLocation);

            cancelBtn = itemView.findViewById(R.id.btnCancel);

            reviewBtn = itemView.findViewById(R.id.btnReview);
            hireBtn = itemView.findViewById(R.id.btnHireAgain);
            */

        }
    }
}
