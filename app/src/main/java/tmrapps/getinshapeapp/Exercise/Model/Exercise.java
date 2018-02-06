package tmrapps.getinshapeapp.Exercise.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tamir on 2/1/2018.
 */
@Entity
public class Exercise {

    @PrimaryKey
    @NonNull
    private String id;
    private String categoryId;
    private String name;
    private String data;
    private String note;
    private String url;
    private long lastUpdateDate;

    public Exercise() {

    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {this.id = id;}

    public void setName(String name) {
        this.name = name;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getName() {
        return this.name;
    }

    public String getData() {
        return this.data;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(String catId) {
        this.categoryId = catId;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(long lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }


    /**
     * This is a function that maps the object to name & value
     * It helps inserting the object to the firebase database
     * @return
     */
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", this.id);
        result.put("categoryId", this.categoryId);
        result.put("name", this.name);
        result.put("data", this.data);
        result.put("note", this.note);
        result.put("url", this.url);
        result.put("lastUpdateDate", ServerValue.TIMESTAMP);
        return  result;
    }
}
