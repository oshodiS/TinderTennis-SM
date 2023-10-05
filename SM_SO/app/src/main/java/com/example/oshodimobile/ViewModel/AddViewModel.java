package com.example.oshodimobile.ViewModel;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.oshodimobile.Database.DatabaseRepository;
import com.example.oshodimobile.Entities.Match;
import com.example.oshodimobile.Entities.User;
import com.example.oshodimobile.R;
import com.example.oshodimobile.Utilities;

import java.util.List;

public class AddViewModel extends AndroidViewModel {
private  final Application application;
private final MutableLiveData<Bitmap> imageBitmap= new MutableLiveData<>();
    DatabaseRepository repository;
    public AddViewModel( @NonNull Application application) {
        super(application);
        repository = new DatabaseRepository(application);
this.application=application;
initializeBitmap();
    }
    public  void setBitmap(Bitmap bitmap){
        imageBitmap.setValue(bitmap);

    }
public  MutableLiveData<Bitmap> getImageBitmap(){
        return imageBitmap;
}
public void initializeBitmap(){
    Drawable drawable = ResourcesCompat.getDrawable(this.application.getResources(), R.drawable.ic_launcher_foreground,application.getTheme());
Bitmap bitmap = Utilities.drawableToBitmap(drawable);
imageBitmap.setValue(bitmap);
    }
    public void addMatch(Match match){repository.addMatch(match);}
    public void addUser(User user){repository.addUser(user);}
public  void updateUser(String user, String password, String lvl, Integer id, String img){repository.updateUseres(user,password,lvl,id,img);}
    public  void updateMatch(Integer user, String status, Integer match){repository.updateMatch(user,status, match);}

}
