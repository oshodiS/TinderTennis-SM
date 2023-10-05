package com.example.oshodimobile.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "match")
public class Match {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "match_id" )
    private  Integer id;
    @ColumnInfo(name = "match_creator_user" )
    private Integer userCreatorId;

    @ColumnInfo(name = "match_opponent_user" )
    private Integer userOpponentId;
    @ColumnInfo(name = "match_longDesc" )

    private String longDesc;
    @ColumnInfo(name = "match_date" )

    private String date;
    @ColumnInfo(name = "match_hour" )

    private String hour;
    @ColumnInfo(name = "match_cost" )

    private float cost;
    @ColumnInfo(name = "match_court" )

    private Court court;
    @ColumnInfo(name = "match_status" )

    private Status status;
    @ColumnInfo(name = "match_place" )

    private String place;

    @ColumnInfo(name = "match_lat" )
    private Double lat;
    @ColumnInfo(name = "match_longi" )
    private Double longi;
    @ColumnInfo(name = "match_imgPath" )

    private String imgPath;


        public Match(Integer userCreatorId,
                        String longDesc,
                        String date,
                        String hour,
                        float cost,
                        Court court,
                        String place,
                        Double lat,
                         Double longi,
                        String imgPath) {

            this.userCreatorId = userCreatorId;
            this.longDesc = longDesc;
            this.date = date;
            this.hour = hour;
            this.cost = cost;
            this.court = court;
            this.place = place;
            this.imgPath = imgPath;
            this.status = Status.IN_CORSO;
            this.longi = longi;
            this.lat = lat;

        }

        public void setOpponent(Integer userOpponent) {
            this.userOpponentId = userOpponent;
        }

        public Court getCourt() {
            return court;
        }

        public float getCost() {
            return cost;
        }

        public String getDate() {
            return date;
        }

        public String getHour() {
            return hour;
        }

        public Status getStatus() {
            return status;
        }

        public String getLongDesc() {
            return longDesc;
        }

        public String getPlace() {
            return place;
        }

        public Integer getUserOpponent() {
            return userOpponentId;
        }

        public Integer getUserCreator() {
            return userCreatorId;
        }

        public String getImgPath() {
            return imgPath;
        }

        public void setImgPath(String imgPath) {
            this.imgPath = imgPath;
        }

        public void setStatus(Status status) {
            this.status = status;
        }
        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public void setCourt(Court court) {
        this.court = court;
    }

    public Integer getUserCreatorId() {
        return userCreatorId;
    }

    public Integer getUserOpponentId() {
        return userOpponentId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setUserCreatorId(Integer userCreatorId) {
        this.userCreatorId = userCreatorId;
    }

    public void setUserOpponentId(Integer userOpponentId) {
        this.userOpponentId = userOpponentId;
    }

    public Double getLongi() {
        return longi;
    }

    public Double getLat() {
        return lat;
    }

    public void setLongi(Double longi) {
        this.longi = longi;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }
}
