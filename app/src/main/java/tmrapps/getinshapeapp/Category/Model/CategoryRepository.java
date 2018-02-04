package tmrapps.getinshapeapp.Category.Model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

/**
 * Created by tamir on 2/4/2018.
 */

public class CategoryRepository {
    public static final CategoryRepository instance = new CategoryRepository();

    MutableLiveData<List<Category>> categoriesLiveData;

    CategoryRepository() {

    }

    public LiveData<List<Category>> getCategories() {
        synchronized (this) {
            if (this.categoriesLiveData == null) {
                this.categoriesLiveData = new MutableLiveData<List<Category>>();
                CategoryFirebase.getCategoriesAndObserve((data) -> {
                    if (data != null) {
                        this.categoriesLiveData.setValue(data);
                    }
                });
            }
        }

        return this.categoriesLiveData;
    }

    /**
     * Add new category to firebase with the category name
     * @param categoryName
     */
    public void addCategory(String categoryName) {
        CategoryFirebase.addCategory(categoryName);
    }
}
