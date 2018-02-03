package tmrapps.getinshapeapp.PersonalArea.Model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import tmrapps.getinshapeapp.Main.AppLocalStore;



/**
 * Created by nathan on 31/01/2018.
 */

public class PersonalAreaRepository {

    public final static PersonalAreaRepository instance = new PersonalAreaRepository();

    private PersonalAreaRepository(){

    }

    MutableLiveData<PersonalInformation> personalInfoliveData;

    public LiveData<PersonalInformation> getPersonalInformation(String userId){

        synchronized (this) {
             if (personalInfoliveData == null) {
                 personalInfoliveData = new MutableLiveData<>();
                 personalInfoliveData.setValue(AppLocalStore.db.personalInformationDAO().getPersonalInformation(userId));
                 if (personalInfoliveData.getValue() == null) {
                     PersonalAreaFirebase.getPersonalInformationByUserId(userId, (personalInformation) -> {
                         if (personalInformation != null)
                             personalInfoliveData.setValue(personalInformation);
                     });
                 }
             }

             return personalInfoliveData;
         }
    }

    public void savePersonalInformation(PersonalInformation personalInformation){
        PersonalAreaFirebase.savePersonalInformation(personalInformation);
        AppLocalStore.db.personalInformationDAO().savePersonalInformation(personalInformation);
    }

}

