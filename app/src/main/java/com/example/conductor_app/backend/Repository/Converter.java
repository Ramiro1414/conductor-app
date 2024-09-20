package com.example.conductor_app.backend.Repository;

import androidx.room.TypeConverter;

import java.sql.Timestamp;
import java.util.Date;

public class Converter {

    @TypeConverter
    public static Long fromDate(Date date) {
        if (date == null) {
            return null;
        }
        return date.getTime();
    }

    @TypeConverter
    public static Date toDate(Long timestamp) {
        if (timestamp == null) {
            return null;
        }
        return new Date(timestamp);
    }

    @TypeConverter
    public static Long fromTimestamp(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.getTime();
    }

    @TypeConverter
    public static Timestamp toTimestamp(Long timestamp) {
        if (timestamp == null) {
            return null;
        }
        return new Timestamp(timestamp);
    }
}
