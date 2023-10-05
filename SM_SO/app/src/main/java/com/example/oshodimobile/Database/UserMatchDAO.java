package com.example.oshodimobile.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.oshodimobile.Entities.MatchWithUser;
import com.example.oshodimobile.Entities.MatchWithUserOpponent;
import com.example.oshodimobile.Entities.User;

import java.util.List;
@Dao
public interface UserMatchDAO {

    @Query("SELECT *  FROM `match` "+"INNER JOIN user ON match_creator_user = user_id ORDER BY match_id DESC")
    LiveData<List<MatchWithUser>> getMatchWithUser();

    @Query("SELECT *  FROM `match` "+"INNER JOIN user ON match_creator_user = user_id WHERE match_status LIKE 'IN_CORSO' ORDER BY match_id DESC")
    LiveData<List<MatchWithUser>> getMatchWithUserOpen();
    @Query("SELECT *  FROM `match` "+"INNER JOIN user ON match_creator_user = user_id  WHERE match_id=:id ")
    LiveData<MatchWithUser> getMatchWithUserbyId(Integer id);
    @Query("SELECT *  FROM `match` "+"INNER JOIN user ON match_creator_user = user_id  WHERE match_creator_user=:id ORDER BY match_id DESC")
    LiveData<List<MatchWithUser>> getMatchWithUserbyUserId(Integer id);
    @Query("SELECT *  FROM `match` "+"INNER JOIN user ON match_creator_user = user_id WHERE match_opponent_user=:id AND match_status LIKE 'COMPLETATA' ORDER BY match_id DESC ")
    LiveData<List<MatchWithUser>> getMatchAccepted(Integer id);

}
