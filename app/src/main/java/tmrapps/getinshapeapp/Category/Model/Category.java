package tmrapps.getinshapeapp.Category.Model;

import java.util.LinkedList;
import java.util.List;

import tmrapps.getinshapeapp.SubCategories.Model.SubCategory;

/**
 * Created by tamir on 1/20/2018.
 */

public class Category {
    private String id;
    private String name;
    private List<SubCategory> subCategories;

    public Category(String name) {
        this.name = name;
        this.subCategories = new LinkedList<>();
    }

    public String getId() {return this.id;}

    public void setId(String id) {this.id = id;}

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SubCategory> getSubCategories() {
        return this.subCategories;
    }

    public void setSubCategories(List<SubCategory> newSubCategories) {
        this.subCategories = newSubCategories;
    }


}
