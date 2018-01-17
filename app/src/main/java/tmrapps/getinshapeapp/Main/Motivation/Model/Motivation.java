package tmrapps.getinshapeapp.Main.Motivation.Model;

import java.io.Serializable;

/**
 * Created by tamir on 1/17/2018.
 */

public class Motivation implements Serializable {
    String id;
    String imageSrc;

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
}

