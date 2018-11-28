package losdos.help4hire;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener{

    private Button btnResetPass;
    private TextView btnBack;
    private EditText input_email;


    private ConstraintLayout activity_forgot;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        //view

        input_email = (EditText)findViewById(R.id.EnterEmail);
        btnResetPass = (Button) findViewById(R.id.resetpassword);
        btnBack = (TextView) findViewById(R.id.BackButton);
        activity_forgot = (ConstraintLayout) findViewById(R.id.activity_forgot_password);

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

            startActivity(new Intent(OnboardingActivity.this,ProviderProfile.class));
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

                        if(task.isSuccessful())
                        {
                            Snackbar snackbar = Snackbar.make(activity_forgot, "We have sent password to email :"+email,Snackbar.LENGTH_SHORT);
                            snackbar.show();
                        }

                        else
                        {
                            Snackbar snackbar = Snackbar.make(activity_forgot, "Failed to send password, user does not exist",Snackbar.LENGTH_SHORT);
                            snackbar.show();
                        }
                    }


                });

    }
}

