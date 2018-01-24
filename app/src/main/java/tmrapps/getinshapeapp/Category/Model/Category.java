package tmrapps.getinshapeapp.Category.Model;

/**
 * Created by tamir on 1/20/2018.
 */

public class Category {
    private String id;
    private String name;

    public Category(String name) {
        this.name = name;
    }

    public String getId() {return this.id;}

    public void setId(String id) {this.id = id;}

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
