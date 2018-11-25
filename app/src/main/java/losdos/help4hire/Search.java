package losdos.help4hire;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class Search extends android.support.v4.app.Fragment {

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.search, container, false);


        ImageButton submitSearch = myView.findViewById(R.id.search_go);
        final TextView queryArg = myView.findViewById(R.id.searchQuery);

        final android.support.v4.app.FragmentManager fm = getFragmentManager();



            submitSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String query = queryArg.getText().toString();
                    if(query.length() == 0) {

                        try  {
                            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                        } catch (Exception e) {

                        }

                        Toast.makeText(getActivity(), "Enter a service name.",
                                Toast.LENGTH_SHORT).show();

                    }else{
                        Bundle bundle = new Bundle();
                        bundle.putString("toSearch", query);

                        Results resultFrag = new Results();
                        resultFrag.setArguments(bundle);

                        try  {
                            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                        } catch (Exception e) {

                        }

                        fm.beginTransaction().replace(R.id.content_frame
                                , resultFrag).addToBackStack("Search")
                                .commit();
                    }
                }
            });

            queryArg.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    Bundle bundle = new Bundle();
                    bundle.putString("toSearch", queryArg.getText().toString());

                    Results resultFrag = new Results();
                    resultFrag.setArguments(bundle);
                    
                    fm.beginTransaction().replace(R.id.content_frame
                            , resultFrag).addToBackStack("Search")
                            .commit();

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


        return myView;


    }

}
