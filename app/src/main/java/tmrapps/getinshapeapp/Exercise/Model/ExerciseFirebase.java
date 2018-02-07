package tmrapps.getinshapeapp.Exercise.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
        void onComplete(List<Exercise> exercise);
        void onComplete();
    }

    public interface OnGetStoredImageListener {
        void onComplete(Bitmap bitmap);
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

    public static void addExerciseUrl(String exerciseId, String url) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref = firebaseDatabase.getReference("exercises/" + exerciseId).child("url");
        ref.setValue(url);
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

    public static void getExerciseImage(String url, final ExerciseReposirory.OnGetImageListener listener) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference httpsReference = storage.getReferenceFromUrl(url);
        final long ONE_MEGABYTE = 1024 * 1024 * 5;
        httpsReference.getBytes(3* ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap image = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                listener.complete(image);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception exception) {
                listener.fail();
            }
        });
    }
}
