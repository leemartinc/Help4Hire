package losdos.help4hire;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class ReviewActivity extends AppCompatActivity {

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
                if (ratingBar.getRating() == 0)  {
                    Toast.makeText(ReviewActivity.this, "Please include a rating.", Toast.LENGTH_LONG).show();
                } else {
                    //save review data to database
                    rating = ratingBar.getRating();
                    review = etReview.getText().toString();

                    //reset view to empty state
                    etReview.setText("");
                    ratingBar.setRating(0);
                    Toast.makeText(ReviewActivity.this, "Thank you for your review!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
