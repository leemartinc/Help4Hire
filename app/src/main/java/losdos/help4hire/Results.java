package losdos.help4hire;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class Results extends Fragment {

    View myView;
    ListView mList;


    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private CollectionReference reference = firestore.collection("services");
    private ResultsAdapter adapter;
    Query q;
    private ImageButton search;
    RecyclerView recyclerView;
    EditText queryArgs;


    @SuppressLint("ResourceType")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        myView = inflater.inflate(R.layout.results, container, false);

        //showAdapter(q);

        queryArgs  = myView.findViewById(R.id.resSearch);

        Bundle bundle = getArguments();
        String query = bundle.getString("toSearch");

        queryArgs.setText(query);

        //adapter.startListening();

        recyclerView = (RecyclerView) myView.findViewById(R.id.recycler_view);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        q = reference.orderBy("serviceName").startAt(query).endAt(query + "\uf8ff");

        FirestoreRecyclerOptions<preResults> options = new FirestoreRecyclerOptions.Builder<preResults>()
                .setQuery(q, preResults.class)
                .build();

        adapter = new ResultsAdapter(options);

        recyclerView.setAdapter(adapter);

        if(queryArgs.getText().toString().length() != 0) {
            adapter.startListening();
            Toast.makeText(getActivity(), "im listening",
                    Toast.LENGTH_SHORT).show();
        }else{
            //adapter.stopListening();
            Toast.makeText(getActivity(), "im not listening",
                    Toast.LENGTH_SHORT).show();
        }


        queryArgs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = queryArgs.getText().toString();

                if (s.toString().length() == 0) {
                    q = firestore.collection("services");
                    //showAdapter(q);
                    FirestoreRecyclerOptions<preResults> options = new FirestoreRecyclerOptions.Builder<preResults>()
                            .setQuery(q, preResults.class)
                            .build();

                    adapter = new ResultsAdapter(options);
                    recyclerView.setAdapter(adapter);


                } // This is used as if user erases the characters in the search field.
                else {

                    recyclerView = (RecyclerView) myView.findViewById(R.id.recycler_view);

                    recyclerView.setHasFixedSize(true);

                    q = reference.orderBy("serviceName").startAt(query).endAt(query + "\uf8ff");
                    //showAdapter(q);
                    FirestoreRecyclerOptions<preResults> options = new FirestoreRecyclerOptions.Builder<preResults>()
                            .setQuery(q, preResults.class)
                            .build();

                    adapter = new ResultsAdapter(options);

                    recyclerView.setAdapter(adapter);

                }

                if(queryArgs.getText().toString().length() != 0) {
                    adapter.startListening();
                    /*debug
                    Toast.makeText(getActivity(), "im listening",
                            Toast.LENGTH_SHORT).show();
                            */
                }else{
                    //adapter.stopListening();
                    /*
                    Toast.makeText(getActivity(), "im not listening",
                            Toast.LENGTH_SHORT).show();
                            */
                }
            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        search = myView.findViewById(R.id.search_go);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String query = queryArgs.getText().toString();
                if(query.length() == 0) {

                    try  {
                        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {

                    }

                    Toast.makeText(getActivity(), "Enter a service name.",
                            Toast.LENGTH_SHORT).show();

                }else{
                    recyclerView = (RecyclerView) myView.findViewById(R.id.recycler_view);

                    recyclerView.setHasFixedSize(true);


                    q = reference.orderBy("serviceName").startAt(query).endAt(query + "\uf8ff");

                    FirestoreRecyclerOptions<preResults> options = new FirestoreRecyclerOptions.Builder<preResults>()
                            .setQuery(q, preResults.class)
                            .build();

                    adapter = new ResultsAdapter(options);

                    recyclerView.setAdapter(adapter);

                    if(queryArgs.getText().toString().length() != 0) {
                        adapter.startListening();
                        Toast.makeText(getActivity(), "im listening",
                                Toast.LENGTH_SHORT).show();
                    }else{
                        //adapter.stopListening();
                        Toast.makeText(getActivity(), "im not listening",
                                Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });



        return myView;
    }

    /*
    void showAdapter(Query q1) {
        q1.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                FirestoreRecyclerOptions<preResults> options = new FirestoreRecyclerOptions.Builder<preResults>()
                        .setQuery(q, preResults.class)
                        .build();



                List<SearchModel> names = new ArrayList<>();
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()) {
                        preResults model = document.toObject(preResults.class);
                        names.add(model);
                    }
                    mList = findViewById(R.id.listSearch);
                    adapter = new SearchAdapter(AllPlaces.this, names);
                    mList.setAdapter(adapter);
                }
            }
        });


    }
*/

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