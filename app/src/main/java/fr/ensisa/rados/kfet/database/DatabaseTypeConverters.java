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
    @TypeConverter
    public static Double long2Double (long price) {

        return Double.valueOf(price).doubleValue();
    }

    @TypeConverter
    public static long double2Long (Double price) {
        if (price == null) return 0;
        return price.longValue();
    }

    @TypeConverter
    public static Integer long2integer (long price) {

        return Integer.valueOf((int) price).intValue();
    }

    @TypeConverter
    public static long integer2Long (Integer price) {
        if (price == null) return 0;
        return price.longValue();
    }

}
