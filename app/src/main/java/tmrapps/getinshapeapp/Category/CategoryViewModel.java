package tmrapps.getinshapeapp.Category;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import tmrapps.getinshapeapp.Category.Model.Category;
import tmrapps.getinshapeapp.Category.Model.CategoryRepository;

/**
 * Created by tamir on 2/4/2018.
 */

public class CategoryViewModel extends ViewModel {

    private LiveData<List<Category>> categories;

    public CategoryViewModel() {
        this.categories = CategoryRepository.instance.getCategories();
    }

    public LiveData<List<Category>> getCategories() {
        return categories;
    }

    public void addCategory(String categoryName) {
        CategoryRepository.instance.addCategory(categoryName);
    }
}
