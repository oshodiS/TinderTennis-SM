    package com.example.oshodimobile;

    import androidx.activity.result.ActivityResultCallback;
    import androidx.activity.result.ActivityResultLauncher;
    import androidx.activity.result.contract.ActivityResultContracts;
    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.core.app.ActivityCompat;
    import androidx.fragment.app.Fragment;
    import androidx.lifecycle.ViewModelProvider;
    import androidx.lifecycle.ViewModelStoreOwner;

    import android.Manifest;
    import android.app.Activity;
    import android.app.AlertDialog;
    import android.app.TimePickerDialog;
    import android.content.Context;
    import android.content.Intent;
    import android.content.pm.PackageManager;
    import android.location.Location;
    import android.location.LocationManager;
    import android.media.Image;
    import android.media.audiofx.Equalizer;
    import android.net.ConnectivityManager;
    import android.net.Network;
    import android.net.NetworkInfo;
    import android.os.Build;
    import android.os.Bundle;
    import android.os.Looper;
    import android.provider.Settings;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ArrayAdapter;
    import android.widget.AutoCompleteTextView;
    import android.widget.Button;
    import android.widget.ImageButton;
    import android.widget.TimePicker;

    import com.android.volley.Request;
    import com.android.volley.RequestQueue;
    import com.android.volley.Response;
    import com.android.volley.VolleyError;
    import com.android.volley.toolbox.JsonObjectRequest;
    import com.android.volley.toolbox.Volley;
    import com.example.oshodimobile.Entities.Court;
    import com.example.oshodimobile.Entities.Match;
    import com.example.oshodimobile.ViewModel.AddViewModel;
    import com.google.android.gms.location.FusedLocationProviderClient;
    import com.google.android.gms.location.LocationCallback;
    import com.google.android.gms.location.LocationRequest;
    import com.google.android.gms.location.LocationResult;
    import com.google.android.gms.location.LocationServices;
    import com.google.android.gms.location.Priority;
    import com.google.android.material.snackbar.Snackbar;
    import com.google.android.material.textfield.TextInputEditText;
    import com.google.android.material.textfield.TextInputLayout;

    import org.json.JSONException;
    import org.json.JSONObject;

    import java.util.Calendar;


    public class AddFragment extends Fragment {
        private static final String LOG = "Add Match Fragment";
        private static final String PERMISSION_REQUESTED = Manifest.permission.ACCESS_FINE_LOCATION ;
        private FusedLocationProviderClient fusedLocationProviderClient;
        private LocationCallback locationCallback;
        private LocationRequest locationRequest;
        private ActivityResultLauncher<String> requestPermissionLauncher;
        private  boolean requestingLocationUpdates = false;
        private RequestQueue requestQueue;
        private ConnectivityManager.NetworkCallback networkCallback;
        private  final static  String OSM_REQUEST_TAG="OSM_REQUEST";
        ImageButton gps;
        TextInputEditText dateTIET;
        TextInputLayout dateILayout;
        TextInputEditText hourTIET;
        TextInputLayout hourILayout;
        TextInputEditText longDescTIET;
        TextInputEditText placeTIET;
        TextInputLayout placeIlayout;
        Button btnSave;
        TextInputEditText costTIET;
        AutoCompleteTextView courtTXW;
        TextInputLayout courtILayout;
        Double longi,lat;
        private Boolean isNetworkConnected = false;
       private Snackbar snackbar;


        final Calendar myCalendar = Calendar.getInstance();
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.new_match, container, false);

        }

        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            final Activity activity = getActivity();
            AddViewModel addViewModel;


            if (activity != null) {

requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
    @Override
    public void onActivityResult(Boolean result) {
        if(result){
            startLocationUpdates(activity);
            Log.d("TT","PERMESSO CONCESSO");

        }else{
            Log.d("TT","PERMESSO NON CONCESSO");
            showDialog(activity);
        }
    }
});

                //Insert data in select
                String[] inputs = new String[]{Court.ERBA.getValue(), Court.TERRA_BATTUTA.getValue(), Court.CEMENTO_SINTETICO.getValue()};
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity.getBaseContext(), R.layout.list_items, inputs);
                AutoCompleteTextView menu = view.findViewById(R.id.txtcourt);
                menu.setAdapter(adapter);
                Init(view);
                requestQueue = Volley.newRequestQueue(activity);

                InitLoc(activity);

                snackbar = Snackbar.make(activity.findViewById(R.id.fragmentContainerView), "No internet disponibile", Snackbar.LENGTH_INDEFINITE).setAction("Settings", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_WIRELESS_SETTINGS);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.startActivity(intent);
                    }

                });

                networkCallback = new ConnectivityManager.NetworkCallback() {
                    @Override
                    public void onAvailable(@NonNull Network network) {
                        super.onAvailable(network);
                        isNetworkConnected = true;
                        snackbar.dismiss();
                        if (requestingLocationUpdates) {
                            startLocationUpdates(activity);
                        }
                    }


                    @Override
                    public void onLost(@NonNull Network network) {
                        super.onLost(network);
                        isNetworkConnected = false;
                        snackbar.show();

                    }

                };

                addViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(AddViewModel.class);
                Init(view);

                Utilities.setDatePicker(dateTIET, myCalendar, true);


                hourTIET.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        Calendar mcurrentTime = Calendar.getInstance();
                        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                        int minute = mcurrentTime.get(Calendar.MINUTE);


                        TimePickerDialog mTimePicker;
                        mTimePicker = new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                hourTIET.setText(selectedHour + ":" + selectedMinute);
                            }
                        }, hour, minute, true);
                        mTimePicker.show();

                    }
                });

                gps.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        requestingLocationUpdates = true;
                        registerNetworkCallback(activity);
                        startLocationUpdates(activity);
                    }
                });

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (FromValidation()
                        ) {
                            addViewModel.addMatch(new Match(Utilities.getCurrUser(), longDescTIET.getText().toString(), dateTIET.getText().toString(), hourTIET.getText().toString(), Float.valueOf(costTIET.getText().toString()), Court.valueOf(courtTXW.getText().toString()), placeTIET.getText().toString(), lat,longi,"D"));
                            ((AppCompatActivity) activity).getSupportFragmentManager().popBackStack();

                        }
                    }
                });


            } else {

                Log.e(LOG, "Activity is null");
            }}

        @Override
        public void onStart(){
            super.onStart();
            if(getActivity() != null && requestingLocationUpdates){
                registerNetworkCallback(getActivity());
            }}
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
        public void onStop(){
            super.onStop();
            if(requestQueue != null)
                requestQueue.cancelAll(OSM_REQUEST_TAG);
        unregisterNetworkCallback(getActivity());
        }
        private boolean FromValidation() {
            boolean flag = true;
            if (dateTIET.getText().toString().matches("")
            ) {
                dateILayout.setError("inserisci  data");
                flag = false;
            }
            if (hourTIET.getText().toString().matches("")) {
                hourILayout.setError("inserisci orario ");
                flag = false;
            }
            if (placeTIET.getText().toString().matches("")) {
                placeIlayout.setError("inserisci luogo");
                flag = false;
            }
            if (courtTXW.getText().toString().matches("")) {
                courtILayout.setError("inserisci tipo di campo");
                flag = false;
            }


            return flag;
        }

        private void Init(View view) {
            //extract input
            dateTIET = view.findViewById(R.id.date_edittext);
            hourTIET = view.findViewById(R.id.user_edittext);
            placeTIET = view.findViewById(R.id.place_edittext);
            longDescTIET = view.findViewById(R.id.info_edittext);
            costTIET = view.findViewById(R.id.cost_edittext);
            courtTXW = view.findViewById(R.id.txtcourt);
            dateILayout = view.findViewById(R.id.date_textinput);
            hourILayout = view.findViewById(R.id.hour_textinput);
            placeIlayout = view.findViewById(R.id.place_textinput);
            courtILayout = view.findViewById(R.id.court_spinner);
            btnSave = view.findViewById(R.id.btnSave);
            gps = view.findViewById(R.id.gps_button);
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
                    placeTIET.setText(loc);
                    lat=location.getLatitude();
                    longi=location.getLongitude();
    sendVolleyRequest(String.valueOf(location.getLatitude()),String.valueOf(location.getLongitude()));
                    requestingLocationUpdates = false;
                    stopLocationUpdates();
                }
            };


        }

        private void startLocationUpdates(Activity activity) {
            if (ActivityCompat.checkSelfPermission(activity,PERMISSION_REQUESTED) == PackageManager.PERMISSION_GRANTED) {
    checkGPS(activity);
                fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback,Looper.getMainLooper());

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



        private void registerNetworkCallback(Activity activity){
            ConnectivityManager connectivityManager=(ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
            if(connectivityManager != null){
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
                    connectivityManager.registerDefaultNetworkCallback(networkCallback);
                }else{
NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                    isNetworkConnected=(networkInfo != null && networkInfo.isConnected());
                }

            }else {
                isNetworkConnected=false;
            }
        }

        private void unregisterNetworkCallback(Activity activity){
            if(getActivity() != null){
                ConnectivityManager connectivityManager=(ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
                if(connectivityManager != null){
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                        connectivityManager.unregisterNetworkCallback(networkCallback);
                    }else{
                        snackbar.dismiss();
                    }
                }
            }

        }
        private void sendVolleyRequest(String latitude, String longitude) {
            String url = "https://nominatim.openstreetmap.org/reverse?lat=" + latitude +
                    "&lon="+ longitude + "&format=jsonv2&limit=1";

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                placeTIET.setText(response.get("name").toString());
                                unregisterNetworkCallback(getActivity());
                            } catch (JSONException e) {
                                placeTIET.setText("/");
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("TT-ADDFRAGMENT", error.toString());
                        }
                    });

            jsonObjectRequest.setTag(OSM_REQUEST_TAG);
            requestQueue.add(jsonObjectRequest);

        }
    }
