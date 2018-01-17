package tmrapps.getinshapeapp.Main;

import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import tmrapps.getinshapeapp.Main.Exercise.Exercise;
import tmrapps.getinshapeapp.Main.Motivation.MotivationFragment;
import tmrapps.getinshapeapp.Main.PersonalArea.PersonalAreaFragment;
import tmrapps.getinshapeapp.R;

public class MainActivity extends AppCompatActivity implements PersonalAreaFragment.OnFragmentInteractionListener, Exercise.OnFragmentInteractionListener, MotivationFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_main);
        setupTabs();
    }

    private void setupTabs() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);

        ActionBar.Tab tab1 = actionBar
                .newTab()
                .setText("איזור אישי")
               // .setIcon(R.drawable.ic_home)
                .setTabListener(new SupportFragmentTabListener<PersonalAreaFragment>(R.id.flContainer, this,
                        "first", PersonalAreaFragment.class));

        actionBar.addTab(tab1);
        actionBar.selectTab(tab1);

        ActionBar.Tab tab2 = actionBar
                .newTab()
                .setText("תרגילים")
                //.setIcon(R.drawable.ic_mentions)
                .setTabListener(new SupportFragmentTabListener<Exercise>(R.id.flContainer, this,
                        "second", Exercise.class));
        actionBar.addTab(tab2);

        ActionBar.Tab tab3 = actionBar
                .newTab()
                .setText("מוטיבציה")
                //.setIcon(R.drawable.ic_mentions)
                .setTabListener(new SupportFragmentTabListener<MotivationFragment>(R.id.flContainer, this,
                        "third", MotivationFragment.class));
        actionBar.addTab(tab3);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
