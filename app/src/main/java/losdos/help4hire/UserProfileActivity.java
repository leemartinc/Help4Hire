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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

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
    private TextView resetPass;

    // Reference to firestore database
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);


        fName = findViewById(R.id.firstNameTextView);
        lName = findViewById(R.id.lastNameTextView);
        address = findViewById(R.id.addressTextView);

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

        // Checks field for empty or not
        if(isEmptyField(fName))return;
        if(isEmptyField(lName))return;
        if(isEmptyField(address))return;

        Map<String,Object> myMap = new HashMap<String,Object>();
        myMap.put(KEY_FNAME,fname);
        myMap.put(KEY_LNAME,lname);
        myMap.put(KEY_ADDRESS, my_address);

        /*
         By geting rid of the document, Firestore will generate a new Id each time a
         new user is created.
        */
        db.collection("userDemo").document()
                .set(myMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(UserProfileActivity.this, "User Saved", Toast.LENGTH_SHORT).show();
                // This should go to the home screen if the user is succesfully entered
                startActivity(new Intent(UserProfileActivity.this,MainActivity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserProfileActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, e.toString());
            }
        });
    }

    // This method will validate if EditText fields are empty
    private boolean isEmptyField (EditText editText){
        boolean result = editText.getText().toString().length() <= 0;
        if (result)
            Toast.makeText(UserProfileActivity.this, "Fill all fields!", Toast.LENGTH_SHORT).show();
        return result;
    }

}
