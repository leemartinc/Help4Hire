package losdos.help4hire;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ProfileActivity extends Fragment {

        View myView;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                Bundle bundle = this.getArguments();
                String fullName = bundle.getString("fullName");
                int rating = bundle.getInt("rating");
                 String serviceDescription = bundle.getString("serviceDescription");
                Log.d("help4hire5", "STRING FROM BUNDLE: " + fullName);
            Log.d("help4hire5", "STRING FROM BUNDLE: " + rating);
            Log.d("help4hire5", "STRING FROM BUNDLE: " + serviceDescription);
        myView = inflater.inflate(R.layout.user_profile, container, false);
        return myView;
    }

}
