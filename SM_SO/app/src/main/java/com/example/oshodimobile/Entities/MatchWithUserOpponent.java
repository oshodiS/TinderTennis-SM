package com.example.oshodimobile.Entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class MatchWithUserOpponent {
    @Embedded
    Match match;

    @Relation(entity = Match.class,parentColumn = "match_id", entityColumn = "match_id")
    List<Match> matchList;
    @Relation(entity = User.class,parentColumn = "match_opponent_user",entityColumn = "user_id")
    List<User> userList;

    public List<Match> getMatchList() {
        return matchList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public void setMatchList(List<Match> matchList) {
        this.matchList = matchList;
    }

}
