package com.example.oshodimobile.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.oshodimobile.Entities.Match;
import com.example.oshodimobile.Entities.User;

import java.util.List;
@Dao
public interface MatchDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addMatch(Match match);


    @Transaction
    @Query("SELECT * FROM `match` ORDER BY match_id DESC")
    LiveData<List<Match>> getMatch();
   @Query("SELECT user_id, user_username, user_birth, user_cell,user_lastname,usr_img  FROM `match` "+"INNER JOIN user ON match_creator_user = user_id ORDER BY match_id DESC")
    LiveData<List<User>> getCreatorUsers();
    @Query ("UPDATE `match` SET match_opponent_user=:user, match_status =:status WHERE match_id =:match")
    void updateMatch( Integer user, String status,Integer match);

}
