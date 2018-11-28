package losdos.help4hire;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Constraints;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class OnboardingActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnLogin;
    EditText input_email,input_password;
    TextView btnSignup,btnForgotPass;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    ConstraintLayout activity_onboard;
    String role;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onboarding_flow);

        //view

        btnLogin = (Button)findViewById(R.id.loginButton);
        input_email = (EditText)findViewById(R.id.usernameEditText);
        input_password =  (EditText)findViewById(R.id.passwordEditText);
        btnSignup = (TextView)findViewById(R.id.signupButton);
        btnForgotPass = (TextView) findViewById(R.id.forgotButton);
        activity_onboard = (ConstraintLayout)findViewById(R.id.activity_onboarding);

        btnSignup.setOnClickListener(this);
        btnForgotPass.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        //init Firebase Auth
        auth = FirebaseAuth.getInstance();

        //check already session , if ok -> Dashboard
        if (auth.getCurrentUser() != null)
            startActivity(new Intent(OnboardingActivity.this,MainActivity.class));
    }

    @Override

    public void onClick(View view) {

        if (view.getId() == R.id.forgotButton){

            startActivity(new Intent(OnboardingActivity.this,ForgotPassword.class));
            finish();
        }

        else if (view.getId() == R.id.signupButton){

            //to sign up screen signUp
            if(input_email.length() == 0 || input_password.length() == 0){
                Snackbar snackbar = Snackbar.make(activity_onboard, "Enter your Email and Password",Snackbar.LENGTH_SHORT);
                snackbar.show();
            }else {
                String email = input_email.getText().toString().trim();
                String password = input_password.getText().toString().trim();
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            finish();
                            startActivity(new Intent(OnboardingActivity.this, PreSignUp.class));
                        } else {

                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
            }

        }

        else if (view.getId() == R.id.loginButton){

            if(input_email.length() == 0 || input_password.length() == 0){
                Snackbar snackbar = Snackbar.make(activity_onboard, "Enter your Email and Password",Snackbar.LENGTH_SHORT);
                snackbar.show();
            }else {
                loginUser(input_email.getText().toString(), input_password.getText().toString());
            }
        }
    }

    private void loginUser(final String email, final String password) {
        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(!task.isSuccessful())
                        {
                            Snackbar snackbar = Snackbar.make(activity_onboard, "Login Failed. Try again",Snackbar.LENGTH_SHORT);
                            snackbar.show();
                        }
                        else {

                            //check if user or provider

                            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                            final String RegisteredUserID = currentUser.getUid();



                            FirebaseFirestore firestore = FirebaseFirestore.getInstance().getInstance();
                            CollectionReference reference = firestore.collection("users");


                            reference.document(RegisteredUserID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                    //save user role
                                    editor = getSharedPreferences("PREFERENCE",Context.MODE_PRIVATE).edit();
                                    editor.putString("CURRENT_USER_UID", RegisteredUserID);
                                    editor.apply();

                                    DocumentSnapshot documentSnapshot = task.getResult();

                                    String userType = documentSnapshot.getString("role");


                                    switch (userType) {
                                        case "provider":
                                            //shared preferences
                                            editor.putString("role", "provider");
                                            editor.apply();

                                            //Snackbar snackbar = Snackbar.make(activity_onboard, "role is user22",Snackbar.LENGTH_SHORT);
                                            //snackbar.show();

                                            break;
                                        case "user":
                                            //shared preferences
                                            editor.putString("role", "user");
                                            editor.apply();
                                            break;
                                        default:
                                            //shared preferences
                                            editor.putString("role", "no role found");
                                            editor.apply();
                                            break;
                                    }

                                }
                            });

                            startActivity(new Intent(OnboardingActivity.this,MainActivity.class));
                        }
                    }


                });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    public void hideKeyboard(View view){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }


}


