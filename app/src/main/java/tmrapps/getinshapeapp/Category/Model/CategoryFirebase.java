package tmrapps.getinshapeapp.Category.Model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

//import tmrapps.getinshapeapp.Exercise.Model.FirebaseExercise;

/**
 * Created by tamir on 2/4/2018.
 */

public class CategoryFirebase {
    private static CategoryFirebase instance = null;

    protected CategoryFirebase() {
        // Exists only to defeat instantiation.
    }
    public static CategoryFirebase getInstance() {
        if(instance == null) {
            instance = new CategoryFirebase();
        }
        return instance;
    }

    public interface OnCategoryListener {
        void onComplete(List<Category> categories);
    }

    public static void getCategoriesAndObserve(final OnCategoryListener listener) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref = firebaseDatabase.getReference("categories");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Category> list = new LinkedList<>();

                for(DataSnapshot snap: dataSnapshot.getChildren()) {
                    Map<String, Object> value = (Map<String, Object>) snap.getValue();
                    String id = (String) value.get("id");
                    String name = (String) value.get("name");

                    Category newCat = new Category(name, id);
                    list.add(newCat);
                }

                listener.onComplete(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onComplete(null);
            }
        });
    }

    public static void getCategoriesAndObserve(long lastUpdate, final OnCategoryListener callback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("categories");
        Query query = ref.orderByChild("lastUpdate").startAt(lastUpdate);
        ValueEventListener listener = query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Category> list = new LinkedList<>();
                for (DataSnapshot snap: dataSnapshot.getChildren()) {
                    Category category = snap.getValue(Category.class);
                    list.add(category);
                }
                callback.onComplete(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onComplete(null);
            }
        });
    }

    public static void addCategory(String categoryName) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        String key = firebaseDatabase.getReference().push().getKey();
        DatabaseReference ref = firebaseDatabase.getReference("categories").child(key);
        Category category = new Category(categoryName, key);
        category.setId(key);
        ref.setValue(category);
    }
}
