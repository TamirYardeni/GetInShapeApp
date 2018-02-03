package tmrapps.getinshapeapp.Exercise.Model;

import android.graphics.Bitmap;

/**
 * Created by tamir on 2/1/2018.
 */

public class Exercise {

    private String _catId;
    private String _name;
    private String _data;
    private String _note;
    private String _url;

    public Exercise() {

    }

    public String getId() {
        return "1";
    }

    public void setName(String name) {
        this._name = name;
    }

    public void setData(String data) {
        this._data = data;
    }

    public void setNotes(String notes) {
        this._note = notes;
    }

    public String getName() {
        return this._name;
    }

    public String getData() {
        return this._data;
    }

    public String getNotes() {
        return this._note;
    }

    public String getCatId() {
        return this._catId;
    }

    public void setCatId(String catId) {
        this._catId = catId;
    }

    public String getUrl() {
        return this._url;
    }

    public void setUrl(String url) {
        this._url = url;
    }
}
