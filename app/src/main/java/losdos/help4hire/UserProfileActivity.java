package losdos.help4hire;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


/*
    This class is for the user profile activity. User enters fname, lname and
    address and saves it. This class will store all of that information into
    firestore and use firebase for auth

 */

public class UserProfileActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String KEY_FNAME = "First Name";
    private static final String KEY_LNAME = "Last Name";
    private static final String KEY_ADDRESS = "Address";

    private EditText fName;
    private EditText lName;
    private EditText address;

    // Reference to firestore database
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);


        fName = findViewById(R.id.firstNameTextView);
        lName = findViewById(R.id.lastNameTextView);
        address = findViewById(R.id.addressTextView);

        //no need for a toolbar here

        //also, add input for email address and password and send that through authentication
        //using a method like the one below
        /*
        mAuth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }

                // ...
            }
        });
         */

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(UserProfileActivity.this,PreSignUp.class));
        finish();
    }


    // This method will save into firestore
    public void mySaveInfo(View view){

        String fname = fName.getText().toString();
        String lname = lName.getText().toString();
        String my_address = address.getText().toString();


        Map<String,Object> myMap = new HashMap<String,Object>();
        myMap.put(KEY_FNAME,fname);
        myMap.put(KEY_LNAME,lname);
        myMap.put(KEY_ADDRESS, my_address);


        db.collection("userDemo").document("First User")
                .set(myMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(UserProfileActivity.this, "User Saved", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserProfileActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, e.toString());
            }
        });
    }

}
