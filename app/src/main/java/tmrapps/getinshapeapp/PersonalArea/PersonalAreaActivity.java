package tmrapps.getinshapeapp.PersonalArea;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import tmrapps.getinshapeapp.R;

public class PersonalAreaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_area);
        setupTabs();
    }

    private void setupTabs() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);

        ActionBar.Tab tab1 = actionBar
                .newTab()
                .setText("First")
                //.setIcon(R.drawable.ic_home)
                .setTabListener(
                        new FragmentTabListener<HomePage>(R.id.tabLayout, this, "first",
                                HomePage.class));

        actionBar.addTab(tab1);
        actionBar.selectTab(tab1);

        ActionBar.Tab tab2 = actionBar
                .newTab()
                .setText("Second")
                //.setIcon(R.drawable.ic_mentions)
                .setTabListener(
                        new FragmentTabListener<HomePage>(R.id.tabLayout, this, "second",
                                HomePage.class));

        actionBar.addTab(tab2);
    }
}
