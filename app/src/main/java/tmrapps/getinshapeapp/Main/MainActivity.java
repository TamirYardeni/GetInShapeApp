package tmrapps.getinshapeapp.Main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import tmrapps.getinshapeapp.Main.Exercise.Exercise;
import tmrapps.getinshapeapp.Main.Motivation.MotivationFragment;
import tmrapps.getinshapeapp.Main.PersonalArea.PersonalAreaFragment;
import tmrapps.getinshapeapp.R;
import tmrapps.getinshapeapp.User.AuthActivity;
import tmrapps.getinshapeapp.User.AuthFragment;
import tmrapps.getinshapeapp.User.RoleType;

public class MainActivity extends AppCompatActivity implements PersonalAreaFragment.OnFragmentInteractionListener, Exercise.OnFragmentInteractionListener, MotivationFragment.OnFragmentInteractionListener {

    private ActionBar actionBar;
    private RoleType role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_main);
        role = RoleType.USER;
        setupTabs(role);
    }

    private void setupTabs(RoleType role) {
        actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);

        if (role == RoleType.USER) {
            ActionBar.Tab tab1 = actionBar
                    .newTab()
                    .setText("איזור אישי")
                    // .setIcon(R.drawable.ic_home)
                    .setTabListener(new SupportFragmentTabListener<PersonalAreaFragment>(R.id.mainContent, this,
                            "first", PersonalAreaFragment.class));

            actionBar.addTab(tab1);
            actionBar.selectTab(tab1);

            ActionBar.Tab tab2 = actionBar
                    .newTab()
                    .setText("תרגילים")
                    //.setIcon(R.drawable.ic_mentions)
                    .setTabListener(new SupportFragmentTabListener<Exercise>(R.id.mainContent, this,
                            "second", Exercise.class));
            actionBar.addTab(tab2);

            ActionBar.Tab tab3 = actionBar
                    .newTab()
                    .setText("מוטיבציה")
                    //.setIcon(R.drawable.ic_mentions)
                    .setTabListener(new SupportFragmentTabListener<MotivationFragment>(R.id.mainContent, this,
                            "third", MotivationFragment.class));
            actionBar.addTab(tab3);
        } else if (role == RoleType.ADMIN) {
            ActionBar.Tab tab1 = actionBar
                    .newTab()
                    .setText("תרגילים")
                    //.setIcon(R.drawable.ic_mentions)
                    .setTabListener(new SupportFragmentTabListener<Exercise>(R.id.mainContent, this,
                            "first", Exercise.class));
            actionBar.addTab(tab1);

            ActionBar.Tab tab2 = actionBar
                    .newTab()
                    .setText("מוטיבציה")
                    //.setIcon(R.drawable.ic_mentions)
                    .setTabListener(new SupportFragmentTabListener<MotivationFragment>(R.id.mainContent, this,
                            "second", MotivationFragment.class));
            actionBar.addTab(tab2);
        }

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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /**
     * After connecting with google auth and saving the user to firebase
     * we want to start the main activity of the app.
     * For that we hide the auth fragment and starts PersonalAreaActivity.
     */
    @SuppressLint("RestrictedApi")
    private void updateUI() {
        actionBar.closeOptionsMenu();
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
        finish();
    }
}
