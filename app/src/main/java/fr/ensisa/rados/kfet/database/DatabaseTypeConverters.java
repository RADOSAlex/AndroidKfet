package fr.ensisa.rados.kfet.database;

import androidx.room.TypeConverter;

import java.util.Date;

public class DatabaseTypeConverters {

    @TypeConverter
    public static Date long2Date (long time) {
        return new Date (time);
    }

    @TypeConverter
    public static long date2Long (Date date) {
        if (date == null) return 0;
        return date.getTime();
    }
}
