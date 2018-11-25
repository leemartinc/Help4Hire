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
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ProviderOptions extends Fragment{

    View myView;

    RecyclerView recyclerView;

    SharedPreferences prefs;

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private CollectionReference reference = firestore.collection("requests");
    private ProviderOptionsAdapter adapter;
    Query q;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.provider_options, container, false);

        //get shared preferences
        prefs = getActivity().getSharedPreferences("PREFERENCE",Context.MODE_PRIVATE);

        //get current user
        String uid = prefs.getString("CURRENT_USER_UID", "NONE");

        //SET UP RECYCLER
        recyclerView = (RecyclerView) myView.findViewById(R.id.provider_options_recycler);

        //recycler settings
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        q =  reference.orderBy("requestStatus");

        FirestoreRecyclerOptions<ProviderOptionsDB> options = new FirestoreRecyclerOptions.Builder<ProviderOptionsDB>()
                .setQuery(q, ProviderOptionsDB.class)
                .build();

        adapter = new ProviderOptionsAdapter(options);

        recyclerView.setAdapter(adapter);

        adapter.startListening();
        Toast.makeText(getActivity(), "im listening " + uid,
                Toast.LENGTH_SHORT).show();



        return myView;
    }



}
