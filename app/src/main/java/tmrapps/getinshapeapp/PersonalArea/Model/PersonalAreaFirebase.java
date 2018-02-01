package tmrapps.getinshapeapp.PersonalArea.Model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
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

    public static void getPersonalInformationByUserId(String userId, final GetPersonalInformationByIdListener listener){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("personal_info").child(userId);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String,Object> value = (Map<String, Object>) dataSnapshot.getValue();
                PersonalInformation info  = new PersonalInformation();
                info.setUserId((String) value.get("userId"));
                info.setCurrentWeight((double) value.get("currentWeight"));
                info.setDateEndOfTrain((Date) value.get("dateEndOfTrain"));
                info.setHourTrain((int) value.get("hourTrain"));
                info.setMinuteTrain((int) value.get("minuteTrain"));
                info.setWeightToAchieve((double) value.get("weightToAchieve"));
                info.setDayOfWeek((List<String>) value.get("dayOfWeek"));

                listener.onComplete(info);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onComplete(null);
            }
        });
    }

    public static void savePersonalInformation(PersonalInformation info){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("personal_info").child(info.getUserId());

        Map<String,Object> value = new HashMap<>();
        value.put("userId", info.getUserId());
        value.put("currentWeight", info.getCurrentWeight());
        value.put("dateEndOfTrain", info.getDateEndOfTrain());
        value.put("hourTrain", info.getHourTrain());
        value.put("minuteTrain", info.getMinuteTrain());
        value.put("weightToAchieve", info.getWeightToAchieve());
        value.put("dayOfWeek ", info.getDayOfWeekAppend());
        myRef.setValue(value);
    }
}
