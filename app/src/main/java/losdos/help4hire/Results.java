package losdos.help4hire;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class Results extends Fragment {

    View myView;
    ListView mList;


    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private CollectionReference reference = firestore.collection("services");
    private ResultsAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        myView = inflater.inflate(R.layout.results, container, false);

        //showAdapter(q);

        final EditText queryArgs = myView.findViewById(R.id.resSearch);

        Bundle bundle = getArguments();
        String query = bundle.getString("toSearch");



        //queryArgs.setText(query);



        //adapter.startListening();

        RecyclerView recyclerView = (RecyclerView) myView.findViewById(R.id.recycler_view);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Query q = reference.orderBy("service est hours", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<preResults> options = new FirestoreRecyclerOptions.Builder<preResults>()
                .setQuery(q, preResults.class)
                .build();

        adapter = new ResultsAdapter(options);


        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setMinimumHeight(800);



        if(query.length() == 0) {
            adapter.startListening();
            Toast.makeText(getActivity(), "im listening",
                    Toast.LENGTH_SHORT).show();
        }else{
            adapter.stopListening();
            Toast.makeText(getActivity(), "im not listening",
                    Toast.LENGTH_SHORT).show();
        }
        




        return myView;
    }


/*
    @Override
    public void onCreate(){
        super.onStart();
        adapter.startListening();
    }
*/
    /*
    @Override
    public void onStop(){
        super.onStart();
        adapter.stopListening();
    }
    */




}