package tmrapps.getinshapeapp.Category.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.LinkedList;
import java.util.List;

import tmrapps.getinshapeapp.Exercise.Model.Exercise;
import tmrapps.getinshapeapp.SubCategories.Model.SubCategory;

/**
 * Created by tamir on 1/20/2018.
 */
@Entity
public class Category {
    @PrimaryKey
    @NonNull
    private String id;
    private String name;
    //private long lastUpdated;

    @Ignore
    private List<Exercise> exercises;

    public Category(String name, String id) {
        this.name = name;
        this.id = id;
        this.exercises = new LinkedList<>();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {this.id = id;}

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*public void setLastUpdated(long lastUpdated) { this.lastUpdated = lastUpdated;}

    public long getLastUpdate() { return this.lastUpdated; }*/
}
