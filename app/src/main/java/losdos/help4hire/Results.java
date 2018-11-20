package losdos.help4hire;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Results extends Fragment {

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.results, container, false);


        final TextView queryArgs = myView.findViewById(R.id.resSearch);
        Bundle bundle = getArguments();
        String query = bundle.getString("toSearch");

        queryArgs.setText(query);




        return myView;
    }

}