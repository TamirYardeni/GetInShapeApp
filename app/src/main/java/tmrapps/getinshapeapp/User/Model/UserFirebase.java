package tmrapps.getinshapeapp.User.Model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

/**
 * Created by tamir on 2/3/2018.
 * With this class we communicate with the firebase
 * in all that connected to the user
 */

public class UserFirebase {
    private static UserFirebase instance = null;

    protected UserFirebase() {
        // Exists only to defeat instantiation.
    }

    public static UserFirebase getInstance() {
        if(instance == null) {
            instance = new UserFirebase();
        }
        return instance;
    }

    /**
     *
     * @param userId
     * @param listener
     */
    public static void createUser(String userId, final GetUserListener listener) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref = firebaseDatabase.getReference("users").child(userId);
        User user = new User(userId, 0);
        ref.setValue(user);
        listener.onComplete(user);
    }

    public interface GetUserListener {
        void onComplete(User user);
    }

    public static void getUserAndObserve(String userId, final GetUserListener listener) {
        DatabaseReference mUserReference = FirebaseDatabase.getInstance().getReference("users").child(userId);
        mUserReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> value = (Map<String, Object>) dataSnapshot.getValue();
                User user = new User();

                if (value != null) {
                    user.setId((String) value.get("id"));
                    user.setRoleType(((Long) value.get("roleType")).intValue());
                }

                listener.onComplete(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onComplete(null);
            }
        });
    }
}
