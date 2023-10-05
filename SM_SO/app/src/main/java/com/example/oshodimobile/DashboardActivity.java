package com.example.oshodimobile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.oshodimobile.ViewModel.AddViewModel;

public class DashboardActivity extends AppCompatActivity {
    DashboardFragment dashboard = new DashboardFragment();
    DashboardProfileFragment dashboard_profile = new DashboardProfileFragment();
    ProfileFragment profile = new ProfileFragment();
    private int REQUEST_IMAGE_CAPTURE=1;
private AddViewModel addViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Utilities.insertFragment(this, new DashboardFragment(), DashboardFragment.class.getSimpleName());
        Utilities.setBottomNavView(this, dashboard, dashboard_profile, profile);
    addViewModel= new ViewModelProvider(this).get(AddViewModel.class);
    }


@Override protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            if(extras!=null){
                Bitmap imageBitMap = (Bitmap) extras.get("data");
                addViewModel.setBitmap(imageBitMap);
            }
        }
}
}