package tmrapps.getinshapeapp.Main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;

import com.google.firebase.auth.FirebaseAuth;

import tmrapps.getinshapeapp.Exercise.ExerciseActivity;
import tmrapps.getinshapeapp.Motivation.MotivationActivity;
import tmrapps.getinshapeapp.PersonalArea.PersonalAreaActivity;
import tmrapps.getinshapeapp.R;
import tmrapps.getinshapeapp.User.AuthActivity;
import tmrapps.getinshapeapp.User.RoleType;

public class MainActivity extends AppCompatActivity implements MainAdminFragment.OnFragmentInteractionListener, MainUserFragment.OnFragmentInteractionListener {
    
    private RoleType role;
    private Fragment mainFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_main);
        role = RoleType.ADMIN;
        openMainFrag(role);
    }

    /**
     * Open the suitable fragment according to the permissions of the user (regular user or admin)
     * @param role
     */
    private void openMainFrag(RoleType role) {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();

        if (role == RoleType.USER) {
            mainFrag = new MainUserFragment();
        } else if (role == RoleType.ADMIN) {
            mainFrag = new MainAdminFragment();
        }

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
        updateUI();
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
        if (this.role == permissions) {
            android.support.v4.app.FragmentTransaction tran = getSupportFragmentManager().beginTransaction();
            tran.hide(mainFrag);
            tran.commit();

            Intent intent = new Intent(this, activityClass);
            intent.putExtra("ROLE_TYPE", this.role.name());
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
    protected void onResume() {
        super.onResume();
        this.openMainFrag(this.role);
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
