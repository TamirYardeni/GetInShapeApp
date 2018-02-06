package tmrapps.getinshapeapp.Exercise.Model;

import android.graphics.Bitmap;
import android.net.Uri;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
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

    public static void saveImage(Bitmap imageBmp, String name, final ExerciseReposirory.OnSaveImageListener listener){
        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference imagesRef = storage.getReference().child("images").child(name);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception exception) {
                listener.fail();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                listener.complete(downloadUrl.toString());
            }
        });
    }
}
