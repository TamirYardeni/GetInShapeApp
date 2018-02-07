package tmrapps.getinshapeapp.Exercise.Model;

import android.arch.persistence.room.Entity;

/**
 * Created by tamir on 2/5/2018.
 */
public class ExerciseInCategory {
    private String id;

    // The exercise name
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String exerciseName) {
        this.name = exerciseName;
    }
}
