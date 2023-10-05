package com.example.oshodimobile;

import android.app.Activity;
import android.app.Application;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.oshodimobile.Entities.Court;
import com.example.oshodimobile.Entities.Lvl;
import com.example.oshodimobile.Entities.Match;
import com.example.oshodimobile.Entities.Status;
import com.example.oshodimobile.Entities.User;
import com.example.oshodimobile.ViewModel.AddViewModel;
import com.example.oshodimobile.ViewModel.ListViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ProfileFragment extends Fragment {
    TextView nameText;
    TextView lastnameText;
    TextView dateText;
    TextView cellText;
    TextInputEditText userTIET;
    TextInputEditText passTIET;
    Button btnSave;
    AutoCompleteTextView lvlTXW;
    String name; String lastname;
    String username;
    String password;
    String lvl;
    String oldP;
    ImageView img;
Button btnImg;
    private  final MutableLiveData<Bitmap> imageBitmap = new MutableLiveData<>();
    static final Integer REQUEST_IMAGE_CAPTURE =1;

    private static final String LOG = "Profile Fragment";
    ListViewModel listViewModel;
    AddViewModel addViewModel;
    Bitmap bitmap=null;
    Integer check=1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile, container, false);

    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Activity activity = getActivity();
        Init(view);

        if(activity != null){
            String[] inputs = new String[] { Lvl.PRINCIPIANTE.getValue(), Lvl.AMATORIALE.getValue(), Lvl.AGONISTA.getValue()};
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, R.layout.list_items,inputs);

            lvlTXW.setAdapter(adapter);
            listViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(ListViewModel.class);
            addViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(AddViewModel.class);

            listViewModel.getUserbyID(Utilities.getCurrUser()).observe((LifecycleOwner) activity, new Observer<User>() {
                @Override
                public void onChanged(User user) {
                    name =user.getName();
                    String uri=user.getImgPath();
                    if(user.getImgPath()!=null){
                        if(user.getImgPath()=="ic_baseline_android_24"){
                            Drawable drawable = AppCompatResources.getDrawable(activity,activity.getResources().getIdentifier(uri,"drawable",activity.getPackageName()));
                            img.setImageDrawable(drawable);

                        }else{
                            img.setImageBitmap(Utilities.getImageBitmap(activity, Uri.parse(uri)));
                        }
                    }
                    lastname = user.getLast_name();
                    nameText.setText(""+name+ " "+user.getLast_name());
                    dateText.setText(user.getBirth());
                    cellText.setText(user.getCell());
                    userTIET.setText(user.getUsername());
                    username=user.getUsername();
                    passTIET.setText(user.getPassword());
                    password=user.getPassword();
                     lvlTXW.setHint(user.getLvl().getValue());
                     oldP=user.getImgPath();
                lvl=user.getLvl().getValue();
                }
            });


            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
bitmap= addViewModel.getImageBitmap().getValue();
String imageUri=null;
try{
    if(bitmap != null){
       Integer check=0;
   imageUri=String.valueOf(saveImage(bitmap,(AppCompatActivity) activity));
    }else{
        imageUri="ic_baseline_account_circle";
    }
}catch (FileNotFoundException e){
    e.printStackTrace();
}

            if(!userTIET.getText().toString().matches(""))
                username=userTIET.getText().toString();

            if(!passTIET.getText().toString().matches(""))
                    password=passTIET.getText().toString();

            if(!lvlTXW.getText().toString().matches(""))
                      lvl=lvlTXW.getText().toString();

                    if(imageUri==null){
                        addViewModel.updateUser(username,password,(lvl), Utilities.getCurrUser(),oldP);


                    }else{
                        addViewModel.updateUser(username,password,(lvl), Utilities.getCurrUser(),imageUri);
                    }
                }

            });


        }else{
            Log.e(LOG,"Activity is null");

        }
        addViewModel.getImageBitmap().observe((getViewLifecycleOwner()), new Observer<Bitmap>() {
            @Override
            public void onChanged(Bitmap bitmap) {
                img.setImageBitmap(bitmap);
            }
        });


     btnImg.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

                 Intent takepicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                 if(takepicture.resolveActivity(activity.getPackageManager())!=null){
                     activity.startActivityForResult(takepicture,REQUEST_IMAGE_CAPTURE);
                 }


         }
     });
    }

    private Uri saveImage(Bitmap value, AppCompatActivity activity) throws FileNotFoundException {
        String timestamp=new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ITALY).format(new Date());
        String name = "JPEG"+timestamp+".jpg";
        ContentResolver contentResolver = activity.getContentResolver();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME,name);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE,"image/jpg");
        Uri imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
        OutputStream outputStream = contentResolver.openOutputStream(imageUri);
addViewModel.setBitmap(bitmap);
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        try{outputStream.close();
        }catch(IOException e){
            e.printStackTrace();}
        return  imageUri;
    }

private void Init(View view){
     nameText = view.findViewById(R.id.name_text);
     dateText = view.findViewById(R.id.textDate);
     userTIET = view.findViewById(R.id.user_edittext);
     passTIET  = view.findViewById(R.id.pass_edittext);
     btnSave= view.findViewById(R.id.btnSave);
    lvlTXW = view.findViewById(R.id.lvl_type);
cellText=view.findViewById(R.id.cell_text);
img=view.findViewById(R.id.imgProfile);
btnImg=view.findViewById(R.id.btnImg);
}
    }
