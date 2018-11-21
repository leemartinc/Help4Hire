package losdos.help4hire;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

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


    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    CollectionReference reference = firestore.collection("Users");
    Query q;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.results, container, false);

        q = firestore.collection("Users");
        //showAdapter(q);

        final EditText queryArgs = myView.findViewById(R.id.resSearch);

        Bundle bundle = getArguments();
        String query = bundle.getString("toSearch");

        //queryArgs.setText(query);

        if(query.length() != 0) {
            //go to firestore

        }

        queryArgs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //go to firestore
                if (s.toString().length() == 0) {
                    q = firestore.collection("Users");
                    //showAdapter(q);
                } // This is used as if user erases the characters in the search field.
                else {
                    q = reference.orderBy("name").startAt(s.toString().trim()).endAt(s.toString().trim() + "\uf8ff"); // name - the field for which you want to make search
                    ///showAdapter(q);
                }
                //adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
/*
        void showAdapter(Query q1) {
            q1.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    List<SearchModel> names = new ArrayList<>();
                    if(task.isSuccessful()){
                        for(QueryDocumentSnapshot document : task.getResult()) {
                            SearchModel model = document.toObject(SearchModel.class);
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

        return myView;
    }

}