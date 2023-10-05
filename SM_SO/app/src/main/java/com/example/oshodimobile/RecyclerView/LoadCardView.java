package com.example.oshodimobile.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.oshodimobile.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LoadCardView extends RecyclerView.ViewHolder implements View.OnClickListener {
    //riferimenti alle View
    TextView txt_date;
    TextView txt_place;

    TextView txt_status;

    TextView txt_court;
    private onItemListener itemListener;


    public LoadCardView(@NonNull View itemView, onItemListener listener) {

        super(itemView);
        txt_date  = itemView.findViewById(R.id.date);
        txt_place  = itemView.findViewById(R.id.place);
        txt_status = itemView.findViewById(R.id.status);
        txt_court  = itemView.findViewById(R.id.txtcourt);
        itemListener =  listener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        itemListener.onItemClick(getAdapterPosition());
    }
}
