package tmrapps.getinshapeapp.ExerciseList.Model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import tmrapps.getinshapeapp.Category.Model.Category;
import tmrapps.getinshapeapp.Category.Model.CategoryFirebase;

/**
 * Created by tamir on 2/5/2018.
 */

public class ExerciseListFirebase {
    private static ExerciseListFirebase instance = null;

    protected ExerciseListFirebase() {
        // Exists only to defeat instantiation.
    }
    public static ExerciseListFirebase getInstance() {
        if(instance == null) {
            instance = new ExerciseListFirebase();
        }
        return instance;
    }

    public interface OnExerciseListListener {
        void onComplete(List<ExerciseListItem> exerciseListItem);
    }

    public static void getExercisesOfCategoryAndObserve(long lastUpdate, String categoryId, final OnExerciseListListener callback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("exercisesInCategory");
        Query query = ref.orderByChild("lastUpdated").startAt(lastUpdate);
        // This is single because we dont want to update the view of other users
        // When a new exercise was added or update in the firebase by the admin
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<ExerciseListItem> exerciseListItems = new ArrayList<>();

                // Run over all the exercises in categories
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ExerciseListItem exerciseListItem = new ExerciseListItem();
                    List<ExerciseInCategory> exerciseInCategoryList = new LinkedList<>();

                    // Parse one item to ExerciseListItem object
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        if (snap.getKey().equals("categoryId")) {
                            exerciseListItem.setCategoryId(snap.getValue().toString());
                        } else if (snap.getKey().equals("lastUpdated")) {
                            exerciseListItem.setLastUpdateDate((Long) snap.getValue());
                        } else {
                            ExerciseInCategory exerciseInCategory = (snap.getValue(ExerciseInCategory.class));
                            exerciseInCategoryList.add(exerciseInCategory);
                        }
                    }
                    exerciseListItem.setExercisesInCategory(exerciseInCategoryList);
                    exerciseListItems.add(exerciseListItem);
                }

                callback.onComplete(exerciseListItems);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onComplete(null);
            }
        });
    }

    public static void addExerciseListItem(String categoryId, String exerciseName, String exerciseId) {
        /*FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref = firebaseDatabase.getReference("exercisesInCategory").child(categoryId);
        ExerciseListItem exerciseListItem = new ExerciseListItem();
        exerciseListItem.setCategoryId(categoryId);
        ArrayList<ExerciseInCategory> exerciseInCategories = new ArrayList<>();
        ExerciseInCategory exerciseInCategory = new ExerciseInCategory();
        exerciseInCategory.setExerciseName(exerciseName);
        exerciseInCategory.setExerciseName(exerciseId);
        exerciseInCategories.add(exerciseInCategory);
        exerciseListItem.setExerciseItem(exerciseInCategories);
        ref.setValue(exerciseListItem.toMap());*/

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref = firebaseDatabase.getReference("exercisesInCategory").child(categoryId);

        Map<String, Object> exerciseItem = new HashMap<>();
        exerciseItem.put("id", exerciseId);
        exerciseItem.put("name", exerciseName);

        Map<String, Object> childUpdates = new HashMap<>();
        /*childUpdates.put("/exercisesInCategory/" + categoryId +"/categoryId", categoryId);
        childUpdates.put("/exercisesInCategory/" + categoryId +"/lastUpdated", ServerValue.TIMESTAMP);
        childUpdates.put("/exercisesInCategory/" + categoryId +"/" + exerciseId, exerciseItem);*/
        childUpdates.put("categoryId", categoryId);
        childUpdates.put("lastUpdated", ServerValue.TIMESTAMP);
        childUpdates.put(exerciseId, exerciseItem);

        ref.updateChildren(childUpdates);

    }
}
