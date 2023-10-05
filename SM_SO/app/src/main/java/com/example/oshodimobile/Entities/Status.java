package com.example.oshodimobile.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

    public enum Status {

        COMPLETATA("COMPLETATA"),
        IN_CORSO("IN_CORSO");

        private final String value;

        Status(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
