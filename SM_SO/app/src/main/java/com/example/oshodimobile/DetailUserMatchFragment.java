package com.example.oshodimobile;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.oshodimobile.Entities.MatchWithUser;
import com.example.oshodimobile.Entities.Status;
import com.example.oshodimobile.Entities.User;
import com.example.oshodimobile.ViewModel.AddViewModel;
import com.example.oshodimobile.ViewModel.ListViewModel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class DetailUserMatchFragment extends Fragment implements OnMapReadyCallback {


    private static final String LOG = "Detail Match Fragment";
    ListViewModel listViewModel;
    AddViewModel addViewModel;

    TextView nome;
    TextView eta;
    String place;
    TextView cell;
    TextView lvl;
    TextView cost;
    TextView type;
    TextView hour;
    TextView date;
    ImageView img;
    TextView longDesc;
    TextView title;
    MapView mMapView;
    GoogleMap map;
    double lat,longi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.user_details_match, container, false);

        return v;

    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Activity activity = getActivity();


        if (activity != null) {


            Init(view);
            listViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(ListViewModel.class);
            addViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(AddViewModel.class);

            listViewModel.getMatchWithUserbyId(Utilities.getCurrMatch()).observe((LifecycleOwner) activity, new Observer<MatchWithUser>() {
                @Override
                public void onChanged(MatchWithUser matchWithUsers) {

                    if (matchWithUsers.getMatchList().get(0).getStatus().getValue() == "COMPLETATA") {
                        nome.setText("" + matchWithUsers.getUserList().get(0).getName() + " " + matchWithUsers.getUserList().get(0).getLast_name());
                        eta.setText("" + matchWithUsers.getUserList().get(0).getBirth());
                        lvl.setText("" + matchWithUsers.getUserList().get(0).getLvl());
                        nome.setVisibility(View.VISIBLE);
                        eta.setVisibility(View.VISIBLE);
                        lvl.setVisibility(View.VISIBLE);
                        title.setVisibility(View.VISIBLE);
                        String uri=matchWithUsers.getUserList().get(0).getImgPath();
                        if(matchWithUsers.getUserList().get(0).getImgPath()!=null){
                            if(matchWithUsers.getUserList().get(0).getImgPath().contains("ic_")){
                                Drawable drawable = AppCompatResources.getDrawable(activity,activity.getResources().getIdentifier("ic_baseline_account_circle","drawable",activity.getPackageName()));
                                img.setImageDrawable(drawable);

                            }else{
                                img.setImageBitmap(Utilities.getImageBitmap(activity, Uri.parse(uri)));
                            }
                        }

                    } else {

                        cell.setVisibility(View.GONE);
                        title.setVisibility(View.GONE);
                        nome.setVisibility(View.GONE);
                        eta.setVisibility(View.GONE);
                        lvl.setVisibility(View.GONE);

                    }
                    date.setText("" + matchWithUsers.getMatchList().get(0).getDate());
                    hour.setText("" + matchWithUsers.getMatchList().get(0).getHour());
                    cost.setText("€ " + matchWithUsers.getMatchList().get(0).getCost());
                    type.setText(" " + matchWithUsers.getMatchList().get(0).getCourt());
                    longDesc.setText("" + matchWithUsers.getMatchList().get(0).getLongDesc());
                    lat=matchWithUsers.getMatchList().get(0).getLat();
                    longi=matchWithUsers.getMatchList().get(0).getLongi();
                    place=matchWithUsers.getMatchList().get(0).getPlace();
                   // if(matchWithUsers.getUserList().get(0).getImgPath()=="ic_baseline_android_24"){

                    //}


                    if (matchWithUsers.getMatchList().get(0).getStatus().getValue() == "COMPLETATA") {
                        cell.setText(matchWithUsers.getUserList().get(0).getCell());
                        cell.setVisibility(View.VISIBLE);
                    }

                }

            });
            try {
                mMapView = (view.findViewById(R.id.matchDet).findViewById(R.id.mapview));
                mMapView.onCreate(savedInstanceState);

                mMapView.getMapAsync(this);




            } catch (Exception e) {
            }
        } else {

            Log.e(LOG, "Activity is null");
        }


    }

    private void Init(View view) {
        View usr = view.findViewById(R.id.userDet);
        View match = view.findViewById(R.id.matchDet);
        nome = (TextView) usr.findViewById(R.id.name_text);
        eta = (TextView) usr.findViewById(R.id.age_text);
        cost = (TextView) match.findViewById(R.id.text_cost);
        date = (TextView) match.findViewById(R.id.datetText);
        hour = (TextView) match.findViewById(R.id.hourtext);
        lvl = (TextView) usr.findViewById(R.id.exp_text);
        type = (TextView) match.findViewById(R.id.type_text);
        cell = (TextView) usr.findViewById(R.id.txtcell);
        longDesc = (TextView) match.findViewById(R.id.long_text);
        title = usr.findViewById(R.id.txtTitleG);

img=usr.findViewById(R.id.imgProfil);
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setMyLocationButtonEnabled(false);
        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true);

        // For dropping a marker at a point on the Map
        LatLng sydney = new LatLng(lat, longi);
        MarkerOptions marker = new MarkerOptions().position(
                sydney).title(place);
        marker.icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
        map.addMarker(marker);
        // For zooming automatically to the location of the marker


        CameraPosition cameraPosition =  CameraPosition.builder()
                .target(sydney)
                .zoom(20)
                .bearing(0)
                .tilt(45)
                .build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,11));


    }@Override
    public void onResume() {
        mMapView.onResume();
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

}
