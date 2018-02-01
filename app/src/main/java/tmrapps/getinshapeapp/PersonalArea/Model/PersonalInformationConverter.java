package tmrapps.getinshapeapp.PersonalArea.Model;

import android.arch.persistence.room.TypeConverter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by nathan on 01/02/2018.
 */

public class PersonalInformationConverter {
    @TypeConverter
    public PersonalInformation storedStringToLanguages(String value) {
        List<String> langs = Arrays.asList(value.split("\\s*,\\s*"));
        return new PersonalInformation();
    }

    @TypeConverter
    public String languagesToStoredString(PersonalInformation cl) {
        String value = "";

        for (String lang :cl.getDayOfWeek())
            value += lang + ",";

        return value;
    }
}
