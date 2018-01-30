package tmrapps.getinshapeapp.Motivation;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import tmrapps.getinshapeapp.Main.MainAdminFragment;
import tmrapps.getinshapeapp.Main.MainUserFragment;
import tmrapps.getinshapeapp.R;
import tmrapps.getinshapeapp.User.RoleType;

public class MotivationActivity extends AppCompatActivity implements MotivationFragment.OnFragmentInteractionListener, MotivationAdminFragment.OnFragmentInteractionListener {

    private RoleType role;

    private Fragment motivationFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motivation);

        this.role = RoleType.valueOf(getIntent().getStringExtra("ROLE_TYPE"));

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();

        if (this.role == RoleType.USER) {
            motivationFrag = new MotivationFragment();
        } else if (role == RoleType.ADMIN) {
            motivationFrag = new MotivationFragment();
        }

        fragmentTransaction.add(R.id.motivationContent, motivationFrag);
        fragmentTransaction.commit();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
