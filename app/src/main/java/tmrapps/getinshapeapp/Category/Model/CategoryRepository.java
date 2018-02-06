package tmrapps.getinshapeapp.Category.Model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.util.List;

import tmrapps.getinshapeapp.GetInShapeApp;
import tmrapps.getinshapeapp.Main.AppLocalStore;

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

                // Get the last update date
                long lastUpdateDate = 0;
                try {
                    lastUpdateDate = GetInShapeApp.getMyContext()
                            .getSharedPreferences("TAG", Context.MODE_PRIVATE).getLong("lastUpdateDateCategory", 0);
                } catch (Exception e) {

                }

                CategoryFirebase.getCategoriesAndObserve(lastUpdateDate, (data) -> {
                    updateCategoryDataInLocalStore(data);
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

    private void updateCategoryDataInLocalStore(List<Category> data) {
        GetCategoriesTatk categoriesTatk = new GetCategoriesTatk();
        categoriesTatk.execute(data);
    }

    class GetCategoriesTatk extends AsyncTask<List<Category>, String, List<Category>>{

        @Override
        protected List<Category> doInBackground(List<Category>[] lists) {
            if (lists.length > 0) {

                SharedPreferences sharedPreferences = GetInShapeApp.getMyContext()
                                                        .getSharedPreferences("TAG", Context.MODE_PRIVATE);

                List<Category> data = lists[0];
                long lastUpdateDate = 0;
                try {
                    lastUpdateDate = sharedPreferences.getLong("lastUpdateDateCategory", 0);
                } catch (Exception ex) {

                }

                if (data != null && data.size() > 0) {
                    long recentUpdate = lastUpdateDate;
                    for (Category category : data) {
                        AppLocalStore.db.categoryDao().insertAll(category);
                        if (category.getLastUpdated() > recentUpdate) {
                            recentUpdate = category.getLastUpdated();
                        }
                    }
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.commit();
                    SharedPreferences.Editor editor1 = sharedPreferences.edit();
                    editor1.putLong("lastUpdateDateCategory", recentUpdate);
                    editor1.commit();
                }
                List<Category> categoryList = AppLocalStore.db.categoryDao().getAll();

                return categoryList;
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Category> categories) {
            super.onPostExecute(categories);
            categoriesLiveData.setValue(categories);
        }
    }
}
