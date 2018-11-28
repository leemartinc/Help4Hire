package losdos.help4hire;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {
    public static ArrayList<MessagesModel> mm;

    public MessagesAdapter(ArrayList<MessagesModel> mm) {
        this.mm = mm;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = (View) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_messages, viewGroup, false);
        return new ViewHolder(view);
    }

//    @Override
//    public void onBindViewHolder(@NonNull MessagesAdapter.ViewHolder viewHolder, int i) {
//
//        MessagesModel model = mm.get(i);
//        viewHolder.message.setText(model.getMessage());
//        viewHolder.time.setText(model.getTime());
//
//    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {


        viewHolder.message.setText(mm.get(i).getMessage());
        viewHolder.time.setText(mm.get(i).getTime());

    }

    @Override
    public int getItemCount() {
        if(mm != null) {
            return mm.size();
        }else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
//        public final View view;
        public TextView message;
        public TextView time;

        public ViewHolder(@NonNull View view) {
            super(view);
//            this.view = view;
            message = view.findViewById(R.id.text_message_body);
            System.out.println("body message id is: "+message);
            time = view.findViewById(R.id.text_message_time);
        }
    }
}
