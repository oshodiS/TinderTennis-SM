package com.example.oshodimobile.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.oshodimobile.Entities.User;

import java.util.List;
@Dao
public interface UserDAO {
@Insert(onConflict = OnConflictStrategy.IGNORE)
    void addUser(User user);


    @Transaction
    @Query("SELECT * FROM user ORDER BY user_id DESC")
    LiveData<List<User>> getUsers();

@Query("SELECT * FROM user WHERE user_id=:id")
    LiveData<User> getUserbyID(Integer id);

    @Query("SELECT user_id FROM user WHERE user_username LIKE :user AND user_password LIKE :password")
    LiveData<Integer> userLog(String user, String password);

    @Query("SELECT user_id FROM user WHERE user_username LIKE :user")
    LiveData<Integer> getUserbyUsername(String user);

    @Query ("UPDATE user SET user_username=:user, user_password =:password, lvl=:lvl, usr_img=:img WHERE user_id=:id")
    void updateUsers( String user, String password, String lvl, Integer id,String img);

}
