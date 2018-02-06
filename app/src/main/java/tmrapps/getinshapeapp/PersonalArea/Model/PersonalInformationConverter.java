package tmrapps.getinshapeapp.PersonalArea.Model;

import android.arch.persistence.room.TypeConverter;

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
}
