package com.example.oshodimobile.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

   public enum Lvl {

        PRINCIPIANTE("PRINCIPIANTE"),
        AMATORIALE("AMATORIALE"),
        AGONISTA("AGONISTA"),
       PROFESSIONISTA("PROFESSIONISTA");


       private final String value;

        Lvl(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

