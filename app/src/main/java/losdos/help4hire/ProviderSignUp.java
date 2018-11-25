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
import android.widget.Toast;

import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProviderSignUp extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "MainActivity";
    private static final String KEY_FNAME = "First Name";
    private static final String KEY_LNAME = "Last Name";
    private static final String KEY_ADDRESS = "Address";
    private static final String KEY_SPINNER_VALUE = "Spinner Value";
    private static final String KEY_FIXED= "Fixed Rate";
    private static final String KEY_HOURLY = "Hourly Rate";
    private static final String KEY_AGE_VALE= "Age Value";
    private static final String KEY_DOLLAR_VALUE = "Dollar Value";
    private static final String KEY_YES_INSURED = "Insured";
    private static final String KEY_NO_INSURED = "Insured";


    private EditText fName;
    private EditText lName;
    private EditText address;
    private Spinner my_spinner;
    private RadioButton fixedRadioButton;
    private RadioButton hourlyRadioButton;
    private EditText ageEditText;
    private EditText dollarEditText;
    private RadioButton yesButton;
    private RadioButton noButton;
    private RadioGroup daGroup;
    private RadioGroup daGroup2;


    // Reference to firestore database
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.provider_signup);


        Spinner spinner = findViewById(R.id.mySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.provider_choices, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        // FireStore Storage for Provider

        fName = findViewById(R.id.firstName);
        lName = findViewById(R.id.lastName);
        address = findViewById(R.id.Address);
        ageEditText = findViewById(R.id.ageEditText);
        dollarEditText = findViewById(R.id.dollarEditText);
//
//        if(isEmptyField(fName))return;
//        if(isEmptyField(lName))return;
//        if(isEmptyField(address))return;
//        if(isEmptyField(ageEditText))return;
//        if(isEmptyField(dollarEditText))return;

        my_spinner = (Spinner)findViewById(R.id.mySpinner);
        fixedRadioButton = findViewById(R.id.fixedRadioButton);
        hourlyRadioButton = findViewById(R.id.hourlyRadioButton);

        yesButton = findViewById(R.id.yesRadioButton);
        noButton = findViewById(R.id.noRadioButton);
        daGroup = findViewById(R.id.myRadioGroup);
        daGroup2 = findViewById(R.id.hourlyFixedRadioGroup);


        //add input for email address and password and send that through authentication
        //so that we can add them as a user
        //using the method below
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
        startActivity(new Intent(ProviderSignUp.this,PreSignUp.class));
        finish();
    }

    // These two methods below are for the spinner in the ProviderSignUp
    @Override
    // Will show a toast message after user selects spinner item
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        String text = adapterView.getItemAtPosition(position).toString();
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    // This method will save into firestore
    public void saveInfo(View view){

        String fname = fName.getText().toString();
        String lname = lName.getText().toString();
        String my_address = address.getText().toString();
        String spinner = my_spinner.getSelectedItem().toString();
        String fixed_radioButton = fixedRadioButton.getText().toString();
        String hourly_Radiobutton = hourlyRadioButton.getText().toString();
        String age = ageEditText.getText().toString();
        String dollar = dollarEditText.getText().toString();
        String yes_button = yesButton.getText().toString();
        String no_button = noButton.getText().toString();

        // checks field if empty or not
        if(isEmptyField(fName))return;
        if(isEmptyField(lName))return;
        if(isEmptyField(address))return;
        if(isEmptyField(ageEditText))return;
        if(isEmptyField(dollarEditText))return;


        Map<String,Object> myMap = new HashMap<String,Object>();
        myMap.put(KEY_FNAME,fname);
        myMap.put(KEY_LNAME,lname);
        myMap.put(KEY_ADDRESS, my_address);
        myMap.put(KEY_AGE_VALE, age);
        myMap.put(KEY_DOLLAR_VALUE, dollar);
        myMap.put(KEY_SPINNER_VALUE , spinner);

        if(daGroup2.getCheckedRadioButtonId() == R.id.hourlyRadioButton){
            myMap.put(KEY_HOURLY, true);
        }else if(daGroup2.getCheckedRadioButtonId() == R.id.fixedRadioButton){
            myMap.put(KEY_FIXED , true );
        }

        if (daGroup.getCheckedRadioButtonId() == R.id.yesRadioButton){
            myMap.put(KEY_YES_INSURED, true);
        } else if (daGroup.getCheckedRadioButtonId() == R.id.noRadioButton){
            myMap.put(KEY_NO_INSURED, false);
        }


        db.collection("demoProviders").document("First Provider")
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

