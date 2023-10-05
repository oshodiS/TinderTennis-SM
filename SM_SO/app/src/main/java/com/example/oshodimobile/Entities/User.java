package com.example.oshodimobile.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "user")
public class User {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id" )
    private  int id;
    @ColumnInfo(name = "user_name" )
    String name;
    @ColumnInfo(name = "user_lastname" )
    String last_name;
    @ColumnInfo(name = "user_birth" )
    String birth;
    @ColumnInfo(name = "user_password" )
    String password;
    @ColumnInfo(name = "user_username" )
    String username;
    @ColumnInfo(name = "user_cell" )
    String cell;
    @ColumnInfo(name = "usr_img" )
    String imgPath;



   Lvl lvl;

    public User(String name, String last_name, String birth, String password, String username, String cell, Lvl lvl,String imgPath) {
        this.name = name;
        this.last_name = last_name;
        this.birth = birth;
        this.password = password;
        this.username = username;
        this.lvl = lvl;
        this.cell = cell;
        this.imgPath=imgPath;

    }


    public void setBirth(String birth) {
        this.birth = birth;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setLvl(Lvl lvl) {
        this.lvl = lvl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Lvl getLvl() {
        return lvl;
    }

    public String getBirth() {
        return birth;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCell() {
        return cell;
    }

    public String getUsername() {
        return username;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getImgPath() {
        return imgPath;
    }
}
