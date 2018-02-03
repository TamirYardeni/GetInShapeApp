package tmrapps.getinshapeapp.PersonalArea;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import tmrapps.getinshapeapp.R;

public class PersonalAreaActivity extends AppCompatActivity implements PersonalAreaFragment.OnFragmentInteractionListener {

    PersonalAreaFragment personalAreaFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_area);

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();

        this.personalAreaFrag = new PersonalAreaFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", "1234");
        this.personalAreaFrag.setArguments(bundle);
        fragmentTransaction.add(R.id.personalAreaContent, this.personalAreaFrag);
        fragmentTransaction.commit();
    }
}
