package com.example.oshodimobile;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oshodimobile.Entities.Match;
import com.example.oshodimobile.Entities.MatchWithUser;
import com.example.oshodimobile.Entities.MatchWithUserOpponent;
import com.example.oshodimobile.RecyclerView.Adapter;
import com.example.oshodimobile.RecyclerView.PrivateAdapter;
import com.example.oshodimobile.RecyclerView.onItemListener;
import com.example.oshodimobile.ViewModel.ListViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;


public class AcceptedbyMeMatchFragment extends Fragment implements onItemListener {
    RecyclerView rc;
    Adapter adapter;
    private ListViewModel listViewModel;
    List<MatchWithUser> mUsers = new ArrayList<>();

    MapView mMapView;
    GoogleMap map;
    double lat,longi;


    private static final String LOG = " Fragment";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dashboard2, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final  Activity activity = getActivity();
        if(activity != null){
            List<Match> list = new ArrayList<>();

            final onItemListener listener = this;

            adapter = new Adapter(activity,listener);


            Utilities.setRecyclerView(activity, view, adapter, R.id.recycler_view,rc);
            Utilities.addFab(activity,view);

            listViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(ListViewModel.class);
            listViewModel.getAcceptedMatch(Utilities.getCurrUser()).observe((LifecycleOwner) activity, new Observer<List<MatchWithUser>>() {
                @Override
                public void onChanged(List<MatchWithUser> matchWithUsers) {
mUsers=matchWithUsers;
                    adapter.setData(matchWithUsers);
                    adapter.notifyDataSetChanged();
                }
            });


        }else{

            Log.e(LOG,"Activity is null");
        }
    }

    @Override
    public void onItemClick(int pos) {
        Activity activity = getActivity();
        if(activity != null){
            Utilities.setMatch(mUsers.get(pos).getMatch().getId());
            Utilities.insertFragment((AppCompatActivity) activity, new DetailMatchLoadedFragment(),DetailMatchLoadedFragment.class.getSimpleName());

        }
    }





}
