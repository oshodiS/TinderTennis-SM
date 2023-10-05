package com.example.oshodimobile.Database;

import androidx.room.TypeConverter;

import com.example.oshodimobile.Entities.Court;
import com.example.oshodimobile.Entities.Lvl;
import com.example.oshodimobile.Entities.Status;

class Converter {

    @TypeConverter
    public static String fromLvlToString(Lvl lvl) {
        return lvl.getValue();
    }

    /**
     * Convert an integer to Health
     */
    @TypeConverter
    public static Lvl fromStringToLvl(String lvl) {
        return Lvl.valueOf(lvl);
    }

    @TypeConverter
    public static String fromCourtToString(Court court) {
        return court.getValue();
    }

    /**
     * Convert an integer to Health
     */
    @TypeConverter
    public static Status fromStringToStatus(String status) {
        return Status.valueOf(status);
    }

    @TypeConverter
    public static String fromStatusToString(Status status) {
        return status.getValue();
    }

    @TypeConverter
    public static Court fromStringToCourt(String court) {
        return Court.valueOf(court);
    }
}
