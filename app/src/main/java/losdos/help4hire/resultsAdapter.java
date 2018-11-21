package losdos.help4hire;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ResultsAdapter extends FirestoreRecyclerAdapter<preResults, ResultsAdapter.ResultHolder> {


    public ResultsAdapter(@NonNull FirestoreRecyclerOptions<preResults> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ResultHolder holder, int position, @NonNull preResults model) {
        Bundle bundle = new Bundle();

        holder.textViewProviderName.setText(model.getServiceName());
        holder.textViewProviderLoc.setText(model.getProviderLoc());
        holder.textViewProviderRating.setText(String.valueOf(model.getProviderRating()));
        holder.textViewAbbrevCost.setText(String.valueOf(model.getAbbrevCost()));

    }

    @NonNull
    @Override
    public ResultHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_item,
                viewGroup, false);


        return new ResultHolder(v);
    }

    class ResultHolder extends RecyclerView.ViewHolder{

        TextView textViewProviderName;
        TextView textViewProviderLoc;
        TextView textViewProviderRating;
        TextView textViewAbbrevCost;

        public ResultHolder(@NonNull View itemView) {
            super(itemView);

            textViewProviderName = itemView.findViewById(R.id.resultItem1);
            textViewProviderLoc = itemView.findViewById(R.id.resultItem2);
            textViewProviderRating = itemView.findViewById(R.id.resultItem3);
            textViewAbbrevCost = itemView.findViewById(R.id.resultItem4);





        }
    }
}
