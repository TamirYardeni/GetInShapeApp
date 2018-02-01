package tmrapps.getinshapeapp.PersonalArea.Model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;


/**
 * Created by nathan on 31/01/2018.
 */

public class PersonalAreaRepository {

    public final static PersonalAreaRepository instace = new PersonalAreaRepository();

    private PersonalAreaRepository(){
        
    }

    MutableLiveData<PersonalInformation> personalInfoliveData;

    public LiveData<PersonalInformation> getPersonalInformation(String userId){
         synchronized (this) {
             if (personalInfoliveData == null) {
                 personalInfoliveData = new MutableLiveData<>();
                 PersonalAreaFirebase.getPersonalInformationByUserId(userId, new PersonalAreaFirebase.GetPersonalInformationByIdListener() {
                     @Override
                     public void onComplete(PersonalInformation personalInformation) {
                         if (personalInformation != null) personalInfoliveData.setValue(personalInformation);
                     }
                 });
             }

             return personalInfoliveData;
         }
    }
}

