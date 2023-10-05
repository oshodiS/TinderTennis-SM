package com.example.oshodimobile;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.oshodimobile.Entities.Court;
import com.example.oshodimobile.Entities.Lvl;
import com.example.oshodimobile.Entities.Match;
import com.example.oshodimobile.Entities.User;
import com.example.oshodimobile.ViewModel.AddViewModel;
import com.example.oshodimobile.ViewModel.ListViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.xml.validation.Validator;

public class SignUpActivity extends AppCompatActivity {
    ListViewModel listViewModel;
    AddViewModel addViewModel;
    Button btnSign;
    Button btnImg;
    TextInputEditText nameTIET;
    TextInputLayout nameILayout;
    TextInputEditText lastnameTIET;
    TextInputLayout lastnameILayout;
    TextInputEditText usernameTIET;
    TextInputLayout userILayout;
    TextInputEditText passTIET;
    TextInputLayout passILayout;
    TextInputEditText dateTIET;
    TextInputLayout dateIlayout;
    TextInputEditText cellTIET;
    TextInputLayout cellIlayout;
    AutoCompleteTextView lvlTXW;
    TextInputLayout lvlIlayout;
    ImageView imgprof;
    final Calendar myCalendar= Calendar.getInstance();
    private  final MutableLiveData<Bitmap> imageBitmap = new MutableLiveData<>();


    static final Integer REQUEST_IMAGE_CAPTURE =1;
public void initializeBitMap(){
    Drawable drawable = ResourcesCompat.getDrawable(getApplication().getResources(),R.drawable.ic_launcher_foreground,getApplication().getTheme());

Bitmap bitmap=Utilities.drawableToBitmap(drawable);
imageBitmap.setValue(bitmap);
}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        imgprof=findViewById(R.id.imgProf);
        Init();
        initializeBitMap();
        Utilities.setDatePicker(dateTIET,myCalendar,false);
        listViewModel =  new ViewModelProvider((ViewModelStoreOwner) this).get(ListViewModel.class);
        addViewModel =   new ViewModelProvider((ViewModelStoreOwner) this).get(AddViewModel.class);

        String[] inputs = new String[] { Lvl.PRINCIPIANTE.getValue(), Lvl.AMATORIALE.getValue(), Lvl.AGONISTA.getValue(),Lvl.PROFESSIONISTA.getValue()};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_items,inputs);
        AutoCompleteTextView menu = findViewById(R.id.lvl_type);
        menu.setAdapter(adapter);
        AppCompatActivity activity = this;
        btnSign.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(FromValidation()){listViewModel.getUserByUsername(usernameTIET.getText().toString()).observe(activity, new Observer<Integer>() {
                @Override
                public void onChanged(Integer idUsr) {
                    String uri="ic_baseline_android_24";
                    try{
                        if(imageBitmap != null)
                             uri=String.valueOf(saveImage(imageBitmap.getValue(),activity));
                    }catch (FileNotFoundException e){
                        uri="ic_baseline_android_24";
                    }
                    if(idUsr==null){
                        addViewModel.addUser(new User(nameTIET.getText().toString(),
                                lastnameTIET.getText().toString(),
                                dateTIET.getText().toString(),
                                passTIET.getText().toString(),
                                usernameTIET.getText().toString(),
                                cellTIET.getText().toString(),
                                Lvl.valueOf(lvlTXW.getText().toString()),uri)
                        );
                        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                        startActivity(intent);
                    }else{
                        userILayout.setError("username già esistente");

                    }


                    }


                });

            }
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
        imageBitmap.getValue().compress(Bitmap.CompressFormat.JPEG,100,outputStream);
try{outputStream.close();
}catch(IOException e){
e.printStackTrace();}
        return  imageUri;
}


    private boolean FromValidation(){
        boolean flag=true;
        if(nameTIET.getText().toString().matches("") ||
                !nameTIET.getText().toString().matches("^[A-Za-z]+$")
        ){
            nameILayout.setError("inserisci nome valido");
            flag =false;
        }
        if(lastnameTIET.getText().toString().matches("") ||!lastnameTIET.getText().toString().matches("^[A-Za-z]+$") ){
            lastnameILayout.setError("inserisci cognome valido");
            flag=false;
        }
        if(dateTIET.getText().toString().matches("")){
            dateIlayout.setError("inserisci data");
            flag =false;
        }
            if(passTIET.getText().toString().matches("")){
                passILayout.setError("inserisci password");
                flag =false;
            }
                if(usernameTIET.getText().toString().matches("")){
                    userILayout.setError("inserisci username");
                    flag =false;
                }
        if(lvlTXW.getText().toString().matches("")){
            lvlIlayout.setError("inserisci livello esperienza");
            flag =false;
        }

        if(cellTIET.getText().toString().matches("") ||
                !PhoneNumberUtils.isGlobalPhoneNumber(cellTIET.getText().toString())){
            cellIlayout.setError("inserisci numero di cellulare valido");
            flag =false;
        }



                    return flag;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data){
    super.onActivityResult(requestCode,resultCode,data);
    if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
        Bundle extras = data.getExtras();
        if(extras != null){
            extras.get("data");
imgprof=findViewById(R.id.imgProfile);
        imgprof.setImageBitmap((Bitmap) extras.get("data"));
        }
    }

    }

    private void Init(){
        btnSign = findViewById(R.id.btnSign);
        nameTIET = findViewById(R.id.name_edittext);
        nameILayout = findViewById(R.id.name_textinput);

        lastnameTIET = findViewById(R.id.lastname_edittext);
        lastnameILayout = findViewById(R.id.lastname_textinput);

        usernameTIET = findViewById(R.id.user_edittext);
        userILayout = findViewById(R.id.user_textinput);

        dateTIET = findViewById(R.id.date_edittext);
        dateIlayout = findViewById(R.id.date_textinput);

        passTIET = findViewById(R.id.psw_edittext);
        passILayout = findViewById(R.id.psw_textinput);

        lvlTXW=findViewById(R.id.lvl_type);
        lvlIlayout=findViewById(R.id.exp_spinner);

       cellTIET=findViewById(R.id.cell_textedit);
        cellIlayout=findViewById(R.id.cell_textinput);
        btnImg=findViewById(R.id.btnImg);

    }

}
