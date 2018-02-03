package tmrapps.getinshapeapp.User.Model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

/**
 * Created by tamir on 2/3/2018.
 */

public class UserRepository {
    public static final UserRepository instance = new UserRepository();

    MutableLiveData<User> userLiveData;

    UserRepository() {

    }

    public LiveData<User> getUser(String userId) {
        synchronized (this) {
            if (this.userLiveData == null) {
                 this.userLiveData = new MutableLiveData<User>();
                UserFirebase.getUserAndObserve(userId, (data) -> {
                    if (data != null) {
                        if (data.getId() != null) {
                            this.userLiveData.setValue(data);
                        } else {
                            UserFirebase.createUser(userId, (user) -> {
                                if (user != null) {
                                    this.userLiveData.setValue(user);
                                }
                            });
                        }
                    }
                });
            }
        }

        return this.userLiveData;
    }

}
