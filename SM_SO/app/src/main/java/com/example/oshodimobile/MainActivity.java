package com.example.oshodimobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.oshodimobile.ViewModel.ListViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    ListViewModel listViewModel;
    Button btnLog;
    TextInputEditText usernameTIET;
    TextInputEditText passTIET;
    TextView signTW;
    String userText;
    String passText;
    TextInputLayout nameILayout;
    TextInputLayout passILayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        listViewModel =  new ViewModelProvider((ViewModelStoreOwner) this).get(ListViewModel.class);
        Init();
        AppCompatActivity activity = this;


        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              userText=usernameTIET.getText().toString();
              passText=passTIET.getText().toString();
              if(FormValidation()){
                  listViewModel.userLog(userText,passText).observe(activity, new Observer<Integer>() {
                      @Override
                      public void onChanged(Integer idUsr) {
                          if (idUsr == null) {
                              nameILayout.setError("username o password non validi");
                              passILayout.setError("username o password non validi");

                          } else {
                              Utilities.setUser(idUsr);
                              Intent intent = new Intent(getBaseContext(), DashboardActivity.class);
                              startActivity(intent);
                          }
                      }

                  });


              }



            }
        });
        signTW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

    }


    private boolean FormValidation(){
        boolean flag=true;
        if(userText.matches("")){
            nameILayout.setError("inserisci username");
           flag =false;
        }
        if(passText.matches("")){
            passILayout.setError("inserisci password");
          flag=false;
        }
        return flag;
    }

    private void Init(){
        btnLog=findViewById(R.id.btnSign);
        usernameTIET= findViewById(R.id.name_edittext);
        passTIET=findViewById(R.id.psw_edittext);
        nameILayout = findViewById(R.id.name_textinput);
        passILayout = findViewById(R.id.psw_textinput);
        signTW=findViewById(R.id.sign_text);

    }

}