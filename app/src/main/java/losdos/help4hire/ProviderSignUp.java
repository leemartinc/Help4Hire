package losdos.help4hire;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProviderSignUp extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String KEY_FNAME = "firstName";
    private static final String KEY_LNAME = "lastName";
    private static final String KEY_ADDRESS = "address";



    private EditText fName;
    private EditText lName;
    private EditText address;

    private String RegisteredUserID;
    private TextView userEmail;


    // Reference to firestore database
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.provider_signup);



        // FireStore Storage for Provider

        fName = findViewById(R.id.firstName);
        lName = findViewById(R.id.lastName);
        address = findViewById(R.id.Address);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String RegisteredUserEmail = currentUser.getEmail();

        RegisteredUserID = currentUser.getUid();

        userEmail = findViewById(R.id.providerProfileEmail);
        userEmail.setText(RegisteredUserEmail);

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ProviderSignUp.this,PreSignUp.class));
        finish();
    }


    // This method will save into firestore
    public void saveInfo(View view){

        String fname = fName.getText().toString();
        String lname = lName.getText().toString();
        String my_address = address.getText().toString();

        // checks field if empty or not
        if(isEmptyField(fName))return;
        if(isEmptyField(lName))return;
        if(isEmptyField(address))return;


        Map<String,Object> myMap = new HashMap<String,Object>();
        myMap.put(KEY_FNAME,fname);
        myMap.put(KEY_LNAME,lname);
        myMap.put(KEY_ADDRESS, my_address);
        myMap.put("role", "provider");
        myMap.put("rating", 0);

         /*
         By geting rid of the document, Firestore will generate a new Id each time a
         new user is created.

        */

        db.collection("users").document(RegisteredUserID)
                .set(myMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ProviderSignUp.this, "User Saved", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ProviderSignUp.this,MainActivity.class));

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ProviderSignUp.this, "Error!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, e.toString());
            }
        });
    }

    // This method will validate if EditText fields are empty
    private boolean isEmptyField (EditText editText){
        boolean result = editText.getText().toString().length() <= 0;
        if (result)
            Toast.makeText(ProviderSignUp.this, "Fill all fields!", Toast.LENGTH_SHORT).show();
        return result;
    }
}

