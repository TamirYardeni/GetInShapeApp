package tmrapps.getinshapeapp.Main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.firebase.auth.FirebaseAuth;

import tmrapps.getinshapeapp.Exercise.ExerciseActivity;
import tmrapps.getinshapeapp.Motivation.MotivationActivity;
import tmrapps.getinshapeapp.PersonalArea.PersonalAreaActivity;
import tmrapps.getinshapeapp.R;
import tmrapps.getinshapeapp.User.AuthActivity;
import tmrapps.getinshapeapp.User.Model.User;
import tmrapps.getinshapeapp.User.RoleType;

public class MainActivity extends AppCompatActivity implements MainUserFragment.OnFragmentInteractionListener {

    private User user;
    private Fragment mainFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_main);
        openMainFrag();
    }

    /**
     * Open the suitable fragment according to the permissions of the user (regular user or admin)
     */
    private void openMainFrag() {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();

        mainFrag = new MainUserFragment();

        fragmentTransaction.add(R.id.mainContent, mainFrag);
        fragmentTransaction.commit();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.log_out:
                logOut();
                return true;
            default:
                return true;
        }
    }

    private void logOut() {
        FirebaseAuth.getInstance().signOut();
        // Google sign out
        /*Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<AsyncTask.Status>() {
                    @Override
                    public void onResult(@NonNull AsyncTask.Status status) {
                        updateUI();
                    }
                });*/
    }

    /**
     * After doing logout we go back to auth activity
     */
    @SuppressLint("RestrictedApi")
    private void updateUI() {
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onShowPersonalArea() {
        moveToNewActivity(RoleType.USER, PersonalAreaActivity.class);
    }

    @Override
    public void onShowExercise() {
        moveToNewActivity(RoleType.USER, ExerciseActivity.class);
    }

    @Override
    public void onShowMotivation() {
        moveToNewActivity(RoleType.USER, MotivationActivity.class);
    }

    private void moveToNewActivity(RoleType permissions, Class activityClass){
        if (this.user.roleType == permissions.ordinal()) {
            android.support.v4.app.FragmentTransaction tran = getSupportFragmentManager().beginTransaction();
            tran.hide(mainFrag);
            tran.commit();

            Intent intent = new Intent(this, activityClass);
            intent.putExtra("ROLE_TYPE", RoleType.values()[this.user.roleType]);
            intent.putExtra("USER_ID", this.user.getId());
            startActivity(intent);
        }
    }

    @Override
    public void onShowExerciseAdmin() {
        moveToNewActivity(RoleType.ADMIN, ExerciseActivity.class);
    }

    @Override
    public void onShowMotivationAdmin() {
        moveToNewActivity(RoleType.ADMIN, MotivationActivity.class);
    }

    @Override
    public void onHasRoleType(User user) {
        this.user = user;
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.openMainFrag();
    }

    //    @Override
//    public void onShowCategory(String categoryId) {
//       /* android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
//        android.support.v4.app.FragmentTransaction fragmentTransaction =
//                fragmentManager.beginTransaction();
//
//        fragmentTransaction.remove(exerciseAdminFragment);
//        CategoryFragment fragment = new CategoryFragment();
//        fragmentTransaction.add(R.id.mainContent, fragment);
//        fragmentTransaction.commit();*/
//    }
}
