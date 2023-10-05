package com.example.oshodimobile.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.oshodimobile.Database.DatabaseRepository;
import com.example.oshodimobile.Entities.Match;
import com.example.oshodimobile.Entities.MatchWithUser;
import com.example.oshodimobile.Entities.MatchWithUserOpponent;
import com.example.oshodimobile.Entities.User;

import java.util.List;

public class ListViewModel extends AndroidViewModel {

    public LiveData<List<User>> userCreator;
    public LiveData<List<User>> userList;
    public LiveData<List<Match>> matchList;
    public LiveData<List<MatchWithUser>> matchWithUserlist;
    public LiveData<List<MatchWithUser>> matchWithUserlistOpen;
    DatabaseRepository repository;
    public ListViewModel( @NonNull Application application) {
        super(application);
        repository = new DatabaseRepository(application);
        userList = repository.getUserList();
        matchList = repository.getMatchList();
        userCreator = repository.getUserCreators();
        matchWithUserlist = repository.getMatchUserList();
        matchWithUserlistOpen = repository.getMatchUserListOpen();

    }

    public LiveData<List<User>> getUserList() {
        return userList;
    }
    public LiveData<List<Match>> getMatchList() {
        return matchList;
    }
public LiveData<Integer> userLog(String username, String password){
        return repository.userLog(username,password);
}

    public LiveData<Integer> getUserByUsername(String username){
        return repository.getUserbyUsername(username);
    }
    public LiveData<List<MatchWithUser>> getAcceptedMatch(Integer id ){
        return repository.getMatchAccepted(id);

    }
    public LiveData<User> getUserbyID(Integer id){
        return repository.getUserbyID(id);

    }

    public LiveData<List<MatchWithUser>> getMatchWithUser(){
        return matchWithUserlist;
    }
    public LiveData<List<MatchWithUser>> getMatchWithUserlistOpen(){
        return matchWithUserlistOpen;
    }
    public LiveData<MatchWithUser> getMatchWithUserbyId(Integer id){
        return repository.getMatchUserbyId(id);
    }

    public LiveData<List<MatchWithUser>> getMatchWithUserbyUserId(Integer id){
        return repository.getMatchUserbyUserId(id);
    }

    public LiveData<List<User>> getUserCreator() {
        return userCreator;
    }
}
