package losdos.help4hire;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ReviewActivity extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference ref = db.collection("reviews");
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        Bundle bundle = this.getArguments();
//        if (bundle != null) {
            final String reviewee = bundle.getString("reviewee");
            Log.d("help4hire", "STRING FROM BUNDLE: " + reviewee);
//        }

        myView = inflater.inflate(R.layout.activity_review, container, false);

        final RatingBar ratingBar = (RatingBar) myView.findViewById(R.id.ratingBar);
        final EditText etReview = (EditText) myView.findViewById(R.id.review_entry);
        Button submitReview = (Button) myView.findViewById(R.id.btnSubmit);

        submitReview.setOnClickListener(new View.OnClickListener() {
            Float rating;
            String review;

            @Override
            public void onClick(View view) {
                Map<String, Object> data = new HashMap<>();


                if (ratingBar.getRating() == 0)  {
                    Toast.makeText(getActivity(), "Please include a rating.", Toast.LENGTH_LONG).show();
                } else {
                    //save review data to database
                    rating = ratingBar.getRating();
                    review = etReview.getText().toString();


                    data.put("review", review);
                    data.put("review date", FieldValue.serverTimestamp());
                    data.put("review rating", rating);
                    data.put("reviewee", reviewee);
                    data.put("reviewer", user.getEmail()); //can get other identifiers such as UUID

                    data.put("service name", "");
                    ref.add(data);

                    //reset view to empty state
                    //should also probably exit the review screen here
                    etReview.setText("");
                    ratingBar.setRating(0);
                    Toast.makeText(getActivity(), "Thank you for your review!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return myView;
    }

}
