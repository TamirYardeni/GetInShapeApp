package tmrapps.getinshapeapp.Category.Model;

import java.util.LinkedList;
import java.util.List;

import tmrapps.getinshapeapp.Exercise.Model.Exercise;
import tmrapps.getinshapeapp.SubCategories.Model.SubCategory;

/**
 * Created by tamir on 1/20/2018.
 */

public class Category {
    private String id;
    private String name;
    private List<Exercise> exercises;

    public Category(String name) {
        this.name = name;
        this.exercises = new LinkedList<>();
    }

    public Category(String name, String id) {
        this.name = name;
        this.id = id;
        this.exercises = new LinkedList<>();
    }

    public String getId() {
        //return this.id;
        return this.id;
    }

    public void setId(String id) {this.id = id;}

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
