package com.example.oshodimobile;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Utilities {
     static private Integer currUser;
    static private Integer currMatch;
    static void setUser(Integer usr){
        currUser=usr;
    }
    static  Integer getCurrUser(){
        return  currUser;
    }

    static void setMatch(Integer mat){
        currMatch=mat;
    }
    static  Integer getCurrMatch(){
        return  currMatch;
    }
    public static Bitmap drawableToBitmap(Drawable drawable){
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
    /**
     * Utility Method that get the Bitmap from the URI where the image is stored
     * @param activity activity when the method is executed
     * @param currentPhotoUri the URI for the image to get
     * @return the bitmap contained in the URI
     */
    public static Bitmap getImageBitmap(Activity activity, Uri currentPhotoUri){
        ContentResolver resolver = activity.getApplicationContext()
                .getContentResolver();
        try {
            InputStream stream = resolver.openInputStream(currentPhotoUri);
            Bitmap bitmap = BitmapFactory.decodeStream(stream);
            stream.close();
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    static void insertFragment(AppCompatActivity activity, Fragment fragment, String tag) {
      FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
      transaction.replace(R.id.fragmentContainerView, fragment,tag);
      if(!(fragment instanceof DashboardFragment))
        transaction.addToBackStack(tag);
      transaction.commit();

    }
static  void addFab(final Activity activity, View view){
        FloatingActionButton fab = view.findViewById(R.id.floating_action_button);
        fab.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilities.insertFragment((AppCompatActivity) activity, new AddFragment(),
                        AddFragment.class.getSimpleName());
            }
        });
    }

    static void setDatePicker(EditText dateTIET, Calendar myCalendar, boolean fixLimit){
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                String myFormat="dd/MM/yy";
                SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.ITALIAN);
                dateTIET.setText(dateFormat.format(myCalendar.getTime()));
            }
        };
        dateTIET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(view.getContext(),date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));
              if(fixLimit){  dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                dialog.show();}else{dialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                  dialog.show();}
            }
        });
    }

    static void setBottomNavView(AppCompatActivity activity,Fragment fragment1, Fragment fragment2, Fragment fragment3 ) {
        BottomNavigationView bottomNavigationView = activity.findViewById(R.id.bottom_navbar);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav1:
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, fragment1 ,fragment1.getClass().getSimpleName()).commit();
                        return true;
                    case R.id.nav2:
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, fragment2,fragment2.getClass().getSimpleName()).commit();
                        return true;
                    case R.id.nav3:
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, fragment3,fragment3.getClass().getSimpleName()).commit();
                        return true;

                }
                return false;
            }
        });




    }
    static void setRecyclerView(final Activity activity, View view, RecyclerView.Adapter adapter, int rec_id, RecyclerView rc){

        rc = view.findViewById(rec_id);
        rc.setHasFixedSize(true);
        rc.setAdapter(adapter);


    }

     public static double distFrom(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 3958.75;  //this is in miles I believe
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = earthRadius * c;

        return dist;
    }


}
