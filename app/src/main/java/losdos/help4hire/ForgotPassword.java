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
import com.google.firebase.auth.FirebaseAuth;

public abstract class ForgotPassword extends AppCompatActivity implements View.OnClickListener{

    private Button btnResetPass;
    private TextView btnBack;
    private EditText input_email;


    private RelativeLayout activity_forgot;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        //view

        input_email = (EditText)findViewById(R.id.usernameEditText);
        btnResetPass = (Button) findViewById(R.id.resetpassword);
        btnBack = (TextView) findViewById(R.id.BackButton);
        activity_forgot = (RelativeLayout)findViewById(R.id.activity_forgot_password);

        btnResetPass.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        //init Firebase Auth
        auth = FirebaseAuth.getInstance();


    }

    @Override

    public void onClick(View view) {

        if (view.getId() == R.id.BackButton){

            startActivity(new Intent(this,OnboardingActivity.class));
            finish();
        }

        /*else if (view.getId() == R.id.signupButton){

            startActivity(new Intent(OnboardingActivity.this,ProfileActivity.class));
            finish();
        }*/

        else if (view.getId() == R.id.resetpassword){

            resetPassword(input_email.getText().toString());
        }
    }

    private void resetPassword(final String email) {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(!task.isSuccessful())
                        {
                            Snackbar snackbar = Snackbar.make(activity_forgot, "We have sent password to email :"+email,Snackbar.LENGTH_SHORT);
                            snackbar.show();
                        }

                        else
                        {
                            Snackbar snackbar = Snackbar.make(activity_forgot, "Failed to send password",Snackbar.LENGTH_SHORT);
                            snackbar.show();
                        }
                    }


                });

    }
}

