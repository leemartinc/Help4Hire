package losdos.help4hire;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class ReviewActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference ref = db.collection("reviews");
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        final RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        final EditText etReview = (EditText) findViewById(R.id.review_entry);
        Button submitReview = (Button) findViewById(R.id.btnSubmit);

        submitReview.setOnClickListener(new View.OnClickListener() {
            Float rating;
            String review;

            @Override
            public void onClick(View view) {
                Map<String, Object> data = new HashMap<>();


                if (ratingBar.getRating() == 0)  {
                    Toast.makeText(ReviewActivity.this, "Please include a rating.", Toast.LENGTH_LONG).show();
                } else {
                    //save review data to database
                    rating = ratingBar.getRating();
                    review = etReview.getText().toString();


                    data.put("review", review);
                    data.put("review date", FieldValue.serverTimestamp());
                    data.put("review rating", rating);
                    data.put("reviewee", "");
                    data.put("reviewer", user.getEmail()); //can get other identifiers such as UUID

                    data.put("service name", "");
                    ref.add(data);

                    //reset view to empty state
                    etReview.setText("");
                    ratingBar.setRating(0);
                    Toast.makeText(ReviewActivity.this, "Thank you for your review!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
