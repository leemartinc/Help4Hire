package losdos.help4hire;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class PreSignUp extends AppCompatActivity implements View.OnClickListener {

    Button userButton,providerButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pre_signup);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.userButton){

            //to user sign up screen signUp

            //startActivity(new Intent(PreSignUp.this,####.class));
            //finish();

            userButton = findViewById(R.id.userButton);
            userButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openUserSignUp();
                }
            });
        }

        else if (v.getId() == R.id.providerButton){

            //to provider sign up screen signUp

            //startActivity(new Intent(PreSignUp.this,####.class));
            //finish();
            providerButton = findViewById(R.id.providerButton);
            providerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openProviderSignUp();
                }
            });

        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(PreSignUp.this,OnboardingActivity.class));
        finish();
    }

    /* This function will open up the Provider Activity which will go the
        the provider sign up screen when the button is clicked

    */
    public void openProviderSignUp(){
        Intent intent = new Intent(this, ProviderSignUp.class);
        startActivity(intent);
    }

    public void openUserSignUp(){
        Intent intent = new Intent(this, UserProfileActivity.class);
        startActivity(intent);
    }
}
