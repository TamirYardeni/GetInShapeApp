package tmrapps.getinshapeapp.User;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import tmrapps.getinshapeapp.User.Model.User;
import tmrapps.getinshapeapp.User.Model.UserRepository;

/**
 * Created by tamir on 2/3/2018.
 */

public class UserViewModel extends ViewModel {

    private LiveData<User> user;

    public UserViewModel(String userId) {
        this.user = UserRepository.instance.getUser(userId);
    }

    public LiveData<User> getUser(String userId) {
        return user;
    }
}
