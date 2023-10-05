package com.example.oshodimobile.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.oshodimobile.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PrivateCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    //riferimenti alle View
    ImageView img_prof;
    TextView txt_name;
    TextView txt_date;
    TextView txt_age;
    TextView txt_hour;
    TextView txt_lvl;
    TextView txt_court;

    private onItemListener itemListener;

    public PrivateCardViewHolder(@NonNull View itemView, onItemListener listener) {
        super(itemView);
      //  img_prof = itemView.findViewById(R.id.imgProf);
        txt_name = itemView.findViewById(R.id.textName);
        txt_date  = itemView.findViewById(R.id.textAge);
        txt_age  = itemView.findViewById(R.id.textAge);
        txt_hour  = itemView.findViewById(R.id.textHour);
        txt_lvl  = itemView.findViewById(R.id.textExp);
        txt_court  = itemView.findViewById(R.id.textCourt);
        itemListener =  listener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        itemListener.onItemClick(getAdapterPosition());
    }
}
