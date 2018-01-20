package tmrapps.getinshapeapp.Main.Motivation.Model;

import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.URLUtil;
import android.widget.ImageView;

import java.io.Serializable;

import tmrapps.getinshapeapp.Main.FirebaseModel;

/**
 * Created by tamir on 1/17/2018.
 */

public class Motivation implements Serializable {
    String id;
    String imageSrc;
    ImageView img;

    public Motivation(String id, String imageSrc) {
        this.id = id;
        this.imageSrc = imageSrc;
    }

    public String getId() {
        return this.id;
    }

    public String getImageSrc() {
        return this.imageSrc;
    }

    public void setImageSrc(String url) {
        this.imageSrc = url;
    }
}

