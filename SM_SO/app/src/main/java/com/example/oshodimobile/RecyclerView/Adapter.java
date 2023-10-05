package com.example.oshodimobile.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.example.oshodimobile.Entities.Match;
import com.example.oshodimobile.Entities.MatchWithUser;
import com.example.oshodimobile.Entities.User;
import com.example.oshodimobile.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<CardViewHolder> implements Filterable {
    private List<MatchWithUser> proposteList= new ArrayList<>();
    private List<MatchWithUser> proposteListNoFilter= new ArrayList<>();
    private List<User> users;


    Double longi,lat;

    Activity activity;
    onItemListener listener;
    User user;
    public Adapter(Activity activity, onItemListener listener){
        this.activity = activity;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,parent,false);
        return new CardViewHolder(layoutView,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        Match currentProposta = proposteList.get(position).getMatchList().get(0);
     User currentUser = proposteList.get(position).getUserList().get(0);
             holder.txt_name.setText(currentUser.getName());
        holder.txt_court.setText(currentProposta.getCourt().name());
        holder.txt_lvl.setText(currentUser.getLvl().getValue());
        holder.txt_date.setText(currentProposta.getDate().toString());
        holder.txt_age.setText(currentProposta.getDate().toString());
        holder.txt_hour.setText(currentProposta.getHour().toString());
        String imagePath = currentProposta.getImgPath();
        switch (currentProposta.getCourt().name()){
            case "TERRA_BATTUTA":
                holder.cardView.setBackgroundResource(R.drawable.orange);
                holder.txt_name.setTextColor(Color.parseColor("#030201"));
                holder.txt_court.setTextColor(Color.parseColor("#030201"));;
                holder.txt_lvl.setTextColor(Color.parseColor("#030201"));;
                holder.txt_date.setTextColor(Color.parseColor("#030201"));
                holder.txt_age.setTextColor(Color.parseColor("#030201"));
                holder.txt_hour.setTextColor(Color.parseColor("#030201"));
                break;
            case "CEMENTO_SINTETICO":
                holder.cardView.setBackgroundResource(R.drawable.blueb);
                holder.txt_name.setTextColor(Color.parseColor("#ffffff"));
                holder.txt_court.setTextColor(Color.parseColor("#ffffff"));;
                holder.txt_lvl.setTextColor(Color.parseColor("#ffffff"));;
                holder.txt_date.setTextColor(Color.parseColor("#ffffff"));
                holder.txt_age.setTextColor(Color.parseColor("#ffffff"));
                holder.txt_hour.setTextColor(Color.parseColor("#ffffff"));
                break;
            case "ERBA":
                holder.cardView.setBackgroundResource(R.drawable.yellow);
                holder.txt_name.setTextColor(Color.parseColor("#030201"));
                holder.txt_court.setTextColor(Color.parseColor("#030201"));;
                holder.txt_lvl.setTextColor(Color.parseColor("#030201"));;
                holder.txt_date.setTextColor(Color.parseColor("#030201"));
                holder.txt_age.setTextColor(Color.parseColor("#030201"));
                holder.txt_hour.setTextColor(Color.parseColor("#030201"));
                break;
        }

    }

    @Override
    public int getItemCount() {
        return proposteList.size();
    }


  public void setData(List<MatchWithUser> list){
        this.proposteList = new ArrayList<>(list);
      this.proposteListNoFilter= new ArrayList<>(list);
        final MatchUserDiffCall diffCall = new MatchUserDiffCall(this.proposteList,list);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCall);

        diffResult.dispatchUpdatesTo(this);

    }

    private final Filter cardFilter = new Filter() {
        /**
         * Called to filter the data according to the constraint
         *
         * @param constraint constraint used to filtered the data
         * @return the result of the filtering
         */
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<MatchWithUser> filteredList = new ArrayList<>();

            //if you have no constraint --> return the full list
            if (constraint == "" || constraint.length() ==0) {
                filteredList.addAll(proposteListNoFilter);
            } else {
                if(constraint=="AMATORIALE"||
                    constraint=="AGONISTA"||
                    constraint=="PRINCIPIANTE" ||
                    constraint=="PROFESSIONISTA"){
                //else apply the filter and return a filtered list
                for (MatchWithUser item : proposteListNoFilter) {
                    if (item.getUserList().get(0).getLvl().getValue()== constraint) {
                        filteredList.add(item);
                    }
                }
            }else{
                Double otherLan, otherLong;
                String[] parts = constraint.toString().split(",");
                    Double lat = Double. valueOf(parts[0]); // 004
                    Double longi =Double. valueOf( parts[1]); // 034556
                    for (MatchWithUser item : proposteListNoFilter) {

                    otherLan=item.getMatchList().get(0).getLat();
                    otherLong=item.getMatchList().get(0).getLongi();
                    if (distFrom(lat,longi,otherLan,otherLong)<20) {
                        filteredList.add(item);
                    }
                }
             }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<MatchWithUser> filteredList = new ArrayList<>();
            List<?> result = (List<?>) results.values;
            for (Object object : result) {
                if (object instanceof MatchWithUser) {
                    filteredList.add((MatchWithUser) object);
                }
            }

            //warn the adapter that the data are changed after the filtering
            updateCardList(filteredList);
        }



        };

    public void updateCardList(List<MatchWithUser> filteredList) {
        final MatchUserDiffCall diffCallback = new MatchUserDiffCall(this.proposteList, filteredList);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

     this.proposteList.clear();
     this.proposteList.addAll(filteredList);
        diffResult.dispatchUpdatesTo(this);
    }

    public  Double distFrom(Double lat1, Double lng1, Double lat2, Double lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        Double dist = (Double) (earthRadius * c);
        double meterConversion = 1609.00;
        return dist*meterConversion;
    }

    @Override
    public Filter getFilter() {
        return cardFilter;    }


;}
