package tmrapps.getinshapeapp.PersonalArea.Model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nathan on 01/02/2018.
 */

public class PersonalAreaFirebase {

    protected PersonalAreaFirebase() {

    }


    public interface GetPersonalInformationByIdListener{
        void onComplete(PersonalInformation personalInformation);
    }

    public static void getPersonalInformationByUserId(String userId, long lastUpdate, final GetPersonalInformationByIdListener listener){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = database.getReference("personal_info/" + userId).child(userId);
        DatabaseReference myRef = database.getReference("personal_info").child(userId);
        //Query query = myRef.orderByChild("lastUpdateDate").startAt(lastUpdate);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                PersonalInformation info  = new PersonalInformation();
                Map<String,Object> value = (Map<String, Object>) dataSnapshot.getValue();

                if (value != null) {
                    info.setUserId((String) value.get("userId"));
                    info.setCurrentWeight((Long)value.get("currentWeight"));
                    info.setDateEndOfTrain(value.get("dateEndOfTrain").toString());
                    info.setHourTrain(((Long)value.get("hourTrain")).intValue());
                    info.setMinuteTrain(((Long)value.get("minuteTrain")).intValue());
                    info.setWeightToAchieve((Long)value.get("weightToAchieve"));
                    info.setDayOfWeek(value.get("dayOfWeek").toString());
                }
                else {
                    info = null;
                }

                listener.onComplete(info);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onComplete(null);
            }
        });
    }

    public interface SavePersonalInformationListener{
        void onComplete(PersonalInformation personalInformation);
    }

    public static void savePersonalInformation(PersonalInformation info, SavePersonalInformationListener listener){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("personal_info").child(info.getUserId());

        Map<String,Object> value = new HashMap<>();
        value.put("userId", info.getUserId());
        value.put("currentWeight", info.getCurrentWeight());
        value.put("dateEndOfTrain", info.getDateEndOfTrain());
        value.put("hourTrain", info.getHourTrain());
        value.put("minuteTrain", info.getMinuteTrain());
        value.put("weightToAchieve", info.getWeightToAchieve());
        value.put("dayOfWeek", info.getDayOfWeekAppend());
        myRef.setValue(value);

        listener.onComplete(info);
    }
}
