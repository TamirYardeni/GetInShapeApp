package tmrapps.getinshapeapp.Exercise.Model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

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
        void onComplete(Exercise exercise);
    }

    public static void getExerciseByIdAndObserve(long lastUpdate, String exerciseId, final OnExerciseListener callback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("exercises").child(exerciseId);
        Query query = ref.orderByChild("lastUpdateDate").startAt(lastUpdate);
        // This is single because we dont want to update the view of other users
        // When a new exercise was added or update in the firebase by the admin
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Exercise exercise = dataSnapshot.getValue(Exercise.class);
                callback.onComplete(exercise);
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
        callback.onComplete(exercise);
    }
}
