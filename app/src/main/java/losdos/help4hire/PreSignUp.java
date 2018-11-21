package losdos.help4hire;

import android.content.Intent;
import android.os.Bundle;
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
        }

        else if (v.getId() == R.id.providerButton){

            //to provider sign up screen signUp

            //startActivity(new Intent(PreSignUp.this,####.class));
            //finish();

        }
    }
}
