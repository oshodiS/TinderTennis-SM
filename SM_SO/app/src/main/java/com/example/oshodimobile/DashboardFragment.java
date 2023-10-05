package com.example.oshodimobile;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

import com.android.volley.RequestQueue;
import com.example.oshodimobile.Entities.Court;
import com.example.oshodimobile.Entities.Match;
import com.example.oshodimobile.Entities.MatchWithUser;
import com.example.oshodimobile.Entities.User;

import com.example.oshodimobile.RecyclerView.Adapter;
import com.example.oshodimobile.RecyclerView.onItemListener;
import com.example.oshodimobile.ViewModel.ListViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

import java.util.ArrayList;
import java.util.List;


public class DashboardFragment extends Fragment implements  onItemListener{
    RecyclerView rc;
    Adapter adapter;
    private static final String LOG = "Dashboard Fragment";
    private ListViewModel listViewModel;
    List<MatchWithUser> mUsers = new ArrayList<>();
    TextView txt;
    Button ago;
    boolean agoC=false;
    Button vicinanza;
    boolean vicC=false;
    Button princ;
    boolean princC=false;
    Button ama;
    boolean amaC=false;
    Button pro;
    boolean proC=false;
    double lat,longi;
    boolean vicinanzaC=false;


    private static final String PERMISSION_REQUESTED = Manifest.permission.ACCESS_FINE_LOCATION ;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;

    private ActivityResultLauncher<String> requestPermissionLauncher;
    private  boolean requestingLocationUpdates = false;
    private RequestQueue requestQueue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.dashboard, container, false);

    }

    @Override
    public void onResume(){
        super.onResume();
        if(requestingLocationUpdates && getActivity() != null){
            startLocationUpdates(getActivity());
        }
    }
    @Override
    public void onPause(){
        super.onPause();
        stopLocationUpdates();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        InitLoc(getActivity());
        startLocationUpdates(getActivity());

        super.onViewCreated(view, savedInstanceState);
        final  Activity activity = getActivity();
ago=view.findViewById(R.id.ago);
ama=view.findViewById(R.id.ama);
pro=view.findViewById(R.id.prof);
princ = view.findViewById(R.id.princ);
vicinanza=view.findViewById(R.id.vicinanza);
        if(activity != null){

            final onItemListener listener = this;

            adapter = new Adapter(activity,listener);

            //Utilities.setRecyclerView(activity, view, adapter, R.id.recycler_view,rc);
            Utilities.addFab(activity,view);
            rc = view.findViewById(R.id.recycler_view);
            rc.setHasFixedSize(true);
            rc.setAdapter(adapter);
            listViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(ListViewModel.class);
            listViewModel.getMatchWithUserlistOpen().observe((LifecycleOwner) activity, new Observer<List<MatchWithUser>>() {
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
        ago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                            agoC=!agoC;
            if(agoC){  adapter.getFilter().filter("AGONISTA");
                ago.setBackgroundColor(Color.parseColor("#7e57c2"));
                ago.setTextColor(Color.parseColor("#ffffff"));

                ama.setBackgroundColor(Color.parseColor("#81c784"));
                ama.setTextColor(Color.parseColor("#000000"));
                princ.setBackgroundColor(Color.parseColor("#81c784"));
                princ.setTextColor(Color.parseColor("#000000"));
                pro.setBackgroundColor(Color.parseColor("#81c784"));
                pro.setTextColor(Color.parseColor("#000000"));


            }else{
                adapter.getFilter().filter("AGONISTA");
                adapter.getFilter().filter("");
                ago.setBackgroundColor(Color.parseColor("#81c784"));
                ago.setTextColor(Color.parseColor("#000000"));



            }adapter.notifyDataSetChanged();
                      }
                        });

        ama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                amaC=!amaC;
                if(amaC){  adapter.getFilter().filter("AMATORIALE");
                    ama.setBackgroundColor(Color.parseColor("#7e57c2"));
                    ama.setTextColor(Color.parseColor("#ffffff"));
                    ago.setBackgroundColor(Color.parseColor("#81c784"));
                    ago.setTextColor(Color.parseColor("#000000"));
                    princ.setBackgroundColor(Color.parseColor("#81c784"));
                    princ.setTextColor(Color.parseColor("#000000"));
                    pro.setBackgroundColor(Color.parseColor("#81c784"));
                    pro.setTextColor(Color.parseColor("#000000"));
                    vicinanza.setBackgroundColor(Color.parseColor("#81c784"));
                    vicinanza.setTextColor(Color.parseColor("#000000"));
                }else{
                    adapter.getFilter().filter("AMATORIALE");
                    adapter.getFilter().filter("");

                    ama.setBackgroundColor(Color.parseColor("#81c784"));
                    ama.setTextColor(Color.parseColor("#000000"));

                }adapter.notifyDataSetChanged();
            }
        });




        pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                proC=!proC;
                if(proC){  adapter.getFilter().filter("PROFESSIONISTA");
                    pro.setBackgroundColor(Color.parseColor("#7e57c2"));
                    pro.setTextColor(Color.parseColor("#ffffff"));
                    ama.setBackgroundColor(Color.parseColor("#81c784"));
                    ama.setTextColor(Color.parseColor("#000000"));
                    princ.setBackgroundColor(Color.parseColor("#81c784"));
                    princ.setTextColor(Color.parseColor("#000000"));
                    ago.setBackgroundColor(Color.parseColor("#81c784"));
                    ago.setTextColor(Color.parseColor("#000000"));
                    vicinanza.setBackgroundColor(Color.parseColor("#81c784"));
                    vicinanza.setTextColor(Color.parseColor("#000000"));
                }else{
                    adapter.getFilter().filter("PROFESSIONISTA");
                    adapter.getFilter().filter("");

                    pro.setBackgroundColor(Color.parseColor("#81c784"));
                    pro.setTextColor(Color.parseColor("#000000"));

                }adapter.notifyDataSetChanged();
            }
        });

        vicinanza.setOnClickListener(new View.OnClickListener() {
             @Override
            public void onClick(View view) {
                requestingLocationUpdates = true;

                 vicinanzaC=!vicinanzaC;
                 if(vicinanzaC){

                     adapter.getFilter().filter(""+lat+","+longi);
                     vicinanza.setBackgroundColor(Color.parseColor("#7e57c2"));
                     vicinanza.setTextColor(Color.parseColor("#ffffff"));
                     pro.setBackgroundColor(Color.parseColor("#81c784"));
                     pro.setTextColor(Color.parseColor("#000000"));
                     ama.setBackgroundColor(Color.parseColor("#81c784"));
                     ama.setTextColor(Color.parseColor("#000000"));
                     princ.setBackgroundColor(Color.parseColor("#81c784"));
                     princ.setTextColor(Color.parseColor("#000000"));
                     ago.setBackgroundColor(Color.parseColor("#81c784"));
                     ago.setTextColor(Color.parseColor("#000000"));
                 }else{
                     adapter.getFilter().filter("PRINCIPIANTE");
                     adapter.getFilter().filter("");
                     vicinanza.setBackgroundColor(Color.parseColor("#81c784"));
                     vicinanza.setTextColor(Color.parseColor("#000000"));

                 }                 adapter.notifyDataSetChanged();




            }
        });



        princ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                princC=!princC;
                if(princC){  adapter.getFilter().filter("PRINCIPIANTE");
                    princ.setBackgroundColor(Color.parseColor("#7e57c2"));
                    princ.setTextColor(Color.parseColor("#ffffff"));
                    ama.setBackgroundColor(Color.parseColor("#81c784"));
                    ama.setTextColor(Color.parseColor("#000000"));
                    ago.setBackgroundColor(Color.parseColor("#81c784"));
                    ago.setTextColor(Color.parseColor("#000000"));
                    pro.setBackgroundColor(Color.parseColor("#81c784"));
                    pro.setTextColor(Color.parseColor("#000000"));
                    vicinanza.setBackgroundColor(Color.parseColor("#81c784"));
                    vicinanza.setTextColor(Color.parseColor("#000000"));
                }else{
                    adapter.getFilter().filter("PRINCIPIANTE");
                    adapter.getFilter().filter("");
                    princ.setBackgroundColor(Color.parseColor("#81c784"));
                    princ.setTextColor(Color.parseColor("#000000"));

                }adapter.notifyDataSetChanged();
            }
        });


    }


    private void InitLoc(Activity activity) {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient((activity));
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(1000);
        locationRequest.setPriority(Priority.PRIORITY_HIGH_ACCURACY);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Location location = locationResult.getLastLocation();
                String loc = location.getLatitude() + "," + location.getLongitude();
                lat=location.getLatitude();
                longi=location.getLongitude();
                requestingLocationUpdates = false;
                stopLocationUpdates();
            }
        };


    }

    private void startLocationUpdates(Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity,PERMISSION_REQUESTED) == PackageManager.PERMISSION_GRANTED) {
            checkGPS(activity);
            fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback, Looper.getMainLooper());

        }else if(ActivityCompat.shouldShowRequestPermissionRationale(activity,PERMISSION_REQUESTED)){
            showDialog(activity);
        }else{
            requestPermissionLauncher.launch(PERMISSION_REQUESTED);
        }


        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());

    }

    private void stopLocationUpdates(){
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    private void showDialog(Activity activity){
        new AlertDialog.Builder(activity).setMessage("Permesso GPS necessario")
                .setCancelable(false)
                .setPositiveButton("OK",((dialogInterface, i) -> activity.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))))
                .setNegativeButton("Cancel",((dialoInterfaces,i)->dialoInterfaces.cancel())).create().show();

    }
    private void checkGPS(Activity activity){
        final LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        if(locationManager != null & !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            new AlertDialog.Builder(activity)
                    .setMessage("Il GPS è spento, vuoi abilitarlo?")
                    .setCancelable(false).setPositiveButton("Yes",(((dialogInterface, i) -> activity.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)))))
                    .setNegativeButton("No",((dialogInterface, i) -> dialogInterface.cancel()))
                    .create()
                    .show();
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
