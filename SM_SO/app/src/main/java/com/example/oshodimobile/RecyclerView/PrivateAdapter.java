package com.example.oshodimobile.RecyclerView;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oshodimobile.Entities.Match;
import com.example.oshodimobile.Entities.MatchWithUserOpponent;
import com.example.oshodimobile.Entities.User;
import com.example.oshodimobile.R;

import java.util.ArrayList;
import java.util.List;

public class PrivateAdapter extends RecyclerView.Adapter<PrivateCardViewHolder> {
    private List<MatchWithUserOpponent> proposteList=new ArrayList<>();
    Activity activity;
    onItemListener listener;
    public PrivateAdapter(Activity activity, onItemListener listener){
        this.activity = activity;
        this.listener=listener;

    }

    @NonNull
    @Override
    public PrivateCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,parent,false);
        return new PrivateCardViewHolder(layoutView,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull PrivateCardViewHolder holder, int position) {
        Log.d("pRivateee", "PROV");

        Match currentProposta = proposteList.get(position).getMatchList().get(0);

        User currentUser = proposteList.get(position).getUserList().get(0);
        holder.txt_name.setText(currentUser.getName());
        holder.txt_court.setText(currentProposta.getCourt().name());
        holder.txt_lvl.setText(currentUser.getLvl().getValue());
        holder.txt_date.setText(currentProposta.getDate().toString());
        holder.txt_age.setText(currentUser.getBirth().toString());
        holder.txt_hour.setText(currentProposta.getHour().toString());
        String imagePath = currentProposta.getImgPath();

        Log.d("pRivateee", currentProposta.getCourt().name());

       /* holder.txt_place.setText(currentProposta.getPlace());
        holder.txt_court.setText(currentProposta.getCourt().name());
        holder.txt_status.setText(currentProposta.getStatus().name());
        holder.txt_date.setText(currentProposta.getDate().toString());
*/

    }

    @Override
    public int getItemCount() {
        return proposteList.size();
    }

    public void setData(List<MatchWithUserOpponent> list){

        this.proposteList = new ArrayList<MatchWithUserOpponent>(list);
        /*final MatchDiffCall diffCall = new MatchDiffCall(this.proposteList,list);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCall);

        diffResult.dispatchUpdatesTo(this);*/
    }
}
