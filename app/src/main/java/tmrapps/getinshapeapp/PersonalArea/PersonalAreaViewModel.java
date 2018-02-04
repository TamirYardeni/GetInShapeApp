package tmrapps.getinshapeapp.PersonalArea;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import tmrapps.getinshapeapp.PersonalArea.Model.PersonalAreaRepository;
import tmrapps.getinshapeapp.PersonalArea.Model.PersonalInformation;

/**
 * Created by nathan on 01/02/2018.
 */

public class PersonalAreaViewModel extends ViewModel {

    private LiveData<PersonalInformation> info;


    public LiveData<PersonalInformation> getPersonalInformation(String id) {
        if (info == null) info = PersonalAreaRepository.instance.getPersonalInformation(id);
        return info;
    }
}
