package com.example.oshodimobile.RecyclerView;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oshodimobile.Entities.Match;
import com.example.oshodimobile.Entities.MatchWithUser;
import com.example.oshodimobile.Entities.User;
import com.example.oshodimobile.R;

import java.util.ArrayList;
import java.util.List;

public class LoadedByMeAdapter extends RecyclerView.Adapter<LoadCardView>{

    private List<MatchWithUser> proposteList= new ArrayList<>();
    private List<User> users;
    Activity activity;
    onItemListener listener;
    User user;
    public LoadedByMeAdapter(Activity activity, onItemListener listener){
        this.activity = activity;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LoadCardView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_profile,parent,false);
        return new LoadCardView(layoutView,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull LoadCardView holder, int position) {
        Match currentProposta = proposteList.get(position).getMatchList().get(0);
        User currentUser = proposteList.get(position).getUserList().get(0);
        holder.txt_court.setText(currentProposta.getCourt().getValue());
        holder.txt_place.setText(currentProposta.getPlace());
        holder.txt_date.setText(currentProposta.getDate().toString());
        holder.txt_status.setText(currentProposta.getStatus().getValue());


        String imagePath = currentProposta.getImgPath();


    }

    @Override
    public int getItemCount() {
        return proposteList.size();
    }


    public void setData(List<MatchWithUser> list){
        this.proposteList = new ArrayList<>(list);
        final MatchUserDiffCall diffCall = new MatchUserDiffCall(this.proposteList,list);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCall);

        diffResult.dispatchUpdatesTo(this);

    }



}
