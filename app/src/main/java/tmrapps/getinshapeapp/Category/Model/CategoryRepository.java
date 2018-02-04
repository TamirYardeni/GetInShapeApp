package tmrapps.getinshapeapp.Category.Model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import tmrapps.getinshapeapp.GetInShapeApp;

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

 /*   class GetCategoriesTatk extends AsyncTask<List<Category>, String, List<Category>>{

        @Override
        protected List<Category> doInBackground(List<Category>[] lists) {
            if (lists.length > 0) {
                List<Category> data = lists[0];
                long lastUpdateDate = 0;
                try {
                    lastUpdateDate = GetInShapeApp.getMyContext()
                            .getSharedPreferences("TAG", Context.MODE_PRIVATE).getLong("lastUpdateDate", 0);
                } catch (Exception ex) {

                }

                if (data != null && data.size() > 0) {
                    long reacentUpdate = lastUpdateDate;

                }
            }
        }
    }*/
}
