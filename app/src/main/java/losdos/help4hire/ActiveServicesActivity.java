package losdos.help4hire;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ActiveServicesActivity extends Fragment {

    View myView;

    RecyclerView recyclerView;

    SharedPreferences prefs;

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private CollectionReference reference = firestore.collection("requests");
    private ResultsAdapter adapter;
    Query q;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.active_services, container, false);

        //get shared preferences
        prefs = getActivity().getSharedPreferences("PREFERENCE",Context.MODE_PRIVATE);

        //get current user
        String uid = prefs.getString("CURRENT_USER_UID", "NONE");

        //SET UP RECYCLER
        recyclerView = (RecyclerView) myView.findViewById(R.id.active_services_recycler);

        //recycler settings
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        q =  firestore.collection("requests")
                .orderBy("serviceStatus")
                .whereEqualTo("serviceStatus","active")
                .whereEqualTo("requestUser", uid);



        FirestoreRecyclerOptions<preResults> options = new FirestoreRecyclerOptions.Builder<preResults>()
                .setQuery(q, preResults.class)
                .build();

        adapter = new ResultsAdapter(options);

        recyclerView.setAdapter(adapter);


        //adapter.startlistening();



        return myView;
    }

}
