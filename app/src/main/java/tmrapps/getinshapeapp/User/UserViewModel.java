package tmrapps.getinshapeapp.User;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

import tmrapps.getinshapeapp.User.Model.User;
import tmrapps.getinshapeapp.User.Model.UserRepository;

/**
 * Created by tamir on 2/3/2018.
 * This is the viewModel of the user -
 * the main user fragment get the user data from it
 */
public class UserViewModel extends ViewModel {

    private LiveData<User> user;

    public UserViewModel() {
        String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.user = UserRepository.instance.getUser(userUid);
    }

    public LiveData<User> getUser() {
        return user;
    }
}
