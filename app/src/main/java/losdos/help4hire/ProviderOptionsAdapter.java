package losdos.help4hire;
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

import java.util.HashMap;
import java.util.Map;


public class ProviderOptionsAdapter extends FirestoreRecyclerAdapter<ProviderOptionsDB, ProviderOptionsAdapter.ProviderOptionsHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    //String requestDocID;
    int pos;


    public ProviderOptionsAdapter(@NonNull FirestoreRecyclerOptions<ProviderOptionsDB> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ProviderOptionsHolder holder, final int position, @NonNull ProviderOptionsDB model) {

        CollectionReference UserReference = firestore.collection("users");

        pos = position;

        if (model.getRequestUser() != null) {
            UserReference.document(model.getRequestUser()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();

                        String customerFirstName = documentSnapshot.getString("firstName");
                        String customerLastName = documentSnapshot.getString("lastName");
                        String customerFullName = customerFirstName + " " + customerLastName;

                        holder.textViewRequestUser.setText(customerFullName);

                    } else {
                        holder.textViewRequestUser.setText("ERROR");
                    }
                }
            });
        } else {
            holder.textViewRequestUser.setText("ERROR");
        }

        CollectionReference serviceReference = firestore.collection("services");

        if(model.getRequestService() != null){
            serviceReference.document(model.getRequestService()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();

                        String serviceTitle = documentSnapshot.getString("serviceName");

                        holder.textViewRequestServiceTitle.setText(serviceTitle);

                    } else {
                        holder.textViewRequestServiceTitle.setText("ERROR");
                    }

                }
            });

        }else {
            holder.textViewRequestServiceTitle.setText("ERROR");
        }
        if(model.getRequestStatus() != null) {
            holder.textViewRequestStatus.setText(model.getRequestStatus());

            if (model.getRequestStatus().equals("Accepted")) {
                holder.endBtn.setVisibility(View.VISIBLE);
                holder.messagesBtn.setVisibility(View.VISIBLE);
            }else if(model.getRequestStatus().equals("Pending")){
                holder.acceptBtn.setVisibility(View.VISIBLE);
                holder.declineBtn.setVisibility(View.VISIBLE);
            }
        }else{holder.textViewRequestStatus.setText("ERROR");}



        if(model.getTotalCost() != null) {
            holder.textViewTotalCost.setText("Cost: $" + model.getTotalCost());

        }else{ holder.textViewTotalCost.setText("ERROR");}



        //button magic
        holder.endBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();

                String requestDocID = getSnapshots().getSnapshot(position).getId();

                System.out.println(requestDocID);

                Map<String,Object> myMap = new HashMap<String,Object>();
                myMap.put("requestStatus","Closed");
                myMap.put("serviceStatus", "Closed");

                firestore.collection("requests").document(requestDocID).update(myMap);

                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, new ProviderOptions())
                        .addToBackStack("nextFragment")
                        .commit();
            }

        });

        holder.acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();

                String requestDocID = getSnapshots().getSnapshot(position).getId();

                System.out.println(requestDocID);

                Map<String,Object> myMap = new HashMap<String,Object>();
                myMap.put("requestStatus","Accepted");
                myMap.put("serviceStatus", "Active");

                firestore.collection("requests").document(requestDocID).update(myMap);

                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, new ProviderOptions())
                        .addToBackStack("nextFragment")
                        .commit();
            }

        });

        holder.declineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();

                String requestDocID = getSnapshots().getSnapshot(position).getId();

                System.out.println(requestDocID);

                Map<String,Object> myMap = new HashMap<String,Object>();
                myMap.put("requestStatus","Closed");
                myMap.put("serviceStatus", "Closed");

                firestore.collection("requests").document(requestDocID).update(myMap);

                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, new ProviderOptions())
                        .addToBackStack("nextFragment")
                        .commit();
            }

        });




    }

    @NonNull
    @Override
    public ProviderOptionsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.provider_options_item,
                viewGroup, false);

        //final CollectionReference requestRef = firestore.collection("requests");

        Button messageButton = v.findViewById(R.id.btnProviderMessage);
        messageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Messages messagesFrag = new Messages();
                //messagesFrag.setArguments(bundle);
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, messagesFrag)
                        .addToBackStack("nextFragment")
                        .commit();
            }
        });

/*
        Button endButton = v.findViewById(R.id.btnEnd);
        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();

                System.out.println(requestDocID);

                Map<String,Object> myMap = new HashMap<String,Object>();
                myMap.put("requestStatus","Closed");
                myMap.put("serviceStatus", "Closed");

                //firestore.collection("requests").document(requestDocID).update(myMap);

            }
        });

        */



        return new ProviderOptionsHolder(v);
    }

    class ProviderOptionsHolder extends RecyclerView.ViewHolder {

        TextView textViewRequestUser;
        TextView textViewRequestServiceTitle;
        TextView textViewRequestStatus;
        TextView textViewTotalCost;

        Button messagesBtn;
        Button endBtn;

        Button acceptBtn;
        Button declineBtn;

        public ProviderOptionsHolder(@NonNull View itemView) {
            super(itemView);

            textViewRequestUser = itemView.findViewById(R.id.customer_name);
            textViewRequestServiceTitle = itemView.findViewById(R.id.customer_service_title);
            textViewRequestStatus = itemView.findViewById(R.id.customer_service_status);
            textViewTotalCost = itemView.findViewById(R.id.customer_service_cost);

            messagesBtn = itemView.findViewById(R.id.btnProviderMessage);
            endBtn = itemView.findViewById(R.id.btnEnd);
            acceptBtn = itemView.findViewById(R.id.btnAccept);
            declineBtn = itemView.findViewById(R.id.btnDecline);




        }
    }
}
