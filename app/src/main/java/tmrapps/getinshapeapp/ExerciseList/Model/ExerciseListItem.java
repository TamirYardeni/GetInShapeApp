package tmrapps.getinshapeapp.ExerciseList.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.firebase.database.ServerValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tamir on 2/5/2018.
 * This is the exercise list of a specific category
 */
@Entity
public class ExerciseListItem {
    @PrimaryKey
    @NonNull
    private String categoryId;

    private String exerciseItem;

    private long lastUpdateDate;

    @Ignore
    private List<ExerciseInCategory> exercisesInCategory;

    public ExerciseListItem() {
        this.exercisesInCategory = new ArrayList<>();
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public long getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(long lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getExerciseItem() {
        return exerciseItem;
    }

    public void setExerciseItem(String exerciseItem) {
        this.exerciseItem = exerciseItem;
        this.exercisesInCategory = new ArrayList<>();
        ExerciseListItem.exerciseItemToMap(exerciseItem).forEach((key,val) -> {
            ExerciseInCategory exerciseInCategory = new ExerciseInCategory();
            exerciseInCategory.setId(key);
            exerciseInCategory.setName(val);
            this.exercisesInCategory.add(exerciseInCategory);
        });
    }

    /*public List<ExerciseInCategory> getExerciseInCategoryFromExerciseItem() {
        ExerciseListItem.exerciseItemToMap(this.exerciseItem).forEach((key,val) -> {
            ExerciseInCategory exerciseInCategory = new ExerciseInCategory();
            exerciseInCategory.setId(key);
            exerciseInCategory.setName(val);
            this.exercisesInCategory.add(exerciseInCategory);
        });
    }*/

    public List<ExerciseInCategory> getExercisesInCategory() {
        return exercisesInCategory;
    }

    public void setExercisesInCategory(List<ExerciseInCategory> exercisesInCategory) {
        this.exercisesInCategory = exercisesInCategory;
        StringBuilder stringBuilder = new StringBuilder();
        exercisesInCategory.stream().forEach((item) ->{
            stringBuilder.append(item.getId());
            stringBuilder.append("@");
            stringBuilder.append(item.getName());
            stringBuilder.append(";");
        });

        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        this.exerciseItem = stringBuilder.toString();
    }


    /**
     * This is a function that maps the object to name & value
     * It helps inserting the object to the firebase database
     * @return
     */
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("categoryId", this.categoryId);
        result.put("exerciseItem", exerciseItemToMap(this.exerciseItem));
        result.put("lastUpdated", ServerValue.TIMESTAMP);
        return  result;
    }

    /**
     * '31231@nameEx;3332@name2'... to Hashmap
     * @return
     */
    public static Map<String, String> exerciseItemToMap(String exerciseItemStr) {
        HashMap<String, String> result = new HashMap<>();
        String[] exercises = exerciseItemStr.split(";");
        for (String item: exercises) {
            String[] params = item.split("@");
            // key= id, value = name
            result.put(params[0],params[1]);
        }

        return result;
    }

    /**
     * Hashmap to '31231@nameEx;3332@name2'...
     * @return
     */
    public static String exerciseItemToMap(HashMap<String, Object> exerciseItemHash) {
        /*exerciseItemHash.put()

        HashMap<String, Object> result = new HashMap<>();
        String[] exercises = exerciseItemStr.split(";");
        for (String item: exercises) {
            String[] params = item.split("@");
            result.put("id",params[0]);
            result.put("exerciseName",params[1]);
        }
*/
        return null;
    }
}
