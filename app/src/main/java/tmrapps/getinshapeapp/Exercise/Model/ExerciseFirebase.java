package tmrapps.getinshapeapp.Exercise.Model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import tmrapps.getinshapeapp.ExerciseList.Model.ExerciseInCategory;

/**
 * Created by tamir on 2/5/2018.
 */

public class ExerciseFirebase {
    private static ExerciseFirebase instance = null;

    protected ExerciseFirebase() {
        // Exists only to defeat instantiation.
    }
    public static ExerciseFirebase getInstance() {
        if(instance == null) {
            instance = new ExerciseFirebase();
        }
        return instance;
    }

    public interface OnExerciseListener {
        void onComplete(List<Exercise> exercise);
        void onComplete();
    }

    public static void getAllExerciseAndObserve(long lastUpdate, String categoryId, final OnExerciseListener callback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("exercises");
        Query query = ref.orderByChild("lastUpdateDate").startAt(lastUpdate);
        ValueEventListener listener = query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Exercise> exercises = new ArrayList<>();
                for (DataSnapshot snap: dataSnapshot.getChildren()) {
                    Exercise exercise = snap.getValue(Exercise.class);
                    exercises.add(exercise);
                }

                callback.onComplete(exercises);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onComplete(null);
            }
        });
    }

    public static void getExerciseByIdAndObserve(long lastUpdate, String exerciseId, final OnExerciseListener callback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("exercises");
        Query query = ref.orderByChild("lastUpdateDate").startAt(lastUpdate);
        ValueEventListener listener = query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Exercise> exercises = new ArrayList<>();
                for (DataSnapshot snap: dataSnapshot.getChildren()) {
                    Exercise exercise = snap.getValue(Exercise.class);
                    exercises.add(exercise);
                }

                callback.onComplete(exercises);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onComplete(null);
            }
        });
    }

    public static void addExercise(Exercise exercise, final  OnExerciseListener callback) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        String key = firebaseDatabase.getReference().push().getKey();
        DatabaseReference ref = firebaseDatabase.getReference("exercises").child(key);
        exercise.setId(key);
        ref.setValue(exercise.toMap());
        List<Exercise> exerciseList = new ArrayList<>();
        exerciseList.add(exercise);
        callback.onComplete();
    }
}
