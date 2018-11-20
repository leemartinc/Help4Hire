package losdos.help4hire;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class Search extends android.support.v4.app.Fragment {

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.search, container, false);
        ImageButton submitSearch = myView.findViewById(R.id.search_go);



        final android.support.v4.app.FragmentManager fm = getFragmentManager();

        submitSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                    fm.beginTransaction().replace(R.id.content_frame
                    , new Results())
                    .commit();

        }
        });


        return myView;


    }

}
