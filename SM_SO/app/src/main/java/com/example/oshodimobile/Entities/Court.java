package com.example.oshodimobile.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


    public enum Court {

            CEMENTO_SINTETICO("CEMENTO_SINTETICO"),TERRA_BATTUTA("TERRA_BATTUTA"), ERBA("ERBA");

        private final String value;

        Court(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }


        }

