package losdos.help4hire;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Messages extends Fragment {

    View myView;
    Button btn;
    TextView txtview;
    private RecyclerView rview;
    private RecyclerView.Adapter rviewadap;

//    public ArrayList<MessagesModel> initMessages() {
//        ArrayList<MessagesModel> mm = new ArrayList<>();
//        mm.add(new MessagesModel("Hello this is a test", "12:41"));
//        mm.add(new MessagesModel("This is another test", "12:43"));
//        mm.add(new MessagesModel("How are you doing today", "12:43"));
//        return mm;
//    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myView = inflater.inflate(R.layout.activity_messages, container, false);
//        setContentView(R.layout.activity_messages);

//        ArrayList<MessagesModel> mm = initMessages();
        final ArrayList<MessagesModel> mm = new ArrayList<>();
        final SimpleDateFormat sdf = new SimpleDateFormat("h:mm a", Locale.US);

        this.rview = myView.findViewById(R.id.reyclerview_message_list);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this.getActivity());
        this.rview.setLayoutManager(layout);

        rviewadap = new MessagesAdapter(mm);
        this.rview.setAdapter(rviewadap);

        btn = myView.findViewById(R.id.button_chatbox_send);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtview = myView.findViewById(R.id.edittext_chatbox);
//                rview = myView.findViewById(R.id.reyclerview_message_list);
//                txtview.getText();
                mm.add(new MessagesModel(txtview.getText().toString(), "" + sdf.format(System.currentTimeMillis())));
            }
        });
        return myView;
    }
}
