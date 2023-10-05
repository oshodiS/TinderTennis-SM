package com.example.oshodimobile.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.oshodimobile.Entities.Match;
import com.example.oshodimobile.Entities.MatchWithUser;
import com.example.oshodimobile.Entities.MatchWithUserOpponent;
import com.example.oshodimobile.Entities.User;

import java.util.List;

public class DatabaseRepository {

    private final UserDAO userDAO;
    private final MatchDAO matchDAO;
    private final UserMatchDAO matchWithUserDAO;
    private final LiveData<List<User>> userList;
    private final LiveData<List<Match>> matchList;
    private final LiveData<List<MatchWithUser>> matchUserList;
    private final LiveData<List<MatchWithUser>> matchUserListOpen;
    public DatabaseRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        userDAO = db.userDAO();
        matchDAO = db.matchDAO();
        matchWithUserDAO=db.userMatchDAO();
        userList = userDAO.getUsers();
        matchList = matchDAO.getMatch();
        matchUserList = matchWithUserDAO.getMatchWithUser();
        matchUserListOpen = matchWithUserDAO.getMatchWithUserOpen();

    }


    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<User>> getUserList() {
        return userList;
    }
    public LiveData<List<Match>> getMatchList() {
        return matchList;
    }
    public LiveData<User> getUserbyID(Integer user) {
        return userDAO.getUserbyID(user);
    }
    public LiveData<Integer> userLog(String user, String password){return  userDAO.userLog(user,password);}
    public LiveData<Integer> getUserbyUsername(String user){return  userDAO.getUserbyUsername(user);}
    public LiveData<List<MatchWithUser>> getMatchAccepted(Integer id){return matchWithUserDAO.getMatchAccepted(id);
        }

    public LiveData<List<MatchWithUser>> getMatchUserList() {
        return matchUserList;
    }
    public LiveData<List<MatchWithUser>> getMatchUserListOpen() {
        return matchUserListOpen;
    }

    public LiveData<List<User>> getUserCreators() {
        return matchDAO.getCreatorUsers();
    }

    public void addUser(User user) {
        AppDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                userDAO.addUser(user);
            }
        });
    }
public  void updateUseres(String user, String password, String lvl, Integer id, String img){
    AppDatabase.executor.execute(new Runnable() {
        @Override
        public void run() {
            userDAO.updateUsers(user, password,  lvl,  id,img);
        }
    });
}
    public  void updateMatch(Integer user, String status, Integer match){
        AppDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                matchDAO.updateMatch(user, status,match);
            }
        });
    }
    public void addMatch(Match match) {
        AppDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                matchDAO.addMatch(match);
            }
        });
    }

    public LiveData<MatchWithUser> getMatchUserbyId(Integer id) {
        return matchWithUserDAO.getMatchWithUserbyId(id);
    }
    public LiveData<List<MatchWithUser>> getMatchUserbyUserId(Integer id) {
        return matchWithUserDAO.getMatchWithUserbyUserId(id);
    }


}
