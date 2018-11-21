package losdos.help4hire;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public abstract class OnboardingActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnLogin;
    EditText input_email,input_password;
    TextView btnSignup,btnForgotPass;

    RelativeLayout activity_onboard;

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
        activity_onboard = (RelativeLayout)findViewById(R.id.activity_onboarding);

        btnSignup.setOnClickListener(this);
        btnForgotPass.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        //init Firebase Auth
        auth = FirebaseAuth.getInstance();

        //check already session , if ok -> Dashboard

        if (auth.getCurrentUser() != null)
            startActivity(new Intent(OnboardingActivity.this,Search.class));



    }

    @Override

    public void onClick(View view) {

        if (view.getId() == R.id.forgotButton){

            startActivity(new Intent(OnboardingActivity.this,ForgotPassword.class));
            finish();
        }

        else if (view.getId() == R.id.signupButton){

            startActivity(new Intent(OnboardingActivity.this,ProfileActivity.class));
            finish();
        }

        else if (view.getId() == R.id.loginButton){

            loginUser(input_email.getText().toString(),input_password.getText().toString());
        }
    }

    private void loginUser(String email,final String password) {
        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(!task.isSuccessful())
                        {
                            Snackbar snackbar = Snackbar.make(activity_onboard, "Password length must be over 6",Snackbar.LENGTH_SHORT);
                            snackbar.show();
                        }

                        else {
                            startActivity(new Intent(OnboardingActivity.this,Search.class));
                        }
                    }


                });

    }


}


