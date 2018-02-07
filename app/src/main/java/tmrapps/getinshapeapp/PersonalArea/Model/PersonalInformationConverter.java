package tmrapps.getinshapeapp.PersonalArea.Model;

import android.arch.persistence.room.TypeConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by nathan on 01/02/2018.
 */

public class PersonalInformationConverter {
    @TypeConverter
    public Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public Long dateToTimestamp(Date date) {
        if (date == null) {
            return null;
        } else {
            return date.getTime();
        }
    }

    @TypeConverter
    public List<String> fromString(String value){
        List<String> dayOfWeek = new ArrayList<>();

        if(value != null) {
            String[] parts = value.split(",");

            for (String item :
                    parts) {
                dayOfWeek.add(item);
            }

            return dayOfWeek;
        }

        return dayOfWeek;
    }

    @TypeConverter
    public String listToString(List<String> data) {
        if(data != null) {
            StringBuilder build = new StringBuilder();
            for (String item :
                    data) {
                build.append(item + ",");
            }

            return build.toString();
        }

        return null;
    }
}
