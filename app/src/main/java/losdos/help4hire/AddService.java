package losdos.help4hire;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddService extends Fragment {
    View myView;

    private Spinner my_spinner;
    private RadioButton fixedRadioButton;
    private RadioButton hourlyRadioButton;
    private EditText ageEditText;
    private EditText dollarEditText;
    private RadioButton yesButton;
    private RadioButton noButton;
    private RadioGroup daGroup;
    private RadioGroup daGroup2;
    private Button save;
    private String service;

    private static final String TAG = "MainActivity";
    private static final String KEY_FNAME = "firstName";
    private static final String KEY_LNAME = "lastName";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_SPINNER_VALUE = "serviceName";
    private static final String KEY_FIXED= "fixedRate";
    private static final String KEY_HOURLY = "hourlyRate";
    private static final String KEY_AGE_VALE= "ageValue";
    private static final String KEY_DOLLAR_VALUE = "dollarValue";
    private static final String KEY_YES_INSURED = "insured";
    private static final String KEY_NO_INSURED = "insured";

    private String RegisteredUserID;

    // Reference to firestore database
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @SuppressLint("ResourceType")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.add_service, container, false);

        ageEditText = myView.findViewById(R.id.ageEditText);
        dollarEditText = myView.findViewById(R.id.dollarEditText);

        my_spinner = myView.findViewById(R.id.mySpinner);
        fixedRadioButton = myView.findViewById(R.id.fixedRadioButton);
        hourlyRadioButton = myView.findViewById(R.id.hourlyRadioButton);

        yesButton = myView .findViewById(R.id.yesRadioButton);
        noButton = myView.findViewById(R.id.noRadioButton);
        daGroup = myView.findViewById(R.id.myRadioGroup);
        daGroup2 = myView.findViewById(R.id.hourlyFixedRadioGroup);

        save = myView.findViewById(R.id.btnAddService);



        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        RegisteredUserID = currentUser.getUid();

        String[] services = new String[]{"Baby Sitter", "Main", "Dog Walker", "Plumber", "mechanic"};

        Spinner spinner = myView.findViewById(R.id.mySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.provider_choices, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String text = adapterView.getItemAtPosition(position).toString();
                text = service;
                Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                service = my_spinner.getSelectedItem().toString();
                String fixed_radioButton = fixedRadioButton.getText().toString();
                String hourly_Radiobutton = hourlyRadioButton.getText().toString();
                String age = ageEditText.getText().toString();
                String dollar = dollarEditText.getText().toString();
                String yes_button = yesButton.getText().toString();
                String no_button = noButton.getText().toString();


                final Map<String,Object> myMap = new HashMap<String,Object>();
                myMap.put("serviceName",service);
                myMap.put("serviceRate", dollar);
                myMap.put("serviceProvider" , RegisteredUserID);
                myMap.put("serviceDescription", "");

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


                db.collection("services").document()
                        .set(myMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), "Service Saved", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getContext(),MainActivity.class));

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
            }
        });


        return myView;
    }
}
