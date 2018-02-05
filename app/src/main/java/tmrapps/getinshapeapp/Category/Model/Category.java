package tmrapps.getinshapeapp.Category.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
    private long lastUpdated;

    public Category() {

    }

    public Category(String name, String id) {
        this.name = name;
        this.id = id;
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

    public void setLastUpdated(long lastUpdated) { this.lastUpdated = lastUpdated;}

    public long getLastUpdated() { return this.lastUpdated; }

    /**
     * This is a function that maps the object to name & value
     * It helps inserting the object to the firebase database
     * @return
     */
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("name", name);
        result.put("lastUpdated", ServerValue.TIMESTAMP);
        return  result;
    }
}
